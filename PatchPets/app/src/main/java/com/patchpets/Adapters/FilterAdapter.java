package com.patchpets.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.interfaces.OnRemove;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    private ArrayList<String> myData;
    private OnRemove onRemove;
    private String type = "";

    public FilterAdapter(ArrayList<String> myData, OnRemove onRemove, String type) {
        this.myData = myData;
        this.onRemove = onRemove;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_filter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(myData.get(i));
        myViewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemove.onDataRemove(i, type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView ivRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            ivRemove = itemView.findViewById(R.id.ivRemove);
        }
    }
}
