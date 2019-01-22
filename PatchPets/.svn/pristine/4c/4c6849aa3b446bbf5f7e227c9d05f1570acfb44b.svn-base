package com.patchpets.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityShareYourExperienceBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.ShareYourExperienceRequest;
import com.patchpets.model.responseModel.ApiResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

public class ShareYourExperienceActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityShareYourExperienceBinding binding;
    private HeaderBar headerBar;
    private TextView tvSubmit;
    private EditText etTitle, etExperiance, etFutureFeature;
    private String strEtTitle, strEtExperiance, strEtFutureFeature;
    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private String phoneName, appVersion, strOsVersion;
    private Activity context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_your_experience);
        context = ShareYourExperienceActivity.this;

        bindViews();
        setHeaderBar();
        setListener();
        setData();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        tvSubmit = binding.tvSubmit;
        etTitle = binding.etTitle;
        etExperiance = binding.etExperiance;
        etFutureFeature = binding.etFutureFeature;
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.share_us_your_experince_title);
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    private void setData() {
        try {
            user = SessionManager.getInstance().getUser(ShareYourExperienceActivity.this);
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName;
            strOsVersion = Build.VERSION.RELEASE;
            phoneName = android.os.Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        KeyboardUtility.hideKeyboard(ShareYourExperienceActivity.this, v);
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;

            case R.id.tvSubmit:
                if (isValid()) {
                    callShareYourExpApi();
                }
                break;
        }
    }

    private boolean isValid() {
        try {
            strEtTitle = etTitle.getText().toString().trim();
            strEtExperiance = etExperiance.getText().toString().trim();
            strEtFutureFeature = etFutureFeature.getText().toString().trim();

            if (strEtTitle.length() == 0) {
                etTitle.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.title_null), etTitle, this);
                return false;
            }
            if (strEtExperiance.length() == 0) {
                etExperiance.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.experince_null), etExperiance, this);
                return false;
            }
            if (strEtFutureFeature.length() == 0) {
                etFutureFeature.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.feature_null), etFutureFeature, this);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void callShareYourExpApi() {
        if (Helper.isCheckInternet(ShareYourExperienceActivity.this)) {
            pDialog = new ProgressDialog(ShareYourExperienceActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.shareYourExperienceAPI(shareYourExperienceRequest(), responseCallback);
        }
    }

    private ShareYourExperienceRequest shareYourExperienceRequest() {
        ShareYourExperienceRequest shareYourExperienceRequest = new ShareYourExperienceRequest();
        shareYourExperienceRequest.setUserId(user.getUserId());
        shareYourExperienceRequest.setAccessToken(user.getAccessToken());
        shareYourExperienceRequest.setTitle(strEtTitle);
        shareYourExperienceRequest.setExperienceDetails(strEtExperiance);
        shareYourExperienceRequest.setSuggestionsForFuture(strEtFutureFeature);
        shareYourExperienceRequest.setMobileName(phoneName);
        shareYourExperienceRequest.setAppVersion(appVersion);
        shareYourExperienceRequest.setEmail(user.getEmail());
        shareYourExperienceRequest.setDeviceType(Constants.DEVICE_TYPE);
        return shareYourExperienceRequest;
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
                    AlertDialogUtility.showAlert(ShareYourExperienceActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            });
                } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                    AlertDialogUtility.showAlert(ShareYourExperienceActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(ShareYourExperienceActivity.this, SignInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
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
