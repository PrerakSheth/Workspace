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

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.ViewHolder> {

    Context context;
    ArrayList<MyMeals.MealsSnacksBean> snacksBeanArrayList;
int otherUserId;
    public SnackAdapter(Context context, ArrayList<MyMeals.MealsSnacksBean> snacksBeanArrayList) {
        this.context = context;
        this.snacksBeanArrayList = snacksBeanArrayList;

    }

    public SnackAdapter(Context context, ArrayList<MyMeals.MealsSnacksBean> snacksBeanArrayList, int otherUserId) {
        this.context = context;
        this.snacksBeanArrayList = snacksBeanArrayList;
        this.otherUserId = otherUserId;
    }

    @NonNull
    @Override
    public SnackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dynamic_my_meal_items, parent, false);
        return new SnackAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackAdapter.ViewHolder holder, final int position) {
        final MyMeals.MealsSnacksBean lunchBeanList = snacksBeanArrayList.get(position);

        holder.tvMealName.setText(lunchBeanList.getFoodName());
        holder.tvQuant.setText("" + lunchBeanList.getQuantity());
        holder.tvUnit.setText(lunchBeanList.getUnit());
        holder.tvMealName.setSelected(true);
        holder.tvQuant.setSelected(true);
        holder.tvUnit.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otherUserId<1) {
                    if(context instanceof MealDetailsActivity){
                    }
                    LogM.LogE("You have Clicked Lunch");
                    Intent intentrvLunch = new Intent(context, AddMiMealActivity.class);
                    intentrvLunch.putExtra(GlobalData.SNACK, snacksBeanArrayList);
                    context.startActivity(intentrvLunch);
                }
            }
        });
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
        }

    }
}

