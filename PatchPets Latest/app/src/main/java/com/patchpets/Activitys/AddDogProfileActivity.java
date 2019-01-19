package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.Adapters.PickerAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityAddDogProfileBinding;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.BreedListRequestModel;
import com.patchpets.model.requestModel.GetDogDetailsRequest;
import com.patchpets.model.responseModel.BreedListResponseModel;
import com.patchpets.model.responseModel.GetDogDetailsResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.Helper;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.utils.NonSwipeableViewPager;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

public class AddDogProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddDogProfileBinding binding;
    private ImageView ivClose;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;
    private TextView tvDogName;
    private TextView tvBreedName;
    private TextView tvSizeHeader;
    private TextView tvSize;
    private AppCompatSpinner spinSize;
    private TextView tvDogAge;
    private TextView tvDogGender;
    private TextView tvDogDesexed;
    private TextView tvVaccinations;
    private TextView tvVaccinationsHeader;
    private TextView tvTellUs;
    private EditText etTellUs;
    private AppCompatSpinner spinVaccinations;
    private EditText etDogName;
    //    private EditText etBreedName;
    private ConstraintLayout clNext;

    private TextView tvInstagram;
    private EditText etInstagram;
    private TextView tvInstagramLink;

    private TextView tvBreedNameValue;
    private AppCompatSpinner spinBreedName;

    private View viewMale;
    private View viewFemale;

    private ImageView ivNextArrow;
    private TextView tvMale;
    private TextView tvFemale;

    private String dogName;
    private String breedName;
    private int breedId;
    private String[] vaccinations;
    private String[] arraySize;
    private ArrayAdapter<String> hoursAdapter;
    private ArrayAdapter<String> breedAdapter;

    private NonSwipeableViewPager viewPager;
    private TextView tvNext;

    RecyclerView rv;
    PickerAdapter adapterPicker;

    private ImageView ivMale;
    private ImageView ivFemale;

    boolean maleSelected;
    boolean yesNoSelected;

//    private TextView tvYesBig;
//    private TextView tvNoBig;
//
//    private TextView tvYesSmall;
//    private TextView tvNoSmall;
//
//    private View viewYesNo;

    private View viewMovable;
    private TextView tvYes;
    private TextView tvNo;
    private boolean check = true;
    private View snackBar;
    int dogAge;
    int dogGender = 1;
    int dogIsDesexed = 1;
    String dogVaccinations;
    String dogDescription;
    String dogInstaLink;
    String dogSize;

    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private MultipartBody.Builder request = new MultipartBody.Builder().setType(MultipartBody.FORM);

    private ArrayList<BreedListResponseModel.BreedListBean> alBreed = new ArrayList<>();
    private User user;
    private DogDetails dog;
    PickerLayoutManager pickerLayoutManager;
    private int dogId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_dog_profile);
        user = SessionManager.getInstance().getUser(AddDogProfileActivity.this);
        snackBar = findViewById(android.R.id.content);
        breedName = getResources().getString(R.string.select_breed);
        dogSize = getResources().getString(R.string.size);
        dog = getIntent().getParcelableExtra(Constants.DOG_DETAILS);
        if (dog != null && dog.getDogId() > 0) {
            dogId = dog.getDogId();
        }

        bindViews();

        WizardPagerAdapter adapter = new WizardPagerAdapter();
        viewPager.setAdapter(adapter);
        setListener();

        callBreedListAPI();

        maleSelected = true;
        yesNoSelected = true;

        pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);

        adapterPicker = new PickerAdapter(this, getData(Constants.MAX_DOG_AGE), rv);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(pickerLayoutManager);
        rv.setAdapter(adapterPicker);

        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
