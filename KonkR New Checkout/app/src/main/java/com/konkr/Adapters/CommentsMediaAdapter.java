package com.konkr.Adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.Comments;
import com.konkr.Models.MeidaPhotoComment;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;
import java.util.List;

public class CommentsMediaAdapter extends RecyclerView.Adapter<CommentsMediaAdapter.ViewHolder> {
    Activity context;
    List<MeidaPhotoComment.CommentsBean> commentArrayList;

    public CommentsMediaAdapter(Activity context, List<MeidaPhotoComment.CommentsBean> commentArrayList) {
        this.context = context;
        this.commentArrayList = commentArrayList;
    }


    @NonNull
    @Override
    public CommentsMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comments, parent, false);
        return new CommentsMediaAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsMediaAdapter.ViewHolder holder, final int position) {
        final MeidaPhotoComment.CommentsBean comList = commentArrayList.get(position);
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
