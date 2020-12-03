package com.example.forfoodiesbyfoodies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Abstract class with abstract methods
public class ReviewRestAdapter extends  RecyclerView.Adapter<ReviewRestAdapter.ReviewRestHolder> {

//Declaration
    ArrayList<ReviewsRest> list;
    ReviewRestHolder.OnCardClickListener listener;

    public ReviewRestAdapter(ArrayList<ReviewsRest> list, ReviewRestHolder.OnCardClickListener _listener)
    {
        this.list = list;
        this.listener = _listener;
    }
    @NonNull
    @Override
 //Creates an object and links it to the reviewcard (the pattern)
    public ReviewRestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard,
                parent, false);
        ReviewRestHolder holder = new ReviewRestHolder(v, listener);
        return holder;
    }
    @Override
//Poulate the views
    public void onBindViewHolder(@NonNull ReviewRestAdapter.ReviewRestHolder holder, int position) {

        holder.tv.setText(list.get(position).getAuthorR());
        holder.text.setText("Posted By: ");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

//Holds one card
    public static class ReviewRestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv, text;
        OnCardClickListener listener;

        public ReviewRestHolder(@NonNull View itemView, OnCardClickListener _listener) {
            super(itemView);

 //Link the views with their xml counterparts in the reviewcard
            tv = itemView.findViewById(R.id.reviewcard_tv);
            text = itemView.findViewById(R.id.reviewcard_text);
            this.listener = _listener;
            itemView.setOnClickListener(this);
        }
        public interface OnCardClickListener {
            public void onCardClick(int i);
        }
        @Override
        public void onClick(View v) {
            this.listener.onCardClick(getAdapterPosition());

        }
    }
}
