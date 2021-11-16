package com.IsmailUsman_i180516_i180634;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CallHistories extends AppCompatActivity {

    ImageView people, chat, camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_histories);

        // When Click on People Icon, Switch to Contacts Screen
        people = findViewById(R.id.people);

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CallHistories.this, Contacts.class);
                startActivity(intent);

            }
        });

        // When Click on Chat Icon, Switch to Chat/Home Screen
        chat = findViewById(R.id.chat);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CallHistories.this, Home.class);
                startActivity(intent);

            }
        });

        // Camera There
    }
}