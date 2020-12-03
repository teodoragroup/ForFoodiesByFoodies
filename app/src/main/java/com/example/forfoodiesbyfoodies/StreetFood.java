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

public class StreetFood extends AppCompatActivity implements StallAdapter.StallHolder.OnCardClickListener{

//Declaration
    RecyclerView rv;
    ArrayList<Stall> list = new ArrayList<>();
    DatabaseReference dbref;
    StallAdapter adapter;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_food);

//Receiving the current logged in user's email from the previous activity
        String em = getIntent().getStringExtra("EM");

        add = findViewById(R.id.stall_add);
 //Functions to be executed when button Add is clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //Open new activity and pass the current user's email
                Intent k = new Intent(StreetFood.this, AddStall.class);
                k.putExtra("EM", em);
                startActivity(k);
            }
        });

//Settings for the Recycler View's layout
        rv = findViewById(R.id.stall_list);
        rv.setLayoutManager(new LinearLayoutManager(StreetFood.this,
                LinearLayoutManager.VERTICAL, false));
////Create ref object to Street_Food node and attach a listener for reading from Firebase
        dbref = FirebaseDatabase.getInstance().getReference("Street_Food");
        dbref.addListenerForSingleValueEvent(listener);

    }
 //Create Listener object
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss: snapshot.getChildren())
            {
                Stall stall = dss.getValue(Stall.class);
                list.add(stall);
            }
 //Creating method for sorting the name of the stalls in alphabetical order
            Collections.sort(list, new Comparator<Stall>() {
                @Override
                public int compare(Stall o1, Stall o2) {
                    return o1.getNameS().compareTo(o2.getNameS());
                }
            });
//instantiate adapter
            adapter = new StallAdapter(list, StreetFood.this);
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
    public void onCardClick(int i)
    {
//when a card is clicked, navigate to new activity and pass the current user's email
//and the whole object's values that is chosen
        String em = getIntent().getStringExtra("EM");
        Intent intent = new Intent(StreetFood.this, StallDetails.class);
        intent.putExtra("STALL", list.get(i));
        intent.putExtra("EM", em);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(StreetFood.this, Login.class);
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