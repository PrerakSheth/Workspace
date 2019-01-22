package com.konkr.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.konkr.Models.Partners;
import com.konkr.Models.PremiumMembership;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityPremiumMembershipBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;

public class PremiumMemberShipActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPremiumMembershipBinding binding;
    private Headerbar headerBar;
    private SimpleDraweeView ivMonthSelect, ivAnnualSelect;
    private MyTextView proceedBtn, tvMonthPrice, tvAnnualPrice;
    private MyTextView tvCounterDaysValue, tvCounterHoursValue, tvCounterMinutesValue, tvCounterSecondsValue;
    private Context context;
    private boolean isMonthlySubs = false;
    private boolean isAnnualSubs = false;
    private ConstraintLayout clOne, clTwo;
    private LinearLayout llRemainingTime;
    double premiumAmount;
    int subscriptionId;
    int monthSubscriptionId;
    int yearlySubscriptionIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_premium_membership);
        context = PremiumMemberShipActivity.this;
        bindViews();
        setHeaderBar();
        setListener();
        callGetSubscriptionLists();
        ivMonthSelect.setImageResource(R.drawable.unselected);
        ivAnnualSelect.setImageResource(R.drawable.unselected);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ivMonthSelect = binding.ivMonthSelect;
        ivAnnualSelect = binding.ivAnnualSelect;
        tvMonthPrice = binding.tvMonthPrice;
        tvAnnualPrice = binding.tvAnnualPrice;
        proceedBtn = binding.proceedBtn;
        clOne = binding.clOne;
        clTwo = binding.clTwo;
        tvCounterDaysValue = binding.tvCounterDaysValue;
        tvCounterHoursValue = binding.tvCounterHoursValue;
        tvCounterMinutesValue = binding.tvCounterMinutesValue;
        tvCounterSecondsValue = binding.tvCounterSecondsValue;
        llRemainingTime = binding.llRemainingTime;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.premium_subscription_title);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        clOne.setOnClickListener(this);
        clTwo.setOnClickListener(this);
        proceedBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                onBackPressed();
                break;

            case R.id.clOne:
                if (!isMonthlySubs) {
                    ivMonthSelect.setImageResource(R.drawable.selected);
                    ivAnnualSelect.setImageResource(R.drawable.unselected);
                    premiumAmount = Double.parseDouble(tvMonthPrice.getText().toString().replaceAll("[^A-Za-z0-9]", ""));
                    isMonthlySubs = true;
                    isAnnualSubs = false;
                    subscriptionId = monthSubscriptionId;
                } else {
                    ivMonthSelect.setImageResource(R.drawable.unselected);
                    ivAnnualSelect.setImageResource(R.drawable.unselected);
                    isMonthlySubs = false;
                    premiumAmount = 0;
                    subscriptionId = 0;
                    isAnnualSubs = false;
                }
                break;

            case R.id.clTwo:
                if (!isAnnualSubs) {
                    ivMonthSelect.setImageResource(R.drawable.unselected);
                    ivAnnualSelect.setImageResource(R.drawable.selected);
                    premiumAmount = Double.parseDouble(tvAnnualPrice.getText().toString().replaceAll("[^A-Za-z0-9]", ""));
                    isAnnualSubs = true;
                    subscriptionId = yearlySubscriptionIds;
                    isMonthlySubs = false;
                } else {
                    ivMonthSelect.setImageResource(R.drawable.unselected);
                    ivAnnualSelect.setImageResource(R.drawable.unselected);
                    isAnnualSubs = false;
                    premiumAmount = 0;
                    subscriptionId = 0;
                    isMonthlySubs = false;
                }
                break;

            case R.id.proceedBtn:
                if (premiumAmount > 0) {
                    LogM.LogE("Membership amount===>" + premiumAmount);
                    Intent intent = new Intent(context, CardListActivity.class);
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.PREMIUM_MEMBERSHIP);
                    intent.putExtra(GlobalData.SUBSCRIPTION_AMOUNT, premiumAmount);
                    intent.putExtra(GlobalData.SUBSCRIPTION_ID, subscriptionId);
                    startActivity(intent);
                } else {
                    AlertDialogUtility.showSnakeBar(getString(R.string.select_subscription), proceedBtn, this);
                }
                break;
        }
    }

    private void callGetSubscriptionLists() {
        if (ConnectivityDetector.isConnectingToInternet(context)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.SET_UP_CARD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                LogM.LogE("Request : GET_SUBSCRIPTION_LISTS : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(PremiumMemberShipActivity.this, jsonObject, WebField.BASE_URL + WebField.GET_SUBSCRIPTION_LISTS.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        try {
                            if (isSuccess) {
                                LogM.LogE("Response : GET_SUBSCRIPTION_LISTS : " + jsonObject.toString());
                                PremiumMembership premiumMembership = new Gson().fromJson(String.valueOf(jsonObject), PremiumMembership.class);
          LogM.LogE("you check ==>"+premiumMembership.getSubscriptionData().get(0).getSubscriptionName());
//
 if (premiumMembership.getSubscriptionData().get(0).getSubscriptionName().equalsIgnoreCase("MONTHLY")) {

                                    tvMonthPrice.setText("$" + premiumMembership.getSubscriptionData().get(0).getAmount());
                                    monthSubscriptionId = premiumMembership.getSubscriptionData().get(0).getSubscriptionId();
                                }
                                if (premiumMembership.getSubscriptionData().get(1).getSubscriptionName().equalsIgnoreCase("YEARLY")) {
                                    tvAnnualPrice.setText("$" + premiumMembership.getSubscriptionData().get(1).getAmount());
                                    yearlySubscriptionIds = premiumMembership.getSubscriptionData().get(1).getSubscriptionId();
                                }

                                if (jsonObject.getInt(WebField.SET_UP_CARD.PARAM_EXPIRYDATETIME) == 0) {
                                    clOne.setVisibility(View.VISIBLE);
                                    clTwo.setVisibility(View.VISIBLE);
                                    proceedBtn.setVisibility(View.VISIBLE);
                                    SessionManager.setSubscribed(PremiumMemberShipActivity.this, 0);
                                    llRemainingTime.setVisibility(View.GONE);
                                } else {
                                    clOne.setVisibility(View.GONE);
                                    clTwo.setVisibility(View.GONE);
                                    proceedBtn.setVisibility(View.GONE);
                                    SessionManager.setSubscribed(PremiumMemberShipActivity.this, 1);
                                    llRemainingTime.setVisibility(View.VISIBLE);

                                    long milliseconds = Long.parseLong(jsonObject.getString(WebField.SET_UP_CARD.PARAM_EXPIRYDATETIME).trim())*1000; // converting seconds into milli seconds
//                                    milliseconds = 995550000;
                                    CountdownView mCvCountdownView = new CountdownView(PremiumMemberShipActivity.this);
                                    mCvCountdownView.start(milliseconds); // Millisecond
                                    tvCounterDaysValue.setText(String.valueOf(TimeUnit.MILLISECONDS.toDays(milliseconds)));
                                    tvCounterHoursValue.setText(String.valueOf(((milliseconds / (1000 * 60 * 60)) % 24)));
                                    tvCounterMinutesValue.setText(String.valueOf(((milliseconds / 1000) / 60) % 60));
                                    tvCounterSecondsValue.setText(String.valueOf((milliseconds / 1000) % 60));
                                    mCvCountdownView.setOnCountdownIntervalListener(1000, new CountdownView.OnCountdownIntervalListener() {
                                        @Override
                                        public void onInterval(CountdownView cv, long remainTime) {
                                            tvCounterDaysValue.setText(String.valueOf(cv.getDay()));
                                            tvCounterHoursValue.setText(String.valueOf(cv.getHour()));
                                            tvCounterMinutesValue.setText(String.valueOf(cv.getMinute()));
                                            tvCounterSecondsValue.setText(String.valueOf(cv.getSecond()));
                                        }
                                    });
                                }
                            } else {
                                AlertDialogUtility.showSnakeBar((GlobalData.MESSAGE), proceedBtn, context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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