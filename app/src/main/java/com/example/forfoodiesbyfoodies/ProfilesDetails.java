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

public class ProfilesDetails extends AppCompatActivity {
//Declaration
    TextView fn1, fn2, ln1, ln2, em1, em2, type1, type2;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles_details);

//Receiving current logged in user's email and all values of the user we want to see the details for
        Users users = getIntent().getParcelableExtra("USERS");
        String email = getIntent().getStringExtra("EM");

//Linking views to their xml counterparts
        fn1 = findViewById(R.id.tv1);
        fn2 = findViewById(R.id.tv11);
        ln1 = findViewById(R.id.tv2);
        ln2 = findViewById(R.id.tv22);
        em1 = findViewById(R.id.tv3);
        em2 = findViewById(R.id.tv33);
        type1 = findViewById(R.id.tv4);
        type2 = findViewById(R.id.tv44);

        edit = findViewById(R.id.edit);

        fn1.setText("First Name:");
        ln1.setText("Last Name:");
        em1.setText("Email Address:");
        type1.setText("Type of user:");
 //Set the text of the view with values of the last viewed user
        fn2.setText(users.getFname());
        ln2.setText(users.getLname());
        em2.setText(users.getEmail());

 // If last viewed user's profile has email ttt@gmail.com, their type is Admin
        if (users.getEmail().compareTo("ttt@gmail.com")==0)
        {
            type2.setText("Admin");
        }
  //Respectively Critic or User
        if (users.getEmail().compareTo("vvv@gmail.com")==0)
        {
                type2.setText("Critic");
        }
        if (users.getEmail().compareTo("ddd@gmail.com")==0)
        {
            type2.setText("User");
        }

 //If the current logged in user is the same as the last wiewed user, then show button Edit (profile)
        if (users.getEmail().compareTo(email)==0)
        {
            edit.setVisibility(View.VISIBLE);
        }
        else
  // If it is not the same, hide the button
        {
            edit.setVisibility(View.INVISIBLE);
        }

  //When button Edit is clicked, open MyProfile Activity, carrying out
  //the current user and the last viewed user
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(ProfilesDetails.this, MyProfile.class);
                s.putExtra("EM", email);
                s.putExtra("USERS", users);
                startActivity(s);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(ProfilesDetails.this, Login.class);
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