//                Toast.makeText(AddDogProfileActivity.this, ("Selected value : " + ((TextView) view).getText().toString()), Toast.LENGTH_SHORT).show();

                if (((TextView) view).getText().toString().equalsIgnoreCase("<1")) {
                    dogAge = 0;
                } else {
                    dogAge = Integer.parseInt(((TextView) view).getText().toString());
                }

            }
        });

    }

    private void setData() {
        try {
            if (dog != null && dog.getDogName() != null) {
                etDogName.setText(dog.getDogName());
            }
//            pickerLayoutManager.onScrollStateChanged(dog.getDogAge());
            if (dog != null && dog.getDogAge() >= 0) {
                pickerLayoutManager.scrollToPosition(dog.getDogAge());
            }
//            rv.setVerticalScrollbarPosition(dog.getDogAge());
//            dogGender = dog.getGender();
//            if (dogGender == 2) {
//                tvFemale.performLongClick();
//            } else {
//                tvMale.performLongClick();
//            }
//            dogIsDesexed = dog.getIsDesexed();
//            if (dogIsDesexed == 2) {
//                tvNo.performLongClick();
//            } else {
//                tvYes.performLongClick();
//            }
            etTellUs.setText(dog.getDogDesc());
            etInstagram.setText(dog.getDogInstaLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callBreedListAPI() {
        if (Helper.isCheckInternet(AddDogProfileActivity.this)) {
            pDialog = new ProgressDialog(AddDogProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.breedListAPI(breedListRequest(), responseCallback);
        }
    }

    private BreedListRequestModel breedListRequest() {
        BreedListRequestModel breedListRequest = new BreedListRequestModel();
        breedListRequest.setUserId(user.getUserId());
        breedListRequest.setAccessToken(user.getAccessToken());
        return breedListRequest;
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                if (object != null) {
                    BreedListResponseModel response = (BreedListResponseModel) object;
                    if (response.getStatus() == 1) {
                        alBreed.clear();
                        alBreed.addAll(response.getBreedList());
                        setData();
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, AddDogProfileActivity.this);
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

    public List<String> getData(int count) {
        List<String> data = new ArrayList<>();
        data.add("<1");
        for (int i = 1; i <= count; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    private void bindViews() {
        rv = binding.rv;
        ivClose = binding.ivClose;
        tvOne = binding.tvOne;
        tvTwo = binding.tvTwo;
        tvThree = binding.tvThree;
        tvDogName = binding.tvDogName;
        tvDogAge = binding.tvDogAge;
        tvBreedName = binding.tvBreedName;
        etDogName = binding.etDogName;
//        etBreedName = binding.etBreedName;
        tvDogGender = binding.tvDogGender;
        clNext = binding.clNext;
        tvDogDesexed = binding.tvDogDesexed;
        tvVaccinationsHeader = binding.tvVaccinationsHeader;
        spinVaccinations = binding.spinVaccinations;
        tvVaccinations = binding.tvVaccinations;
        tvTellUs = binding.tvTellUs;
        etTellUs = binding.etTellUs;
        tvNext = binding.tvNext;
        viewMale = binding.viewMale;
        viewFemale = binding.viewFemale;
        tvMale = binding.tvMale;
        tvFemale = binding.tvFemale;
        ivMale = binding.ivMale;
        ivFemale = binding.ivFemale;
//        tvYesBig = binding.tvYesBig;
//        tvNoBig = binding.tvNoBig;
//        tvYesSmall = binding.tvYesSmall;
//        tvNoSmall = binding.tvNoSmall;
//        viewYesNo = binding.viewYesNo;
        tvYes = binding.tvYes;
        tvNo = binding.tvNo;
        viewMovable = binding.viewMovable;
        viewPager = binding.viewpager;
        viewPager.setOffscreenPageLimit(9);

        tvInstagram = binding.tvInstagram;
        etInstagram = binding.etInstagram;
        tvInstagramLink = binding.tvInstagramLink;
        ivNextArrow = binding.ivNextArrow;

        tvSizeHeader = binding.tvSizeHeader;
        tvSize = binding.tvSize;
        spinSize = binding.spinSize;
        tvBreedNameValue = binding.tvBreedNameValue;
        spinBreedName = binding.spinBreedName;
    }

    private void setListener() {
        tvBreedNameValue.setOnClickListener(this);
        clNext.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvVaccinations.setOnClickListener(this);
        tvSize.setOnClickListener(this);
        tvMale.setOnClickListener(this);
        tvFemale.setOnClickListener(this);
        ivMale.setOnClickListener(this);
        ivFemale.setOnClickListener(this);
//        tvYesBig.setOnClickListener(this);
//        tvNoSmall.setOnClickListener(this);
//        tvYesSmall.setOnClickListener(this);
        tvOne.setOnClickListener(this);
        tvTwo.setOnClickListener(this);
        tvThree.setOnClickListener(this);

        tvYes.setOnClickListener(this);
        tvNo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        try {
            InputMethodManager imm;
            Animation animation;
            switch (view.getId()) {
                case R.id.ivClose:
                    finish();
                    break;
                case R.id.tvVaccinations:
                    KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvVaccinations);
                    spinVaccinations.performClick();
                    break;

                case R.id.tvBreedNameValue:
                    KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvBreedNameValue);
                    spinBreedName.performClick();
                    break;

                case R.id.tvSize:
                    KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvSize);
                    spinSize.performClick();
                    break;
                case R.id.tvMale:
                    if (!maleSelected) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right_anim);
                        viewMale.startAnimation(animation);
                        viewMale.setBackgroundColor(getResources().getColor(R.color.male_color));
                        maleSelected = true;
                        tvFemale.setTextColor(getResources().getColor(R.color.text_hint_color));
                        tvMale.setTextColor(getResources().getColor(R.color.text_color));
                        dogGender = 1;
                    }
                    break;

                case R.id.tvFemale:
                    if (maleSelected) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left_anim);
                        viewMale.startAnimation(animation);
                        viewMale.setBackgroundColor(getResources().getColor(R.color.female_color));
                        maleSelected = false;
                        tvFemale.setTextColor(getResources().getColor(R.color.text_color));
                        tvMale.setTextColor(getResources().getColor(R.color.text_hint_color));
                        dogGender = 2;
                    }
                    break;

                case R.id.ivMale:
                    tvMale.performClick();
                    break;

                case R.id.ivFemale:
                    tvFemale.performClick();
                    break;

                case R.id.tvYes:
                    tvYes.setTextSize(40);
                    tvNo.setTextSize(16);
                    tvYes.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvNo.setTextColor(getResources().getColor(R.color.email_text));
                    if (!check) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
                        viewMovable.startAnimation(animation);
                    }
                    check = true;
                    dogIsDesexed = 1;
                    break;

                case R.id.tvNo:
                    tvNo.setTextSize(40);
                    tvYes.setTextSize(16);
                    tvNo.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvYes.setTextColor(getResources().getColor(R.color.email_text));
                    if (check) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
                        viewMovable.startAnimation(animation);
                    }
                    check = false;
                    dogIsDesexed = 2;
                    break;

                case R.id.tvOne:
                    if (tvOne.getText().toString().equalsIgnoreCase("1")) {
                        viewPager.setCurrentItem(0);
                        tvOne.setText("1");
                        tvOne.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvTwo.setText("2");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvThree.setText("3");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("2")) {
                        if (etDogName.getText().toString().trim().length() == 0) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_dog_name), snackBar, AddDogProfileActivity.this);
                            return;
                        }
                        viewPager.setCurrentItem(1);
                        spinBreedName.performClick();
