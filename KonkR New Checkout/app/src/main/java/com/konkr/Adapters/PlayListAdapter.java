package com.konkr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.MyWorkouts;
import com.konkr.Models.SpotifyModel;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SpotifyModel.Item> playList;
    private OnRecyclerViewItemClickListener mListener;

    public PlayListAdapter(Context c, ArrayList<SpotifyModel.Item> playList, OnRecyclerViewItemClickListener mListener) {
        this.context = c;
        this.playList = playList;
        this.mListener = mListener;
    }

    @Override
    public PlayListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_play_list, parent, false);
        return new PlayListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayListAdapter.MyViewHolder holder, int position) {
        try {
            SpotifyModel.Item data = playList.get(position);

            holder.tvAlbumName.setText(data.getName());
            try {
                holder.ivAlbumPhoto.setImageURI(data.getImages().get(0).getUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (data.getTracks().getTotal() > 1) {
                holder.tvArtistName.setText("" + data.getTracks().getTotal() + " Songs available");
            } else if(data.getTracks().getTotal()<=1){
                holder.tvArtistName.setText("" + data.getTracks().getTotal() + " Song available");
            }

            holder.tvAlbumName.setSelected(true);
            holder.tvArtistName.setSelected(true);
            holder.itemView.setOnClickListener(view -> {
                mListener.onItemClickListener(view, position);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return playList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextView tvAlbumName, tvArtistName, tvEstTime;
        SimpleDraweeView ivAlbumPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvAlbumName = itemView.findViewById(R.id.tvSongTitle);
            tvArtistName = itemView.findViewById(R.id.tvSingerName);
            ivAlbumPhoto = itemView.findViewById(R.id.ivSongPhoto);
            tvEstTime = itemView.findViewById(R.id.tvEstTime);
            tvEstTime.setVisibility(View.GONE);

        }


    }
}
