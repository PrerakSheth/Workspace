package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.Admiring;
import com.konkr.Models.ExpressionList;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

/**
 * Created by Android on 6/20/2018.
 */

public class AdmiringAdapter extends RecyclerView.Adapter<AdmiringAdapter.ViewHolder>{
    Context context;
    ArrayList<ExpressionList.Admiring> admiringArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    public AdmiringAdapter(Context context, ArrayList<ExpressionList.Admiring> admiringArrayList,OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.context = context;
        this.admiringArrayList=admiringArrayList;
        this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;
    }


    @NonNull
    @Override
    public AdmiringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.row_admiring, parent, false);
        return new AdmiringAdapter.ViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmiringAdapter.ViewHolder holder, final int position) {
        final ExpressionList.Admiring admiringList = admiringArrayList.get (position);
        holder.ivUserPhoto.setImageURI (admiringList.getProfilePic ());
        holder.tvUserName.setText (admiringList.getFirstName ()+" "+admiringList.getLastName());
        switch (admiringList.getBadge ()) {

            case 2:
                holder.ivBadgePhoto.setImageResource (R.drawable.celebrity);
                break;
            case 3:
                holder.ivBadgePhoto.setImageResource (R.drawable.inflencer);
                break;
            case 4:
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
    }

    @Override
    public int getItemCount() {
        return admiringArrayList.size ();
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