//                        setBreedName();
                        tvBreedName.setText(dogName + "'s" + " " + getResources().getString(R.string.breed));
                        tvOne.setText("1");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("2");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("3");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("3")) {
                        if (etDogName.getText().toString().trim().length() == 0) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_dog_name), snackBar, AddDogProfileActivity.this);
                            return;
                        }
                        viewPager.setCurrentItem(2);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvOne);
                        tvSizeHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.size_qm));
//                        setDogSize();
                        tvOne.setText("2");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("3");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("4");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("4")) {
                        viewPager.setCurrentItem(3);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvOne);
                        tvDogAge.setText(dogName + "'s" + " " + getResources().getString(R.string.age));
                        tvOne.setText("3");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("4");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("5");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("5")) {
                        viewPager.setCurrentItem(4);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvOne);
//                        tvDogGender.setText(dogName + "'s" + " " + getResources().getString(R.string.gender));
                        tvDogGender.setText(getResources().getString(R.string.dexter) + "'s" + " " + getResources().getString(R.string.gender));
                        tvOne.setText("4");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("5");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("6");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("6")) {
                        viewPager.setCurrentItem(5);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvOne);
//                        tvDogDesexed.setText(getResources().getString(R.string.does) + " " + dogName + " " + getResources().getString(R.string.desexed));
                        tvDogDesexed.setText(getResources().getString(R.string.does) + " " + getResources().getString(R.string.dexter) + " " + getResources().getString(R.string.desexed));
                        tvOne.setText("5");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("6");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("7");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("7")) {
                        viewPager.setCurrentItem(6);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvOne);
