package com.patchpets.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    ArrayList<String> mydata;
    OnRemove onRemove;
    String type = "";

    public FilterAdapter(ArrayList<String> mydata, OnRemove onRemove, String type) {
        this.mydata = mydata;
        this.onRemove = onRemove;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_filter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(mydata.get(i));
        myViewHolder.ivremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* mydata.remove(i);
                notifyDataSetChanged();*/
                onRemove.ondataRemove(i, type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView ivremove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            ivremove = itemView.findViewById(R.id.ivremove);
        }
    }

    public interface OnRemove {
        void ondataRemove(int pos, String type);
    }
}
