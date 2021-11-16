package com.IsmailUsman_i180516_i180634;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class chatScreen extends AppCompatActivity {

    Chat chat;
    Contact contact;
    RecyclerView recyclerView;
    Contact receiver;
    Contact sender;
    List<Message> messages;
    DatabaseReference reference;
    DatabaseReference chatReference;
    DatabaseReference referenceUsers;
    DatabaseReference msg_ref;
    ChatAdapter adapter;
    List<String> mKeys;

    FirebaseUser user;
    FirebaseAuth auth;
    EditText text;
    ImageView send;
    ImageView sendPicture;
    String chat_key;
    String key2;
    String key3;
    String messagePhoto;
    boolean chatExists = false;


    private ImageView gallery;
    private static final int pick_Image = 2;
    Uri imageUri;

//    RecyclerView rv;
//    MssgAdapter adapter;

    List<Mssg> ls;

    ImageView back;
    TextView contactName;

    ImageView callIcon, send_mssg;

    EditText textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        text=findViewById(R.id.text_of_msg);
        send=findViewById(R.id.send_button);
        sendPicture = findViewById(R.id.emojiButton);

        chat = new Chat();
        key2 = new String();
        key3 = new String();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        contact = new Contact();
        messages = new ArrayList<>();
        mKeys = new ArrayList<>();
        if(user != null) {
            receiver = (Contact) getIntent().getSerializableExtra("receiver");
            sender = (Contact) getIntent().getSerializableExtra("sender");
            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            chatReference = FirebaseDatabase.getInstance().getReference("chats");
            referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
            recyclerView = findViewById(R.id.chat_rv);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    contact = dataSnapshot.getValue(Contact.class);
                    adapter = new ChatAdapter(messages,msg_ref,mKeys,contact,chatScreen.this,recyclerView);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            chatReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    messages.clear();
                    mKeys.clear();
                    Chat c = dataSnapshot.getValue(Chat.class);
                    if((c.getReceiver().equals(sender.getEmail()) && c.getSender().equals(receiver.getEmail()))
                            || (c.getReceiver().equals(receiver.getEmail()) && c.getSender().equals(sender.getEmail())))
                    {
                        chat_key = dataSnapshot.getKey();
                        chatExists = true;
                        msg_ref = chatReference.child(chat_key).child("messages");
                        msg_ref.keepSynced(true);
                        adapter.notifyDataSetChanged();
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Message m = new Message();
                                m.setContent(text.getText().toString());
                                Date date = Calendar.getInstance().getTime();
                                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                                String strDate = dateFormat.format(date);
                                m.setTime(strDate);
                                m.setSender(contact.getEmail());
                                m.setReceiver(receiver.getEmail());
                                m.setSenderPhoto(contact.getprofilepic());
                                if(messagePhoto != null){
                                    m.setPhoto(messagePhoto);
                                    m.setIsPhoto(true);
                                }
                                chatReference.child(chat_key).child("lastMessage").setValue(m.getContent());
                                chatReference.child(chat_key).child("lastTime").setValue(m.getTime());
                                msg_ref.push().setValue(m);
                                text.setText("");


                            }
                        });
                        sendPicture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                                pickPhoto.setType("image/*");
                                startActivityForResult(pickPhoto, 1);


                            }
                        });
                        msg_ref.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                messages.add(dataSnapshot.getValue(Message.class));
                                mKeys.add(dataSnapshot.getKey());
                                int last = messages.size()-1;

                                recyclerView.smoothScrollToPosition(last);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            TextView isOnline = findViewById(R.id.isOnline);
            TextView receiverName = findViewById(R.id.receiverName);
            ImageView back = findViewById(R.id.back_button);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            receiverName.setText(receiver.getUsername());
            if(receiver.getOnline())
            {
                isOnline.setText("online");
            }
            else{
                isOnline.setText("offline");
            }

        }


//        Intent i = getIntent();
//        String fName = i.getStringExtra("fName");
//        String profileUriStr = i.getStringExtra("profileUriStr");

        // Setting The Name of Contact Here
//        contactName = findViewById(R.id.profilename);
//        contactName.setText(fName);

//        rv = findViewById(R.id.rv);
//        ls = new ArrayList<Mssg>();


//        adapter = new MssgAdapter(ls, this);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(chatScreen.this);
//        rv.setLayoutManager(manager);
//        rv.setAdapter(adapter);


        // for Send Mssg Button

//        textMsg = findViewById(R.id.textMsg);
//
//        send_mssg = findViewById(R.id.send_mssg);

//        send_mssg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String textMssg = textMsg.getText().toString();
//
//                ls.add(new Mssg("", "", textMssg, null, true));

//                ls.add(new Mssg("", "", "",
//                        Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                                        "://" + getResources().getResourcePackageName(R.drawable.temppicx)
//                                        + '/' + getResources().getResourceTypeName(R.drawable.temppicx) + '/' + getResources().getResourceEntryName(R.drawable.temppicx)
//                        ), false));

//                adapter.notifyDataSetChanged();
//            }
//        });

        gallery = findViewById(R.id.gallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // for Uploading Image from Gallery

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);

                startActivityForResult(Intent.createChooser(gallery, "select Picture"), pick_Image);

            }
        });


        // For Calling a Contact
        callIcon = findViewById(R.id.callicon);

        callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chatScreen.this, Calll.class);
                startActivity(intent);
            }
        });




        // Back Arrow Functionality
        back = findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    messagePhoto = selectedImage.toString();
                    StorageReference storeRef1 = FirebaseStorage.getInstance().getReference();
                    final StorageReference storeRef = storeRef1.child(user.getUid()+".jpg");
                    UploadTask uploadTask = storeRef.putFile(Uri.parse(messagePhoto));
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
                                messagePhoto = downloadUri.toString();

                            } else {
                                // Handle failures
                                // ...
                                Toast.makeText(getApplicationContext(),"Could not load Image Url",Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
                break;

            case pick_Image:
                if (resultCode == RESULT_OK) {

                }
                break;

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == pick_Image && resultCode == RESULT_OK){
//
//            imageUri = data.getData();
//
//            ls.add(new Mssg("", "", "", imageUri, false));
//
//            adapter.notifyDataSetChanged();
//
////            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
////                gallery.setImageBitmap(bitmap);
////            }catch (IOException e){
////                e.printStackTrace();
////            }
//        }
//    }
}