//                        tvVaccinationsHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.vaccinations_qm));
                        tvVaccinationsHeader.setText(getResources().getString(R.string.dexter) + "'s" + " " + getResources().getString(R.string.vaccinations_qm));
                        tvOne.setText("6");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("7");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("8");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("8")) {
                        viewPager.setCurrentItem(7);
                        tvTellUs.setText(getResources().getString(R.string.tell_us_something) + "" + getResources().getString(R.string.dexter) + "..");
                        tvOne.setText("7");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("8");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("9");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvOne.getText().toString().equalsIgnoreCase("9")) {
                        viewPager.setCurrentItem(8);
                        tvInstagram.setText(getResources().getString(R.string.link) + " " + getResources().getString(R.string.dexter) + "'s " + getResources().getString(R.string.instagram_account));
                        tvOne.setText("7");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("8");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvThree.setText("9");
                        tvThree.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                    }
                    break;

                case R.id.tvTwo:
                    if (tvTwo.getText().toString().equalsIgnoreCase("2")) {
                        if (etDogName.getText().toString().trim().length() == 0) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_dog_name), snackBar, AddDogProfileActivity.this);
                            return;
                        }
                        viewPager.setCurrentItem(1);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvTwo);
                        spinBreedName.performClick();
//                        setBreedName();
                        tvBreedName.setText(etDogName.getText().toString().trim() + "'s" + " " + getResources().getString(R.string.breed));
                        tvOne.setText("1");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("2");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("3");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("3")) {
                        viewPager.setCurrentItem(2);
                        spinSize.performClick();
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvTwo);
                        tvSizeHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.size_qm));
//                        setDogSize();
                        tvOne.setText("2");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("3");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("4");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("4")) {
                        viewPager.setCurrentItem(3);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvTwo);
                        tvDogAge.setText(getResources().getString(R.string.dexter) + "'s" + " " + getResources().getString(R.string.age));
                        tvOne.setText("3");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("4");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("5");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("5")) {
                        viewPager.setCurrentItem(4);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvTwo);
//                        tvDogGender.setText(dogName + "'s" + " " + getResources().getString(R.string.gender));
                        tvDogGender.setText(getResources().getString(R.string.dexter) + "'s" + " " + getResources().getString(R.string.gender));
                        tvOne.setText("4");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("5");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("6");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("6")) {
                        viewPager.setCurrentItem(5);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvTwo);
//                        tvDogDesexed.setText(getResources().getString(R.string.does) + " " + dogName + " " + getResources().getString(R.string.desexed));
                        tvDogDesexed.setText(getResources().getString(R.string.does) + " " + getResources().getString(R.string.dexter) + " " + getResources().getString(R.string.desexed));
                        tvOne.setText("5");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("6");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("7");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("7")) {
                        viewPager.setCurrentItem(6);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvTwo);
