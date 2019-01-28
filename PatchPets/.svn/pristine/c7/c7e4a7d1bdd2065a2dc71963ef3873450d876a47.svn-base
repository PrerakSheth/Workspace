package com.patchpets.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patchpets.R;

import java.util.List;

public class BusinessProfileServiceAdapter extends RecyclerView.Adapter<BusinessProfileServiceAdapter.MyViewHolder> {
    List<String> mydata;

    public BusinessProfileServiceAdapter(List<String> mydata) {
        this.mydata = mydata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_business_services, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(mydata.get(i));

    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }


}
