package com.konkr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Activities.AddMiMealActivity;
import com.konkr.Activities.MealDetailsActivity;
import com.konkr.Models.MyMeals;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.ViewHolder> {

    Context context;
    ArrayList<MyMeals.MealsDinnerBean> dinnerBeanArrayList;
    int otherUserId;

    public DinnerAdapter(Context context, ArrayList<MyMeals.MealsDinnerBean> dinnerBeanArrayList) {
        this.context = context;
        this.dinnerBeanArrayList = dinnerBeanArrayList;

    }

    public DinnerAdapter(Context context, ArrayList<MyMeals.MealsDinnerBean> dinnerBeanArrayList, int otherUserId) {
        this.context = context;
        this.dinnerBeanArrayList = dinnerBeanArrayList;
        this.otherUserId = otherUserId;
    }

    @NonNull
    @Override
    public DinnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dynamic_my_meal_items, parent, false);
        return new DinnerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DinnerAdapter.ViewHolder holder, final int position) {
        final MyMeals.MealsDinnerBean dinnerBeanList = dinnerBeanArrayList.get(position);

        holder.tvMealName.setText(dinnerBeanList.getFoodName());
        holder.tvQuant.setText("" + dinnerBeanList.getQuantity());
        holder.tvUnit.setText(dinnerBeanList.getUnit());
        holder.tvMealName.setSelected(true);
        holder.tvQuant.setSelected(true);
        holder.tvUnit.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogM.LogE("You have Clicked Lunch");
                if(otherUserId<1) {
                    if(context instanceof MealDetailsActivity){
                    }
                    Intent intentrvLunch = new Intent(context, AddMiMealActivity.class);
                    intentrvLunch.putExtra(GlobalData.DINNER, dinnerBeanArrayList);
                    context.startActivity(intentrvLunch);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return dinnerBeanArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MyTextView tvMealName, tvQuant, tvUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuant = itemView.findViewById(R.id.tvQuant);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
        }

    }
}


