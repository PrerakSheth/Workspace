package com.patchpets.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.patchpets.R;
import com.patchpets.model.responseModel.PartnerListResponse;
import com.patchpets.utils.CircleTransform;
import com.patchpets.utils.MyApp;

import java.util.ArrayList;

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PartnerListResponse.PartnerListBean> alPartners;

    public PartnersAdapter(Context context, ArrayList<PartnerListResponse.PartnerListBean> alPartners) {
        this.context = context;
        this.alPartners = alPartners;
    }

    @Override
    public PartnersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_partners, parent, false);
        return new PartnersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyApp.picasso
                .load(alPartners.get(position).getLogo())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .fit().centerCrop()
                .transform(new CircleTransform())
                .into(holder.ivPartner);
    }

    @Override
    public int getItemCount() {
        return alPartners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPartner;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPartner = itemView.findViewById(R.id.ivPartner);
        }
    }
}
