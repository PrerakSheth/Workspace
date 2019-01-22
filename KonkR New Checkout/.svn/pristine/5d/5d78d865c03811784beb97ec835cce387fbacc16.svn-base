package com.konkr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Activities.AddMiMealActivity;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.Meals;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

public class MiMealProfileAdapter extends RecyclerView.Adapter<MiMealProfileAdapter.ViewHolder> {

    Activity context;
    ArrayList<ArrayList<UserDetails.UserDetailsBean.Meal>> alMeal;

    ProfileMealClick profileMealClick;

    public MiMealProfileAdapter(Activity context,ProfileMealClick profileMealClick ,ArrayList<ArrayList<UserDetails.UserDetailsBean.Meal>> alMeal) {
        this.context = context;
        this.alMeal = alMeal;
        this.profileMealClick = profileMealClick;

    }



    @NonNull
    @Override

    public MiMealProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mi_my_meal, parent, false);
        return new MiMealProfileAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MiMealProfileAdapter.ViewHolder holder, final int position) {

        if (alMeal.get(position).size() > 0) {
            int mealType = alMeal.get(position).get(0).getMealType();
            if (mealType == 1) {
                holder.tvMealName.setText(GlobalData.BREAKFAST);
            } else if (mealType == 2) {
                holder.tvMealName.setText(GlobalData.LUNCH);
            } else if (mealType == 3) {
                holder.tvMealName.setText(GlobalData.SNACK);
            } else if (mealType == 4) {
                holder.tvMealName.setText(GlobalData.DINNER);
            } else if (mealType == 5) {
                holder.tvMealName.setText(alMeal.get(position).get(0).getMealName());
            }
            String imgUrl = "";
            for (int i = 0; i < alMeal.get(position).size(); i++) {
                if (!alMeal.get(position).get(i).getMealPhoto().equalsIgnoreCase("")) {
                    imgUrl = alMeal.get(position).get(i).getMealPhoto();
                    break;
                }
            }
            holder.ivMealPhoto.setImageURI(imgUrl);
        }

        for (int i = 0; i < alMeal.get(position).size(); i++) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.dynamic_my_meal_items, null);
            holder.parentLinearLayout.addView(rowView);
            MyTextView tvMealName = rowView.findViewById(R.id.tvMealName);
            MyTextView tvQuant = rowView.findViewById(R.id.tvQuant);
            MyTextView tvUnit = rowView.findViewById(R.id.tvUnit);
            tvMealName.setText(alMeal.get(position).get(i).getFoodName());
            tvQuant.setText(alMeal.get(position).get(i).getQuantity());
            tvUnit.setText(alMeal.get(position).get(i).getUnit());
            tvMealName.setSelected(true);
            tvQuant.setSelected(true);
            tvUnit.setSelected(true);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogM.LogE("uuu have clicked profile one ==>");

                profileMealClick.mealItemClick(position);
//
//           ArrayList<Meals.Meal> arrayList= new ArrayList<>();
//           if(alMeal.get(position).size()>0){
//               for(int i=0;i<alMeal.get(position).size();i++){
//                   arrayList.add(new Meals.Meal(alMeal.get(position).get(i).getMealId(),
//                           alMeal.get(position).get(i).getMealPhoto(),
//                           alMeal.get(position).get(i).getMealType(),
//                           alMeal.get(position).get(i).getFoodName(),
//                           alMeal.get(position).get(i).getMealName(),
//                           alMeal.get(position).get(i).getUnit(),
//                           alMeal.get(position).get(i).getQuantity()));
//               }
//           }
//
//
//           Intent intent= new Intent(context, AddMiMealActivity.class);
//           intent.putExtra(GlobalData.MEAL,arrayList);
//            context.startActivityForResult(intent,GlobalData.MEAL_DETAIL_REQ);
            }
        });
    }


    @Override
    public int getItemCount() {
        return alMeal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MyTextView tvMealName;
        LinearLayout parentLinearLayout;
        SimpleDraweeView ivMealPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLinearLayout = itemView.findViewById(R.id.parentLinearLayout);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            ivMealPhoto = itemView.findViewById(R.id.ivMealPhoto);
        }

    }

    public interface ProfileMealClick {

        public void mealItemClick(int pos);
    }

}
