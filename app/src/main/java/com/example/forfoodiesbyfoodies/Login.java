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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
//Declaration
    EditText email, password;
    Button signIn, register;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseUser current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//Linking views and ref objects
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        signIn = findViewById(R.id.login_btn_signIn);
        register = findViewById(R.id.login_btn_register);
        auth = FirebaseAuth.getInstance();
        current = FirebaseAuth.getInstance().getCurrentUser();

 //Auth Listener checking if the current user is connected
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Checks if the user is signed in
                if (current!=null)
                {
                    Toast.makeText(Login.this, "Connected",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Login.this, "Not Connected",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

//When button register is clicked, navigate to Register activity
    register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Login.this, Register.class);
        startActivity(i);
    }
});
 //When button sign in is clicked
    signIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
 //Check if user's input for email and password is empty
            String em = email.getText().toString();
            String pw = password.getText().toString();
            if (!em.equals("") && !pw.equals(""))
            {
                auth.signInWithEmailAndPassword(em, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
 // check if user already exists(if registered)
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
 //If so, display message and open Welcome activity, carrying out the logged in user's email
                            Toast.makeText(Login.this, "You Are Now Signed In",
                                    Toast.LENGTH_LONG).show();

                            Intent j = new Intent(Login.this, Welcome.class);
                            j.putExtra("EM", em);
                            startActivity(j);
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Login Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
 //If user is not registered, display a warning message
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "You Need To Register First",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    });

    }

    @Override
 //when activity starts
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
  //When user is signed out
    protected void onStop() {
        super.onStop();
        if (authListener !=null)
        {
            auth.removeAuthStateListener(authListener);
        }
    }
}