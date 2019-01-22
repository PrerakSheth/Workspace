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
import com.konkr.Models.ExpressionMedia;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class GoalMediaAdapter extends RecyclerView.Adapter<GoalMediaAdapter.ViewHolder>{
    Context context;
    private ArrayList<ExpressionMedia.GoalsBean> goalArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public GoalMediaAdapter(Context context, ArrayList<ExpressionMedia.GoalsBean> goalArrayList, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.context = context;
        this.goalArrayList=goalArrayList;
        this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;
    }


    @NonNull
    @Override
    public GoalMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.row_goal, parent, false);
        return new GoalMediaAdapter.ViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalMediaAdapter.ViewHolder holder, final int position) {
        final ExpressionMedia.GoalsBean goaList = goalArrayList.get (position);
        holder.ivUserPhoto.setImageURI (goaList.getProfilePic ());
        holder.tvUserName.setText (goaList.getFirstName ()+" "+goaList.getLastName());

        switch (goaList.getBadge ()) {

            case "2":
                holder.ivBadgePhoto.setImageResource (R.drawable.celebrity);
                break;
            case "3":
                holder.ivBadgePhoto.setImageResource (R.drawable.inflencer);
                break;
            case "4":
                holder.ivBadgePhoto.setImageResource (R.drawable.sponsor);
                break;
            default:
                holder.ivBadgePhoto.setVisibility (View.GONE);
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
//        holder.itemView.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View view) {
//                itemClickListener.onItemClick (view, position);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return goalArrayList.size ();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivUserPhoto,ivBadgePhoto;
        MyTextView tvUserName;

        public ViewHolder(View itemView) {
            super (itemView);
            ivUserPhoto=itemView.findViewById (R.id.ivUserPhoto);
            tvUserName=itemView.findViewById (R.id.tvUserName);
            ivBadgePhoto=itemView.findViewById (R.id.ivBadgePhoto);
        }

    }
}

