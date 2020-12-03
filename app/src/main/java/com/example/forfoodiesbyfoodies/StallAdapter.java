package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Abstract class with abstract methods
public class StallAdapter extends RecyclerView.Adapter<StallAdapter.StallHolder> {

 //Declaration
    ArrayList<Stall> list;
    StallHolder.OnCardClickListener listener;

    public StallAdapter(ArrayList<Stall> list, StallHolder.OnCardClickListener _listener){

        this.list = list;
        this.listener = _listener;
    }
    @NonNull
    @Override
    //creating object and link it to the card (the pattern)
    public StallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stallcard,
                parent, false);
        StallHolder holder = new StallHolder(v, listener);
        return holder;
    }
    @Override
//populate the views
    public void onBindViewHolder(@NonNull StallHolder holder, int position) {
        holder.tv.setText(list.get(position).getNameS());
//Image library for Android for loading and displaying images
        Picasso.get().load(list.get(position).getUrlS()).fit().into(holder.iv);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

 //Holds one card
    public static class StallHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        ImageView iv;
        OnCardClickListener listener;

        public StallHolder(@NonNull View itemView, OnCardClickListener _listener) {
            super(itemView);
//Link the views with their xml counterparts in the stallcard
            tv = itemView.findViewById(R.id.stall_card_tv);
            iv = itemView.findViewById(R.id.stall_card_image);

            this.listener = _listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.listener.onCardClick(getAdapterPosition());
        }


        public  interface OnCardClickListener
        {
            public void onCardClick(int i);
        }


    }
}
