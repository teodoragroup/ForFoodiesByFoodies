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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddRestaurant extends AppCompatActivity {
//Declaration
    ImageView imageR;
    Button upload, addR;
    EditText nameR, locationR, descR, addedByR;
    Uri path;
    DatabaseReference dbref;
    StorageReference sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
//Receiving the current logged in user's email from the previous activity
        String email = getIntent().getStringExtra("EM");
//Linking objects to Firebase Storage, Firebase db and views to their xml counterparts
        imageR = findViewById(R.id.ar_image);
        addR = findViewById(R.id.ar_btn_addR);
        upload = findViewById(R.id.ar_btn_pick);
        nameR = findViewById(R.id.ar_et_Rname);
        locationR = findViewById(R.id.ar_et_Rloc);
        descR = findViewById(R.id.ar_et_Rdesc);
        addedByR = findViewById(R.id.ar_et_RaddedBy);

 //ref object to Firebase Storage, folder R_images
        sref = FirebaseStorage.getInstance().getReference("R_images");
 //ref object to Firebase database, node Restaurants
        dbref = FirebaseDatabase.getInstance().getReference("Restaurants");
//Showing the current logged in user
        addedByR.setText(email);

//Functions executing when button upload is clicked
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//Showing all image files on the phone
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 77);
            }
        });
//Functions executing when button addR is clicked
        addR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Firebase db will create a unique id for every new Restaurant added
                String id = dbref.push().getKey();
   //Storage ref. object having the id and the image extension
                StorageReference sr = sref.child(id + "." + getExtension(path));

                sr.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
 //Check if some of the fields is empty
                                if (nameR.getText().length() > 0 && locationR.getText().length() >0
                                        && descR.getText().length() > 0 )
                                {
                                    Toast.makeText(AddRestaurant.this, "A New Restaurant Has Been Added Successfully", Toast.LENGTH_LONG).show();
//New object from Rest class is created and its values saved into db, along with the image url
                                    String urlR = uri.toString();
                                    Rest rest = new Rest(nameR.getText().toString(), locationR.getText().toString(),
                                            descR.getText().toString(), email, urlR);
                                    dbref.child(id).setValue(rest);
//Navigate to opening activity Restaurants, carrying out the current user's email
                                    Intent j = new Intent(AddRestaurant.this, Restaurants.class);
                                    j.putExtra("EMAIL", email);
                                    startActivity(j);
                                }
                                else
                                {
                                    Toast.makeText(AddRestaurant.this,
                                            "All Fields Are Required", Toast.LENGTH_LONG).show();
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
  //if getting the url failed, delete the ref.object
                                sr.delete();
                                Toast.makeText(AddRestaurant.this,
                                        "Connection Lost", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddRestaurant.this,
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
        if (requestCode == 77 && resultCode == RESULT_OK && data.getData() !=null)
        {
            Picasso.get().load(data.getData()).fit().into(imageR);
            path = data.getData();

            Toast.makeText(AddRestaurant.this,
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
            Intent t = new Intent(AddRestaurant.this, Login.class);
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

