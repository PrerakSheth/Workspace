package com.konkr.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.konkr.Models.Country;
import com.konkr.Models.FbUserAvailable;
import com.konkr.Models.SignIn;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivitySignInBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SignInActivity extends Activity implements View.OnClickListener {
    private ActivitySignInBinding binding;

    private MyEditText etEmailId;
    private MyEditText etPassword;
    private MyTextView tvForgotPassword;
    private MyTextView tvSignInBtn;
    private MyTextView tvSignInForFacebook;
    private MyTextView tvCreateAccount;
    private String email, password;
    private Activity context;
    private String deviceToken;
    private View snackBarView;

    private String facebookEmail;
    private String strfacebookId;

    private CallbackManager callbackManager;
    private List<String> permissionNeeds = Arrays.asList("public_profile,email");

    private ArrayList<Country.CountryListBean> alCountry = new ArrayList<>();
    private ArrayList<Country.CountryListBean> alCountryName = new ArrayList<>();
    private ArrayAdapter<Country.CountryListBean> alCountryArrayAdapter;
    private int countryId;
    private AppCompatSpinner spinCountry;
    MyTextView tvCountry;
    private String country;

    private String fbFirstName;
    private String fbLastName;
    private String fbProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        context = SignInActivity.this;
        snackBarView = findViewById(android.R.id.content);
        String token = FirebaseInstanceId.getInstance().getToken();

        if (token != null) {
            LogM.LogE("Sign in token+" + token);
            SessionManager.setDeviceToken(context, token);
        }
        bindViews();
        setListener();
        getDeviceToken();
    }

    private void getDeviceToken() {
        deviceToken = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("Android", "Android ID : " + deviceToken);
    }

    private void setListener() {
        try {
            tvCreateAccount.setOnClickListener(this);
            tvSignInForFacebook.setOnClickListener(this);
            tvSignInBtn.setOnClickListener(this);
            tvForgotPassword.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            etEmailId = binding.etEmailId;
            etPassword = binding.etPassword;
            tvForgotPassword = binding.tvForgotPassword;
            tvSignInBtn = binding.tvSignInBtn;
            tvSignInForFacebook = binding.tvSignInForFacebook;
            tvCreateAccount = binding.tvCreateAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        System.exit(0);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.tvSignInBtn:
                    KeyboardUtility.HideKeyboard(this, tvSignInBtn);
                    setValidation();
                    break;

                case R.id.tvSignInForFacebook:
                    KeyboardUtility.HideKeyboard(context, v);
                    if (ConnectivityDetector
                            .isConnectingToInternet(context)) {
                        getFBLogin();
                    } else {
                        AlertDialogUtility.showInternetAlert(context);
                    }
                    break;

                case R.id.tvCreateAccount:
                    Intent intent = new Intent(this, SignUpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.tvForgotPassword:
                    Intent intent1 = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent1);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setValidation() {
        try {
            email = etEmailId.getText().toString().trim();
            password = etPassword.getText().toString().trim();

            if (email.length() == 0) {
                etEmailId.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.email_validation), etEmailId, this);
            } else if (!validateEmail(email)) {
                etEmailId.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_email), etEmailId, this);
            } else if (password.length() == 0) {
                etPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.pwd_validation), etPassword, this);
            }
