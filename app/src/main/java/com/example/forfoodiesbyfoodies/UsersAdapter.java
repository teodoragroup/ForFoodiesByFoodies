package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Abstract class with abstract methods
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

 //Declaration
    ArrayList<Users> list;
    UsersHolder.OnCardClickListener listener;
    public  UsersAdapter(ArrayList<Users> list, UsersHolder.OnCardClickListener _listener)
    {
        this.list = list;
        this.listener = _listener;
    }
    @NonNull
    @Override
 //Creates an object and links it to the reviewcard (the pattern)
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard,
                parent, false);
        UsersHolder holder = new UsersHolder(v, listener);
        return holder;
    }
    @Override
 //Poulate the views
    public void onBindViewHolder(@NonNull UsersAdapter.UsersHolder holder, int position) {

        holder.tv.setText(list.get(position).getEmail());
        holder.text.setText("Email Address: ");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

 //Holds one card
    public static class UsersHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv, text;
        OnCardClickListener listener;

        public UsersHolder(@NonNull View itemView, OnCardClickListener _listener) {
            super(itemView);

 //Link the views with their xml counterparts in the reviewcard
            tv = itemView.findViewById(R.id.reviewcard_tv);
            text = itemView.findViewById(R.id.reviewcard_text);
            this.listener = _listener;
            itemView.setOnClickListener(this);
        }

        public interface OnCardClickListener
        {
            public void onCardClick(int i);
        }

        @Override
        public void onClick(View v) {
            this.listener.onCardClick(getAdapterPosition());
        }
    }



}
