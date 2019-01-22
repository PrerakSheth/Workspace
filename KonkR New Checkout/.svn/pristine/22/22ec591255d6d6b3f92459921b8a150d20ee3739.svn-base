package com.konkr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Activities.MyWorkoutDetailsActivity;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.HomeFeed;
import com.konkr.R;
import com.konkr.Utils.GlobalData;

import java.util.ArrayList;

/**
 * Created by Android on 6/13/2018.
 */

public class HomeFeedMiTrainingAdapter extends RecyclerView.Adapter<HomeFeedMiTrainingAdapter.ViewHolder> {
    Activity context;
    private OnRecyclerViewItemClickListener itemClick;
    ArrayList<HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean> alHFMiTraining;
    int pos;

    public HomeFeedMiTrainingAdapter(int pos,OnRecyclerViewItemClickListener itemClick, Activity context, ArrayList<HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean> alHFMiTraining) {
        this.context = context;
        this.itemClick = itemClick;
        this.alHFMiTraining = alHFMiTraining;
        this.pos=pos;
    }

    @Override
    public HomeFeedMiTrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_home_feed_mi_training, parent, false);

        return new HomeFeedMiTrainingAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean partnerList = alHFMiTraining.get(position);
        holder.setIsRecyclable(false);
        if (Integer.parseInt(partnerList.getMediaType()) == GlobalData.MEDIA_TYPE_VIDEO) {
            holder.ibVideoIcon.setVisibility(View.VISIBLE);
            holder.ivHomeFeedMiTraining.setImageURI(Uri.parse(partnerList.getVideoThumbImage()));
        } else {
            holder.ibVideoIcon.setVisibility(View.GONE);
            holder.ivHomeFeedMiTraining.setImageURI(Uri.parse(partnerList.getUrl()));
        }

        holder.ivHomeFeedMiTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClickListener(view, pos);
            }
        });
    }

    public interface MyReciclerClickListener {
        void onItemClick(View view, int pos);
    }

    @Override
    public int getItemCount() {
        return alHFMiTraining.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivHomeFeedMiTraining;
        ImageButton ibVideoIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ivHomeFeedMiTraining = itemView.findViewById(R.id.ivHomeFeedMiTraining);
            ibVideoIcon = itemView.findViewById(R.id.ibVideoIcon);
        }
    }
}
