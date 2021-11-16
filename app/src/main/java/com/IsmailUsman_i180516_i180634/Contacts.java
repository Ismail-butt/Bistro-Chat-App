package com.IsmailUsman_i180516_i180634;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Contacts extends AppCompatActivity {

    ImageView chat, camera, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // When Click on Chat Icon, Switch to Chat/Home Screen
        chat = findViewById(R.id.chat);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contacts.this, Home.class);
                startActivity(intent);

            }
        });

        // When Click on People Icon, Switch to Contacts Screen
        call = findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contacts.this, CallHistories.class);
                startActivity(intent);

            }
        });

        // Camera There
    }
}