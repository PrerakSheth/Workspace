package com.konkr.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.konkr.Activities.ProfileActivity;
import com.konkr.Activities.ScanQRCodeActivity;
import com.konkr.Activities.SearchUserListActivity;
import com.konkr.Adapters.AdCardAdapter;
import com.konkr.Models.Advertisement;
import com.konkr.Models.Country;
import com.konkr.Models.QRCode;
import com.konkr.Models.SearchUser;
import com.konkr.Models.SuggestedTrainingGoals;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.FragmentSearchUserBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import mabbas007.tagsedittext.TagsEditText;

import static android.app.Activity.RESULT_OK;

public class SearchUserFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private FragmentSearchUserBinding binding;
    private Activity context;

    private MyTextView tvGender;
  //  private MyEditText etNickname;
    private MyEditText etFirstNameLastName;
    private MyEditText etEmail;
    private MyTextView tvCountry;
    private AppCompatSpinner spCountry;
    private AppCompatSpinner spinGender;

    private ImageView ivEctomorph;
    private ImageView ivMesomorph;
    private ImageView ivEndomorph;

    private String nickName;
    private String gender;
    private String currentTrainingGoal;
    private String bodyType = "";

    private ConstraintLayout clSearch;
    private ConstraintLayout clSearchViaQRCode;
    String type = "";
    private String[] genderType;
    private int strTypeOfGender;
    private ArrayAdapter<String> genderArrayAdapter;

    private ArrayList<Country.CountryListBean> alCountry = new ArrayList<>();
    private ArrayList<Country.CountryListBean> alCountryName = new ArrayList<>();
    private ArrayAdapter<Country.CountryListBean> alCountryArrayAdapter;
    private int countryId;

    private CheckBox cbCelebrity, cbInfluence, cbSponsor;

    private String status = "";
    private TagsEditText etCurrentTrainingGoal;
    private int badge = 0;

    private ArrayList<SearchUser.UserSearchListBean> alSearchUser = new ArrayList<>();
    private boolean permission = false;
    private int SCAN_REQUEST_CODE = 150;
    private static final int MY_PERMISSIONS_REQUEST = 108;
    private MyTextView tvUpdateProfile;
    private View snackBarView;

    private ArrayList<SuggestedTrainingGoals.SuggestedTrainingGoalsBean> alTrainingGoals = new ArrayList<>();
    private String[] trainingGoals;
    private ArrayAdapter<String> exerciseAdapter;

    private boolean flag = true;
    private Timer timer = new Timer();
    private final long DELAY = 100;
    StringBuffer sb;
    private ViewPager vpAdvertisment;
    private ArrayList<Advertisement.AdvertiseListBean> alAdvertisment = new ArrayList<>();
    private ConstraintLayout clAdvertisement;
    AdCardAdapter adapterAdCard;

    int currentPage = 0;
    Timer timerAdd;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    public SearchUserFragment() {
    }

    public static SearchUserFragment newInstance() {
        SearchUserFragment fragment = new SearchUserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_user, container, false);
        View view = binding.getRoot();
        context = getActivity();
        snackBarView = context.findViewById(android.R.id.content);
        bindViews();
        setListner();
        callCountryListAPI();
        getGenderArray();
        setCheckBox();
        callAdvertisment();

        etCurrentTrainingGoal.addTextChangedListener(mTextWatcher);

        etCurrentTrainingGoal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    etCurrentTrainingGoal.dismissDropDown();
                    callGetSuggestedTrainingGoals(etCurrentTrainingGoal.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private void callAdvertisment() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.ADVERTISEMENT.PARAM_SCREENPOSITION, 1);
            LogM.LogE("Request : Advertisment : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADVERTISEMENT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Advertisment : " + jsonObject.toString());
                    Advertisement advertisment = new Gson().fromJson(String.valueOf(jsonObject), Advertisement.class);
                    if (isSuccess) {
                        if (advertisment.getAdvertiseList().size() > 0) {
                            clAdvertisement.setVisibility(View.VISIBLE);

                            alAdvertisment.addAll(advertisment.getAdvertiseList());
                            adapterAdCard = new AdCardAdapter(context, alAdvertisment, 1);
                            vpAdvertisment.setAdapter(adapterAdCard);

                            /*After setting the adapter use the timer */
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == alAdvertisment.size()) {
                                        currentPage = 0;
                                    }
                                    vpAdvertisment.setCurrentItem(currentPage++, true);
                                }
                            };

                            timerAdd = new Timer(); // This will create a new Thread
                            timerAdd.schedule(new TimerTask() { // task to be scheduled

                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, DELAY_MS, PERIOD_MS);
                        } else {
                            clAdvertisement.setVisibility(View.GONE);
                        }
                        adapterAdCard.notifyDataSetChanged();
                    } else {
                        AlertDialogUtility.showSnakeBar(advertisment.getMessage(), snackBarView, getActivity());
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListner() {
        tvGender.setOnClickListener(this);
        tvCountry.setOnClickListener(this);
        ivEndomorph.setOnClickListener(this);
        ivMesomorph.setOnClickListener(this);
        ivEctomorph.setOnClickListener(this);
        clSearch.setOnClickListener(this);
        clSearchViaQRCode.setOnClickListener(this);
        etCurrentTrainingGoal.setOnItemClickListener(this);
    }

    private void bindViews() {
        etFirstNameLastName = binding.etFirstNameLastName;
        etCurrentTrainingGoal = binding.etTrainingGoals;
        etCurrentTrainingGoal.setThreshold(1);
        etEmail = binding.etEmail;
      //  etNickname = binding.etNickname;
        tvCountry = binding.tvCountry;
        spCountry = binding.spCountry;
        clSearch = binding.clSearch;
        tvGender = binding.tvGender;
        spinGender = binding.spinGender;
        ivEctomorph = binding.ivEctomorph;
        ivMesomorph = binding.ivMesomorph;
        ivEndomorph = binding.ivEndomorph;

        cbCelebrity = binding.cbCelebrity;
        cbInfluence = binding.cbInfluence;
        cbSponsor = binding.cbSponsor;
        clSearchViaQRCode = binding.clSearchViaQRCode;
        tvUpdateProfile = binding.tvUpdateProfile;
        vpAdvertisment = binding.vpAdvertisment;
        clAdvertisement = binding.clAdvertisement;
    }

    private void setCheckBox() {
        try {
            cbCelebrity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        badge = 2;
                        cbInfluence.setChecked(false);
                        cbSponsor.setChecked(false);
                    } else {
                        if (badge == 2) {
                            badge = 0;
                        }
                    }
                }
            });

            cbInfluence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        badge = 3;
                        cbCelebrity.setChecked(false);
                        cbSponsor.setChecked(false);
                    } else {
                        if (badge == 3) {
                            badge = 0;
                        }
                    }
                }
            });

            cbSponsor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        badge = 4;
                        cbCelebrity.setChecked(false);
                        cbInfluence.setChecked(false);
                    } else {
                        if (badge == 4) {
                            badge = 0;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    permission = false;
                    super.requestPermissions(new String[]{Manifest.permission.CAMERA}, 108);
                } else {
                    permission = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String item = parent.getItemAtPosition(position).toString();
        flag = false;
//        etCurrentTrainingGoal.setText(item);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag = true;
            }
        }, DELAY * 2);
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.clSearchViaQRCode:
//                    if (permission) {
//                    Intent iScan = new Intent(getActivity(), ScanActivity.class);
////                    startActivity(iScan);
//                    startActivityForResult(iScan, SCAN_REQUEST_CODE);
                    if (permission) {
                        Intent iScan = new Intent(getActivity(), ScanQRCodeActivity.class);
                        startActivityForResult(iScan, SCAN_REQUEST_CODE);
                    } else {
                        checkPermission();
                    }
                    break;

                case R.id.clSearch:
                    if (isValid()) {
//                        callSearchUser();
                        Intent intentSearch = new Intent(context, SearchUserListActivity.class);
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_NAME, etFirstNameLastName.getText().toString());
                       // intentSearch.putExtra(GlobalData.SEARCH_PARAM_NICKNAME, etNickname.getText().toString());
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_GENDER, strTypeOfGender);
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_CURRENTTRAININGGOALS, sb.toString());
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_BODYTYPE, bodyType);
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_EMAIL_ID, etEmail.getText().toString());
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_BADGE, badge);
                        intentSearch.putExtra(GlobalData.SEARCH_PARAM_COUNTRYID, countryId);
