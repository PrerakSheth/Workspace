package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.patchpets.R;
import com.patchpets.databinding.ActivitySignInBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.SignInRequest;
import com.patchpets.model.responseModel.SignInResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.Helper;
import com.patchpets.utils.LogM;
import com.patchpets.utils.MyClickableSpan;
import com.patchpets.utils.TypefaceSpan;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignInBinding binding;
    private EditText etEmail, etPassword;
    private CheckBox cbRememberMe;
    private TextView tvForgotPassword, tvSignUpHyperLink, tvSignIn;
    private ImageButton ibFacebook;
    private View snackBar;

    private LoginButton facebookLogin;
    private CallbackManager callbackManager;

    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private SignInRequest request = new SignInRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        snackBar = findViewById(android.R.id.content);
        callbackManager = CallbackManager.Factory.create();
        SessionManager.getInstance().clearCredential(SignInActivity.this);
        bindViews();
        setListener();
        setData();
        facebookLogin();
    }

    private void bindViews() {
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        cbRememberMe = binding.cbRememberMe;
        tvForgotPassword = binding.tvForgotPassword;
        tvSignUpHyperLink = binding.tvSignUpHyperLink;
        tvSignIn = binding.tvSignIn;
        ibFacebook = binding.ibFacebook;
        facebookLogin = binding.facebookLogin;
    }

    private void setListener() {
        tvSignIn.setOnClickListener(this);
        ibFacebook.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

        SpannableString spanString = new SpannableString(getResources().getString(R.string.dont_have_an_account));
        spanString.setSpan(new MyClickableSpan(SignInActivity.this, 1, Constants.SIGN_UP), spanString.length() - 7, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hyper_link_signin_signup)), spanString.length() - 7, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new RelativeSizeSpan(1.1f), spanString.length() - 7, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new TypefaceSpan(this, getResources().getString(R.string.font_poppins_bold)), spanString.length() - 7, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignUpHyperLink.setText(spanString);
        tvSignUpHyperLink.setMovementMethod(LinkMovementMethod.getInstance());
        tvSignUpHyperLink.setHighlightColor(Color.TRANSPARENT);
    }

    private void setData() {
        User user = SessionManager.getInstance().getUser(SignInActivity.this);
        if (user.isRememberMe()) {
            etEmail.setText(user.getEmail());
            cbRememberMe.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvSignIn:
                    if (isValid()) {
                        callSignIn();
                    }
                    break;

                case R.id.ibFacebook:
                    facebookLogin.performClick();
                    break;

                case R.id.tvForgotPassword:
                    Intent forgotPasswordIntent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                    startActivity(forgotPasswordIntent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid() {
        try {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_email), snackBar, SignInActivity.this);
                etEmail.requestFocus();
                return false;
            }
            if (!GlobalMethods.isValidEmail(email)) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.invalid_email), snackBar, SignInActivity.this);
                etEmail.requestFocus();
                return false;
            }

            if (password.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_password), snackBar, SignInActivity.this);
                etPassword.requestFocus();
                return false;
            }

            request.setEmail(email);
            request.setPassword(password);
            request.setDeviceType(Constants.DEVICE_TYPE);
            request.setDeviceToken(SessionManager.getInstance().getDeviceToken(SignInActivity.this));
            request.setFbAccessToken("");
            request.setLoginType(Constants.LOGIN_TYPE_NORMAL);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void callSignIn() {
        if (Helper.isCheckInternet(SignInActivity.this)) {
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.signInAPI(request, responseCallback);
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
                    SignInResponse response = (SignInResponse) object;
                    if (response.getStatus() == 1) {
                        User user = new User();
                        user.setUserId(response.getUserDetails().getUserId());
                        user.setFirstName(response.getUserDetails().getFirstName());
                        user.setLastName(response.getUserDetails().getLastName());
                        user.setAccessToken(response.getUserDetails().getAccessToken());
                        user.setLoginType(response.getUserDetails().getLoginType());
                        user.setUserType(response.getUserDetails().getUserType());
                        user.setEmail(response.getUserDetails().getEmail());
                        user.setQrCodeUrl(response.getUserDetails().getQrcodeURL());
                        user.setProfilePic(response.getUserDetails().getProfilePic());
                        user.setRememberMe(cbRememberMe.isChecked());
                        user.setBusinessName(response.getUserDetails().getBusinessName());
                        user.setContactNo(response.getUserDetails().getContactNo());
                        user.setAboutBusiness(response.getUserDetails().getAboutBusiness());
                        user.setServices(response.getUserDetails().getCategory());
                        user.setInstaLink(response.getUserDetails().getInstaLink());
                        SessionManager.getInstance().saveUser(SignInActivity.this, user);
                        SessionManager.getInstance().setSession(SignInActivity.this, true);

                        Intent iSignIn = new Intent(SignInActivity.this, HomeActivity.class);
                        iSignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(iSignIn);
                        finish();
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, SignInActivity.this);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void ResponseFailCallBack(Object object) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookLogin() {
        try {
            facebookLogin.setReadPermissions(Arrays.asList("email"));
            printKeyHash();
            facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest graphRequest = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        AccessToken token = AccessToken.getCurrentAccessToken();
                                        LogM.e("FacebookAccessToken : " + String.valueOf(token.getToken()));
                                        request.setEmail(object.getString("email"));
                                        request.setPassword("");
                                        request.setDeviceType(Constants.DEVICE_TYPE);
                                        request.setDeviceToken(SessionManager.getInstance().getDeviceToken(SignInActivity.this));
                                        request.setFbAccessToken(String.valueOf(token.getToken()));
                                        request.setLoginType(Constants.LOGIN_TYPE_FACEBOOK);
                                        callSignIn();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday");
                    graphRequest.setParameters(parameters);
                    graphRequest.executeAsync();
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash() {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                final PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
                final Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
                final MessageDigest md = MessageDigest.getInstance("SHA");
                for (Signature signature : signatures) {
                    md.update(signature.toByteArray());
                    final String signatureBase64 = new String(Base64.encode(md.digest(), Base64.DEFAULT));
                    LogM.e("KeyHash : " + signatureBase64);
                }
            } else {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (android.content.pm.Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    LogM.e("KeyHash : " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
