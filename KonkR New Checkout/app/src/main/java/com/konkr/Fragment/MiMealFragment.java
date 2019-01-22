package com.konkr.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.konkr.Activities.AddMiMealActivity;
import com.konkr.Activities.MainActivity;
import com.konkr.Adapters.DinnerAdapter;
import com.konkr.Adapters.BreakFastAdapter;
import com.konkr.Adapters.DropDownAdapter;
import com.konkr.Adapters.LunchAdapter;
import com.konkr.Adapters.MiMealAdapter;
import com.konkr.Adapters.SnackAdapter;
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
import com.konkr.databinding.FragmentMiMealBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MiMealFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MiMealFragment";
    private FragmentMiMealBinding binding;
    private RecyclerView rvMeal;
    private LinearLayoutManager mealLinearLayout;
    private TextView  tvEmptyMeal;

    private ArrayList<ArrayList<Meals.Meal>> alMeal = new ArrayList<>();
    private View snackBarView;
    private MiMealAdapter mealAdapter;


    RecyclerView rvDropDown;

    int[] myArray = new int[4];

    public MiMealFragment() {
    }

    public static MiMealFragment newInstance() {
        MiMealFragment fragment = new MiMealFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mi_meal, container, false);
        View view = binding.getRoot();
        rvMeal = binding.rvMeals;
        tvEmptyMeal = binding.tvMiMealEmpty;

        snackBarView = getActivity().findViewById(android.R.id.content);

        setListner();
        setLayoutManger();
        callGetMiMeal(1);
        return view;

    }

    private void setListner() {
    }

    @Override
    public void onResume() {
        super.onResume();

        callGetMiMeal(1);

    }

    public void callGetMiMeal(int drop) {

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId(getActivity()));
                jsonObject.put(WebField.SET_UP_CARD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(getActivity()));
                LogM.LogE("Request : GetMiMeals : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(getActivity(), jsonObject, WebField.BASE_URL + WebField.GET_MY_MEALS.MODE, 1, new OnUpdateListener() {
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
                            if (alMeal.size() > 0) {
                                rvMeal.setVisibility(View.VISIBLE);
                                tvEmptyMeal.setVisibility(View.GONE);
                            } else {
                                rvMeal.setVisibility(View.GONE);
                                tvEmptyMeal.setVisibility(View.VISIBLE);
                            }

                            mealAdapter = new MiMealAdapter(getActivity(), alMeal);
                            rvMeal.setAdapter(mealAdapter);


                        } else {
                            AlertDialogUtility.showSnakeBar(miSupplimentList.getMessage(), snackBarView, getActivity());
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(getActivity(), GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }


    }


    public int[] getAddedMealInfo() {
        return myArray;
    }

    public int getUserSelectedMealCount() {
        return Integer.parseInt("0");
    }

    private void setLayoutManger() {
        mealLinearLayout = new LinearLayoutManager(getActivity());
        rvMeal.setLayoutManager(mealLinearLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEdit:
                rvDropDown.setVisibility(View.VISIBLE);
                break;
            case R.id.lunch:
                break;
            case R.id.snack:
                break;
            case R.id.dinner:
                LogM.LogE("You have Clicked Dinner");
                break;
            case R.id.clParent:
                rvDropDown.setVisibility(View.GONE);
                break;

        }
    }



    public void reFreshUi() {
        tvEmptyMeal.setVisibility(View.VISIBLE);
        myArray[0] = 0;
        myArray[1] = 0;
        myArray[2] = 0;
        myArray[3] = 0;
    }
}
