package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersProfiles extends AppCompatActivity implements UsersAdapter.UsersHolder.OnCardClickListener {

//Declaration
    RecyclerView rv;
    ArrayList<Users> list = new ArrayList<>();
    DatabaseReference dbref;
    UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profiles);

//Receiving the current logged in user's email from the previous activity
        String email = getIntent().getStringExtra("EM");

 //Settings for the Recycler View's layout
        rv = findViewById(R.id.users_rv);
        rv.setLayoutManager(new LinearLayoutManager(UsersProfiles.this,
                LinearLayoutManager.VERTICAL, false));
//Create ref object to Users node and attach a listener for reading from Firebase
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        dbref.addListenerForSingleValueEvent(listener);

    }
//Create Listener object
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss:snapshot.getChildren())
            {
                Users users = dss.getValue(Users.class);
                list.add(users);
            }
 //instantiate adapter
            adapter = new UsersAdapter(list, UsersProfiles.this);
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
        Intent k = new Intent(UsersProfiles.this, ProfilesDetails.class);
        k.putExtra("USERS", list.get(i));
        k.putExtra("EM", email);
        startActivity(k);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(UsersProfiles.this, Login.class);
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