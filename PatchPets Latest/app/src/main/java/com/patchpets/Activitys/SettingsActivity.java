package com.patchpets.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivitySettingsBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.LogoutRequest;
import com.patchpets.model.responseModel.ApiResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySettingsBinding binding;
    private HeaderBar headerBar;
    private TextView tvTerms;
    private TextView tvPartners;
    private TextView tvSetUpCard;
    private TextView tvSocialShare;
    private TextView tvShareExperience;
    private TextView tvChangePassword;
    private TextView tvLogout;
    private TextView tvNotification;
    private TextView tvHowappworks;
    private Activity context;
    private APIRequest apiRequest;
    private View snackBarView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        context = SettingsActivity.this;
        bindViews();
        setListener();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        tvSetUpCard = binding.tvSetUpCard;
        tvPartners = binding.tvPartners;
        tvNotification = binding.tvNotification;
        tvHowappworks = binding.tvHowappworks;
        tvChangePassword = binding.tvChangePassword;
        tvShareExperience = binding.tvShareExperience;
        tvSocialShare = binding.tvSocialShare;
        tvTerms = binding.tvTerms;
        tvLogout = binding.tvLogout;

        headerBar.tvTitle.setText(getResources().getString(R.string.settings));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        tvSetUpCard.setOnClickListener(this);
        tvPartners.setOnClickListener(this);
        tvNotification.setOnClickListener(this);
        tvHowappworks.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        tvShareExperience.setOnClickListener(this);
        tvSocialShare.setOnClickListener(this);
        tvTerms.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent;
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;
                case R.id.tvSetUpCard:
                    intent = new Intent(this, CardListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvPartners:
                    intent = new Intent(this, PartnersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvNotification:
                    intent = new Intent(this, NotificationListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvHowappworks:
                    intent = new Intent(this, HowTheAppWorksActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvChangePassword:
                    intent = new Intent(this, ChangePasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvShareExperience:
                    intent = new Intent(this, ShareYourExperienceActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvSocialShare:
                    intent = new Intent(this, SocialSharingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tvTerms:
                    intent = new Intent(this, TermsConditionActivity.class);
                    intent.putExtra(Constants.FROM, Constants.SETTINGS);
                    startActivity(intent);
                    break;
                case R.id.tvLogout:
                    showLogoutAlert();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLogoutAlert() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setMessage(getResources().getString(R.string.logout_alert_message));
            builder.setCancelable(true);

            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
//                    Intent intent = new Intent(SettingsActivity.this, SignInActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
                    callLogoutAPI();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callLogoutAPI() {
        if (Helper.isCheckInternet(SettingsActivity.this)) {
            pDialog = new ProgressDialog(SettingsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.logoutAPI(logoutRequest(), responseCallback);
        }
    }

    private LogoutRequest logoutRequest() {
        User user = SessionManager.getInstance().getUser(SettingsActivity.this);
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUserId(user.getUserId());
        logoutRequest.setAccessToken(user.getAccessToken());
        return logoutRequest;
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                ApiResponse response = (ApiResponse) object;
                if (response.getStatus() == 1) {
                    SessionManager.getInstance().clearCredential(SettingsActivity.this);
                    Intent intent = new Intent(SettingsActivity.this, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }

        @Override
        public void ResponseFailCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    };
}
