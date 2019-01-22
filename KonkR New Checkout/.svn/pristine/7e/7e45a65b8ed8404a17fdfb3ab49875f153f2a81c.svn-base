package com.konkr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

/**
 * Created by Android on 6/30/2018.
 */

public class MiSuppliAdapter extends RecyclerView.Adapter<MiSuppliAdapter.ViewHolder> {
    Context context;
    ArrayList<MiSuppliment.SupplementsBean> cardListArray;
    ArrayList<UserDetails.UserDetailsBean.SupplementsBean> alProfileSupplement;

    private MiSuppliAdapter.ItemClickListener itemClickListener;

    public MiSuppliAdapter(MiSuppliAdapter.ItemClickListener itemClickListener, Context context, ArrayList<MiSuppliment.SupplementsBean> listArray, ArrayList<UserDetails.UserDetailsBean.SupplementsBean> alProfileSupplement) {
        this.context = context;
        this.cardListArray = listArray;
        this.alProfileSupplement = alProfileSupplement;
        this.itemClickListener = itemClickListener;
    }


//    public MiSuppliAdapter(Context context, MiSuppliAdapter.ItemClickListener itemClickListener, ArrayList<UserDetails.UserDetailsBean.SupplementsBean> alProfileSupplement) {
//        this.context = context;
//        this.alProfileSupplement = alProfileSupplement;
//        this.itemClickListener = itemClickListener;
//    }

    @Override
    public MiSuppliAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mi_suppliment, parent, false);

        return new MiSuppliAdapter.ViewHolder(itemView);

    }

    public interface ItemClickListener {
        void onItemClick(View view, int pos);
    }

    @Override
    public void onBindViewHolder(MiSuppliAdapter.ViewHolder holder, final int position) {
        int pos = position;


        if (cardListArray.size() > 0) {
            final MiSuppliment.SupplementsBean supplimentList = cardListArray.get(pos);
            holder.tvSupplimentName.setText(supplimentList.getSuppName());
            holder.tvSupplimentDes.setText(supplimentList.getSuppDetails());
            holder.ivSuppliment.setImageURI((supplimentList.getSuppPhoto()));
        } else if (alProfileSupplement.size() > 0) {
            final UserDetails.UserDetailsBean.SupplementsBean supplimentList = alProfileSupplement.get(pos);
            holder.tvSupplimentName.setText(supplimentList.getSuppName());
            holder.tvSupplimentDes.setText(supplimentList.getSuppDetails());
            holder.ivSuppliment.setImageURI((supplimentList.getSuppPhoto()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickListener.onItemClick(view, pos);
            }
        });


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemClickListener.onItemClick(view, position);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (alProfileSupplement.size() > 0) {
//            if (alProfileSupplement.size() < 4) {
//                return alProfileSupplement.size();
//            }
            return alProfileSupplement.size();
        }
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
