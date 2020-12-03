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
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>{

 //Declaration
    ArrayList<Rest> list;
    RestaurantHolder.OnCardClickListener listener;

    public RestaurantAdapter(ArrayList<Rest> list, RestaurantHolder.OnCardClickListener _listener)
    {
        this.list = list;
        this.listener = _listener;
    }
    @NonNull
    @Override
//Creates an object and links it to the stallcard (the pattern)
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stallcard,
                parent, false);
        RestaurantHolder holder = new RestaurantHolder(view, listener);
        return holder;
    }
    @Override
 //Populate the views
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantHolder holder, int position) {
        holder.tv.setText(list.get(position).getNameR());
//Image library for Android for loading and displaying images
        Picasso.get().load(list.get(position).getUrlR()).fit().into(holder.iv);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

 //Holds one card
    public static class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv;
        ImageView iv;
        OnCardClickListener listener;

        public RestaurantHolder(@NonNull View itemView, OnCardClickListener _listener) {
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

        public interface OnCardClickListener
        {
            public void onCardClick(int i);
        }
    }
}

