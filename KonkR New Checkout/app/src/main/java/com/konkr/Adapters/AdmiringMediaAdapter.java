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

public class AdmiringMediaAdapter extends RecyclerView.Adapter<AdmiringMediaAdapter.ViewHolder>{
    Context context;
    ArrayList<ExpressionMedia.AdmiringBean> admiringArrayList;

    public AdmiringMediaAdapter(Context context, ArrayList<ExpressionMedia.AdmiringBean> admiringArrayList) {
        this.context = context;
        this.admiringArrayList=admiringArrayList;
    }


    @NonNull
    @Override
    public AdmiringMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.row_admiring, parent, false);
        return new AdmiringMediaAdapter.ViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmiringMediaAdapter.ViewHolder holder, final int position) {
        final ExpressionMedia.AdmiringBean admiringList = admiringArrayList.get (position);
        holder.ivUserPhoto.setImageURI (admiringList.getProfilePic ());
        holder.tvUserName.setText (admiringList.getFirstName ()+" "+admiringList.getLastName());
        switch (admiringList.getBadge ()) {

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
