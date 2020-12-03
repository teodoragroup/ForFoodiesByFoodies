package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity {

//Declaration
    Button res, stall, users;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//Linking the views to their xml counterparts
        res = findViewById(R.id.w_btn_res);
        stall = findViewById(R.id.w_btn_str);
        tv = findViewById(R.id.w_tv);
        users = findViewById(R.id.w_btn_users);

//Receiving the current logged in user's email and display it into the text view
       String email = getIntent().getStringExtra("EM");
       tv.setText(email);


//When button Restaurants is clicked, opens a new activity and passes the current user's email
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this, Restaurants.class);
                i.putExtra("EMAIL", email);
                startActivity(i);
            }
        });

//When button Stalls is clicked, opens a new activity and passes the current user's email
        stall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Welcome.this, StreetFood.class);
                j.putExtra("EM", email);
                startActivity(j);
            }
        });

 //When button Users is clicked, opens a new activity and passes the current user's email
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(Welcome.this, UsersProfiles.class);
                d.putExtra("EM", email);
                startActivity(d);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(Welcome.this, Login.class);
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