package com.example.forfoodiesbyfoodies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddStall extends AppCompatActivity {
//Declaration
    ImageView imageS;
    TextView tv;
    Button pick, addS;
    EditText nameS, locationS, descS;
    Uri path;
    DatabaseReference dbref;
    StorageReference sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stall);
//Linking objects to Firebase Storage, Firebase db and views to their xml counterparts
       imageS = findViewById(R.id.as_image);
       pick = findViewById(R.id.as_btn_pick);
       addS = findViewById(R.id.as_btn_addS);
       nameS = findViewById(R.id.as_et_nameS);
       locationS = findViewById(R.id.as_et_Sloc);
       descS = findViewById(R.id.as_et_Sdesc);
       tv = findViewById(R.id.as_addedByS);

        sref = FirebaseStorage.getInstance().getReference("S_images");
        dbref = FirebaseDatabase.getInstance().getReference("Street_Food");

//Receiving the current logged in user's email from the previous activity
// and display it into a text field
        String email = getIntent().getStringExtra("EM");
        tv.setText(email);

//Functions executing when button pick is clicked
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
 // showing all image files from the phone
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 88);
            }
        });
//Functions executing when button addS is clicked
        addS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Authomatically generated id for each new stall added
                String id = dbref.push().getKey();

 //Storage ref.object having the id and the image extension
                StorageReference sr = sref.child(id + "." + getExtension(path));

                sr.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
 //If uploading the image and getting the url successful
                            public void onSuccess(Uri uri) {

//Check if some of the fields is empty
                                if (nameS.getText().length() > 0 && locationS.getText().length() >0
                                        && descS.getText().length() > 0 )
                                {
  //Event listener, attached to Street_Food node in the DB checking if the stall already exists
                                    dbref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!snapshot.exists())
                                            {
  //If the stall does not exists, then save as new
                                                Toast.makeText(AddStall.this, "A New Stall Has Been Added Successfully",
                                                        Toast.LENGTH_LONG).show();

                                                String urlS = uri.toString();

  //The values from a newly created object from Stall class will be saved into db,
                                                // along with the image url

                                                Stall stall = new Stall(nameS.getText().toString(), locationS.getText().toString(),
                                                        descS.getText().toString(), email, urlS);
                                                dbref.child(id).setValue(stall);
//Navigate to opening activity Street Food, carrying out the current user's email
                                                Intent j = new Intent(AddStall.this, StreetFood.class);
                                                j.putExtra("EM", email);
                                                startActivity(j);
                                            }
                                            else
                                            {
   //If the stall exists, display a message
                                                Toast.makeText(AddStall.this, "This stall already exists",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                else
 //If some of the fields is empty, display a warning message
                                {
                                    Toast.makeText(AddStall.this,
                                            "All Fields Are Required", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
  //if getting the url failed, delete the ref.object
                                sr.delete();
                                Toast.makeText(AddStall.this,
                                        "Connection Lost", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStall.this,
                                "All Fields Are Required", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    @Override
    //if image uploaded successfully, it will be displayed into the image view
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 88 && resultCode == RESULT_OK && data.getData() !=null)
        {
            Picasso.get().load(data.getData()).fit().into(imageS);
            path = data.getData();

            Toast.makeText(AddStall.this,
                    "Image Uploaded Successfully", Toast.LENGTH_LONG).show();
        }
    }
//Method for taking the original image extension
    private String getExtension(Uri path)
    {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(path));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_signout)
        {
            Intent t = new Intent(AddStall.this, Login.class);
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