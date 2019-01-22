package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.ExpressionMedia;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class InspiringMediaAdapter extends RecyclerView.Adapter<InspiringMediaAdapter.ViewHolder> {
    Context context;
    //    ArrayList<Inspiring> inspiringArrayList;

    private ArrayList<ExpressionMedia.InspiringBean> inspiringArrayList;

    public InspiringMediaAdapter(Context context, ArrayList<ExpressionMedia.InspiringBean> inspiringArrayList) {
        this.context = context;
        this.inspiringArrayList = inspiringArrayList;
    }


    @NonNull
    @Override
    public InspiringMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_inspiring, parent, false);
        return new InspiringMediaAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InspiringMediaAdapter.ViewHolder holder, final int position) {
        final ExpressionMedia.InspiringBean inspiringList = inspiringArrayList.get(position);
        holder.ivUserPhoto.setImageURI(inspiringList.getProfilePic());

        holder.tvUserName.setText(inspiringList.getFirstName() + " " + inspiringList.getLastName());
        switch (inspiringList.getBadge()) {

            case "2":
                holder.ivBadgePhoto.setImageResource(R.drawable.celebrity);
                break;
            case "3":
                holder.ivBadgePhoto.setImageResource(R.drawable.inflencer);
                break;
            case "4":
                holder.ivBadgePhoto.setImageResource(R.drawable.sponsor);
                break;
            default:
                holder.ivBadgePhoto.setVisibility(View.GONE);
                break;


        }

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
