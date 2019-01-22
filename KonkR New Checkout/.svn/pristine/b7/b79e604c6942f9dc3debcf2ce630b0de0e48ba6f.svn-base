package com.konkr.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.konkr.Models.Country;
import com.konkr.Models.SignUp;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivitySignUpBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.konkr.Activities.SignInActivity.validateEmail;

public class SignUpActivity extends Activity implements View.OnClickListener {

    private ActivitySignUpBinding binding;

    private AppCompatSpinner spinCountry;
    private MyEditText etEmailId;
    private MyEditText etPassword;
    private MyTextView tvCountry;

    private CheckBox cbIAccept;
    private MyTextView tvIAccept;
    private MyTextView tvTearmsAndCondition;
    private MyTextView tvSignUpBtn;
    private ConstraintLayout clAlredayHaveAccount;

    private String country, email, password;
    private boolean isCheck = false;
    private CheckBox chkTerms;
    private Activity context;

    private View snackBarView;
    private ArrayList<Country.CountryListBean> alCountry = new ArrayList<>();
    private ArrayList<Country.CountryListBean> alCountryName = new ArrayList<>();
    private ArrayAdapter<Country.CountryListBean> alCountryArrayAdapter;
    private int countryId;
    private String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        context = SignUpActivity.this;
        snackBarView = findViewById(android.R.id.content);
        bindViews();
        setListener();
        callCountryListAPI();
        getDeviceToken();

        cbIAccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbIAccept.setChecked(true);
                } else {
                    cbIAccept.setChecked(false);
                }
            }
        });
    }

    private void getDeviceToken() {
        deviceToken = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("Android", "Android ID : " + deviceToken);
    }

    private void setListener() {
        try {
            tvIAccept.setOnClickListener(this);
            cbIAccept.setOnClickListener(this);
            tvTearmsAndCondition.setOnClickListener(this);
            tvSignUpBtn.setOnClickListener(this);
            tvCountry.setOnClickListener(this);
            clAlredayHaveAccount.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            spinCountry = binding.spinCountry;
            etEmailId = binding.etEmailId;
            etPassword = binding.etPassword;
            tvCountry = binding.tvCountry;

            cbIAccept = binding.cbIAccept;
            tvIAccept = binding.tvIAccept;
            tvTearmsAndCondition = binding.tvTearmsAndCondition;
            tvSignUpBtn = binding.tvSignUpBtn;
            clAlredayHaveAccount = binding.clAlredayHaveAccount;
//            spinCountry = findViewById(R.id.spinCountry);
//            etSignUpEmail = findViewById(R.id.etSignUpEmail);
//            etSignUpPwd = findViewById(R.id.etSignUpPwd);
////            ivImage = findViewById(R.id.ivImage);
//            tvTerms = findViewById(R.id.tvTerms);
//            btnSignUp = findViewById(R.id.btnSignUp);
//            chkTerms = findViewById(R.id.chkTerms);
//            tvCountry = findViewById(R.id.tvCountry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvSignUpBtn:
                    KeyboardUtility.HideKeyboard(this, tvSignUpBtn);
                    setValidation();
                    break;

                case R.id.clAlredayHaveAccount:
                    KeyboardUtility.HideKeyboard(this, tvSignUpBtn);
                    Intent intent = new Intent(context, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.cbIAccept:
//                    if (isCheck) {
//                        isCheck = false;
////                        ivImage.setImageResource(R.drawable.rightmark);
//                        cbIAccept.setChecked(true);
//                    } else {
//                        isCheck = true;
////                        ivImage.setImageResource(R.drawable.shape);
//                        cbIAccept.setChecked(false);
//                    }
                    break;

                case R.id.tvTearmsAndCondition:
                    Intent i = new Intent(SignUpActivity.this, TermsConditionActivity.class);
                    startActivity(i);
                    break;


                case R.id.tvCountry:
                    KeyboardUtility.HideKeyboard(this, tvCountry);
                    spinCountry.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private void setValidation() {
        try {
            country = spinCountry.getSelectedItem().toString().trim();
            email = etEmailId.getText().toString().trim();
            password = etPassword.getText().toString().trim();


            if (country.equalsIgnoreCase(getResources().getString(R.string.country))) {
                spinCountry.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.country_validation), spinCountry, this);
            } else if (email.length() == 0) {
                etEmailId.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.email_validation), etEmailId, this);
            } else if (!validateEmail(email)) {
                etEmailId.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_email), etEmailId, this);
            } else if (password.length() == 0) {
                etPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.pwd_validation), etPassword, this);
            } else if (password.length() < 6) {
                etPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_pwd), etPassword, this);
            } else if (password.length() != 0 && !GlobalMethods.isValidPassword(password)) {
                etPassword.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_combination_pwd), etPassword, this);
            } else if (!cbIAccept.isChecked()) {
                cbIAccept.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_term), cbIAccept, this);
            } else {
                callSignUpAPI();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callSignUpAPI() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.SIGN_UP.PARAM_EMAIL_ID, email);
            jsonObject.put(WebField.SIGN_UP.PARAM_PASSWORD, password);
            jsonObject.put(WebField.SIGN_UP.PARAM_DEVICE_TOKEN, SessionManager.getDeviceToken(context));
            jsonObject.put(WebField.SIGN_UP.PARAM_DEVICE_TYPE, 1);
            jsonObject.put(WebField.SIGN_UP.PARAM_COUNTRYID, countryId);

            LogM.LogE("Request : SignUp : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SIGN_UP.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : SignUp : " + jsonObject.toString());
//
                        final SignUp user = new Gson().fromJson(String.valueOf(jsonObject), SignUp.class);
                        AlertDialogUtility.showAlertWithOnlyYesOption(context, getResources().getString(R.string.konkr_txt), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SessionManager.saveUserDetails(context, user);
                                SessionManager.setIsProfileSetup(context, user.getUserDetails().getIsProfileSetup());
                                Intent intent = new Intent(context, SetUpProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        try {
                            AlertDialogUtility.showSnakeBar(jsonObject.get(WebField.MESSAGE).toString(), snackBarView, context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).execute();

//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                    WebField.BASE_URL + WebField.SIGN_UP.MODE, jsonObject, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    LogM.LogE("Response : SignUp : " + response.toString());
//
//                    SignUp user = new Gson().fromJson(String.valueOf(response), SignUp.class);
//                    //                hidepDialog();
//                    MyProgressDialog.hideProgressDialog();
//                    if (user.getStatus() == 1) {
//                        AlertDialogUtility.showAlertWithOnlyYesOption(context, getResources().getString(R.string.konkr_txt), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(context, SetUpProfileActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                finish();
//                            }
//                        });
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

    private AdapterView.OnItemSelectedListener queOnItemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
//                spinnerQue1.setVisibility(View.INVISIBLE);
                if (position > 0) {
                    final Country.CountryListBean countryListBean = (Country.CountryListBean) spinCountry.getItemAtPosition(position);
                    LogM.LogV("SpinnerCountry onItemSelected: country: " + countryListBean.getCountryId());
                    countryId = countryListBean.getCountryId();
                    tvCountry.setText(countryListBean.getCountryName());
                    tvCountry.setTextColor(getResources().getColor(R.color.text_color));
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
