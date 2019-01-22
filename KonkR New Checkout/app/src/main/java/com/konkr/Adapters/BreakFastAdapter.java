package com.konkr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Activities.AddMiMealActivity;
import com.konkr.Activities.MealDetailsActivity;
import com.konkr.Models.MyMeals;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class BreakFastAdapter extends RecyclerView.Adapter<BreakFastAdapter.ViewHolder> {
    Context context;
    ArrayList<MyMeals.MealsBreakfastBean> breakfastArrayList;
    int otherUserId;

    public BreakFastAdapter(Context context, ArrayList<MyMeals.MealsBreakfastBean> breakfastArrayList) {
        this.context = context;
        this.breakfastArrayList = breakfastArrayList;

    }

    public BreakFastAdapter(Context context, ArrayList<MyMeals.MealsBreakfastBean> breakfastArrayList, int otherUserId) {
        this.context = context;
        this.breakfastArrayList = breakfastArrayList;
        this.otherUserId = otherUserId;
    }

    @NonNull
    @Override
    public BreakFastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dynamic_my_meal_items, parent, false);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otherUserId < 1) {


                    if(context instanceof MealDetailsActivity){
                    }

                    Intent intentrvBreakFast = new Intent(context, AddMiMealActivity.class);
                    intentrvBreakFast.putExtra(GlobalData.BREAKFAST, breakfastArrayList);
                    context.startActivity(intentrvBreakFast);
                }
            }
        });
        return new BreakFastAdapter.ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull BreakFastAdapter.ViewHolder holder, final int position) {
        final MyMeals.MealsBreakfastBean breakFastList = breakfastArrayList.get(position);
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
        SimpleDraweeView ivMealPhoto;
        MyTextView tvMealName, tvQuant, tvUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuant = itemView.findViewById(R.id.tvQuant);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvUnit = itemView.findViewById(R.id.tvUnit);

        }

    }
}


