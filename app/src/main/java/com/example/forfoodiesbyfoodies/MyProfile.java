package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyProfile extends AppCompatActivity {
//Declaration
    TextView fname, lname, em, pass;
    EditText fnameNew, lnameNew, emailNew, passNew;
    Button save;
    DatabaseReference dbref;
    Query q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
//Receiving the whole USERS object from the previous activity
        Users users = getIntent().getParcelableExtra("USERS");
//Reference object to Firebase database
        dbref = FirebaseDatabase.getInstance().getReference();
 //Query for the email of the current user who wants to edit their profile
        q = dbref.child("Users").orderByChild("email").equalTo(users.getEmail());
//Listener attached to the query
        q.addListenerForSingleValueEvent(listener);

 //Link the views to their xml counterparts
        fname = findViewById(R.id.my_tv_fname);
        lname = findViewById(R.id.my_tv_lname);
        em = findViewById(R.id.my_tv_email);
        pass = findViewById(R.id.my_tv_password);
        fnameNew = findViewById(R.id.my_et_fname);
        lnameNew = findViewById(R.id.my_et_lname);
        emailNew = findViewById(R.id.my_et_email);
        passNew = findViewById(R.id.my_et_password);
        save = findViewById(R.id.my_btn_save);

 //Set the text of the text views
        fname.setText("First Name:");
        lname.setText("Last Name:");
        em.setText("Email Address:");
        pass.setText("Password:");

 //Set the text of the editable views which user can edit
        fnameNew.setText(users.getFname());
        lnameNew.setText(users.getLname());
        emailNew.setText(users.getEmail());
        passNew.setText(users.getPassword());
    }
//Creating a Listener for reading from Firebase
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dss:snapshot.getChildren())
            {
 //Retrieving the unique primary key of the current user
                String key = dss.getKey();

 //Functions executing when button Save is clicked
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

 //No empty fields allowed
                        if (!fnameNew.getText().toString().isEmpty() && !lnameNew.getText().toString().isEmpty() &&
                                !emailNew.getText().toString().isEmpty() && !passNew.getText().toString().isEmpty())
                        {
 //Update user's details
                            dbref.child("Users").child(key).child("fname").setValue(fnameNew.getText().toString());
                            dbref.child("Users").child(key).child("lname").setValue(lnameNew.getText().toString());

                            Toast.makeText(MyProfile.this, "Your new details have been saved!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MyProfile.this, "Fill in all fields!",
                                    Toast.LENGTH_LONG).show();
                        }
//Navigate back to UsersProfiles activity
                        String email = getIntent().getStringExtra("EM");
                        Intent o = new Intent(MyProfile.this, UsersProfiles.class);
                        o.putExtra("EM", email);
                        startActivity(o);
                    }
                });
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
}