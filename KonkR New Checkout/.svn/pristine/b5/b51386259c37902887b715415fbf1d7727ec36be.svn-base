package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.konkr.Models.VerificationBadgeList;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.List;

/**
 * Created by Android on 6/18/2018.
 */

public class VerificationBadgeAdaplter extends RecyclerView.Adapter<VerificationBadgeAdaplter.ViewHolder> {
    Context context;
    List<VerificationBadgeList> vBadgeList;
    private ItemClickListener itemClickListener;

    public VerificationBadgeAdaplter(ItemClickListener itemClickListener, Context context, List<VerificationBadgeList> vbList) {
        this.context = context;
        this.vBadgeList=vbList;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.row_verification_badge, parent, false);
        return new ViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final VerificationBadgeList verificationBadgeList = vBadgeList.get (position);
        holder.ivImage.setImageResource (verificationBadgeList.getImageId ());
        holder.tvTitle.setText (verificationBadgeList.getTitle ());
        holder.tvDescription.setText (verificationBadgeList.getDescription ());
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick (view, position);

            }
        });

        if (verificationBadgeList.isChecked()) {
            holder.clMain.setBackground(context.getResources().getDrawable(R.color.verification_tab_text_color));
        } else {
            holder.clMain.setBackground(context.getResources().getDrawable(R.color.white));
        }
    }
    public interface ItemClickListener {
        void onItemClick(View view, int pos);
    }
    @Override
    public int getItemCount() {
        return vBadgeList.size ();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivImage;
        MyTextView tvTitle,tvDescription;
        ConstraintLayout clMain;

     public ViewHolder(View itemView) {
         super (itemView);
         ivImage=itemView.findViewById (R.id.ivImage);
         tvTitle=itemView.findViewById (R.id.tvTitle);
         tvDescription=itemView.findViewById (R.id.tvdescription);
         clMain=itemView.findViewById (R.id.clMain);

     }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick (view,getAdapterPosition ());
        }
    }
}
