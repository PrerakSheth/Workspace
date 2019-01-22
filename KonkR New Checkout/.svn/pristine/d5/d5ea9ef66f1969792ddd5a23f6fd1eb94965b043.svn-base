package com.konkr.Activities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityShareYourExperienceBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ShareYourExperiencesActivity extends Activity implements View.OnClickListener {
    private Headerbar headerBar;
    private EditText etTitle, etExperiance, etFutureFeature;
    private TextView tvRemainingTextExperince, tvRemainingTextFuture, btnFeedbackSubmit;
    private String strEtTitle, strEtExperiance, strEtFutureFeature;
    private ActivityShareYourExperienceBinding binding;
    private Activity context;
    private String strEmailId, strPhoneName, strAppVersion, strOsVersion, strSubject, strDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_your_experience);
        context = ShareYourExperiencesActivity.this;
        bindView();
        setListener();
        setData();
        setHeaderBar();
        setImoIntoEditext();
        setTextCount();
    }

    private void setData() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getPackageName(), 0);
            strAppVersion = info.versionName;
            strOsVersion = Build.VERSION.RELEASE;
            strPhoneName = android.os.Build.MODEL;
            strEmailId = SessionManager.getEmailId(ShareYourExperiencesActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextCount() {

        etExperiance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRemainingTextExperince.setText(500 - s.toString().length() + "");

            }
        });

        etFutureFeature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRemainingTextFuture.setText(500 - s.toString().length() + "");
            }
        });
    }

    private void setImoIntoEditext() {

        etTitle.setImeActionLabel(getString(R.string.enter), KeyEvent.KEYCODE_ENTER);
        etExperiance.setImeActionLabel(getString(R.string.enter), KeyEvent.KEYCODE_ENTER);
        etFutureFeature.setImeActionLabel(getString(R.string.enter), KeyEvent.KEYCODE_ENTER);
    }


    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        btnFeedbackSubmit.setOnClickListener(this);
    }

    private void setHeaderBar() {

        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.share_us_your_experince_title);
    }


    private void bindView() {

        headerBar = binding.headerBar;
        etTitle = binding.etTitle;
        etExperiance = binding.etExperiance;
        etFutureFeature = binding.etFutureFeature;
        tvRemainingTextExperince = binding.tvRemainingTextExperince;
        tvRemainingTextFuture = binding.tvRemainingTextFuture;
        btnFeedbackSubmit = binding.btnFeedbackSubmit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ibtnLeftOne:
                KeyboardUtility.HideKeyboard(this, v);
                finish();
                break;

            case R.id.btnFeedbackSubmit:
                if (validation()) {
                    callShareYourExpApi();
                }
                break;
        }
    }

    private void callShareYourExpApi() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_USER_ID, SessionManager.getUserId(ShareYourExperiencesActivity.this));
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(ShareYourExperiencesActivity.this));
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_TITLE, strEtTitle);
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_EXPERIENCE_DETAILS, strEtExperiance);
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_SUGGESTIONS_FOR_FUTURE, strEtFutureFeature);
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_OSDETAIL, strOsVersion);
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_MOBILENAME, strPhoneName);
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_APPVERSION, strAppVersion);
            jsonObject.put(WebField.SHARE_FEEDBACK.PARAM_EMAIL, strEmailId);

            LogM.LogE("Request : Share your Exp : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SHARE_FEEDBACK.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    try {
                        String msg = jsonObject.getString(GlobalData.MESSAGE).toString();

                        if (isSuccess) {
                            LogM.LogE("Response : Share your Exp :" + jsonObject.toString());
                            AlertDialogUtility.showSnakeBar(msg, btnFeedbackSubmit, context);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, GlobalData.DELAY_TIME);


                        } else {
                            AlertDialogUtility.showSnakeBar(msg, btnFeedbackSubmit, context);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validation() {

        strEtTitle = etTitle.getText().toString().trim();
        strEtExperiance = etExperiance.getText().toString().trim();
        strEtFutureFeature = etFutureFeature.getText().toString().trim();

        if (strEtTitle.length() == 0) {
            etTitle.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.title_null), etTitle, this);
            return false;
        } else if (strEtExperiance.length() == 0) {
            etExperiance.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.experince_null), etExperiance, this);
            return false;
        } else if (strEtFutureFeature.length() == 0) {
            etFutureFeature.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.feature_null), etFutureFeature, this);
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void finish() {
        super.finish();
    }
}
