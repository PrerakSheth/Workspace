package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.Inspiring;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

/**
 * Created by Android on 6/20/2018.
 */

public class InspiringAdapter extends RecyclerView.Adapter<InspiringAdapter.ViewHolder> {
    Context context;
    //    ArrayList<Inspiring> inspiringArrayList;

    private ArrayList<ExpressionList.Inspiring> inspiringArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    public InspiringAdapter(Context context, ArrayList<ExpressionList.Inspiring> inspiringArrayList,OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.context = context;
        this.inspiringArrayList = inspiringArrayList;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }


    @NonNull
    @Override
    public InspiringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_inspiring, parent, false);
        return new InspiringAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InspiringAdapter.ViewHolder holder, final int position) {
        final ExpressionList.Inspiring inspiringList = inspiringArrayList.get(position);
        holder.ivUserPhoto.setImageURI(inspiringList.getProfilePic());

        holder.tvUserName.setText(inspiringList.getFirstName() + " " + inspiringList.getLastName());
        switch (inspiringList.getBadge()) {

            case 2:
                holder.ivBadgePhoto.setImageResource(R.drawable.celebrity);
                break;
            case 3:
                holder.ivBadgePhoto.setImageResource(R.drawable.inflencer);
                break;
            case 4:
                holder.ivBadgePhoto.setImageResource(R.drawable.sponsor);
                break;
            default:
                holder.ivBadgePhoto.setVisibility(View.GONE);
                break;


        }

        holder.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClickListener(holder.ivUserPhoto,position);
            }
        });
        holder.tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClickListener( holder.tvUserName,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return inspiringArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivUserPhoto, ivBadgePhoto;
        MyTextView tvUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivBadgePhoto = itemView.findViewById(R.id.ivBadgePhoto);
        }

    }
}
