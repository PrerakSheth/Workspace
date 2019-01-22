package com.konkr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.Partners;
import com.konkr.R;

import java.util.ArrayList;

/**
 * Created by Android on 6/13/2018.
 */

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.ViewHolder> {
    Context context;
    ArrayList<Partners.PartnerListBean> partnerListBeanArrayList;

    public PartnersAdapter(Context context, ArrayList<Partners.PartnerListBean> partnerListBeanArrayList) {
        this.context = context;
        this.partnerListBeanArrayList = partnerListBeanArrayList;
    }

    @Override
    public PartnersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_partners, parent, false);

        return new PartnersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Partners.PartnerListBean partnerList = partnerListBeanArrayList.get(position);
        holder.ivPartner.setImageURI(partnerList.getLogo());
    }

    @Override
    public int getItemCount() {
        return partnerListBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivPartner;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPartner = itemView.findViewById(R.id.ivPartner);
        }
    }
}
