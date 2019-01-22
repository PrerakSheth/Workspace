package com.konkr.Activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.konkr.Adapters.AdCardAdapter;
import com.konkr.Adapters.PartnersAdapter;
import com.konkr.Models.Advertisement;
import com.konkr.Models.Partners;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GridSpacingItemDecoration;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityPartnersBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PartnersActivity extends AppCompatActivity implements View.OnClickListener {
    private Headerbar headerBar;
    private MyTextView tvEmptyView;
    private RecyclerView partnersRecyclerView;
    private ActivityPartnersBinding binding;
    private GridLayoutManager gridLayoutManager;
    private PartnersAdapter partnersAdapter;
    private Activity context;
    private ArrayList<Partners.PartnerListBean> partnerListBeanArrayList = new ArrayList<>();
    private ViewPager viewpager;
    private ImageView ivClose;
    AdCardAdapter adapterAdCard;
    private Timer timer = new Timer();

    private ArrayList<Advertisement.AdvertiseListBean> alAdvertisment = new ArrayList<>();
    int currentPage = 0;
    //    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    private View snackBarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView (R.layout.activity_partners);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_partners);
        context = PartnersActivity.this;
        snackBarView = findViewById(android.R.id.content);
        bindViews();
        setHeaderBar();
        setListener();
        setGridLayoutManger();
        setAdapter();
        callgetPartnerListApi();

        callAdvertisment();


    }

    private void callAdvertisment() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.ADVERTISEMENT.PARAM_SCREENPOSITION, 0);
            LogM.LogE("Request : Advertisment : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADVERTISEMENT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Advertisment : " + jsonObject.toString());
                    Advertisement advertisment = new Gson().fromJson(String.valueOf(jsonObject), Advertisement.class);
                    if (isSuccess) {
                        if (advertisment.getAdvertiseList().size() > 0) {
                            viewpager.setVisibility(View.VISIBLE);
                            ivClose.setVisibility(View.VISIBLE);

                            alAdvertisment.addAll(advertisment.getAdvertiseList());
                            adapterAdCard = new AdCardAdapter(context, alAdvertisment, 0);
                            viewpager.setAdapter(adapterAdCard);

                            /*After setting the adapter use the timer */
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == alAdvertisment.size() - 1) {
                                        currentPage = 0;
                                    }
                                    viewpager.setCurrentItem(currentPage++, true);
                                }
                            };

//                            timer = new Timer(); // This will create a new Thread
                            timer.schedule(new TimerTask() { // task to be scheduled

                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, DELAY_MS, PERIOD_MS);
                        } else {
                            viewpager.setVisibility(View.GONE);
                            ivClose.setVisibility(View.GONE);
                        }
                        adapterAdCard.notifyDataSetChanged();
                    } else {
                        AlertDialogUtility.showSnakeBar(advertisment.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setGridLayoutManger() {

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        partnersRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        partnersRecyclerView = binding.partnersRecyclerView;
        tvEmptyView = binding.tvEmpty;
        ivClose = binding.ivClose;
        viewpager = binding.viewpager;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.partners_title);
    }

    private void setListener() {

        headerBar.ibtnLeftOne.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;
            case R.id.ivClose:
                viewpager.setVisibility(View.GONE);
                ivClose.setVisibility(View.GONE);
                break;
        }
    }

    private void callgetPartnerListApi() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_PARTNER_LIST.PARAM_USER_ID, SessionManager.getUserId(PartnersActivity.this));
            jsonObject.put(WebField.GET_PARTNER_LIST.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(PartnersActivity.this));

            LogM.LogE("Request : PartnerList : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_PARTNER_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : PartnerList : " + jsonObject.toString());
                    final Partners partners = new Gson().fromJson(String.valueOf(jsonObject), Partners.class);
                    if (isSuccess) {
                        partnerListBeanArrayList.clear();
                        partnerListBeanArrayList.addAll(partners.getPartnerList());

                        if (partnerListBeanArrayList.size() > 0) {
                            partnersAdapter.notifyDataSetChanged();
                            partnersRecyclerView.setVisibility(View.VISIBLE);
//                            setAdapter ();
                            tvEmptyView.setVisibility(View.GONE);
                        } else {
                            tvEmptyView.setVisibility(View.VISIBLE);
                            partnersRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        AlertDialogUtility.showSnakeBar(partners.getMessage(), partnersRecyclerView, context);
                    }

                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        partnersAdapter = new PartnersAdapter(context, partnerListBeanArrayList);
        partnersRecyclerView.setAdapter(partnersAdapter);
//        partnersRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2,
//                getResources().getDimensionPixelSize(R.dimen.recycler_view_item_width)));
    }
}
