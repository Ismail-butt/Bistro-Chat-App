package com.IsmailUsman_i180516_i180634;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class logIn extends AppCompatActivity {

    Button logIn1, register1;
    TextView email1,password1;

    public Contact c;
    FirebaseAuth auth;
    FirebaseUser fUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        c = new Contact();
        auth = FirebaseAuth.getInstance();

        logIn1 = findViewById(R.id.logIn1);
        register1 = findViewById(R.id.register1);
        email1 = (EditText) findViewById(R.id.email1);
        password1=(EditText) findViewById(R.id.password1);

        logIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = email1.getText().toString();
                String p = password1.getText().toString();

                if( e.equals("") || p.equals("") )
                {
                    Toast.makeText(logIn.this , "Please Insert All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                fUser = auth.getCurrentUser();
                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(fUser.getUid());
                                reference.child("online").setValue(true);
                                Intent intent = new Intent(logIn.this, bottom_navigation.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(logIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(logIn.this, signUp.class);
                startActivity(intent);
            }
        });
    }
}