//            else if (password.length() < 6) {
//                etPassword.requestFocus();
//                AlertDialogUtility.showSnakeBar(getString(R.string.valid_pwd), etPassword, this);
//            }
            else {
//                AlertDialogUtility.showAlert(this, getString(R.string.success_login));
                callSignInAPI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static boolean validateEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void callSignInAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.LOGIN.PARAM_EMAIL_ID, email);
            jsonObject.put(WebField.LOGIN.PARAM_PASSWORD, password);
            jsonObject.put(WebField.LOGIN.PARAM_DEVICE_TOKEN, SessionManager.getDeviceToken(context));
            jsonObject.put(WebField.LOGIN.PARAM_DEVICE_TYPE, 1);
            jsonObject.put(WebField.LOGIN.PARAM_COUNTRYID, 0);
            jsonObject.put(WebField.LOGIN.PARAM_FB_ACCESS_TOKEN, "");
            jsonObject.put(WebField.LOGIN.PARAM_LOGIN_TYPE, 0);

            LogM.LogE("Request : SignIn : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.LOGIN.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : SignIn : " + jsonObject.toString());
                    SignIn user = new Gson().fromJson(String.valueOf(jsonObject), SignIn.class);
                    if (isSuccess) {
//                        SignUp user = new Gson().fromJson(String.valueOf(jsonObject), SignUp.class);
                        SessionManager.saveSignInDetails(SignInActivity.this, user);
                        SessionManager.setLogin(context, true);
                        SessionManager.setIsProfileSetup(context, user.getUserDetails().getIsProfileSetup());
                        SessionManager.isSpotifySkip(context, true);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFBLogin() {
        try {
            callbackManager = CallbackManager.Factory.create();
            ArrayList<String> alPermission = new ArrayList<String>();
            alPermission.add("public_profile,email");
            LoginManager.getInstance().logOut();

//            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().logInWithReadPermissions(context, alPermission);
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(final LoginResult loginResults) {
                    LogM.LogE("getAccessToken ::" + loginResults.getAccessToken());
                    if (loginResults.getAccessToken() != null) {
                        final GraphRequest request = GraphRequest.newMeRequest(loginResults.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject jsonFBDetails, GraphResponse response) {
                                        try {
                                            Log.e("id ::", "" + jsonFBDetails.get("id"));
                                            Log.e("first_name ::", "" + jsonFBDetails.get("first_name"));
                                            Log.e("email ::", "" + jsonFBDetails.get("email"));
                                            Log.e("last_name ::", "" + jsonFBDetails.get("last_name"));
                                            Log.e("picture ::", "" + jsonFBDetails.get("picture"));

                                            fbFirstName = jsonFBDetails.getString("first_name");
                                            fbLastName = jsonFBDetails.getString("last_name");
                                            fbProfilePic = jsonFBDetails.getJSONObject("picture").getJSONObject("data").getString("url");

                                            Log.e("first_name ::", "" + fbFirstName);
                                            Log.e("lastName  ::", "" + fbLastName);
                                            Log.e("Profile pic ::", "" + fbProfilePic);

                                            strfacebookId = jsonFBDetails.get("id").toString();
//                                            strfirstName = jsonFBDetails.get("first_name").toString().trim();
//                                            strlastName = jsonFBDetails.get("last_name").toString().trim();
                                            facebookEmail = jsonFBDetails.get("email").toString().trim();

                                            callIsFBUserAvailable(facebookEmail);

//                                            openCoutnryDialog();

//                                            LoginManager.getInstance().logOut();
//                                            Intent in = new Intent(SignInEmployerActivity.this, FacebookLoginActivity.class);
//                                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(in);
//                                            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,last_name,picture,first_name");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                    LogM.LogE("Exception---" + e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callIsFBUserAvailable(String fbEmail) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.IS_FB_LOGIN.PARAM_EMAIL_ID, fbEmail);

            LogM.LogE("Request : isFbUserAvailable : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.IS_FB_LOGIN.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : isFbUserAvailable : " + jsonObject.toString());
                    FbUserAvailable fbUserAvailable = new Gson().fromJson(String.valueOf(jsonObject), FbUserAvailable.class);
                    if (isSuccess) {
                        if (fbUserAvailable.getUserAvailable() == 0) {
                            openCoutnryDialog();
                        } else {
                            callSignInWithFacebookAPI();
                        }
                    } else {
                        AlertDialogUtility.showSnakeBar(fbUserAvailable.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCoutnryDialog() {
        callCountryListAPI();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_country);
        dialog.setCancelable(false);
        tvCountry = dialog.findViewById(R.id.tvCountry);
        spinCountry = dialog.findViewById(R.id.spinCountry);
        MyTextView tvOk = dialog.findViewById(R.id.tvOk);
        MyTextView tvCancel = dialog.findViewById(R.id.tvCancel);


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinCountry.performClick();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                spinCountry.performClick();
                country = spinCountry.getSelectedItem().toString().trim();
                if (country.equalsIgnoreCase(getResources().getString(R.string.country))) {
                    spinCountry.requestFocus();
                    AlertDialogUtility.showSnakeBar(getString(R.string.country_validation), snackBarView, context);
                } else {
                    callSignInWithFacebookAPI();
                    dialog.dismiss();
                }


            }
        });
        dialog.show();
    }

    private void callSignInWithFacebookAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.LOGIN.PARAM_EMAIL_ID, facebookEmail);
            jsonObject.put(WebField.LOGIN.PARAM_PASSWORD, "");
            jsonObject.put(WebField.LOGIN.PARAM_DEVICE_TOKEN, SessionManager.getDeviceToken(context));
            jsonObject.put(WebField.LOGIN.PARAM_DEVICE_TYPE, 1);
            jsonObject.put(WebField.LOGIN.PARAM_COUNTRYID, countryId);
            jsonObject.put(WebField.LOGIN.PARAM_FB_ACCESS_TOKEN, strfacebookId);
            jsonObject.put(WebField.LOGIN.PARAM_LOGIN_TYPE, 1);

            LogM.LogE("Request : Fb: SignIn : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.LOGIN.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Fb : SignIn : " + jsonObject.toString());
                    SignIn user = new Gson().fromJson(String.valueOf(jsonObject), SignIn.class);
                    if (isSuccess) {
//                        SignUp user = new Gson().fromJson(String.valueOf(jsonObject), SignUp.class);
                        SessionManager.saveSignInDetails(SignInActivity.this, user);
                        SessionManager.setLogin(context, true);
                        SessionManager.setIsProfileSetup(context, user.getUserDetails().getIsProfileSetup());
                        if (SessionManager.getIsProfileSetup(context).equalsIgnoreCase("0")) {
                            Intent intent = new Intent(context, SetUpProfileActivity.class);
                            intent.putExtra(GlobalData.FB_FIRST_NAME, fbFirstName);
                            intent.putExtra(GlobalData.FB_LAST_NAME, fbLastName);
                            intent.putExtra(GlobalData.FB_PROFILE_PIC, fbProfilePic);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else if (SessionManager.getIsProfileSetup(context).equalsIgnoreCase("1")) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
//                        Intent intent = new Intent(context, SetUpProfileActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
                    } else {
                        AlertDialogUtility.showSnakeBar(user.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void callCountryListAPI() {
        try {
            new GetJsonWithAndroidNetworkingLib(context, new JSONObject(), WebField.BASE_URL + WebField.COUNTRY_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        Country country = new Gson().fromJson(String.valueOf(jsonObject), Country.class);
                        alCountry = country.getCountryList();
                        LogM.LogE("Country size: " + alCountry.size());

                        alCountryName.addAll(alCountry);

                        Country.CountryListBean questionDatum = new Country.CountryListBean();
                        questionDatum.setCountryName("Select Your Country");
                        questionDatum.setCountryId(0);
                        alCountryName.add(0, questionDatum);

                        alCountryArrayAdapter = new ArrayAdapter<Country.CountryListBean>(context, R.layout.row_spineer, R.id.txtVwSpinner, alCountryName) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view.findViewById(R.id.txtVwSpinner);
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        alCountryArrayAdapter.setDropDownViewResource(R.layout.row_spineer);
                        spinCountry.setAdapter(alCountryArrayAdapter);
                        spinCountry.setOnItemSelectedListener(queOnItemSelectedListener1);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private AdapterView.OnItemSelectedListener queOnItemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
//                spinnerQue1.setVisibility(View.INVISIBLE);
                if (position > 0) {
                    final Country.CountryListBean countryListBean = (Country.CountryListBean) spinCountry.getItemAtPosition(position);
                    LogM.LogE("SpinnerCountry onItemSelected: country: " + countryListBean.getCountryId());
                    countryId = countryListBean.getCountryId();
                    tvCountry.setText(countryListBean.getCountryName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