//                        intentSearch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentSearch);
                    }
                    break;

                case R.id.ivEctomorph:
                    if (type.equalsIgnoreCase(genderType[1])) {
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph_selected));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
                        bodyType = getResources().getString(R.string.Ectomorph);
                    } else if (type.equalsIgnoreCase(genderType[2])) {
                        strTypeOfGender = 1;
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph_selected));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
                        bodyType = getResources().getString(R.string.Female_Ectomorph);
                    }
                    break;

                case R.id.ivMesomorph:
                    if (type.equalsIgnoreCase(genderType[1])) {
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph_selected));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
                        bodyType = getResources().getString(R.string.Mesomorph);
                    } else {
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph_selected));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
                        bodyType = getResources().getString(R.string.Female_Mesomorph);
                    }
                    break;

                case R.id.ivEndomorph:
                    if (type.equalsIgnoreCase(genderType[1])) {
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph_selected));
                        bodyType = getResources().getString(R.string.Endomorph);
                    } else {
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph_selected));
                        bodyType = getResources().getString(R.string.Female_Endomorph);
                    }
                    break;

                case R.id.tvGender:
                    KeyboardUtility.HideKeyboard(getActivity(), tvGender);
                    tvGender.requestFocus();
                    if (genderType != null) {
                        spinGender.performClick();
                    }
                    break;

                case R.id.tvCountry:
                    KeyboardUtility.HideKeyboard(getActivity(), tvCountry);
                    spCountry.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid() {
        currentTrainingGoal = etCurrentTrainingGoal.getText().toString();
        if (currentTrainingGoal != null) {
            String[] alStrGoal = currentTrainingGoal.split(" ");
            sb = new StringBuffer();
            for (int i = 0; i < alStrGoal.length; i++) {
                if (i == alStrGoal.length - 1) {
                    sb.append(alStrGoal[i]);
                } else {
                    sb.append(alStrGoal[i] + ", ");
                }
            }
        }
        if (sb.toString().isEmpty() && etFirstNameLastName.getText().toString().isEmpty()
                 && etEmail.getText().toString().isEmpty() && tvGender.getText().toString().isEmpty()
                && bodyType.isEmpty() && countryId == 0) {
            Toast.makeText(context, "Please select at least one", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etEmail.getText().toString().length() > 0 && !validateEmail(etEmail.getText().toString())) {
            etEmail.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.valid_email), etEmail, context);
            return false;
        }
        return true;
