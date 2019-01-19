package com.patchpets.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.utils.MyApp;

import java.util.List;


public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder> {

    private Context context;
    onPepoleDelete pepoleDelete;
List<String> peopleList;
    public PeopleAdapter(Context c,List<String> peopleList, onPepoleDelete pepoleDelete) {
        this.context = c;
        this.pepoleDelete = pepoleDelete;
        this.peopleList=peopleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_group_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvTitle.setText(peopleList.get(position));
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pepoleDelete.onDeleteClick(position);
            }
        });
        MyApp.picasso
                .load("https://images-na.ssl-images-amazon.com/images/I/51ltYuAUfXL._SY355_.jpg")
                .into(holder.ivProfile);
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivDelete;

        TextView tvTitle;
        ConstraintLayout row_main;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            row_main = itemView.findViewById(R.id.row_main);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

    public interface onPepoleDelete {
        public void onDeleteClick(int position);
    }
}
