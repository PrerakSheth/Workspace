package com.konkr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.MyMeals;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class ImageListAdpater extends RecyclerView.Adapter<ImageListAdpater.ViewHolder> {
    Activity context;
    ArrayList<UserDetails.UserDetailsBean.ImageArrayBean> imageArrayList;
    private OnRecyclerViewItemClickListener itemClick;

    public ImageListAdpater(Activity context,  OnRecyclerViewItemClickListener itemClick,   ArrayList<UserDetails.UserDetailsBean.ImageArrayBean> imageArrayList) {
        this.context = context;
        this.imageArrayList = imageArrayList;
        this.itemClick=itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_images, parent, false);
        return new ImageListAdpater.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LogM.LogE(position+"==url==>"+imageArrayList.get(position).getImage());

        if( !imageArrayList.get(position).toString().equals("")) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.evictFromCache(Uri.parse(imageArrayList.get(position).getImage()));
            imagePipeline.clearCaches();
            holder.ivImage.setImageURI(imageArrayList.get(position).getImage());
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClickListener(view,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }

    }
}
