package com.konkr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.konkr.Activities.AddMiMealActivity;
import com.konkr.Activities.MainActivity;
import com.konkr.Activities.MealDetailsActivity;
import com.konkr.Activities.MusicListActivity;
import com.konkr.Activities.SetUpCardActivity;
import com.konkr.Fragment.MiMealFragment;
import com.konkr.Models.Meals;
import com.konkr.Models.MyMeals;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MiMealAdapter extends RecyclerView.Adapter<MiMealAdapter.ViewHolder> {

    Activity context;
    public ArrayList<ArrayList<Meals.Meal>> alMeal;


    public MiMealAdapter(Activity context, ArrayList<ArrayList<Meals.Meal>> alMeal) {
        this.context = context;
        this.alMeal = alMeal;

    }

    @NonNull
    @Override

    public MiMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mi_my_meal, parent, false);
        return new MiMealAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MiMealAdapter.ViewHolder holder, final int position) {

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
            String imageUrl = "";
            for (int i = 0; i < alMeal.get(position).size(); i++) {
                if (!alMeal.get(position).get(i).getMealPhoto().equalsIgnoreCase("")) {
                    imageUrl = alMeal.get(position).get(i).getMealPhoto();
                    break;
                }
            }
            if(!imageUrl.isEmpty())
            holder.ivMealPhoto.setImageURI(imageUrl);
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
                Intent intentLunch = new Intent(context, AddMiMealActivity.class);
                intentLunch.putExtra(GlobalData.MEAL, alMeal.get(position));
                //intentLunch.putExtra(GlobalData.MEAL_NAME, holder.tvMealName.getText().toString());
                //          intentLunch.putExtra("myArray",myArray);

                context.startActivity(intentLunch);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showConfirmationDialog(holder.getAdapterPosition());
                return false;
            }
        });

    }

    private void showConfirmationDialog(int position) {

        AlertDialogUtility.showConfirmAlert(context, context.getResources().getString(R.string.delete_meal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callDeleteMealApi(Integer.parseInt(alMeal.get(position).get(0).getHomeFeedId()), position);
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

    public void callDeleteMealApi(int homefeedId, int position) {

        LogM.LogE("sent position==>" + position);
        LogM.LogE("size before delete==>" + alMeal.size());

        if (ConnectivityDetector.isConnectingToInternet(context)) {
            JSONObject jsonObject = new JSONObject();
            try {
                try {
                    jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                    jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                    jsonObject.put(WebField.PARAM_HOMEFEEDID, homefeedId);
//                    if (mealType == 5) {
//                        JSONArray mealIdArray = new JSONArray();
//
//                        for (int i = 0; i < alMeal.get(position).size(); i++) {
//                            mealIdArray.put(alMeal.get(position).get(i).getMealId());
//                        }
//                        jsonObject.put(WebField.DELETE_MY_MEALS.MEAL_TYPE, mealType);
//                        jsonObject.put(WebField.DELETE_MY_MEALS.MEAL_ID, mealIdArray);
//                    } else {
//                        jsonObject.put(WebField.DELETE_MY_MEALS.MEAL_TYPE, mealType);
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                LogM.LogE("Request : Delete my meal : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.DELETE_MY_MEALS.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        //    final MyMeals miSupplimentList = new Gson().fromJson(String.valueOf(jsonObject), MyMeals.class);
                        if (isSuccess) {
                            LogM.LogE("Response : Delete my meal : " + jsonObject.toString());
                            LogM.LogE("deleted Position==>" + position);

                           alMeal.remove(position);
                       //   notifyItemChanged(position);
                            notifyItemChanged(position);
                            notifyItemRangeRemoved(position, 1);
//                            //((MusicListActivity) context).paySong(items.get(position).getPerson().getUri(), position);
                            LogM.LogE("After deletion meal size==>" + alMeal.size());
                            if (alMeal.size() < 1) {
                                LogM.LogE("inside refresh==>" + alMeal);
                                if (context instanceof MainActivity)
                                    ((MainActivity) context).reFreshMimeal();
                                if (context instanceof MealDetailsActivity)
                                    ((MealDetailsActivity) context).showEmptyView();
                            }
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }
}
