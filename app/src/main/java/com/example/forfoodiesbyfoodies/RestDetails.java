package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RestDetails extends AppCompatActivity {

//Declaration
    Calendar cal;
    TextView name, loc, desc, textAdded, added;
    Button view, add, book;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_details);

//Object of the calendar
        cal = Calendar.getInstance();
//Receiving the current logged in user's email
        String email = getIntent().getStringExtra("EM");

        add = findViewById(R.id.dr_btn_add);
//If the current user is a Critic, show button Add Restaurant Review
        if (email.compareTo("vvv@gmail.com")==0)
        {
            add.setVisibility(View.VISIBLE);
        }
// If it is not a Critic, do not show that button
        else
        {
            add.setVisibility(View.INVISIBLE);
        }

 //Link the views with their xml counterparts
        name = findViewById(R.id.dr_name);
        loc = findViewById(R.id.dr_loc);
        desc = findViewById(R.id.dr_desc);
        textAdded = findViewById(R.id.dr_text_added);
        added = findViewById(R.id.dr_added);
        view = findViewById(R.id.dr_btn_view);
        book = findViewById(R.id.dr_btn_book);
        image = findViewById(R.id.dr_image);

  //Creates object for selecting the date
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//Setting the Calendar
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
               // String myFormat = "MM/dd/yy";
               // SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
  //Open a new activity, passing that chosen date
                Intent t = new Intent(RestDetails.this, Reservation.class);
                t.putExtra("YEAR", year);
                t.putExtra("MONTH", month);
                t.putExtra("DAY", dayOfMonth);
                startActivity(t);

            }
        };
//When button Book is clicked, a Calendar pop-up window is shown
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RestDetails.this, date, cal.get(java.util.Calendar.YEAR),
                        cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//Receiving the whole object that is chosen from the previous activity
        Rest rest = getIntent().getParcelableExtra("REST");

 //Populate the views
        Picasso.get().load(rest.getUrlR()).fit().into(image);
        name.setText(rest.getNameR());
        textAdded.setText("Added By:");
        loc.setText(rest.getLocationR());
        desc.setText(rest.getDescR());
        added.setText(rest.getAddedByR());

//When button Add Review is clicked, open a new activity and
// pass the current user's email and the name of the current Restaurant
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(RestDetails.this, AddReviewRest.class);
                a.putExtra("EM", email);
                a.putExtra("NAME", rest.getNameR());
                startActivity(a);
            }
        });
 //When button View Restaurant Review is clicked, open a new activity and
 //pass the current user's email and the name of the current Restaurant
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(RestDetails.this, ViewReviewRest.class);
                b.putExtra("EM", email);
                b.putExtra("NAME", rest.getNameR());
                startActivity(b);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(RestDetails.this, Login.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}