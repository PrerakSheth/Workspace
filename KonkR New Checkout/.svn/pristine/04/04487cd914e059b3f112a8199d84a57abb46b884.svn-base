package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class BreakfastTabAdapter extends RecyclerView.Adapter<BreakfastTabAdapter.ViewHolder> {
    Context context;
    ArrayList<UserDetails.UserDetailsBean.Meal> breakfastArrayList;

    public BreakfastTabAdapter(Context context, ArrayList<UserDetails.UserDetailsBean.Meal> breakfastArrayList) {
        this.context = context;
        this.breakfastArrayList = breakfastArrayList;

    }


    @NonNull
    @Override
    public BreakfastTabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_meal_tab_item, parent, false);
        return new BreakfastTabAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastTabAdapter.ViewHolder holder, final int position) {
        final UserDetails.UserDetailsBean.Meal breakFastList = breakfastArrayList.get(position);
//        if(!breakFastList.getMealPhoto().isEmpty()&& breakFastList.getMealPhoto()!=null) {
//            holder.ivMealPhoto.setImageURI(Uri.parse(breakFastList.getMealPhoto()));
//        }
        holder.tvMealName.setText(breakFastList.getFoodName());
        holder.tvQuant.setText("" + breakFastList.getQuantity());
        holder.tvUnit.setText(breakFastList.getUnit());

        holder.tvMealName.setSelected(true);
        holder.tvQuant.setSelected(true);
        holder.tvUnit.setSelected(true);
    }


    @Override
    public int getItemCount() {
        return breakfastArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MyTextView tvMealName, tvQuant, tvUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuant = itemView.findViewById(R.id.tvQuant);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
//        tvQuant.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimension(R.dimen._8ssp));
//        tvMealName.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimension(R.dimen._8ssp));
//        tvUnit.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimension(R.dimen._8ssp));

        }

    }
}


