package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityChangePasswordBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.ChangePasswordRequest;
import com.patchpets.model.responseModel.ApiResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityChangePasswordBinding binding;
    private HeaderBar headerBar;
    private TextView tvSubmit;
    private EditText etCurrentPassword, etConfirmPassword, etNewPassword;
    private String currentPwd, newPwd, confirmPwd;
    private APIRequest apiRequest;
    private ProgressDialog pDialog;
    private User user;
    private View snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        snackBar = findViewById(android.R.id.content);
        user = SessionManager.getInstance().getUser(ChangePasswordActivity.this);

        bindViews();
        setHeaderBar();
        setListener();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        etCurrentPassword = binding.etCurrentPassword;
        etConfirmPassword = binding.etConfirmPassword;
        etNewPassword = binding.etNewPassword;
        tvSubmit = binding.tvSubmit;
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.change_password);
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;

            case R.id.tvSubmit:
                if (isValid()) {
                    KeyboardUtility.hideKeyboard(ChangePasswordActivity.this, tvSubmit);
                    callChangePassword();
                }
                break;
        }
    }

    private boolean isValid() {
        try {
            currentPwd = etCurrentPassword.getText().toString().trim();
            newPwd = etNewPassword.getText().toString().trim();
            confirmPwd = etConfirmPassword.getText().toString().trim();

            if (currentPwd.length() == 0) {
                etCurrentPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_enter_old_password), etCurrentPassword, this);
                return false;
            }
            if (newPwd.length() == 0) {
                etNewPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_enter_new_password), etNewPassword, this);
                return false;
            }
            if (newPwd.length() < 6) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.invalid_password_length), etNewPassword, this);
                etNewPassword.requestFocus();
                return false;
            }
            if (!GlobalMethods.isValidPassword(newPwd)) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.valid_new_combination_pwd), etNewPassword, this);
                etNewPassword.requestFocus();
                return false;
            }
            if (confirmPwd.length() == 0) {
                etConfirmPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_enter_confirm_new_password), etConfirmPassword, this);
                return false;
            }
            if (!newPwd.equals(confirmPwd)) {
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_new_password_confirmpass_not_match), etConfirmPassword, this);
                etConfirmPassword.requestFocus();
                return false;
            }
            if (currentPwd.equals(newPwd)) {
                AlertDialogUtility.showSnakeBar(getString(R.string.msg_current_password_new_password_match), etNewPassword, this);
                etNewPassword.requestFocus();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void callChangePassword() {
        if (Helper.isCheckInternet(ChangePasswordActivity.this)) {
            pDialog = new ProgressDialog(ChangePasswordActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.changePasswordAPI(changePasswordRequest(), responseCallback);
        }
    }

    private ChangePasswordRequest changePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUserId(user.getUserId());
        changePasswordRequest.setAccessToken(user.getAccessToken());
        changePasswordRequest.setNewPass(newPwd);
        changePasswordRequest.setCurrentPass(currentPwd);
        return changePasswordRequest;
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
                    AlertDialogUtility.showAlert(ChangePasswordActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            });
                } else {
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, ChangePasswordActivity.this);
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