//                        tvVaccinationsHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.vaccinations_qm));
                        tvVaccinationsHeader.setText(getResources().getString(R.string.dexter) + "'s" + " " + getResources().getString(R.string.vaccinations_qm));
                        tvOne.setText("5");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("7");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("7");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("8")) {
                        viewPager.setCurrentItem(7);
                        tvTellUs.setText(getResources().getString(R.string.tell_us_something) + "" + getResources().getString(R.string.dexter) + "..");
                        tvOne.setText("7");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("8");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("9");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvTwo.getText().toString().equalsIgnoreCase("9")) {
                        viewPager.setCurrentItem(8);
                        tvInstagram.setText(getResources().getString(R.string.link) + " " + getResources().getString(R.string.dexter) + "'s " + getResources().getString(R.string.instagram_account));
                        tvOne.setText("7");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("8");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvThree.setText("9");
                        tvThree.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                    }
                    break;

                case R.id.tvThree:
                    if (tvThree.getText().toString().equalsIgnoreCase("3")) {
                        if (etDogName.getText().toString().trim().length() == 0) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_dog_name), snackBar, AddDogProfileActivity.this);
                            return;
                        }
//                        setBreedName();
                        if (breedName.equalsIgnoreCase(getResources().getString(R.string.select_breed))) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_breedname), snackBar, AddDogProfileActivity.this);
                            spinSize.requestFocus();
                            return;
                        }
                        viewPager.setCurrentItem(2);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvThree);
                        tvSizeHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.size_qm));
