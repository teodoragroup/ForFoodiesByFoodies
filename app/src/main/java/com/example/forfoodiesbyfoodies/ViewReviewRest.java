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

public class ViewReviewRest extends AppCompatActivity implements ReviewRestAdapter.ReviewRestHolder.OnCardClickListener{

 //Declaration
    RecyclerView rv;
    ArrayList<ReviewsRest> list = new ArrayList<>();
    DatabaseReference dbref;
    ReviewRestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review_rest);

//Receiving the current logged in user's email and the name of the Restaurant chosen
//that we want to view the reviews for, from the previous activity
        String email = getIntent().getStringExtra("EM");
        String name = getIntent().getStringExtra("NAME");

//Settings for the Recycler View's layout
        rv = findViewById(R.id.reviewR_rv);
        rv.setLayoutManager(new LinearLayoutManager(ViewReviewRest.this,
                LinearLayoutManager.VERTICAL, false));
 // //Create ref object to Reviews_Rest node and attach a listener for reading from Firebase
        dbref = FirebaseDatabase.getInstance().getReference("Reviews_Rest");
        dbref.addListenerForSingleValueEvent(listener);
    }
 //Create Listener object
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss:snapshot.getChildren())
            {
                String name = getIntent().getStringExtra("NAME");
                ReviewsRest rest = dss.getValue(ReviewsRest.class);
  //Populate the array only with the reviews for the current chosen Restaurant
                if (rest.getNameR().compareTo(name)==0)
                {
                    list.add(rest);
                }
                
            }
 //instantiate adapter
            adapter = new ReviewRestAdapter(list, ViewReviewRest.this);
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
        Intent z = new Intent(ViewReviewRest.this, ReviewsRestDetails.class);
        z.putExtra("REVR", list.get(i));
        z.putExtra("EM", email);
        startActivity(z);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(ViewReviewRest.this, Login.class);
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