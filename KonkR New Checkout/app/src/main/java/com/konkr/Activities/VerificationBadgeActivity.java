package com.konkr.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.konkr.Adapters.VerificationBadgeAdaplter;
import com.konkr.Models.CommonMessageStatus;
import com.konkr.Models.VerificationBadgeList;
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
import com.konkr.databinding.ActivityVerificationBadgeBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerificationBadgeActivity extends AppCompatActivity implements View.OnClickListener, VerificationBadgeAdaplter.ItemClickListener {
    private ActivityVerificationBadgeBinding binding;
    private Activity context;
    private Headerbar headerBar;
    private RecyclerView verificationRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List <VerificationBadgeList> vBadgeList = null;
    private VerificationBadgeAdaplter adaplter;
    MyTextView sendVerificationBtn;
    private int flag;
    int badgeNo;
    private View snackBarView;
    boolean chkSponser, chkCelebrity, chkInfluncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        binding = DataBindingUtil.setContentView (this, R.layout.activity_verification_badge);
        context = VerificationBadgeActivity.this;
        snackBarView = findViewById (android.R.id.content);

        bindView ();
        setListener ();
        setHeaderBar ();
        callGetCurrentBadgeApi ();


        // getIntentData ();
    }

    private void callGetCurrentBadgeApi() {
        if (ConnectivityDetector.isConnectingToInternet (context)) {

            try {
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put (WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId (VerificationBadgeActivity.this));
                jsonObject.put (WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken (VerificationBadgeActivity.this));

                LogM.LogE ("Request :  getCurrentbade: " + jsonObject.toString ());

                new GetJsonWithAndroidNetworkingLib (context, jsonObject, WebField.BASE_URL + WebField.GET_CURRENT_BADGE.MODE, 1, new OnUpdateListener () {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {

                        CommonMessageStatus user = new Gson ().fromJson (String.valueOf (jsonObject), CommonMessageStatus.class);
                        if (isSuccess) {
                            LogM.LogE ("Response : getCurrentbade " + jsonObject.toString ());
                            try {
                                if (jsonObject.getInt (WebField.BADGE) == 1) {
                                    setVerificationBadgeData (1);
                                } else if (jsonObject.getInt (WebField.BADGE) == 2) {
                                    setVerificationBadgeData (2);
                                } else if (jsonObject.getInt (WebField.BADGE) == 3) {
                                    setVerificationBadgeData (3);
                                } else if (jsonObject.getInt (WebField.BADGE) == 4) {
                                    setVerificationBadgeData (4);
                                } else {
                                    setVerificationBadgeData (0);
                                }
                                setVerificatioBadgeAdapter ();

                                if (jsonObject.getInt (WebField.BADGE_STATUS) == 2) {
                                    AlertDialogUtility.showAlertWithOnlyYesOption (context, user.getMessage (), new DialogInterface.OnClickListener () {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish ();
                                        }
                                    });
                                    sendVerificationBtn.setVisibility (View.GONE);
                                } else if (jsonObject.getInt (WebField.BADGE_STATUS) == 3) {
                                    sendVerificationBtn.setVisibility (View.GONE);
                                } else if (jsonObject.getInt (WebField.BADGE_STATUS) == 4) {
                                    AlertDialogUtility.showAlertWithOnlyYesOption (context, user.getMessage (), new DialogInterface.OnClickListener () {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    });
                                    sendVerificationBtn.setVisibility (View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace ();
                            }
                            // AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
                        } else {
                            AlertDialogUtility.showSnakeBar (user.getMessage (), snackBarView, context);
                        }

                    }
                }).execute ();
            } catch (Exception e) {
                e.printStackTrace ();
            }

        } else {
            AlertDialogUtility.showAlert (VerificationBadgeActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }

    }

    private void setVerificatioBadgeAdapter() {
        linearLayoutManager = new LinearLayoutManager (getApplicationContext ());
        verificationRecyclerView.setLayoutManager (linearLayoutManager);
        adaplter = new VerificationBadgeAdaplter (this, context, vBadgeList);
        verificationRecyclerView.setAdapter (adaplter);
    }

    private void setVerificationBadgeData(int badge) {
        if (vBadgeList == null) {

            vBadgeList = new ArrayList <VerificationBadgeList> ();
            LogM.LogE ("badge set=> " + badge);
            if (badge == 2) {
                vBadgeList.add (new VerificationBadgeList ((R.drawable.sponsor), getResources ().getString (R.string.sponsor), getResources ().getString (R.string.desc_one), false));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.inflencer), getResources ().getString (R.string.influencer), getResources ().getString (R.string.desc_two), false));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.celebrity), getResources ().getString (R.string.celebrity), getResources ().getString (R.string.desc_three), true));
            } else if (badge == 3) {
                vBadgeList.add (new VerificationBadgeList ((R.drawable.sponsor), getResources ().getString (R.string.sponsor), getResources ().getString (R.string.desc_one), false));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.inflencer), getResources ().getString (R.string.influencer), getResources ().getString (R.string.desc_two), true));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.celebrity), getResources ().getString (R.string.celebrity), getResources ().getString (R.string.desc_three), false));
            } else if (badge == 4) {
                vBadgeList.add (new VerificationBadgeList ((R.drawable.sponsor), getResources ().getString (R.string.sponsor), getResources ().getString (R.string.desc_one), true));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.inflencer), getResources ().getString (R.string.influencer), getResources ().getString (R.string.desc_two), false));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.celebrity), getResources ().getString (R.string.celebrity), getResources ().getString (R.string.desc_three), false));
            } else {
                vBadgeList.add (new VerificationBadgeList ((R.drawable.sponsor), getResources ().getString (R.string.sponsor), getResources ().getString (R.string.desc_one), true));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.inflencer), getResources ().getString (R.string.influencer), getResources ().getString (R.string.desc_two), false));
                vBadgeList.add (new VerificationBadgeList ((R.drawable.celebrity), getResources ().getString (R.string.celebrity), getResources ().getString (R.string.desc_three), false));
            }
        }
    }


    private void bindView() {
        headerBar = binding.headerBar;
        verificationRecyclerView = binding.verificationRecyclerView;
        sendVerificationBtn = binding.sendVerificationBtn;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility (View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource (R.drawable.back);
        headerBar.tvTitle.setVisibility (View.VISIBLE);
        headerBar.tvTitle.setText (R.string.verification_badge_title);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener (this);
        sendVerificationBtn.setOnClickListener (this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.ibtnLeftOne:
                finish ();
                break;
            case R.id.sendVerificationBtn:
                LogM.LogE ("FLAG" + flag);
                // Call API...
                getBadgeNumber ();
                callReqForBadgeApi ();
                break;

        }
    }

    private void getBadgeNumber() {
        if (flag == 0) { // sponsor
            badgeNo = 4;
        } else if (flag == 1) { // influncer
            badgeNo = 3;
        } else if (flag == 2) { // celebrity
            badgeNo = 2;
        } else {   // others
            badgeNo = 1;
        }
    }

    private void callReqForBadgeApi() {

        if (ConnectivityDetector.isConnectingToInternet (context)) {

            try {
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put (WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId (VerificationBadgeActivity.this));
                jsonObject.put (WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken (VerificationBadgeActivity.this));
                jsonObject.put (WebField.BADGE, badgeNo);

                LogM.LogE ("Request :  req for badge: " + jsonObject.toString ());

                new GetJsonWithAndroidNetworkingLib (context, jsonObject, WebField.BASE_URL + WebField.REQUEST_FOR_BADGE.MODE, 1, new OnUpdateListener () {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {

                        CommonMessageStatus user = new Gson ().fromJson (String.valueOf (jsonObject), CommonMessageStatus.class);
                        if (isSuccess) {
                            LogM.LogE ("Response :  req for badge: " + jsonObject.toString ());
                          //  AlertDialogUtility.showSnakeBar (user.getMessage (), snackBarView, context);

                            AlertDialogUtility.showAlertWithOnlyYesOption (context, user.getMessage (), new DialogInterface.OnClickListener () {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish ();
                                }
                            });


                        } else {
                            AlertDialogUtility.showSnakeBar (user.getMessage (), snackBarView, context);
                        }

                    }
                }).execute ();
            } catch (Exception e) {
                e.printStackTrace ();
            }

        } else {
            AlertDialogUtility.showAlert (VerificationBadgeActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    @Override
    public void onItemClick(View view, int pos) {

        for (int i = 0; i < vBadgeList.size (); i++) {
            if (pos == i) {
                vBadgeList.get (i).setChecked (true);
                flag = i;
            } else {
                vBadgeList.get (i).setChecked (false);
            }
        }
        adaplter.notifyDataSetChanged ();
        LogM.LogE ("you have selected" + "" + pos);
    }
}
