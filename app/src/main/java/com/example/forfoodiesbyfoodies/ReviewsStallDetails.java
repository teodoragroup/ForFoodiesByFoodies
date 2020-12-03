package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReviewsStallDetails extends AppCompatActivity {

 //Declaration
    TextView name, count, text, author;
    RatingBar bar;
    float rating;
    Button delete;
    DatabaseReference dbref;
    Query q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_stall_details);

//Receiving the current logged in user's email and the whole object
//that is chosen from the previous activity
        ReviewsStall revs = getIntent().getParcelableExtra("REVS");
        String email = getIntent().getStringExtra("EM");

//Ref object to Firebase
        dbref = FirebaseDatabase.getInstance().getReference();

//Query object to Reviews_Stall node, where the review is only the currently chosen one
        q = dbref.child("Reviews_Stall").orderByChild("reviewS").equalTo(revs.getReviewS());

//Attach listener to the query for reading the data from Firebase specific child
        q.addListenerForSingleValueEvent(listener);

//Link the views with their xml counterparts
        delete = findViewById(R.id.reviewS_d_delete);
        name = findViewById(R.id.stall_d_name);
        count = findViewById(R.id.stall_d_count);
        text = findViewById(R.id.stall_d_text);
        author = findViewById(R.id.stall_d_author);
        bar = findViewById(R.id.stall_d_bar);

//Populate the views
        name.setText(revs.getNameS());
        text.setText(revs.getReviewS());
        author.setText("Author: " + revs.getAuthorS());

//Setting the RatingBar values
        rating = revs.getRatingS();
        bar.setRating(rating);

 //Setting the text view accordingly what rate has been given (stars)
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

 //If the author of the chosen review is the current logged in user,
 //or the current user is an Admin, make button Delete Review visible
        if ((revs.getAuthorS().compareTo(email)==0) || (email.compareTo("ttt@gmail.com")==0))
        {
            delete.setVisibility(View.VISIBLE);
        }
//If it is not an Admin or the review does not belong to the current user,
// do not show the button
        else
        {
            delete.setVisibility(View.INVISIBLE);
        }
    }
//Creates listener object
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss:snapshot.getChildren())
            {
//Get the primary key of the node which the current review is at
                String key = dss.getKey();

  //When button Delete Review is clicked, delete that specific node
   //of the current review and show message
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbref.child("Reviews_Stall").child(key).removeValue();
                        Toast.makeText(ReviewsStallDetails.this, "Your review had been deleted!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(ReviewsStallDetails.this, Login.class);
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