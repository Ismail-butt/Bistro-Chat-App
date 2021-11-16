package com.IsmailUsman_i180516_i180634;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class createProfile extends AppCompatActivity {

    private CircleImageView profilePhoto;
    private static final int pick_Image = 1;
    Uri imageUri;

    Button save;
    EditText firstName, lastName, bio;
    String gender;

    Contact c;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        c = new Contact();
        c = (Contact) getIntent().getSerializableExtra("contact");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        bio = findViewById(R.id.bio);

        // For Saving Profile
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c.setFirstName(firstName.getText().toString());
                c.setLastName(lastName.getText().toString());
                c.setBio(bio.getText().toString());
                c.setUsername(c.getFirstName()+" "+c.getLastName());
                c.setOnline(true);


                StorageReference storeRef1 = FirebaseStorage.getInstance().getReference();
                final StorageReference storeRef = storeRef1.child(user.getUid()+".jpg");
                UploadTask uploadTask = storeRef.putFile(imageUri);
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return storeRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            c.setprofilepic(downloadUri.toString());
                            //save to the server
                            reference.setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //Here write code to send this contact to server for saving purpose
                                        Intent s = new Intent(getApplicationContext(), bottom_navigation.class);
                                        s.putExtra("contact",c);
                                        startActivity(s);
                                    }
                                }
                            });
                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(getApplicationContext(),"Could not load Image Url",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }

        });


        // add Profile Photo
        profilePhoto = (CircleImageView) findViewById(R.id.profilePhoto);

        // Old Method of Uploading Picture
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // for Uploading Image from Gallery

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);

                startActivityForResult(Intent.createChooser(gallery, "select Picture"), pick_Image);
            }
        });

        final TextView male, female, noInterest;
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        noInterest = findViewById(R.id.notInterested);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setTextColor(Color.RED);
                male.setBackgroundColor(Color.BLUE);

                gender = "male";

                female.setTextColor(Color.GREEN);
                female.setBackgroundColor(Color.WHITE);

                noInterest.setTextColor(Color.GREEN);
                noInterest.setBackgroundColor(Color.WHITE);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female.setTextColor(Color.RED);
                female.setBackgroundColor(Color.BLUE);

                gender = "female";

                male.setTextColor(Color.GREEN);
                male.setBackgroundColor(Color.WHITE);

                noInterest.setTextColor(Color.GREEN);
                noInterest.setBackgroundColor(Color.WHITE);
            }
        });

        noInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInterest.setTextColor(Color.RED);
                noInterest.setBackgroundColor(Color.BLUE);

                gender = "no Interest";

                female.setTextColor(Color.GREEN);
                female.setBackgroundColor(Color.WHITE);

                male.setTextColor(Color.GREEN);
                male.setBackgroundColor(Color.WHITE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == pick_Image && resultCode == RESULT_OK){

            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePhoto.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}