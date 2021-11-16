package com.IsmailUsman_i180516_i180634;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.PrintStream;
import java.io.Serializable;

public class signUp extends AppCompatActivity {

    Button signUp, logIn;
    EditText email,password,confirm_password;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    Contact c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        c = new Contact();
        email = (EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        confirm_password=(EditText) findViewById(R.id.confirm_password);
        signUp = findViewById(R.id.signUp);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = email.getText().toString();
                String p1 = password.getText().toString();
                String p2 = confirm_password.getText().toString();

                if( e.equals("") || p1.equals("") || p2.equals("") ) {
                    Toast.makeText(signUp.this , "Please Insert All Fields", Toast.LENGTH_SHORT).show();
                }

                else if(!p1.equals(p2)) {
                    Toast.makeText(signUp.this , "Passwords Don't Match ", Toast.LENGTH_SHORT).show();
                }
                else{
                    register(e,p1);
                }

            }
        });


        logIn = findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUp.this, logIn.class);
                startActivity(intent);
            }
        });
    }

    private void register(String e, String p1) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(e,p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users");

                    c.setEmail(e);
                    c.setPassword(p1);
                    DatabaseReference keyRef = reference.child(userid);
                    keyRef.setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent s = new Intent(signUp.this,createProfile.class);
                                s.putExtra("contact", c);
                                startActivity(s);
                            }
                        }
                    });
                } else {
                    Toast.makeText(signUp.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Toast.makeText(signUp.this,
//                    currentUser.getUid() + "",
//                    Toast.LENGTH_SHORT
//            ).show();
//        }
//    }

}