//                        setDogSize();
                        tvOne.setText("2");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("3");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("4");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvThree.getText().toString().equalsIgnoreCase("4")) {
                        if (dogSize.equalsIgnoreCase(getResources().getString(R.string.size))) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_size), snackBar, AddDogProfileActivity.this);
                            spinSize.requestFocus();
                            return;
                        }
                        viewPager.setCurrentItem(3);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvThree);
                        tvDogAge.setText(dogName + "'s" + " " + getResources().getString(R.string.age));
                        tvOne.setText("3");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("4");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("5");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvThree.getText().toString().equalsIgnoreCase("5")) {
                        viewPager.setCurrentItem(4);
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvThree);
                        tvDogGender.setText(dogName + "'s" + " " + getResources().getString(R.string.gender));
                        tvOne.setText("4");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("5");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("6");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvThree.getText().toString().equalsIgnoreCase("6")) {
                        viewPager.setCurrentItem(5);
                        tvDogDesexed.setText(getResources().getString(R.string.does) + " " + dogName + " " + getResources().getString(R.string.desexed));
//                        tvDogDesexed.setText(getResources().getString(R.string.does) + " " + getResources().getString(R.string.dexter) + " " + getResources().getString(R.string.desexed));
                        tvOne.setText("5");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("6");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("7");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvThree.getText().toString().equalsIgnoreCase("7")) {
                        viewPager.setCurrentItem(6);
                        setSpinnerData();
                        spinVaccinations.performClick();
                        KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvThree);
                        tvVaccinationsHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.vaccinations_qm));
                        tvOne.setText("6");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("7");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("8");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvThree.getText().toString().equalsIgnoreCase("8")) {
                        if (dogVaccinations.equalsIgnoreCase(getResources().getString(R.string.vaccinations))) {
                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_vaccination), snackBar, AddDogProfileActivity.this);
                            spinVaccinations.requestFocus();
                            return;
                        }
                        viewPager.setCurrentItem(7);
                        tvTellUs.setText(getResources().getString(R.string.tell_us_something) + "" + dogName);
                        tvOne.setText("7");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("8");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                        tvThree.setText("9");
                        tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                    } else if (tvThree.getText().toString().equalsIgnoreCase("9")) {
                        viewPager.setCurrentItem(8);
                        tvInstagram.setText(getResources().getString(R.string.link) + " " + getResources().getString(R.string.dexter) + "'s " + getResources().getString(R.string.instagram_account));
                        tvOne.setText("7");
                        tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvTwo.setText("8");
                        tvTwo.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                        tvThree.setText("9");
                        tvThree.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                    }
                    break;


                case R.id.clNext:
                    int position = viewPager.getCurrentItem();
                    switch (position) {
                        case 0:
                            if (etDogName.getText().toString().trim().length() == 0) {
                                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_dog_name), snackBar, AddDogProfileActivity.this);
                                return;
                            }
                            dogName = etDogName.getText().toString().trim();
                            viewPager.setCurrentItem(position + 1);
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            setBreedName();
                            tvBreedName.setText(dogName + "'s" + " " + getResources().getString(R.string.breed));
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;

                        case 1:
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            if (breedName.equalsIgnoreCase(getResources().getString(R.string.select_breed))) {
                                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_breedname), snackBar, AddDogProfileActivity.this);
                                spinSize.requestFocus();
                                return;
                            }
                            breedName = tvBreedNameValue.getText().toString();
                            viewPager.setCurrentItem(position + 1);
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            tvSizeHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.size_qm));
                            setDogSize();
                            tvOne.setText("2");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("3");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setText("4");
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;

                        case 2:
                            if (dogSize.equalsIgnoreCase(getResources().getString(R.string.size))) {
                                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_size), snackBar, AddDogProfileActivity.this);
                                spinSize.requestFocus();
                                return;
                            }
                            viewPager.setCurrentItem(position + 1);
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            tvDogAge.setText(dogName + "'s" + " " + getResources().getString(R.string.age));
                            tvOne.setText("3");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("4");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setText("5");
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;

                        case 3:
                            viewPager.setCurrentItem(position + 1);
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            if (dog != null) {
                                dogGender = dog.getGender();
                                if (dogGender == 2) {
                                    if (maleSelected) {
                                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left_anim);
                                        viewMale.startAnimation(animation);
                                        viewMale.setBackgroundColor(getResources().getColor(R.color.female_color));
                                        maleSelected = false;
                                        tvFemale.setTextColor(getResources().getColor(R.color.text_color));
                                        tvMale.setTextColor(getResources().getColor(R.color.text_hint_color));
                                        dogGender = 2;
                                    }
                                } else {
//                                    tvMale.performLongClick();
                                    if (!maleSelected) {
                                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right_anim);
                                        viewMale.startAnimation(animation);
                                        viewMale.setBackgroundColor(getResources().getColor(R.color.male_color));
                                        maleSelected = true;
                                        tvFemale.setTextColor(getResources().getColor(R.color.text_hint_color));
                                        tvMale.setTextColor(getResources().getColor(R.color.text_color));
                                        dogGender = 1;
                                    }
                                }
                            }
                            tvDogGender.setText(dogName + "'s" + " " + getResources().getString(R.string.gender));
                            tvOne.setText("4");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("5");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setText("6");
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;
                        case 4:
                            viewPager.setCurrentItem(position + 1);
                            if (dog != null) {
                                dogIsDesexed = dog.getIsDesexed();
                                if (dogIsDesexed == 2) {
//                                tvNo.performLongClick();
                                    tvNo.setTextSize(40);
                                    tvYes.setTextSize(16);
                                    tvNo.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    tvYes.setTextColor(getResources().getColor(R.color.email_text));
                                    if (check) {
                                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
                                        viewMovable.startAnimation(animation);
                                    }
                                    check = false;
                                    dogIsDesexed = 2;
                                } else {
//                                tvYes.performLongClick();
                                    tvYes.setTextSize(40);
                                    tvNo.setTextSize(16);
                                    tvYes.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    tvNo.setTextColor(getResources().getColor(R.color.email_text));
                                    if (!check) {
                                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
                                        viewMovable.startAnimation(animation);
                                    }
                                    check = true;
                                    dogIsDesexed = 1;
                                }
                            }
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            tvDogDesexed.setText(getResources().getString(R.string.does) + " " + dogName + " " + getResources().getString(R.string.desexed));
                            tvOne.setText("5");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("6");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setText("7");
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;

                        case 5:
                            viewPager.setCurrentItem(position + 1);
                            KeyboardUtility.hideKeyboard(AddDogProfileActivity.this, tvNext);
                            tvVaccinationsHeader.setText(dogName + "'s" + " " + getResources().getString(R.string.vaccinations_qm));
                            setSpinnerData();
                            tvOne.setText("6");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("7");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setText("8");
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;
                        case 6:
                            if (tvVaccinations.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.vaccinations))) {
                                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_enter_vaccination), snackBar, AddDogProfileActivity.this);
                                spinVaccinations.requestFocus();
                                return;
                            }
                            viewPager.setCurrentItem(position + 1);
                            tvTellUs.setText(getResources().getString(R.string.tell_us_something) + "" + dogName);
                            tvOne.setText("7");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("8");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            tvThree.setText("9");
                            tvThree.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            break;
                        case 7:
                            dogDescription = etTellUs.getText().toString();
                            viewPager.setCurrentItem(position + 1);
                            tvInstagram.setText(getResources().getString(R.string.link) + " " + dogName + "'s " + getResources().getString(R.string.instagram_account));
