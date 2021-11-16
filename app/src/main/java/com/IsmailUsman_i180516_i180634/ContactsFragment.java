package com.IsmailUsman_i180516_i180634;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    Contact c;
    ContactsAdapter adapter;
    DatabaseReference reference;
    List<Contact> filtered;
    FirebaseUser user;
    FirebaseAuth auth;

    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        filtered = new ArrayList<Contact>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        c =  new Contact();
        final EditText search = rootView.findViewById(R.id.searchContact);

        if(user != null) {
            final RecyclerView recyclerView = rootView.findViewById(R.id.contacts_rv);

            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    c = dataSnapshot.getValue(Contact.class);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users");
            final List<Contact> all = new ArrayList<Contact>();
            ValueEventListener v = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    all.clear();
                    for(DataSnapshot d: dataSnapshot.getChildren())
                    {
                        if(!d.getValue(Contact.class).getEmail().equals(c.getEmail()))
                        {
                            all.add(d.getValue(Contact.class));
                        }

                    }
                    adapter = new ContactsAdapter(filtered, getContext());
                    for(Contact co:all){
                        filtered.add(co);
                    }
                    adapter.notifyDataSetChanged();
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            ref2.addListenerForSingleValueEvent(v);

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String text = search.getText().toString().toLowerCase();

                    if(text.length()!= 0)
                    {
                        filtered.clear();
                        for(Contact cont:all)
                        {
                            if(cont.getUsername().toLowerCase().contains(text))
                            {
                                filtered.add(cont);
                            }
                        }
                        adapter.notifyDataSetChanged();

                    }
                    else
                    {
                        filtered.clear();
                        for(Contact co:all){
                            filtered.add(co);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
        return rootView;
    }
}