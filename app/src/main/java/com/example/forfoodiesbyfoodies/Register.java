package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
//Declaration
    EditText fname, lname, email, password;
    Button create;
    DatabaseReference dbref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

 //Link views and ref objects
        fname = findViewById(R.id.reg_et_fn);
        lname = findViewById(R.id.reg_et_ln);
        email = findViewById(R.id.reg_et_email);
        password = findViewById(R.id.reg_et_password);
        create = findViewById(R.id.reg_btn);
  //ref object to "Users" node in Firebase DB
        dbref = FirebaseDatabase.getInstance().getReference("Users");
 //ref object to Authentication
        auth = FirebaseAuth.getInstance();

  //When button Create is clicked
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//If neither of the fields is empty
                if (!(TextUtils.isEmpty(fname.getText().toString())) && !(TextUtils.isEmpty(lname.getText().toString())) &&
                        !(TextUtils.isEmpty(email.getText().toString())) &&
                        !(TextUtils.isEmpty(password.getText().toString())))
                {
                    auth.createUserWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//If user does not already exists
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Register.this, "Registration Successful",
                                        Toast.LENGTH_LONG).show();
//Create new user and save record into the Firebase DB with automatically generated id
                                String id = dbref.push().getKey();
                                Users user = new Users(fname.getText().toString(), lname.getText().toString(),
                                        email.getText().toString(), password.getText().toString() );
                                dbref.child(id).setValue(user);
//Open Login activity
                                Intent i = new Intent(Register.this, Login.class);
                                startActivity(i);
                            }
                            else
                            {
  //If user exists, display message
                                Toast.makeText(Register.this, "Already In Use",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
 //If some og the fields is empty
                    Toast.makeText(Register.this, "You must fill in all fields",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}