//                            tvInstagram.setText(getResources().getString(R.string.link) + " " + getResources().getString(R.string.dexter) + "'s " + getResources().getString(R.string.instagram_account));
                            tvOne.setText("7");
                            tvOne.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvTwo.setText("8");
                            tvTwo.setTextColor(getResources().getColor(R.color.number_not_selected_setupprofile));
                            tvThree.setText("9");
                            tvThree.setTextColor(getResources().getColor(R.color.number_selected_setupprofile));
                            break;
                        case 8:
                            dogInstaLink = etInstagram.getText().toString();
                            viewPager.setCurrentItem(position + 1);
                            Intent intent = new Intent(AddDogProfileActivity.this, FullDogProfileActivity.class);
                            intent.putExtra(Constants.FROM, Constants.ADDDOGPROFILE);
                            intent.putExtra(Constants.DOG_DETAILS, dog);
                            intent.putExtra(Constants.DOG_ID, dogId);
                            intent.putExtra(Constants.SP_DOGNAME, dogName);
                            intent.putExtra(Constants.SP_BREED_ID, breedId);
                            intent.putExtra(Constants.SP_BREED_NAME, breedName);
                            intent.putExtra(Constants.SP_SIZE, dogSize);
                            intent.putExtra(Constants.SP_AGE, dogAge);
                            intent.putExtra(Constants.SP_GENDER, dogGender);
                            intent.putExtra(Constants.SP_DESEXED, dogIsDesexed);
                            intent.putExtra(Constants.SP_VACCINATIONS, dogVaccinations);
                            intent.putExtra(Constants.DESCRIPTION, dogDescription);
                            intent.putExtra(Constants.SP_INSTALINK, dogInstaLink);
                            startActivity(intent);
                            break;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class WizardPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.clDogName;
                    break;
                case 1:
                    resId = R.id.clBreedName;
                    break;
                case 2:
                    resId = R.id.clSize;
                    break;
                case 3:
                    resId = R.id.clDogAge;
                    break;
                case 4:
                    resId = R.id.clDogGender;
                    break;
                case 5:
                    resId = R.id.clDesexed;
                    break;
                case 6:
                    resId = R.id.clVaccinations;
                    break;
                case 7:
                    resId = R.id.clTellUs;
                    break;
                case 8:
                    resId = R.id.clInstagram;
                    break;
            }
            return findViewById(resId);
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }
    }

    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            viewMale.clearAnimation();
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(viewMale.getWidth(), viewMale.getHeight());
//            lp.set(50, 100, 0, 0);
//            viewMale.setLayoutParams(lp);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }

    private void setSpinnerData() {
        try {
            vaccinations = getResources().getStringArray(R.array.vaccinations);
            final int alSize = vaccinations.length - 1;

            hoursAdapter = new ArrayAdapter<String>(AddDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, vaccinations) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinVaccinations.setAdapter(hoursAdapter);
            spinVaccinations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dogVaccinations = vaccinations[position];
                    tvVaccinations.setText(vaccinations[position]);
                    if (position != alSize) {
                        tvVaccinations.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvVaccinations.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null && dog.getVaccinations() != null) {
                for (int i = 0; i < vaccinations.length; i++) {
                    if (dog.getVaccinations().equalsIgnoreCase(vaccinations[i])) {
                        spinVaccinations.setSelection(i);
                    }
                }
            } else {
                spinVaccinations.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDogSize() {
        try {
            arraySize = getResources().getStringArray(R.array.size);
            final int alSize = arraySize.length - 1;

            hoursAdapter = new ArrayAdapter<String>(AddDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, arraySize) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinSize.setAdapter(hoursAdapter);
            spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dogSize = arraySize[position];
                    tvSize.setText(arraySize[position]);
                    if (position != alSize) {
                        tvSize.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvSize.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null && dog.getDogSize() != null) {
                for (int i = 0; i < arraySize.length; i++) {
                    if (dog.getDogSize().equalsIgnoreCase(arraySize[i])) {
                        spinSize.setSelection(i);
                    }
                }
            } else {
                spinSize.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBreedName() {
        try {
//            alBreed.clear();
            BreedListResponseModel.BreedListBean breedListBean = new BreedListResponseModel.BreedListBean();
            breedListBean.setBreedName(getResources().getString(R.string.select_breed));
            alBreed.add(breedListBean);
            final int alSize = alBreed.size() - 1;

            ArrayList<String> alBreedName = new ArrayList<>();
            for (int i = 0; i <= alSize; i++) {
                String s = alBreed.get(i).getBreedName();
                alBreedName.add(s);
            }

            breedAdapter = new ArrayAdapter<String>(AddDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, alBreedName) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinBreedName.setAdapter(breedAdapter);
            spinBreedName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    breedName = alBreed.get(position).getBreedName();
                    breedId = alBreed.get(position).getBreedId();
                    tvBreedNameValue.setText(alBreed.get(position).getBreedName());
                    if (position != alSize) {
                        tvBreedNameValue.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvBreedNameValue.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
//
            if (dog != null && dog.getDogBreed() > 0) {
                for (int i = 0; i < alBreed.size(); i++) {
                    if (dog.getDogBreed() == alBreed.get(i).getBreedId()) {
//                    tvBreedNameValue.setText(alBreed.get(i).getBreedName());
                        spinBreedName.setSelection(i);
                    }
                }
            } else {
                spinBreedName.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetDogProfile() {
        if (Helper.isCheckInternet(AddDogProfileActivity.this)) {
            pDialog = new ProgressDialog(AddDogProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.getDogDetailsAPI(getDogDetailsRequest(), responseCallbackGetDogDetails);
        }
    }

    private GetDogDetailsRequest getDogDetailsRequest() {
        GetDogDetailsRequest getDogDetailsRequest = new GetDogDetailsRequest();
        getDogDetailsRequest.setUserId(user.getUserId());
        getDogDetailsRequest.setAccessToken(user.getAccessToken());
        getDogDetailsRequest.setDogId(dog.getDogId());
        return getDogDetailsRequest;
    }

    ResponseCallback responseCallbackGetDogDetails = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                GetDogDetailsResponse response = (GetDogDetailsResponse) object;
                if (response.getStatus() == 1) {

                } else {
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, AddDogProfileActivity.this);
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


