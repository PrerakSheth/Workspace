package com.konkr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konkr.R;

import java.util.ArrayList;

public class DropDownAdapter extends RecyclerView.Adapter <DropDownAdapter.ViewHolder> {
    Context context;
    ArrayList<String> dropDownArralyList;
    private DropDownAdapter.ItemClickListener itemClickListener;
    int pos;

    public DropDownAdapter(DropDownAdapter.ItemClickListener itemClickListener, Context context, ArrayList <String> listArray) {
        this.context = context;
        this.dropDownArralyList = listArray;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public DropDownAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.dropdown_item, parent, false);

        return new DropDownAdapter.ViewHolder(itemView);

    }

    public interface ItemClickListener {
        void onItemClick(View view, int  number);
    }

    @Override
    public void onBindViewHolder(DropDownAdapter.ViewHolder holder, final int position) {
     // final String cardList = dropDownArralyList.get (position);

        holder.tvNumber.setText(dropDownArralyList.get(position).toString());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LogM.LogE("you have clicked===>"+dropDownArralyList.get(position));
//            }
//        });

//        holder.tvNumber.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dropDownArralyList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView tvNumber;

        public ViewHolder(View v) {
            super (v);
            tvNumber = v.findViewById (R.id.tvNumber);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            itemClickListener.onItemClick (view, getAdapterPosition());

        }
    }
}

