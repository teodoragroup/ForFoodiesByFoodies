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

public class AddReviewStall extends AppCompatActivity {
//Declaration
    TextView nameS, countS, addedBy;
    EditText reviewS;
    Button submit;
    RatingBar barS;
    float ratingS;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_stall);

  //Receiving the current logged in user and current restaurant we want to add review for
        String email = getIntent().getStringExtra("EM");
        String name = getIntent().getStringExtra("NAME");

//Link views to their xml counterparts
        nameS = findViewById(R.id.reviewS_nameS);
        countS = findViewById(R.id.reviewS_count);
        addedBy = findViewById(R.id.reviewS_By);
        reviewS = findViewById(R.id.reviewS_text);
        submit = findViewById(R.id.reviewS_submit);
        barS = findViewById(R.id.reviewS_bar);

        nameS.setText(name);
        reviewS.getText().toString();
        addedBy.setText("Author: "+email);

 //ref object to Review_Stall node
        dbref = FirebaseDatabase.getInstance().getReference("Reviews_Stall");

   //Listener attached to the rating bar
        barS.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingS = ratingBar.getRating();

                if (ratingS<=1 && ratingS>0)
                    countS.setText("Bad: "+ratingS+" out of 5");
                else if (ratingS<=2 && ratingS>1)
                    countS.setText("Not too bad: "+ratingS+" out of 5");
                else if (ratingS<=3 && ratingS>2)
                    countS.setText("Good: "+ratingS+" out of 5");
                else if (ratingS<=4 && ratingS>3)
                    countS.setText("Very Good: "+ratingS+" out of 5");
                else if (ratingS<=5 && ratingS>4)
                    countS.setText("Excellent: "+ratingS+" out of 5");
            }
        });

 //Functions executing when button submit is clicked
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = dbref.push().getKey();
  //f any rating is givven
                if (ratingS!=0 && reviewS.getText().length()>0)
                {

 //New object from ReviewRest class is created and the
                    //// record is saved into DN with automatically generated id
                    ReviewsStall revS = new ReviewsStall(name, reviewS.getText().toString(), email,
                            ratingS);
                    dbref.child(id).setValue(revS);

                    Toast.makeText(AddReviewStall.this,
                            "Your Review Has Been Posted!", Toast.LENGTH_LONG).show();

 //Navigating to other activity, carrying out the current user and restaurant
                    Intent t = new Intent(AddReviewStall.this, ViewReviewStall.class);
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
            Intent t = new Intent(AddReviewStall.this, Login.class);
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