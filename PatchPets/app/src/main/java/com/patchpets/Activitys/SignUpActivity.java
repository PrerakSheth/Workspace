package com.patchpets.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.patchpets.R;
import com.patchpets.databinding.ActivitySignUpBinding;
import com.patchpets.location.MyLocation;
import com.patchpets.location.OnLocationFound;
import com.patchpets.model.requestModel.SignUpRequest;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.LogM;
import com.patchpets.utils.MyClickableSpan;
import com.patchpets.utils.TypefaceSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ActivitySignUpBinding binding;
    private EditText etEmail, etPassword;
    private TextView tvSignInHyperLink, tvUserType, tvSignUp;
    private Spinner spinnerUserType;
    private RelativeLayout rlUserTypeSpinner;
    private List<String> alUserType = new ArrayList<>();
    private View snackBar;
    private SignUpRequest request = new SignUpRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        snackBar = findViewById(android.R.id.content);
        buildGoogleApiClient();
        bindViews();
        setListener();
        setUserTypeSpinner();
    }

    private void bindViews() {
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        rlUserTypeSpinner = binding.rlUserTypeSpinner;
        spinnerUserType = binding.spinnerUserType;
        tvSignInHyperLink = binding.tvSignInHyperLink;
        tvUserType = binding.tvUserType;
        tvSignUp = binding.tvSignUp;
    }

    private void setListener() {
        tvSignUp.setOnClickListener(this);
        rlUserTypeSpinner.setOnClickListener(this);

        SpannableString spanString = new SpannableString(getResources().getString(R.string.already_have_an_account));
        spanString.setSpan(new MyClickableSpan(SignUpActivity.this, 1, Constants.SIGN_IN), spanString.length() - 7, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hyper_link_signin_signup)), spanString.length() - 7, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new RelativeSizeSpan(1.1f), spanString.length() - 7, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new TypefaceSpan(this, getResources().getString(R.string.font_poppins_bold)), spanString.length() - 7, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignInHyperLink.setText(spanString);
        tvSignInHyperLink.setMovementMethod(LinkMovementMethod.getInstance());
        tvSignInHyperLink.setHighlightColor(Color.TRANSPARENT);
    }

    private void setUserTypeSpinner() {
        try {
            String[] userType = getResources().getStringArray(R.array.userType);
            alUserType = new ArrayList<>(Arrays.asList(userType));
            final int alSize = alUserType.size() - 1;

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.item_spinner, alUserType) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            spinnerUserType.setAdapter(dataAdapter);
            spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    tvUserType.setText(alUserType.get(i));
                    if (i != alSize) {
                        tvUserType.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvUserType.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerUserType.setSelection(alSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvSignUp:
                    if (isValid()) {
                        Intent iTermsCondition = new Intent(SignUpActivity.this, TermsConditionActivity.class);
                        iTermsCondition.putExtra(Constants.FROM, Constants.SIGN_UP);
                        iTermsCondition.putExtra(Constants.SIGN_UP_DATA, request);
                        startActivity(iTermsCondition);
                    }
                    break;

                case R.id.rlUserTypeSpinner:
                    spinnerUserType.performClick();
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
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_email), snackBar, SignUpActivity.this);
                etEmail.requestFocus();
                return false;
            }
            if (!GlobalMethods.isValidEmail(email)) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.invalid_email), snackBar, SignUpActivity.this);
                etEmail.requestFocus();
                return false;
            }

            if (password.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_password), snackBar, SignUpActivity.this);
                etPassword.requestFocus();
                return false;
            }
            if (password.length() < 6) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.invalid_password_length), snackBar, SignUpActivity.this);
                etPassword.requestFocus();
                return false;
            }
            if (!GlobalMethods.isValidPassword(password)) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.invalid_password), snackBar, SignUpActivity.this);
                etPassword.requestFocus();
                return false;
            }

            int position = spinnerUserType.getSelectedItemPosition();

            if (position > 1) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.select_user_type), snackBar, SignUpActivity.this);
                spinnerUserType.requestFocus();
                return false;
            }

            if (mCurrentLocation == null) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.location_not_available), snackBar, SignUpActivity.this);
                return false;
            }

            request.setEmail(email);
            request.setPassword(password);
            request.setDeviceType(Constants.DEVICE_TYPE);
            request.setDeviceToken(SessionManager.getInstance().getDeviceToken(SignUpActivity.this));
            request.setUserType(position == 0 ? Constants.DOG_OWNER : Constants.BUSINESS_USER);
            request.setLatitude(mCurrentLocation.getLatitude());
            request.setLongitude(mCurrentLocation.getLongitude());
            request.setLocation("Test address " + mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GPS_REQUEST_CODE) {
            LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (enabled) {
                findLocation();
            } else {
                checkGpsEnable();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == Constants.PERMISSION_REQUEST_LOCATION) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_REQUEST_LOCATION);
                        return;
                    }
                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mCurrentLocation == null) {
                        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (enabled) {
                            findLocation();
                        } else {
                            checkGpsEnable();
                        }
                    } else {
                        findLocation();
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_permission_denied), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_REQUEST_LOCATION);
            } else {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mCurrentLocation == null) {
                    LocationManager mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    boolean enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (enabled) {
                        findLocation();
                    } else {
                        checkGpsEnable();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void checkGpsEnable() {
        try {
            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!enabled) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.gps_alert))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.gps_settings), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, Constants.GPS_REQUEST_CODE);
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            } else {
                findLocation();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void findLocation() {
        try {
            new MyLocation(SignUpActivity.this, new OnLocationFound() {
                @Override
                public void myLocation(Location location) {
                    mCurrentLocation = location;
                    LogM.e("Latitude : " + mCurrentLocation.getLatitude());
                }
            }).createLocationRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