//        else {
//            clSearch.setClickable(true);
//            return true;
//        }
    }

    public final static boolean validateEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

//    private void callSearchUser() {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(WebField.SEARCH_USER.PARAM_USER_ID, SessionManager.getUserId(context));
//            jsonObject.put(WebField.SEARCH_USER.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
//            jsonObject.put(WebField.SEARCH_USER.PARAM_NAME, etFirstNameLastName.getText().toString());
//            jsonObject.put(WebField.SEARCH_USER.PARAM_NICKNAME, etNickname.getText().toString());
//            jsonObject.put(WebField.SEARCH_USER.PARAM_GENDER, strTypeOfGender);
//            jsonObject.put(WebField.SEARCH_USER.PARAM_CURRENTTRAININGGOALS, etTrainingGoals.getText().toString());
//            jsonObject.put(WebField.SEARCH_USER.PARAM_BODYTYPE, bodyType);
//            jsonObject.put(WebField.SEARCH_USER.PARAM_EMAIL_ID, etEmail.getText().toString());
//            jsonObject.put(WebField.SEARCH_USER.PARAM_BADGE, badge);
//            jsonObject.put(WebField.SEARCH_USER.PARAM_COUNTRYID, countryId);
//
//
//
//            LogM.LogE("Request : Search User : " + jsonObject.toString());
//
//            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SEARCH_USER.MODE, 1, new OnUpdateListener() {
//                @Override
//                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
//                    if (isSuccess) {
//                        LogM.LogE("Response : Search User : " + jsonObject.toString());
//
//                        SearchUser user = new Gson().fromJson(String.valueOf(jsonObject), SearchUser.class);
//                        LogM.LogE("user" + user.getUserSearchList().size());
//                        alSearchUser.addAll(user.getUserSearchList());
//
//                        Intent intent = new Intent(context, SearchUserListActivity.class);
//                        intent.putParcelableArrayListExtra(GlobalData.SEARCH_USER_ARRAY, alSearchUser);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                }
//            }).execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void getGenderArray() {
        genderType = getResources().getStringArray(R.array.typeOfGender);
        type = genderType[1];
        genderArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, genderType) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(genderArrayAdapter);
        spinGender.setOnItemSelectedListener(genderListner);
    }

    private AdapterView.OnItemSelectedListener genderListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    type = spinGender.getItemAtPosition(position).toString();
                    tvGender.setText(type);
                    tvGender.setTextColor(getResources().getColor(R.color.text_color));
                    LogM.LogE(" onItemSelected: genderType: " + type);
                    if (type.equalsIgnoreCase(genderType[1])) {
                        strTypeOfGender = 0;
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
                    } else if (type.equalsIgnoreCase(genderType[2])) {
                        strTypeOfGender = 1;
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

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
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view.findViewById(R.id.txtVwSpinner);
                                if (position == 0) {
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        alCountryArrayAdapter.setDropDownViewResource(R.layout.row_spineer);
                        spCountry.setAdapter(alCountryArrayAdapter);
                        spCountry.setOnItemSelectedListener(countrySelectedListener);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdapterView.OnItemSelectedListener countrySelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
//                spinnerQue1.setVisibility(View.INVISIBLE);
                if (position > 0) {
                    final Country.CountryListBean countryListBean = (Country.CountryListBean) spCountry.getItemAtPosition(position);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        boolean isTrue = true;
//        for (int i = 0; i < grantResults.length; i++) {
//            if (grantResults[i] == -1) {
//                checkPermission();
//                isTrue = false;
//                break;
//            }
//        }
//        if (isTrue) {
//            permission = true;
//
//        }

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent iScan = new Intent(getActivity(), ScanQRCodeActivity.class);
//                    startActivity(iScan);
                    startActivityForResult(iScan, SCAN_REQUEST_CODE);
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            final String qrCode = uri.toString();
            if (qrCode != null) {
                callCheckQRCode(qrCode);
//                AlertDialogUtility.showConfirmAlert(getActivity(),
//                        "Are you sure, you want to send friend request?",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                callAddFriend(barcode, "0");
//                                callCheckQRCode(qrCode);
//                                dialog.dismiss();
//                            }
//                        });
            } else {
                Toast.makeText(getActivity(), "QR code is invalid", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callCheckQRCode(String qrCode) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.QR_CODE.PARAM_OTHERUSERID, qrCode);

            LogM.LogE("Request : CheckQRCode : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.QR_CODE.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : CheckQRCode : " + jsonObject.toString());

                        QRCode qrcode = new Gson().fromJson(String.valueOf(jsonObject), QRCode.class);
                        LogM.LogE("getOtherUserId" + qrcode.getOtherUserId());
                        int otherUserId = qrcode.getOtherUserId();

                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                        startActivity(intent);
                    } else {
                        try {
                            AlertDialogUtility.showSnakeBar(jsonObject.getString(WebField.MESSAGE), snackBarView, getActivity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (timer != null)
                timer.cancel();
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                if (s.toString().trim().length() > 0) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (flag) {
                                Looper.prepare();
                                String[] alStr = s.toString().trim().split(" ");
                                callGetSuggestedTrainingGoals(alStr[alStr.length - 1]);
                                Looper.loop();
                            }
                        }
                    }, DELAY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void callGetSuggestedTrainingGoals(String searchBy) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_SUGGESTED_TRAINING_GOALS.USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.GET_SUGGESTED_TRAINING_GOALS.ACCESS_TOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.GET_SUGGESTED_TRAINING_GOALS.SEARCHED_TRAINING_GOALS, searchBy);
            LogM.LogE("Request : GetSuggestedTrainingGoals : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_SUGGESTED_TRAINING_GOALS.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetSuggestedTrainingGoals : " + jsonObject.toString());
                    SuggestedTrainingGoals mSuggestedTrainingGoals = new Gson().fromJson(String.valueOf(jsonObject), SuggestedTrainingGoals.class);
                    if (isSuccess) {
                        alTrainingGoals.clear();
                        alTrainingGoals.addAll(mSuggestedTrainingGoals.getSuggestedTrainingGoals());
                        trainingGoals = new String[alTrainingGoals.size()];
                        int index = 0;
                        for (SuggestedTrainingGoals.SuggestedTrainingGoalsBean value : alTrainingGoals) {
                            trainingGoals[index] = value.getGoalName();
                            index++;
                        }

                        exerciseAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, trainingGoals);
                        exerciseAdapter.setNotifyOnChange(true);
                        etCurrentTrainingGoal.setAdapter(exerciseAdapter);
                        etCurrentTrainingGoal.showDropDown();
                    } else {
                        AlertDialogUtility.showSnakeBar(mSuggestedTrainingGoals.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
