package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.konkr.Models.CommonMessageStatus;
import com.konkr.R;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityForgotPasswordBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.konkr.Activities.SignInActivity.validateEmail;

public class ForgotPasswordActivity extends Activity implements View.OnClickListener {

    private MyEditText etForgotEmail;
    private Headerbar headerBar;
    private String forgotEmail;
    private Activity context;
    private MyTextView tvSend;

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot__password);
        context = ForgotPasswordActivity.this;

        bindViews();
        setHeaderBar();
        setListener();
    }

    private void setListener() {
        try {
            tvSend.setOnClickListener(this);
            headerBar.ibtnLeftOne.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.forgot_password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            tvSend = binding.tvSend;
            etForgotEmail = binding.etForgotEmail;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.ibtnLeftOne:
                    finish();
                    break;

                case R.id.tvSend:
                    setValidation();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValidation() {
        try {
            forgotEmail = etForgotEmail.getText().toString().trim();

            if (forgotEmail.length() == 0) {
                AlertDialogUtility.showSnakeBar(getString(R.string.email_validation), etForgotEmail, this);
            } else if (!validateEmail(forgotEmail)) {
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_email), etForgotEmail, this);
            } else {
                KeyboardUtility.HideKeyboard(context, etForgotEmail);
                callForgotPasswordAPI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callForgotPasswordAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.FORGOT_PASSWORD.PARAM_EMAIL_ID, forgotEmail);
            jsonObject.put(WebField.FORGOT_PASSWORD.PARAM_DEVICE_TYPE, 1);

            LogM.LogE("Request : ForgotPassword : " + jsonObject.toString());

//            MyProgressDialog.showProgressDialog(context);
            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.FORGOT_PASSWORD.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : ForgotPassword : " + jsonObject.toString());

                        CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                        //                hidepDialog();
//                        AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
                        Toast.makeText(context, user.getMessage(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }).execute();
//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                    WebField.BASE_URL + WebField.FORGOT_PASSWORD.MODE, jsonObject, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    LogM.LogE("Response : ForgotPassword : " + response.toString());
//
//                    CommonMessageStatus user = new Gson().fromJson(String.valueOf(response), CommonMessageStatus.class);
//                    //                hidepDialog();
//                    MyProgressDialog.hideProgressDialog();
//                    if (user.getStatus() == 1) {
////                        AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
//                        Toast.makeText(context, user.getMessage(), Toast.LENGTH_LONG).show();
//                        Intent i = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
//                        startActivity(i);
//                        finish();
//                    } else {
//                        MyProgressDialog.hideProgressDialog();
//                        AlertDialogUtility.showError(context, snackBarView, user.getMessage());
//                    }
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    MyProgressDialog.hideProgressDialog();
//                    LogM.LogV("Error : " + error.getMessage());
//                    AlertDialogUtility.showError(context, snackBarView, getResources().getString(R.string.something_went_wrong));
//                }
//            });
//            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
//                    GlobalData.TIME_OUT,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            // Adding request to request queue
//            AppController.getInstance().addToRequestQueue(jsonObjReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
