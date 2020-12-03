package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Abstract class with abstract methods
public class ReviewStallAdapter extends RecyclerView.Adapter<ReviewStallAdapter.ReviewStallHolder> {

 //Declaration
    ArrayList<ReviewsStall> list;
    ReviewStallHolder.OnCardClickListener listener;

    public ReviewStallAdapter(ArrayList<ReviewsStall> list, ReviewStallHolder.OnCardClickListener _listener)
    {
        this.list = list;
        this.listener = _listener;
    }
    @NonNull
    @Override
 //Creates an object and links it to the reviewcard (the pattern)
    public ReviewStallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard,
                parent, false);
        ReviewStallHolder holder = new ReviewStallHolder(v, listener);
        return holder;
    }
    @Override
//Populate the views
    public void onBindViewHolder(@NonNull ReviewStallAdapter.ReviewStallHolder holder, int position) {

        holder.tv.setText(list.get(position).getAuthorS());
        holder.text.setText("Posted By: ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

 //Holds one card
    public static class ReviewStallHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv, text;
        OnCardClickListener listener;

        public ReviewStallHolder(@NonNull View itemView, OnCardClickListener _listener) {
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
