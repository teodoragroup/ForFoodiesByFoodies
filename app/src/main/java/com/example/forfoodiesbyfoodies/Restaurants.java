package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Restaurants extends AppCompatActivity implements RestaurantAdapter.RestaurantHolder.OnCardClickListener{

 //Declaration
    RecyclerView rv;
    ArrayList<Rest> list = new ArrayList<>();
    DatabaseReference dbref;
    RestaurantAdapter adapter;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

//Receiving the current logged in user's email from the previous activity
        String em = getIntent().getStringExtra("EMAIL");

        add = findViewById(R.id.rest_add);

 //If the current user is an Admin, make button Add Restaurant visible
        if (em.compareTo("ttt@gmail.com")==0)
        {
            add.setVisibility(View.VISIBLE);
        }
        else
//If it is not an Admin, do not show the button
        {
            add.setVisibility(View.INVISIBLE);
        }

 //Functions to be executed when button Add is clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

  //Open new activity and pass the current user's email
                Intent i = new Intent(Restaurants.this, AddRestaurant.class);
                i.putExtra("EM", em);
                startActivity(i);
            }
        });
//Settings for the Recycler View's layout
        rv = findViewById(R.id.rest_list);
        rv.setLayoutManager(new LinearLayoutManager(Restaurants.this,
                LinearLayoutManager.VERTICAL, false));
 //Create ref object to Restaurants node and attach a listener for reading from Firebase
        dbref = FirebaseDatabase.getInstance().getReference("Restaurants");
        dbref.addListenerForSingleValueEvent(listener);

    }
 //Create Listener object
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss:snapshot.getChildren())
            {
                Rest rest = dss.getValue(Rest.class);
                list.add(rest);
            }
 //Creating method for sorting the name of the restaurants in alphabetical order
            Collections.sort(list, new Comparator<Rest>() {
                @Override
                public int compare(Rest o1, Rest o2) {
                    return o1.getNameR().compareTo(o2.getNameR());
                }
            });
 //instantiate adapter
            adapter = new RestaurantAdapter(list, Restaurants.this);
//needed for the sorting
            adapter.notifyDataSetChanged();
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
// and the whole object's values that is chosen
        String em = getIntent().getStringExtra("EMAIL");
        Intent intent = new Intent(Restaurants.this, RestDetails.class);
        intent.putExtra("EM", em);
        intent.putExtra("REST", list.get(i));
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(Restaurants.this, Login.class);
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