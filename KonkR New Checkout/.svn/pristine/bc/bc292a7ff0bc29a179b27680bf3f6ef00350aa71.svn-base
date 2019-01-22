package com.konkr.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Activities.ProfileActivity;
import com.konkr.Models.Comments;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

/**
 * Created by Android on 6/19/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    Activity context;
    ArrayList<Comments.CommentsOnThisFeedBean> commentArrayList;

    public CommentsAdapter(Activity context, ArrayList<Comments.CommentsOnThisFeedBean> commentArrayList) {
        this.context = context;
        this.commentArrayList = commentArrayList;
    }


    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comments, parent, false);
        return new CommentsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, final int position) {
        final Comments.CommentsOnThisFeedBean comList = commentArrayList.get(position);
        holder.ivUserPhoto.setImageURI(Uri.parse(comList.getProfilePic()));
        holder.tvUserName.setText(comList.getFirstName() + " " + comList.getLastName());
        holder.tvComment.setText(comList.getComment());
        String badge = comList.getBadge();
        if (badge.equalsIgnoreCase("1")) {
            holder.ivBadgePhoto.setVisibility(View.GONE);
        } else if (badge.equalsIgnoreCase("2")) {
            holder.ivBadgePhoto.setVisibility(View.VISIBLE);
            holder.ivBadgePhoto.setBackground(context.getResources().getDrawable(R.drawable.celebrity));
        } else if (badge.equalsIgnoreCase("3")) {
            holder.ivBadgePhoto.setVisibility(View.VISIBLE);
            holder.ivBadgePhoto.setBackground(context.getResources().getDrawable(R.drawable.inflencer));
        } else if (badge.equalsIgnoreCase("4")) {
            holder.ivBadgePhoto.setVisibility(View.VISIBLE);
            holder.ivBadgePhoto.setBackground(context.getResources().getDrawable(R.drawable.sponsor));
        }

        holder.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
//                intent.putExtra(GlobalData.FROM, GlobalData.WORKOUT);
                intent.putExtra(GlobalData.OTHER_USER_ID, comList.getUserId());
                context.startActivity(intent);
            }
        });

        holder.tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
//                intent.putExtra(GlobalData.FROM, GlobalData.WORKOUT);
                intent.putExtra(GlobalData.OTHER_USER_ID, comList.getUserId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivUserPhoto, ivBadgePhoto;
        MyTextView tvComment, tvUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvComment = itemView.findViewById(R.id.tvComments);
            ivBadgePhoto = itemView.findViewById(R.id.ivBadgePhoto);

        }

    }
}
