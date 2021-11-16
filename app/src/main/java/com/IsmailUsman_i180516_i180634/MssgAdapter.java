package com.IsmailUsman_i180516_i180634;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MssgAdapter extends RecyclerView.Adapter<MssgAdapter.MyViewHolder>{

    List<Mssg> ls;
    Context c;

    public MssgAdapter(List<Mssg> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MssgAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.mssg_row, parent, false);
        return new MssgAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MssgAdapter.MyViewHolder holder, int position) {

        if(ls.get(position).getMssgType()){
            holder.my_image_messag.setVisibility(View.GONE);
            holder.my_text_message.setVisibility(View.VISIBLE);

            holder.my_text_message.setText(ls.get(position).getTextMssg());
        }else{
            holder.my_text_message.setVisibility(View.GONE);
            holder.my_image_messag.setVisibility(View.VISIBLE);

            holder.my_image_messag.setImageURI(ls.get(position).getImgMssg());
        }


    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView my_image_messag;
        TextView my_text_message;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            my_image_messag = itemView.findViewById(R.id.my_image_message);

            my_text_message = itemView.findViewById(R.id.my_text_message);
        }
    }
}
