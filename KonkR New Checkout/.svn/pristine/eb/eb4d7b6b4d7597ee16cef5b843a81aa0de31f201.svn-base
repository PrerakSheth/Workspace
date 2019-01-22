package com.konkr.Activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.konkr.Adapters.SearchUserAdapter;
import com.konkr.Models.SearchUser;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivitySearchUserListBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchUserListActivity extends AppCompatActivity implements View.OnClickListener {

    //    private ArrayList<SearchUser.UserSearchListBean> alSearchUser;
    private Headerbar headerBar;
    private RecyclerView rvUser;
    private MyTextView tvNoData;

    private ActivitySearchUserListBinding binding;

    private Activity context;
    SearchUserAdapter searchUserAdapter;
    private ConstraintLayout clRecylerview;

    private String firstNameLastName;
    private String nickName;
    private int typeOfGender;
    private String trainingGoals;
    private String bodyType;
    private String email;
    private int badge;
    private int countryId;

    private int pageIndex = 1;
    private ArrayList<SearchUser.UserSearchListBean> alTempSearchUser = new ArrayList<>();
    private ArrayList<SearchUser.UserSearchListBean> alSearchUser = new ArrayList<>();

    private int currentJobPage = 1, pageNumber = 1;
    private boolean loadingCurrentJob = true, isSecondCurrent = false;
    LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user_list);
        context = SearchUserListActivity.this;

        bindViews();
        setHeaderBar();
        setListner();
        setLayoutManger();
        getIntentData();
//        setAdapter();
//        callSearchUser();

        rvUser.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                if (currentJobPage == 0) {
                } else {
                    if (lastVisibleItem == totalItemCount && loadingCurrentJob) {
                        progressBar.setVisibility(View.VISIBLE);
                        isSecondCurrent = true;
                        pageIndex += 1;
                        currentJobPage = 0;
                        callSearchUser();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callSearchUser();
    }

    private void getIntentData() {
//        if (getIntent() != null) {
//            alSearchUser = getIntent().getParcelableArrayListExtra(GlobalData.SEARCH_USER_ARRAY);
//            Log.i("List", "Passed Array List :: " + alSearchUser.size());
//            if (alSearchUser.size() > 0) {
//                tvNoData.setVisibility(View.GONE);
//                clRecylerview.setVisibility(View.VISIBLE);
//            } else {
//                tvNoData.setVisibility(View.VISIBLE);
//                clRecylerview.setVisibility(View.GONE);
//            }
//        }
        if (getIntent() != null) {

            firstNameLastName = getIntent().getStringExtra(GlobalData.SEARCH_PARAM_NAME);
          //  nickName = getIntent().getStringExtra(GlobalData.SEARCH_PARAM_NICKNAME);
            typeOfGender = getIntent().getIntExtra(GlobalData.SEARCH_PARAM_GENDER, 0);
            trainingGoals = getIntent().getStringExtra(GlobalData.SEARCH_PARAM_CURRENTTRAININGGOALS);
            bodyType = getIntent().getStringExtra(GlobalData.SEARCH_PARAM_BODYTYPE);
            email = getIntent().getStringExtra(GlobalData.SEARCH_PARAM_EMAIL_ID);
            badge = getIntent().getIntExtra(GlobalData.SEARCH_PARAM_BADGE, 0);
            countryId = getIntent().getIntExtra(GlobalData.SEARCH_PARAM_COUNTRYID, 0);

//            intent.putExtra(GlobalData.SEARCH_PARAM_NAME, etFirstNameLastName.getText().toString());
//            intent.putExtra(GlobalData.SEARCH_PARAM_NICKNAME, etNickname.getText().toString());
//            intent.putExtra(GlobalData.SEARCH_PARAM_GENDER, strTypeOfGender);
//            intent.putExtra(GlobalData.SEARCH_PARAM_CURRENTTRAININGGOALS, etTrainingGoals.getText().toString());
//            intent.putExtra(GlobalData.SEARCH_PARAM_BODYTYPE, bodyType);
//            intent.putExtra(GlobalData.SEARCH_PARAM_EMAIL_ID, etEmail.getText().toString());
//            intent.putExtra(GlobalData.SEARCH_PARAM_BADGE, badge);
//            intent.putExtra(GlobalData.SEARCH_PARAM_COUNTRYID, countryId);


        }
    }

    private void callSearchUser() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.SEARCH_USER.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.SEARCH_USER.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.SEARCH_USER.PARAM_NAME, firstNameLastName);
        //    jsonObject.put(WebField.SEARCH_USER.PARAM_NICKNAME, nickName);
            jsonObject.put(WebField.SEARCH_USER.PARAM_GENDER, typeOfGender);
            jsonObject.put(WebField.SEARCH_USER.PARAM_CURRENTTRAININGGOALS, trainingGoals);
            jsonObject.put(WebField.SEARCH_USER.PARAM_BODYTYPE, bodyType);
            jsonObject.put(WebField.SEARCH_USER.PARAM_EMAIL_ID, email);
            jsonObject.put(WebField.SEARCH_USER.PARAM_BADGE, badge);
            jsonObject.put(WebField.SEARCH_USER.PARAM_COUNTRYID, countryId);
            jsonObject.put(WebField.PARAM_PAGEINDEX, pageIndex);
            jsonObject.put(WebField.PARAM_PAGECOUNT, GlobalData.PAGE_ITEM_COUNT);


            LogM.LogE("Request : Search User : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SEARCH_USER.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : Search User : " + jsonObject.toString());

                        SearchUser user = new Gson().fromJson(String.valueOf(jsonObject), SearchUser.class);
                        LogM.LogE("user" + user.getUserSearchList().size());

                        currentJobPage = 1;
                        loadingCurrentJob = true;
                        if (user.getUserSearchList().size() == 0) {
                            loadingCurrentJob = false;
//                            return;
                        }
                        if (isSecondCurrent) {
//                            alTempSearchUser.addAll(user.getUserSearchList());
                            alSearchUser.addAll(user.getUserSearchList());
                            progressBar.setVisibility(View.GONE);
//                            setAdapter();
                            searchUserAdapter.notifyDataSetChanged();
                        } else {
                            alSearchUser.clear();
                            alSearchUser.addAll(user.getUserSearchList());
                            if (alSearchUser != null && alSearchUser.size() != 0) {
                                tvNoData.setVisibility(View.GONE);
                                rvUser.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                setAdapter();
                            } else {
                                tvNoData.setVisibility(View.VISIBLE);
                                rvUser.setVisibility(View.GONE);
                            }
                        }


                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.title_search_user_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        clRecylerview = binding.clRecylerview;
        tvNoData = binding.tvNoData;
        headerBar = binding.headerBar;
        rvUser = binding.rvUser;
        progressBar = binding.progressBar;
    }

    private void setListner() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
    }

    private void setAdapter() {
        searchUserAdapter = new SearchUserAdapter(context, alSearchUser);
        rvUser.setAdapter(searchUserAdapter);
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvUser.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnLeftOne:
                    finish();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
