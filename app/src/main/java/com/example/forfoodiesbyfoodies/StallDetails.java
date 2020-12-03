package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StallDetails extends AppCompatActivity {

//Declaration
    TextView name, loc, desc, textAdded, added;
    Button view, add ;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_details);

 //Link the views with their xml counterparts
        name = findViewById(R.id.ds_name);
        loc = findViewById(R.id.ds_loc);
        desc = findViewById(R.id.ds_desc);
        textAdded = findViewById(R.id.ds_text_added);
        added = findViewById(R.id.ds_added);
        view = findViewById(R.id.ds_btn_view);
        add = findViewById(R.id.ds_btn_add);
        image = findViewById(R.id.ds_image);

//Receiving the current logged in user's email and the whole object
// that is chosen from the previous activity
        String email = getIntent().getStringExtra("EM");
        Stall stall = getIntent().getParcelableExtra("STALL");

 //Populate the views
        Picasso.get().load(stall.getUrlS()).fit().into(image);
        name.setText(stall.getNameS());
        textAdded.setText("Added By:");
        loc.setText(stall.getLocationS());
        desc.setText(stall.getDescS());
        added.setText(stall.getAddedByS());

 //When button View is clicked
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Open a new activity and pass the current user's email and the name of the current Stall
                Intent i = new Intent(StallDetails.this, ViewReviewStall.class);
                i.putExtra("EM", email);
                i.putExtra("NAME", stall.getNameS());
                startActivity(i);
            }
        });
//When button Add is clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //Open a new activity and pass the current user's email and the name of the current Stall
                Intent j = new Intent(StallDetails.this, AddReviewStall.class);
                j.putExtra("EM", email);
                j.putExtra("NAME", stall.getNameS());
                startActivity(j);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(StallDetails.this, Login.class);
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