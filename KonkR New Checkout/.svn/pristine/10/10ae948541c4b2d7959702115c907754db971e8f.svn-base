package com.konkr.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.konkr.Adapters.BreakFastAdapter;
import com.konkr.Adapters.DinnerAdapter;
import com.konkr.Adapters.DropDownAdapter;
import com.konkr.Adapters.LunchAdapter;
import com.konkr.Adapters.MiMealAdapter;
import com.konkr.Adapters.SnackAdapter;
import com.konkr.Fragment.MiMealFragment;
import com.konkr.Models.Meals;
import com.konkr.Models.MyMeals;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityMealDetailsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MealDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MealDetailsActivity";
    private ActivityMealDetailsBinding binding;
    private Headerbar headerBar;
    private MealDetailsActivity context;
    int[] myArray = new int[4];
    private ArrayList<ArrayList<Meals.Meal>> alMeal = new ArrayList<>();
    private MiMealAdapter mealAdapter;
    private RecyclerView rvMeal;
    private MyTextView tvMiMealEmpty;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_details);
        context = MealDetailsActivity.this;
        bindView();
        getIntetData();
        setHeaderBar();
        setListener();
        setLayoutManger();
        callGetMiMeal();
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvMeal.setLayoutManager(linearLayoutManager);
    }


    private void getIntetData() {
        if (getIntent().getExtras() != null) {
            LogM.LogE("your intent" + getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0));
            //  otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callGetMiMeal();
    }

    private void bindView() {
        headerBar = binding.headerBar;
        rvMeal = binding.rvMeals;
        tvMiMealEmpty = binding.tvMiMealEmpty;
    }


    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
        headerBar.ibtnRightTwo.setImageResource(R.drawable.add);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.mi_meal_title);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        headerBar.ibtnRightTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;

            case R.id.ibtnRightTwo:
                Intent intent = new Intent(context, AddMiMealActivity.class);
                intent.putExtra(GlobalData.AddMeal, GlobalData.AddMeal);
                intent.putExtra("myArray", myArray);
                startActivityForResult(intent, 500);
                break;
        }


    }

    public void callGetMiMeal() {
        if (ConnectivityDetector.isConnectingToInternet(context)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.SET_UP_CARD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                LogM.LogE("Request : GetMiMeals : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_MY_MEALS.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final Meals miSupplimentList = new Gson().fromJson(String.valueOf(jsonObject), Meals.class);
                        if (isSuccess) {
                            LogM.LogE("Response : GetMiMeals : " + jsonObject.toString());

                            alMeal.clear();

                            myArray[0] = miSupplimentList.getMealsBreakfast().size();
                            myArray[1] = miSupplimentList.getMealsLunch().size();
                            myArray[2] = miSupplimentList.getMealsSnacks().size();
                            myArray[3] = miSupplimentList.getMealsDinner().size();

                            if (miSupplimentList.getMealsBreakfast().size() > 0)
                                alMeal.add(miSupplimentList.getMealsBreakfast());
                            if (miSupplimentList.getMealsLunch().size() > 0)
                                alMeal.add(miSupplimentList.getMealsLunch());
                            if (miSupplimentList.getMealsSnacks().size() > 0)
                                alMeal.add(miSupplimentList.getMealsSnacks());
                            if (miSupplimentList.getMealsDinner().size() > 0)
                                alMeal.add(miSupplimentList.getMealsDinner());

                            for (ArrayList<Meals.Meal> arrayList : miSupplimentList.getOtherMealData()) {
                                if (arrayList.size() > 0)
                                    alMeal.add(arrayList);
                            }
                            mealAdapter = new MiMealAdapter(context, alMeal);
                            rvMeal.setAdapter(mealAdapter);
                            if (alMeal.size() > 0) {
                                rvMeal.setVisibility(View.VISIBLE);
                                tvMiMealEmpty.setVisibility(View.GONE);
                            } else {
                                rvMeal.setVisibility(View.GONE);
                                tvMiMealEmpty.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // AlertDialogUtility.showSnakeBar(miSupplimentList.getMessage(), snackBarView, context);
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

    public void showEmptyView() {

        tvMiMealEmpty.setVisibility(View.VISIBLE);
        myArray[0] = 0;
        myArray[1] = 0;
        myArray[2] = 0;
        myArray[3] = 0;
    }
}
