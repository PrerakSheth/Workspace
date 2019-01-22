package com.konkr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.MyWorkouts;
import com.konkr.R;
import com.konkr.Utils.GlobalData;

import java.util.ArrayList;

public class MyWorkoutsAdapter extends RecyclerView.Adapter<MyWorkoutsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MyWorkouts.WorkoutsBean> alMyWorkouts;
    private OnRecyclerViewItemClickListener mListener;

    public MyWorkoutsAdapter(Context c, ArrayList<MyWorkouts.WorkoutsBean> alMyWorkouts, OnRecyclerViewItemClickListener mListener) {
        this.context = c;
        this.alMyWorkouts = alMyWorkouts;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_my_workouts, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            MyWorkouts.WorkoutsBean data = alMyWorkouts.get(position);
            if (data.getWorkoutMedia().size() > 0) {
                if (data.getWorkoutMedia().get(0).getMediaType().equalsIgnoreCase("" + GlobalData.MEDIA_TYPE_VIDEO)) { // Video
                    Glide.with(context).asBitmap()
                            .load(data.getWorkoutMedia().get(0).getVideoThumbImage())
                            .into(holder.ivWorkouts);
                } else if (data.getWorkoutMedia().get(0).getMediaType().equalsIgnoreCase("" + GlobalData.MEDIA_TYPE_IMAGE)) {
                    Glide.with(context).asBitmap()
                            .load(data.getWorkoutMedia().get(0).getUrl())
                            .into(holder.ivWorkouts);
                } else {
                    holder.ivWorkouts.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
                }
            } else {
                holder.ivWorkouts.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
            }
            holder.tvWorkOutName.setText(data.getWorkoutName());
            holder.tvWorkOutCategory.setText(data.getWorkoutCategoryName());
            int time = (data.getWorkoutDuration().getHour() * 60) + data.getWorkoutDuration().getMin();
            holder.tvEstTime.setText(context.getResources().getString(R.string.mi_training_est) + " " + time + "minutes");
            holder.tvExeType.setText(data.getExcerciseName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alMyWorkouts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivWorkouts;
        TextView tvWorkOutName, tvWorkOutCategory, tvEstTime, tvExeType;
        OnRecyclerViewItemClickListener mListener;

        public MyViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            this.mListener = mListener;
            ivWorkouts = itemView.findViewById(R.id.ivWorkouts);
            tvWorkOutName = itemView.findViewById(R.id.tvWorkOutName);
            tvWorkOutCategory = itemView.findViewById(R.id.tvWorkOutCategory);
            tvEstTime = itemView.findViewById(R.id.tvEstTime);
            tvExeType = itemView.findViewById(R.id.tvExeType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClickListener(v, getAdapterPosition());
        }
    }
}
