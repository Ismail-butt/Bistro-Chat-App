package com.IsmailUsman_i180516_i180634;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Message> messages;
    private List<String> keys;
    DatabaseReference ref;
    Contact contact;
    RecyclerView r;
    private Context c;

    public ChatAdapter(List<Message> msgs, DatabaseReference msgref, List<String> keys, Contact co, Context c, RecyclerView rv) {

        this.ref = msgref;
        this.keys = keys;
        messages = msgs;
        r = rv;


        this.contact = co;
        this.c = c;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.chat_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.msg.setText(messages.get(position).getContent());
        holder.messageTime.setText(messages.get(position).getTime());

        holder.liked.setVisibility(View.GONE);
        if(messages.get(position).getIsPhoto()){
            holder.msg.setVisibility(View.GONE);
            holder.messagePhoto.setVisibility(View.VISIBLE);
            Glide.with(c).load(Uri.parse(messages.get(position).getPhoto())).into(holder.messagePhoto);
            if(messages.get(position).getSender().equals(contact.getEmail()))
            {
                holder.image.setVisibility(View.GONE);
                holder.chat_wrapper.setGravity(Gravity.END);


            }
            else{
                holder.image.setVisibility(View.VISIBLE);
                holder.chat_wrapper.setGravity(Gravity.START);
                Glide.with(c).load(Uri.parse(messages.get(position).getSenderPhoto())).into(holder.image);

            }

        }
        else{
            holder.messagePhoto.setVisibility(View.GONE);
            holder.msg.setVisibility(View.VISIBLE);
            if(messages.get(position).getSender().equals(contact.getEmail()))
            {
                holder.image.setVisibility(View.GONE);
                holder.chat_wrapper.setGravity(Gravity.END);
                holder.msg.setBackground(ContextCompat.getDrawable(c,R.drawable.sender_message2));
                holder.msg.setTextColor(ContextCompat.getColor(c,R.color.white));

            }
            else{
                holder.image.setVisibility(View.VISIBLE);
                holder.chat_wrapper.setGravity(Gravity.START);
                Glide.with(c).load(Uri.parse(messages.get(position).getSenderPhoto())).into(holder.image);
                holder.msg.setBackground(ContextCompat.getDrawable(c,R.drawable.sender_message));
                holder.msg.setTextColor(ContextCompat.getColor(c,R.color.black));
            }
        }
        if(messages.get(position).isLiked()){
            holder.liked.setVisibility(View.VISIBLE);
        }
        holder.chat_wrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.liked.setVisibility(View.VISIBLE);
//                DatabaseReference ref2 = ref.child(keys.get(position)).child("liked");
//                ref2.setValue(true);

                return true;
            }
        });




    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        TextView messageTime;
        ImageView image;
        ImageView messagePhoto;
        ImageView liked;
        RelativeLayout chat_wrapper;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.msg =itemView.findViewById(R.id.chat_msg);
            this.image =itemView.findViewById(R.id.senderImage);
            this.messagePhoto =itemView.findViewById(R.id.chat_image);
            this.liked =itemView.findViewById(R.id.likeMessage);
            this.messageTime =itemView.findViewById(R.id.messageTime);
            this.chat_wrapper =itemView.findViewById(R.id.chat_wrapper);



        }
    }
}
