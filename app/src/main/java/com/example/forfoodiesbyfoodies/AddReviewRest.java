package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReviewRest extends AppCompatActivity {
//Declaration
    TextView nameR, countR, addedBy;
    EditText reviewR;
    Button submit;
    RatingBar barR;
    float ratingR;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_rest);
//Receiving the current logged in user and current restaurant we want to add review for
        String email = getIntent().getStringExtra("EM");
        String name = getIntent().getStringExtra("NAME");

//Link views to their xml counterparts
        nameR = findViewById(R.id.reviewR_name);
        countR = findViewById(R.id.reviewR_count);
        addedBy = findViewById(R.id.reviewR_By);
        reviewR = findViewById(R.id.reviewR_text);
        submit = findViewById(R.id.reviewR_submit);
        barR = findViewById(R.id.reviewR_bar);

        nameR.setText(name);
        reviewR.getText().toString();
        addedBy.setText("Author: "+email);

//ref object to Review_Rest node
        dbref = FirebaseDatabase.getInstance().getReference("Reviews_Rest");

  //Listener attached to the rating bar
        barR.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingR = ratingBar.getRating();

                if (ratingR<=1 && ratingR>0)
                    countR.setText("Bad: "+ratingR+" out of 5");
                else if (ratingR<=2 && ratingR>1)
                    countR.setText("Not too bad: "+ratingR+" out of 5");
                else if (ratingR<=3 && ratingR>2)
                    countR.setText("Good: "+ratingR+" out of 5");
                else if (ratingR<=4 && ratingR>3)
                    countR.setText("Very Good: "+ratingR+" out of 5");
                else if (ratingR<=5 && ratingR>4)
                    countR.setText("Excellent: "+ratingR+" out of 5");
            }
        });
//Functions executing when button submit is clicked
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//If any rating is givven
                String id = dbref.push().getKey();
                if (ratingR!=0 && reviewR.getText().length()>0)
                {
//New object from ReviewRest class is created and the
// record is saved into DN with automatically generated id
                    ReviewsRest rest = new ReviewsRest(name, reviewR.getText().toString(), email,
                            ratingR);
                    dbref.child(id).setValue(rest);

                    Toast.makeText(AddReviewRest.this,
                            "Your Review Has Been Posted!", Toast.LENGTH_LONG).show();
  //Navigating to other activity, carrying out the current user and restaurant
                    Intent t = new Intent(AddReviewRest.this, ViewReviewRest.class);
                    t.putExtra("EM", email);
                    t.putExtra("NAME", name);
                    startActivity(t);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(AddReviewRest.this, Login.class);
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