package com.IsmailUsman_i180516_i180634;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>{

    private List<Contact> contacts;
    private Contact contact;
    private Context co;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;
    boolean chatExists;

    public ContactsAdapter(List<Contact> c, Context co) {
        this.co = co;
        this.contact = new Contact();
        this.contacts = c;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user != null) {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    contact= dataSnapshot.getValue(Contact.class);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @NonNull
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(co).inflate(R.layout.contacts_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(contacts.get(position).getUsername());
        holder.msg.setText(contacts.get(position).getGender());
        Glide.with(co).load(Uri.parse(contacts.get(position).getprofilepic())).into(holder.pic);
        if(contacts.get(position).getOnline())
        {
            holder.online.setVisibility(View.VISIBLE);
        }
        else{
            holder.online.setVisibility(View.GONE);
        }

        //when remaining space of contact is clicked, its chat screen should be displayed
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(co, chatScreen.class);
                i.putExtra("sender",contact);
                i.putExtra("receiver",contacts.get(position));
                final DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("chats");
                chatExists = false;
                chatReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // messages.clear();
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {
                            Chat c = d.getValue(Chat.class);
                            if((c.getReceiver().equals(contact.getEmail()) && c.getSender().equals(contacts.get(position).getEmail()))
                                    || (c.getReceiver().equals(contacts.get(position).getEmail()) && c.getSender().equals(contact.getEmail())))
                            {

                                chatExists = true;

                                break;
                            }

                        }
                        if(!chatExists)
                        {
                            Chat ch = new Chat(contact.getEmail(),contacts.get(position).getEmail());
                            String pushkey = chatReference.push().getKey();
                            chatReference.child(pushkey).setValue(ch);
                            DatabaseReference msg_ref = chatReference.child(pushkey).child("messages");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                co.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, msg;
        ImageView pic,online;
        LinearLayout row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name =(TextView) itemView.findViewById(R.id.name);
            this.msg =(TextView) itemView.findViewById(R.id.gender_age);
            this.pic = (ImageView) itemView.findViewById(R.id.contact_photo);
            this.online = (ImageView) itemView.findViewById(R.id.onlineContact);
            this.row = (LinearLayout) itemView.findViewById(R.id.contact_content);

        }
    }
}
