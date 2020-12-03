package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewsRestDetails extends AppCompatActivity {

//Declaration
    TextView name, count, text, author;
    RatingBar bar;
    float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_rest_details);

        ReviewsRest rest = getIntent().getParcelableExtra("REVR");
        String email = getIntent().getStringExtra("EM");

//Link the views with their xml counterparts
        name = findViewById(R.id.rest_d_name);
        count = findViewById(R.id.rest_d_count);
        text = findViewById(R.id.rest_d_text);
        author = findViewById(R.id.rest_d_author);
        bar = findViewById(R.id.rest_d_bar);

 //Populate the views
        name.setText(rest.getNameR());
        text.setText(rest.getReviewR());
        author.setText("Author: " + rest.getAuthorR());

 //Setting the RatingBar
        rating = rest.getRatingR();
        bar.setRating(rating);

 //Setting the Text view accordingly what rate has been given (stars)
        if (rating<=1 && rating>0)
            count.setText("Bad: "+rating+" out of 5");
        else if (rating<=2 && rating>1)
            count.setText("Not too bad: "+rating+" out of 5");
        else if (rating<=3 && rating>2)
            count.setText("Good: "+rating+" out of 5");
        else if (rating<=4 && rating>3)
            count.setText("Very Good: "+rating+" out of 5");
        else if (rating<=5 && rating>4)
            count.setText("Excellent: "+rating+" out of 5");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(ReviewsRestDetails.this, Login.class);
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