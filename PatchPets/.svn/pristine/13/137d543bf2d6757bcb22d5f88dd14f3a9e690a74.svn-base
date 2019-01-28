package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityTermsConditionBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.SignUpRequest;
import com.patchpets.model.responseModel.SignUpResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;
import com.skyfishjy.library.RippleBackground;

public class TermsConditionActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityTermsConditionBinding binding;
    private HeaderBar headerBar;
    private ImageView ivchecked;
    private TextView tv_understandandaccept, tv_termscondition;
    private Button btnContinue;
    private boolean check;
    private View viewTxtBg;
    private RippleBackground rippleBackground;
    private ConstraintLayout layoutBottom;
    private View snackBar;

    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private SignUpRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_condition);
        snackBar = findViewById(android.R.id.content);

        bindViews();
        setListener();

        request = getIntent().getParcelableExtra(Constants.SIGN_UP_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ivchecked = binding.ivchecked;
        btnContinue = binding.btnContinue;
        tv_understandandaccept = binding.tvUnderstandandaccept;
        tv_termscondition = binding.tvTermscondition;
        viewTxtBg = binding.viewTxtBg;
        layoutBottom = binding.layoutBottom;
        headerBar.tvTitle.setText(getResources().getString(R.string.label_termscondition));
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.SETTINGS)) {
            layoutBottom.setVisibility(View.GONE);
        }
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
        rippleBackground = binding.content;
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ivchecked.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    finish();
                    break;

                case R.id.ivchecked:
                    if (!check) {
                        check = true;
                        rippleBackground.stopRippleAnimation();
                        btnContinue.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                        tv_understandandaccept.setTextColor(getResources().getColor(android.R.color.black));
                        tv_termscondition.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        ivchecked.setImageResource(R.drawable.select_contact_check_green);
                        viewTxtBg.setVisibility(View.VISIBLE);
                    } else {
                        check = false;
                        rippleBackground.startRippleAnimation();
                        btnContinue.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        tv_understandandaccept.setTextColor(getResources().getColor(R.color.text_hint_color));
                        tv_termscondition.setTextColor(getResources().getColor(R.color.text_hint_color));
                        ivchecked.setImageResource(R.drawable.select_contact_check_grey);
                        viewTxtBg.setVisibility(View.GONE);
                    }
                    break;

                case R.id.btn_continue:
                    if (check) {
                        rippleBackground.stopRippleAnimation();
                        callSignUp();
//                        if (getIntent().getIntExtra(Constants.USER_TYPE, 2) == Constants.BUSINESS_USER) {
//                            Intent Intent = new Intent(TermsConditionActivity.this, SetupBusinessProfileActivity.class);
//                            startActivity(Intent);
//                        } else {
//                            Intent Intent = new Intent(TermsConditionActivity.this, SetupProfileActivity.class);
//                            startActivity(Intent);
//                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callSignUp() {
        if (Helper.isCheckInternet(TermsConditionActivity.this)) {
            pDialog = new ProgressDialog(TermsConditionActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.signUpAPI(request, responseCallback);
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
                    SignUpResponse response = (SignUpResponse) object;
                    if (response.getStatus() == 1) {
                        User user = new User();
                        user.setUserId(response.getUserDetails().getUserId());
                        user.setAccessToken(response.getUserDetails().getAccessToken());
                        user.setLoginType(response.getUserDetails().getLoginType());
                        user.setUserType(response.getUserDetails().getUserType());
                        user.setEmail(response.getUserDetails().getEmail());
                        user.setQrCodeUrl(response.getUserDetails().getQrcodeURL());
                        SessionManager.getInstance().saveUser(TermsConditionActivity.this, user);
                        SessionManager.getInstance().setSession(TermsConditionActivity.this, true);

                        Intent iSetup;
                        if (response.getUserDetails().getUserType() == Constants.BUSINESS_USER) {
                            iSetup = new Intent(TermsConditionActivity.this, SetupBusinessProfileActivity.class);
                        } else {
                            iSetup = new Intent(TermsConditionActivity.this, SetupProfileActivity.class);
                        }
                        iSetup.putExtra(Constants.FROM, Constants.TERMS_CONDITION);
                        startActivity(iSetup);

//                        Intent iTermsCondition = new Intent(TermsConditionActivity.this, TermsConditionActivity.class);
//                        iTermsCondition.putExtra(Constants.FROM, Constants.SIGN_UP);
//                        iTermsCondition.putExtra(Constants.USER_TYPE, response.getUserDetails().getUserType());
//                        startActivity(iTermsCondition);
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, TermsConditionActivity.this);
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
