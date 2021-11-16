package com.IsmailUsman_i180516_i180634;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView rv;
    ContactsAdapter adapter;

    List<Contact> ls;

    MyDBHelper DB;

    ImageView people, chat, camera, call;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv = findViewById(R.id.rv);
        ls = new ArrayList<>();
        DB = new MyDBHelper(Home.this);


        // Add Users Contact to the list of Recycler View for the first time when Home Screen Opens
//        SQLiteDatabase MyDB = DB.getReadableDatabase();
//        Cursor cursor = MyDB.rawQuery("select * from profile" , null) ;
//
//        cursor.moveToFirst() ;
//
//        while ( !cursor.isAfterLast()){
//
//            String profileUriStr =  cursor.getString(cursor.getColumnIndex("profileUriStr"));
//
//            Uri profileUri = Uri.parse(profileUriStr);
//
//            ls.add(new Contact(
//                    "1",
//                    cursor.getString(cursor.getColumnIndex("firstName")),
//                    cursor.getString(cursor.getColumnIndex("lastName")),
//                    cursor.getString(cursor.getColumnIndex("gender")),
//                    cursor.getString(cursor.getColumnIndex("bio")),
//                    profileUri
//            )
//            );
//
//            cursor.moveToNext();
//        }

        adapter = new ContactsAdapter(ls, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(Home.this);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);

        // For Setting Blue Chat, Here



        // Switching Screens When Clicking on Bottom Tab Icons

        // When Click on People Icon, Switch to Contacts Screen
        people = findViewById(R.id.people);

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Contacts.class);
                startActivity(intent);

            }
        });

        // When Click on People Icon, Switch to Contacts Screen
        call = findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, CallHistories.class);
                startActivity(intent);

            }
        });

        // Camera There

    }
}