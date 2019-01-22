package com.konkr.Activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.konkr.Models.CommonMessageStatus;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityChangePasswordBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private Headerbar headerBar;
    private MyEditText etCurrentPassword;
    private MyEditText etNewPassword;
    private MyEditText etConfirmPassword;
    private MyTextView tvChange;
    private String strCurrentPwd, strNewPwd, strConfirmPwd;

    private ActivityChangePasswordBinding binding;
    private Activity context;
    private View snackBarView;

//    etCurrentPassword
//            etNewPassword
//    etConfirmPassword
//            tvChange

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        context = ChangePasswordActivity.this;
        snackBarView = findViewById(android.R.id.content);
        bindViews();
        setHeaderBar();
        setListener();
    }

    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            tvChange.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.change_pwd_txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            headerBar = findViewById(R.id.headerBar);
            etCurrentPassword = binding.etCurrentPassword;
            etNewPassword = binding.etNewPassword;
            etConfirmPassword = binding.etConfirmPassword;
            tvChange = binding.tvChange;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnLeftOne:
                    KeyboardUtility.HideKeyboard(this, v);
                    finish();
                    break;

                case R.id.tvChange:
                    setValidation();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValidation() {
        try {
            strCurrentPwd = etCurrentPassword.getText().toString().trim();
            strNewPwd = etNewPassword.getText().toString().trim();
            strConfirmPwd = etConfirmPassword.getText().toString().trim();

            if (strCurrentPwd.length() == 0) {
                etCurrentPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_enter_old_password), etCurrentPassword, this);
            } else if (strCurrentPwd.length() < 6) {
                etCurrentPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_old_password_length), etCurrentPassword, this);
            } else if (strNewPwd.length() == 0) {
                etNewPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_enter_new_password), etNewPassword, this);
            } else if (strNewPwd.length() < 6) {
                etNewPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_new_password_length), etNewPassword, this);
            } else if (strNewPwd.length() != 0 && !GlobalMethods.isValidPassword(strNewPwd)) {
                etNewPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_new_combination_pwd), etNewPassword, this);
            } else if (strConfirmPwd.length() == 0) {
                etConfirmPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_enter_confirm_new_password), etConfirmPassword, this);
            } else if (strConfirmPwd.length() < 6) {
                etConfirmPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_confrim_password_length), etConfirmPassword, this);
            } else if (!strNewPwd.equals(strConfirmPwd)) {
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_new_password_confirmpass_not_match), etConfirmPassword, this);
            } else if (strCurrentPwd.equals(strNewPwd)) {
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_current_password_new_password_match), etNewPassword, this);
            } else {
//                AlertDialogUtility.showAlertWithOnlyYesOption(this, getResources().getString(R.string.new_pwd_txt), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });
                callChangePassword();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callChangePassword() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.CHANGE_PASSWORD.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.CHANGE_PASSWORD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.CHANGE_PASSWORD.PARAM_CURRENT_PASSWORD, strCurrentPwd);
            jsonObject.put(WebField.CHANGE_PASSWORD.PARAM_NEW_PASSWORD, strNewPwd);

            LogM.LogE("Request : Change Pass : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.CHANGE_PASSWORD.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Change Pass : " + jsonObject.toString());
                    CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                    if (isSuccess) {
                        LogM.LogE("Message  : " + user.getMessage());
                        AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, GlobalData.DELAY_TIME);
                    } else {
                        AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
