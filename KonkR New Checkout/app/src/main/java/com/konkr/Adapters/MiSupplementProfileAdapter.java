package com.konkr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

/**
 * Created by Android on 6/30/2018.
 */

public class MiSupplementProfileAdapter extends RecyclerView.Adapter<MiSupplementProfileAdapter.ViewHolder> {
    Context context;
    ArrayList<UserDetails.UserDetailsBean.SupplementsBean> cardListArray;

    private MiSupplementProfileAdapter.ItemClickListener itemClickListener;

    public MiSupplementProfileAdapter(Context context, ArrayList<UserDetails.UserDetailsBean.SupplementsBean> listArray) {
        this.context = context;
        this.cardListArray = listArray;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MiSupplementProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mi_suppliment, parent, false);

        return new MiSupplementProfileAdapter.ViewHolder(itemView);

    }

    public interface ItemClickListener {
        void onItemClick(View view, int pos);
    }

    @Override
    public void onBindViewHolder(MiSupplementProfileAdapter.ViewHolder holder, final int position) {
        final UserDetails.UserDetailsBean.SupplementsBean supplimentList = cardListArray.get(position);


        holder.tvSupplimentName.setText(supplimentList.getSuppName());
        holder.tvSupplimentDes.setText(supplimentList.getSuppDetails());
        holder.ivSuppliment.setImageURI((supplimentList.getSuppPhoto()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickListener.onItemClick(view, position);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SimpleDraweeView ivSuppliment;
        MyTextView tvSupplimentName, tvSupplimentDes;

        public ViewHolder(View v) {
            super(v);

            ivSuppliment = v.findViewById(R.id.ivSuppliment);
            tvSupplimentName = v.findViewById(R.id.tvSupplimentName);
            tvSupplimentDes = v.findViewById(R.id.tvSupplimentDes);
        }
    }
}
