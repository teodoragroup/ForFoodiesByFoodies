package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewReviewStall extends AppCompatActivity implements ReviewStallAdapter.ReviewStallHolder.OnCardClickListener{

 //Declaration
    RecyclerView rv;
    ArrayList<ReviewsStall> list = new ArrayList<>();
    DatabaseReference dbref;
    ReviewStallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review_stall);

//Receiving the current logged in user's email and the name of the Restaurant chosen
//that we want to view the reviews for, from the previous activity
        String email = getIntent().getStringExtra("EM");
        String name = getIntent().getStringExtra("NAME");

 //Settings for the Recycler View's layout
        rv = findViewById(R.id.reviewS_rv);
        rv.setLayoutManager(new LinearLayoutManager(ViewReviewStall.this,
                LinearLayoutManager.VERTICAL, false));
//Create ref object to Reviews_Stall node and attach a listener for reading from Firebase
        dbref = FirebaseDatabase.getInstance().getReference("Reviews_Stall");
        dbref.addListenerForSingleValueEvent(listener);
    }
//Create Listener object
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss:snapshot.getChildren())
            {

                String name = getIntent().getStringExtra("NAME");
                ReviewsStall revStall = dss.getValue(ReviewsStall.class);
 //Populate the array only with the reviews for the current chosen Stall
                if (revStall.getNameS().compareTo(name)==0)
                {
                    list.add(revStall);
                }

            }
 //instantiate adapter
            adapter = new ReviewStallAdapter(list, ViewReviewStall.this);
 //populate the RecyclerView with all cards
            rv.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onCardClick(int i) {
 //when a card is clicked, navigate to new activity and pass the current user's email
 //and the whole object's values that is chosen
        String email = getIntent().getStringExtra("EM");
        Intent z = new Intent(ViewReviewStall.this, ReviewsStallDetails.class);
        z.putExtra("REVS", list.get(i));
        z.putExtra("EM", email);
        startActivity(z);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(ViewReviewStall.this, Login.class);
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