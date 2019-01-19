package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.patchpets.R;
import com.patchpets.databinding.ActivityForgetPasswordBinding;
import com.patchpets.model.requestModel.ForgotPasswordRequest;
import com.patchpets.model.responseModel.ForgotPasswordResponse;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityForgetPasswordBinding binding;
    private HeaderBar headerBar;
    private EditText etEmail;
    private Button btnSubmit;
    private View snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        snackBar = findViewById(android.R.id.content);
        bindViews();
        setListener();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        etEmail = binding.etEmail;
        btnSubmit = binding.btnSubmit;
        headerBar.tvTitle.setText(getResources().getString(R.string.forgot_passwords));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;

            case R.id.btnSubmit:
                if (isValid()) {
                    KeyboardUtility.hideKeyboard(ForgotPasswordActivity.this, view);
                    callForgotPassword();
                }
                break;
        }
    }

    private boolean isValid() {
        try {
            String email = etEmail.getText().toString().trim();

            if (email.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_email), snackBar, ForgotPasswordActivity.this);
                etEmail.requestFocus();
                return false;
            }
            if (!GlobalMethods.isValidEmail(email)) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.invalid_email), snackBar, ForgotPasswordActivity.this);
                etEmail.requestFocus();
                return false;
            }

            request.setEmail(email);
            request.setDeviceType(Constants.DEVICE_TYPE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private ForgotPasswordRequest request = new ForgotPasswordRequest();

    private void callForgotPassword() {
        if (Helper.isCheckInternet(ForgotPasswordActivity.this)) {
            pDialog = new ProgressDialog(ForgotPasswordActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.forgotPasswordAPI(request, responseCallback);
        }
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (object != null) {
                    ForgotPasswordResponse response = (ForgotPasswordResponse) object;
                    if (response.getStatus() == 1) {
                        AlertDialogUtility.showAlert(ForgotPasswordActivity.this, response.getMessage(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        onBackPressed();
                                    }
                                });
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, ForgotPasswordActivity.this);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
