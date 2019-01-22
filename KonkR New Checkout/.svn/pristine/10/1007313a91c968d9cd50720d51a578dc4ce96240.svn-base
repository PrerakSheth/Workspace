package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Models.MyMeals;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class SnackTabAdapter extends RecyclerView.Adapter<SnackTabAdapter.ViewHolder> {

    Context context;
    ArrayList<UserDetails.UserDetailsBean.Meal> snacksBeanArrayList;

    public SnackTabAdapter(Context context, ArrayList<UserDetails.UserDetailsBean.Meal> snacksBeanArrayList) {
        this.context = context;
        this.snacksBeanArrayList = snacksBeanArrayList;

    }


    @NonNull
    @Override
    public SnackTabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_meal_tab_item, parent, false);
        return new SnackTabAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackTabAdapter.ViewHolder holder, final int position) {
        final UserDetails.UserDetailsBean.Meal lunchBeanList = snacksBeanArrayList.get(position);

        holder.tvMealName.setText(lunchBeanList.getFoodName());
        holder.tvQuant.setText("" + lunchBeanList.getQuantity());
        holder.tvUnit.setText(lunchBeanList.getUnit());
        holder.tvMealName.setSelected(true);
        holder.tvQuant.setSelected(true);
        holder.tvUnit.setSelected(true);
    }


    @Override
    public int getItemCount() {
        return snacksBeanArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MyTextView tvMealName, tvQuant, tvUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuant = itemView.findViewById(R.id.tvQuant);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
//            tvQuant.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimension(R.dimen._8ssp));
//            tvMealName.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimension(R.dimen._8ssp));
//            tvUnit.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimension(R.dimen._8ssp));
        }

    }
}


