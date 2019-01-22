package com.konkr.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.MiTrainingAddWorkout;
import com.konkr.R;
import com.konkr.Utils.GlobalData;

import java.util.ArrayList;

public class HorizontalVideoPhotoAdapter extends RecyclerView.Adapter<HorizontalVideoPhotoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MiTrainingAddWorkout> alWorkoutImagesAndVideos;
    private OnRecyclerViewItemClickListener mListener;

    public HorizontalVideoPhotoAdapter(Context c, ArrayList<MiTrainingAddWorkout> alWorkoutImagesAndVideos, OnRecyclerViewItemClickListener mListener) {
        this.context = c;
        context.getClass().getSimpleName();
        this.alWorkoutImagesAndVideos = alWorkoutImagesAndVideos;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (context.getClass().getSimpleName().contains("MiTrainingActivity")) {
            view = LayoutInflater.from(context).inflate(R.layout.row_item_horizontal_photos_small, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.row_item_horizontal_photos, parent, false);
        }
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            MiTrainingAddWorkout data = alWorkoutImagesAndVideos.get(position);
            if (data.getMediaType() == GlobalData.MEDIA_TYPE_VIDEO) {
                holder.ibVideoIcon.setVisibility(View.VISIBLE);
//                Glide.with(context).asBitmap()
//                        .load(data.getVideoThumbImage())
//                        .into(holder.ivWorkouts);
                holder.ivWorkouts.setImageURI(Uri.parse(data.getVideoThumbImage()));
            } else {
                holder.ibVideoIcon.setVisibility(View.GONE);
//                Glide.with(context).asBitmap()
//                        .load(data.getImageURL())
//                        .into(holder.ivWorkouts);
                holder.ivWorkouts.setImageURI(Uri.parse(data.getImageURL()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alWorkoutImagesAndVideos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView ivWorkouts;
        ImageButton ibVideoIcon;
        OnRecyclerViewItemClickListener mListener;

        public MyViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            this.mListener = mListener;
            ivWorkouts = itemView.findViewById(R.id.ivWorkouts);
            ibVideoIcon = itemView.findViewById(R.id.ibVideoIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClickListener(v, getAdapterPosition());
        }
    }
}
