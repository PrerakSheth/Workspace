package com.patchpets.Activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityFullDogProfileBinding;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.BreedListRequestModel;
import com.patchpets.model.responseModel.AddDogToProfileResponse;
import com.patchpets.model.responseModel.BreedListResponseModel;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.Helper;
import com.patchpets.utils.LogM;
import com.patchpets.utils.MyApp;
import com.patchpets.utils.MyEditText;
import com.patchpets.utils.RoundedCornersTransformation;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.Keys;
import com.patchpets.webservices.ResponseCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.patchpets.utils.Constants.PERMISSION_REQUEST_CAMERA;

public class FullDogProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFullDogProfileBinding binding;

    private TextView tvAddOtherProfile;
    private ImageView ivDone, toolbarImage, ivEdit, ivBack;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private TextView tvGenderName, tvStatusValue, tvVaccinationName, tvWillingToBreedValue, tvAgeValue, tvBreedName;
    private EditText etAbout;

    private RelativeLayout rlBreedNameSpinner, rlAgeSpinner, rlGenderSpinner, rlStatusSpinner, rlVaccinationsSpinner, rlWillingToBreedSpinner;
    private Spinner spinnerBreedName, spinnerAge, spinnerGender, spinnerStatus, spinnerVaccinations, spinnerWillingToBreed;

    private ImageView ivPhotoOne, ivPhotoTwo, ivPhotoThree, ivPhotoFour;
    private ImageButton ibPhotoOneDelete, ibPhotoTwoDelete, ibPhotoThreeDelete, ibPhotoFourDelete;

    private String dogName;
    private String breedName;
    private String dogSize;
    private int dogAge, breedId, dogGender = 1, dogIsDesexed = 1, willingToBreed = 1;
    private String dogVaccinations, dogDescription, dogInstaLink;

    private Uri profilePicUri, profilePicUriOne, profilePicUriTwo, profilePicUriThree, profilePicUriFour;
    private File profilePicFile = null, profilePicFileOne = null, profilePicFileTwo = null, profilePicFileThree = null, profilePicFileFour = null;

    private final int IMAGE1 = 111, IMAGE2 = 222, IMAGE3 = 333, IMAGE4 = 444, IMAGE5 = 555;
    private MultipartBody.Builder request = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private View snackBar;
    private User user;
    private DogDetails dog;
    private int dogId;
    private MyEditText etDogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SessionManager.getInstance().getUser(FullDogProfileActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_dog_profile);
        snackBar = findViewById(android.R.id.content);

        bindViews();
        setListener();
        getIntents();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Display dWidth = getWindowManager().getDefaultDisplay();
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = dWidth.getWidth() * 1 / 3;
                setAppBarOffset(heightPx);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(dogName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(dogName);
                    isShow = false;
                }
            }
        });


        callBreedListAPI();
    }

    private void dialogEditDogName() {
        final Dialog dialog = new Dialog(this, R.style.FullScreenDialogStyle);
        dialog.setContentView(R.layout.dialog_edit_dog_name);

        etDogName = dialog.findViewById(R.id.etDogName);
        etDogName.setText(dogName);
        etDogName.setSelection(dogName.length());
        final TextView tvOk = dialog.findViewById(R.id.tvOk);
        final TextView tvCancel = dialog.findViewById(R.id.tvCancel);
//        viewMovable = dialog.findViewById(R.id.viewMovable);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogM.e("EDT" + etDogName.getText().toString().trim().length());
                int count = etDogName.getText().toString().trim().length();
                if (count == 0) {
                    collapsingToolbar.setTitle(dogName);
                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        boolean isShow = true;
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset == 0) {
                                collapsingToolbar.setTitle(dogName);
                                isShow = true;
                            } else if (isShow) {
                                collapsingToolbar.setTitle(dogName);
                                isShow = false;
                            }
                        }
                    });
                } else {
                    dogName = etDogName.getText().toString();
                    collapsingToolbar.setTitle(dogName);
                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        boolean isShow = true;
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset == 0) {
                                dogName = etDogName.getText().toString();
                                collapsingToolbar.setTitle(dogName);
                                isShow = true;
                            } else if (isShow) {
                                dogName = etDogName.getText().toString();
                                collapsingToolbar.setTitle(dogName);
                                isShow = false;
                            }
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void getIntents() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.ADDDOGPROFILE)) {
                ivBack.setVisibility(View.GONE);
                dog = getIntent().getParcelableExtra(Constants.DOG_DETAILS);
                dogId = dog.getDogId();

                if (dog != null) {
                    if (dog.getDogProfilePic() != null) {
                        MyApp.picasso.invalidate(dog.getDogProfilePic());
                        MyApp.picasso
                                .load(dog.getDogProfilePic())
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .fit().centerCrop()
                                .transform(new RoundedCornersTransformation(10, 0))
                                .into(toolbarImage);

                        new ImageDownloaderTask().execute(dog.getDogProfilePic());
                    }
                    if (dog.getDogPics() != null) {
                        for (int i = 0; i < dog.getDogPics().size(); i++) {
                            int dogImageSize = dog.getDogPics().size();

                            switch (dogImageSize) {
                                case 4:
                                    if (dog.getDogPics().get(3) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(3));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(3))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoFour);
                                        new ImageDownloaderTaskFour().execute(dog.getDogPics().get(3));
                                    }
                                case 3:
                                    if (dog.getDogPics().get(2) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(2));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(2))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoThree);
                                        new ImageDownloaderTaskThree().execute(dog.getDogPics().get(2));
                                    }
                                case 2:
                                    if (dog.getDogPics().get(1) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(1));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(1))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoTwo);
                                        new ImageDownloaderTaskTwo().execute(dog.getDogPics().get(1));
                                    }
                                case 1:
                                    if (dog.getDogPics().get(0) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(0));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(0))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoOne);
                                        new ImageDownloaderTaskOne().execute(dog.getDogPics().get(0));
                                    }
                            }
                        }

                    }
                    dogName = dog.getDogName();
//                    breedName = getIntent().getStringExtra(Constants.SP_BREED_NAME);
//                    tvBreedName.setText(breedName);
                    breedId = dog.getDogBreed();
                    dogAge = dog.getDogAge();
                    if (dogAge == 0) {
                        tvAgeValue.setText("<1");
                    } else {
                        tvAgeValue.setText("" + dogAge);
                    }
                    dogSize = dog.getDogSize();
//                tvAgeValue.setText("" + dogAge);
                    dogGender = dog.getGender();
                    if (dogGender == 1) {
                        tvGenderName.setText(getResources().getString(R.string.male));
                    } else if (dogGender == 2) {
                        tvGenderName.setText(getResources().getString(R.string.female));
                    }
                    dogIsDesexed = dog.getIsDesexed();
                    if (dogIsDesexed == 1) {
                        tvStatusValue.setText(getResources().getString(R.string.sp_desexed));
                    } else if (dogIsDesexed == 2) {
                        tvStatusValue.setText(getResources().getString(R.string.sp_sexed));
                    }

                    willingToBreed = dog.getIsWillingToBreed();
                    if (willingToBreed == 1) {
//                    tvStatusValue.setText(getResources().getString(R.string.sp_desexed));
                    } else if (willingToBreed == 0) {
//                    tvStatusValue.setText(getResources().getString(R.string.sp_sexed));
                    }
                    dogVaccinations = dog.getVaccinations();
                    tvVaccinationName.setText(dogVaccinations);
                    dogDescription = dog.getDogDesc();
                    if (dogDescription.equalsIgnoreCase("")) {
                        dogDescription = "";
                        etAbout.setText("-");
                    } else {
                        etAbout.setText(dogDescription);
                    }
                    dogInstaLink = dog.getDogInstaLink();
                }
            } else if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.EDIT_DOG_PROFILE)) {
                ivBack.setVisibility(View.VISIBLE);
                dog = getIntent().getParcelableExtra(Constants.DOG_DETAILS);
                dogId = dog.getDogId();
                if (dog != null) {
                    if (dog.getDogProfilePic() != null) {
                        MyApp.picasso.invalidate(dog.getDogProfilePic());
                        MyApp.picasso
                                .load(dog.getDogProfilePic())
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .fit().centerCrop()
                                .transform(new RoundedCornersTransformation(10, 0))
                                .into(toolbarImage);

                        new ImageDownloaderTask().execute(dog.getDogProfilePic());
                    }
                    if (dog.getDogPics() != null) {
                        for (int i = 0; i < dog.getDogPics().size(); i++) {
                            int dogImageSize = dog.getDogPics().size();

                            switch (dogImageSize) {
                                case 4:
                                    if (dog.getDogPics().get(3) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(3));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(3))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoFour);
                                        new ImageDownloaderTaskFour().execute(dog.getDogPics().get(3));
                                    }
                                case 3:
                                    if (dog.getDogPics().get(2) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(2));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(2))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoThree);
                                        new ImageDownloaderTaskThree().execute(dog.getDogPics().get(2));
                                    }
                                case 2:
                                    if (dog.getDogPics().get(1) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(1));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(1))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoTwo);
                                        new ImageDownloaderTaskTwo().execute(dog.getDogPics().get(1));
                                    }
                                case 1:
                                    if (dog.getDogPics().get(0) != null) {
                                        MyApp.picasso.invalidate(dog.getDogPics().get(0));
                                        MyApp.picasso
                                                .load(dog.getDogPics().get(0))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(10, 0))
                                                .into(ivPhotoOne);
                                        new ImageDownloaderTaskOne().execute(dog.getDogPics().get(0));
                                    }
                            }
                        }
                    }
                }
                dogName = dog.getDogName();
                dogSize = dog.getDogSize();
                dogDescription = dog.getDogDesc();
                if (dogDescription.equalsIgnoreCase("")) {
                    dogDescription = "";
                    etAbout.setText("-");
                } else {
                    etAbout.setText(dogDescription);
                }
                dogInstaLink = dog.getDogInstaLink();
            }
        }
    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0});
    }

    private void bindViews() {
        tvAddOtherProfile = binding.tvAddOtherProfile;
        ivDone = binding.ivDone;
        toolbarImage = binding.toolbarImage;
        tvGenderName = binding.tvGenderName;
        tvStatusValue = binding.tvStatusValue;
        tvVaccinationName = binding.tvVaccinationName;
        tvWillingToBreedValue = binding.tvWillingToBreedValue;
        tvAgeValue = binding.tvAgeValue;
        tvBreedName = binding.tvBreedName;
        etAbout = binding.etAbout;

        ivPhotoOne = binding.ivPhotoOne;
        ivPhotoTwo = binding.ivPhotoTwo;
        ivPhotoThree = binding.ivPhotoThree;
        ivPhotoFour = binding.ivPhotoFour;

        ibPhotoOneDelete = binding.ibPhotoOneDelete;
        ibPhotoTwoDelete = binding.ibPhotoTwoDelete;
        ibPhotoThreeDelete = binding.ibPhotoThreeDelete;
        ibPhotoFourDelete = binding.ibPhotoFourDelete;

        coordinatorLayout = binding.coordinatorLayout;
        appBarLayout = binding.appBarLayout;
        collapsingToolbar = binding.collapsingToolbar;

        rlBreedNameSpinner = binding.rlBreedNameSpinner;
        rlAgeSpinner = binding.rlAgeSpinner;
        rlGenderSpinner = binding.rlGenderSpinner;
        rlStatusSpinner = binding.rlStatusSpinner;
        rlVaccinationsSpinner = binding.rlVaccinationsSpinner;
        rlWillingToBreedSpinner = binding.rlWillingToBreedSpinner;
        spinnerBreedName = binding.spinnerBreedName;
        spinnerAge = binding.spinnerAge;
        spinnerGender = binding.spinnerGender;
        spinnerStatus = binding.spinnerStatus;
        spinnerVaccinations = binding.spinnerVaccinations;
        spinnerWillingToBreed = binding.spinnerWillingToBreed;
        ivEdit = binding.ivEdit;
        ivBack = binding.ivBack;
    }

    private void setListener() {
        tvAddOtherProfile.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        toolbarImage.setOnClickListener(this);
        ivPhotoOne.setOnClickListener(this);
        ivPhotoTwo.setOnClickListener(this);
        ivPhotoThree.setOnClickListener(this);
        ivPhotoFour.setOnClickListener(this);
        ibPhotoOneDelete.setOnClickListener(this);
        ibPhotoTwoDelete.setOnClickListener(this);
        ibPhotoThreeDelete.setOnClickListener(this);
        ibPhotoFourDelete.setOnClickListener(this);

        rlBreedNameSpinner.setOnClickListener(this);
        rlAgeSpinner.setOnClickListener(this);
        rlGenderSpinner.setOnClickListener(this);
        rlStatusSpinner.setOnClickListener(this);
        rlVaccinationsSpinner.setOnClickListener(this);
        rlWillingToBreedSpinner.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent;
            switch (v.getId()) {
                case R.id.ivBack:
                    onBackPressed();
                    break;
                case R.id.ivEdit:
                    dialogEditDogName();
                    break;
                case R.id.tvAddOtherProfile:
                    intent = new Intent(FullDogProfileActivity.this, AddDogProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(FullDogProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
//                    } else {
//                        selectImage();
//                    }
                    break;

                case R.id.toolbarImage:
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FullDogProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage(IMAGE1);
                    }
                    break;
                case R.id.ivPhotoOne:
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FullDogProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage(IMAGE2);
                    }
                    break;
                case R.id.ivPhotoTwo:
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FullDogProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage(IMAGE3);
                    }
                    break;
                case R.id.ivPhotoThree:
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FullDogProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage(IMAGE4);
                    }
                    break;
                case R.id.ivPhotoFour:
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FullDogProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage(IMAGE5);
                    }
                    break;

                case R.id.ibPhotoOneDelete:
                    alertDeleteImage(R.id.ibPhotoOneDelete);
                    break;

                case R.id.ibPhotoTwoDelete:
                    alertDeleteImage(R.id.ibPhotoTwoDelete);
                    break;

                case R.id.ibPhotoThreeDelete:
                    alertDeleteImage(R.id.ibPhotoThreeDelete);
                    break;

                case R.id.ibPhotoFourDelete:
                    alertDeleteImage(R.id.ibPhotoFourDelete);
                    break;

                case R.id.ivDone:
                    if (isValid()) {
                        callAddDoProfile();
                    }
                    break;

                case R.id.rlBreedNameSpinner:
                    spinnerBreedName.performClick();
                    break;

                case R.id.rlAgeSpinner:
                    spinnerAge.performClick();
                    break;

                case R.id.rlGenderSpinner:
                    spinnerGender.performClick();
                    break;

                case R.id.rlStatusSpinner:
                    spinnerStatus.performClick();
                    break;

                case R.id.rlVaccinationsSpinner:
                    spinnerVaccinations.performClick();
                    break;

                case R.id.rlWillingToBreedSpinner:
                    spinnerWillingToBreed.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alertDeleteImage(final int resId) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(FullDogProfileActivity.this);
            builder.setMessage(getResources().getString(R.string.are_you_sure_dlt_photo));
            builder.setCancelable(true);

            builder.setPositiveButton(getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            switch (resId) {
                                case R.id.ibPhotoOneDelete:
                                    if (profilePicFileOne.exists()) {
                                        profilePicFileOne.delete();
                                    }
                                    profilePicFileOne = null;

                                    MyApp.picasso
                                            .load(profilePicFileOne)
                                            .placeholder(R.drawable.camera)
                                            .error(R.drawable.camera)
                                            .fit().centerCrop()
                                            .transform(new RoundedCornersTransformation(10, 0))
                                            .into(ivPhotoOne);

                                    ibPhotoOneDelete.setVisibility(View.GONE);
                                    break;

                                case R.id.ibPhotoTwoDelete:
                                    if (profilePicFileTwo.exists()) {
                                        profilePicFileTwo.delete();
                                    }
                                    profilePicFileTwo = null;

                                    MyApp.picasso
                                            .load(profilePicFileTwo)
                                            .placeholder(R.drawable.camera)
                                            .error(R.drawable.camera)
                                            .fit().centerCrop()
                                            .transform(new RoundedCornersTransformation(10, 0))
                                            .into(ivPhotoTwo);

                                    ibPhotoTwoDelete.setVisibility(View.GONE);
                                    break;

                                case R.id.ibPhotoThreeDelete:
                                    if (profilePicFileThree.exists()) {
                                        profilePicFileThree.delete();
                                    }
                                    profilePicFileThree = null;

                                    MyApp.picasso
                                            .load(profilePicFileThree)
                                            .placeholder(R.drawable.camera)
                                            .error(R.drawable.camera)
                                            .fit().centerCrop()
                                            .transform(new RoundedCornersTransformation(10, 0))
                                            .into(ivPhotoThree);

                                    ibPhotoThreeDelete.setVisibility(View.GONE);
                                    break;

                                case R.id.ibPhotoFourDelete:
                                    if (profilePicFileFour.exists()) {
                                        profilePicFileFour.delete();
                                    }
                                    profilePicFileFour = null;

                                    MyApp.picasso
                                            .load(profilePicFileFour)
                                            .placeholder(R.drawable.camera)
                                            .error(R.drawable.camera)
                                            .fit().centerCrop()
                                            .transform(new RoundedCornersTransformation(10, 0))
                                            .into(ivPhotoFour);

                                    ibPhotoFourDelete.setVisibility(View.GONE);
                                    break;
                            }
                        }
                    });

            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callAddDoProfile() {
        if (Helper.isCheckInternet(FullDogProfileActivity.this)) {
            pDialog = new ProgressDialog(FullDogProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.addDogProfileAPI(request.build(), responseCallback);
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
                    AddDogToProfileResponse response = (AddDogToProfileResponse) object;
                    if (response.getStatus() == 1) {
                        tvAddOtherProfile.setVisibility(View.VISIBLE);
                        dialogAnotherProfile(response.getMessage());
                    } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                        AlertDialogUtility.showAlert(FullDogProfileActivity.this, response.getMessage(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(FullDogProfileActivity.this, SignInActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, FullDogProfileActivity.this);
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

    private void dialogAnotherProfile(String messsage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(FullDogProfileActivity.this);
            builder.setMessage(messsage);
            builder.setCancelable(false);

            builder.setPositiveButton(getResources().getString(R.string.dlg_add_anthr_prfl), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    Intent intent = new Intent(FullDogProfileActivity.this, AddDogProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
//                    callLogoutAPI();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    Intent intent = new Intent(FullDogProfileActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isValid() {
        try {
            if (profilePicFile == null) {
                AlertDialogUtility.showSnakeBar("Please select " + dogName + " profile photo", snackBar, FullDogProfileActivity.this);
                return false;
            }

            request.addFormDataPart(Keys.USER_ID, "" + user.getUserId());
            request.addFormDataPart(Keys.ACCESS_TOKEN, user.getAccessToken());
            request.addFormDataPart(Keys.DEVICE_TYPE, "" + Constants.DEVICE_TYPE);
            request.addFormDataPart(Keys.DEVICE_TOKEN, SessionManager.getInstance().getDeviceToken(FullDogProfileActivity.this));
            request.addFormDataPart(Keys.USERTYPE, "" + user.getUserType());
            request.addFormDataPart(Keys.DOG_ID, "" + dogId);
            request.addFormDataPart(Keys.DOGNAME, dogName);
            request.addFormDataPart(Keys.DOGBREEDID, "" + breedId);
            request.addFormDataPart(Keys.DOGAGE, "" + dogAge);
            request.addFormDataPart(Keys.GENDER, "" + dogGender);
            request.addFormDataPart(Keys.DOGSIZE, dogSize);
            request.addFormDataPart(Keys.ISDESEXED, "" + dogIsDesexed);
            request.addFormDataPart(Keys.IS_WILLING_TO_BREED, "" + willingToBreed);
            request.addFormDataPart(Keys.VACCINATIONS, dogVaccinations);
            request.addFormDataPart(Keys.DOGDESC, etAbout.getText().toString().trim());
            request.addFormDataPart(Keys.DOGINSTALINK, dogInstaLink);
            if (profilePicFile != null) {
                RequestBody profilePic = RequestBody.create(MediaType.parse("image/*"), profilePicFile);
                if (profilePic != null) {
                    request.addFormDataPart(Keys.DOGPROFILEPIC, getResources().getString(R.string.app_name) + ".jpg", profilePic);
                }
            }

            if (profilePicFileOne != null) {
                RequestBody profilePicOne = RequestBody.create(MediaType.parse("image/*"), profilePicFileOne);
                if (profilePicOne != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS1, getResources().getString(R.string.app_name) + "_One.jpg", profilePicOne);
                }
            }

            if (profilePicFileTwo != null) {
                RequestBody profilePicTwo = RequestBody.create(MediaType.parse("image/*"), profilePicFileTwo);
                if (profilePicTwo != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS2, getResources().getString(R.string.app_name) + "_Two.jpg", profilePicTwo);
                }
            }

            if (profilePicFileThree != null) {
                RequestBody profilePicThree = RequestBody.create(MediaType.parse("image/*"), profilePicFileThree);
                if (profilePicThree != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS3, getResources().getString(R.string.app_name) + "_Three.jpg", profilePicThree);
                }
            }

            if (profilePicFileFour != null) {
                RequestBody profilePicFour = RequestBody.create(MediaType.parse("image/*"), profilePicFileFour);
                if (profilePicFour != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS4, getResources().getString(R.string.app_name) + "_Four.jpg", profilePicFour);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFile = new File(GlobalMethods.makeCameraDir(), R.string.app_name + ".jpg");
            if (profilePicFile.exists()) {
                profilePicFile.delete();
            }

            if (profilePicFile == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFile);
                result.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
//                previousBitmap = result;
//                currentBitmap = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ImageDownloaderTaskOne extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileOne = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_One.jpg");
            if (profilePicFileOne.exists()) {
                profilePicFileOne.delete();
            }

            if (profilePicFileOne == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileOne);
                result.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
                ibPhotoOneDelete.setVisibility(View.VISIBLE);
//                previousBitmap = result;
//                currentBitmap = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ImageDownloaderTaskTwo extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileTwo = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_Two.jpg");
            if (profilePicFileTwo.exists()) {
                profilePicFileTwo.delete();
            }

            if (profilePicFileTwo == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileTwo);
                result.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
                ibPhotoTwoDelete.setVisibility(View.VISIBLE);
//                previousBitmap = result;
//                currentBitmap = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ImageDownloaderTaskThree extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileThree = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_Three.jpg");
            if (profilePicFileThree.exists()) {
                profilePicFileThree.delete();
            }

            if (profilePicFileThree == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileThree);
                result.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
                ibPhotoThreeDelete.setVisibility(View.VISIBLE);
//                previousBitmap = result;
//                currentBitmap = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ImageDownloaderTaskFour extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileFour = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_Four.jpg");
            if (profilePicFileFour.exists()) {
                profilePicFileFour.delete();
            }

            if (profilePicFileFour == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileFour);
                result.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
                ibPhotoFourDelete.setVisibility(View.VISIBLE);
//                previousBitmap = result;
//                currentBitmap = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void selectImage(final int requestCode) {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_photo), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(FullDogProfileActivity.this);
        builder.setTitle(getString(R.string.profile_pic));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    if (requestCode == IMAGE1) {
                        profilePicUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        try {
                            profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                            if (profilePicFile.exists()) {
                                profilePicFile.delete();
                            }
                            profilePicFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUri);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE2) {
                        profilePicUriOne = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        try {
                            profilePicFileOne = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_One.jpg");
                            if (profilePicFileOne.exists()) {
                                profilePicFileOne.delete();
                            }
                            profilePicFileOne.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriOne);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE3) {
                        profilePicUriTwo = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        try {
                            profilePicFileTwo = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Two.jpg");
                            if (profilePicFileTwo.exists()) {
                                profilePicFileTwo.delete();
                            }
                            profilePicFileTwo.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriTwo);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE4) {
                        profilePicUriThree = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        try {
                            profilePicFileThree = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Three.jpg");
                            if (profilePicFileThree.exists()) {
                                profilePicFileThree.delete();
                            }
                            profilePicFileThree.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriThree);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE5) {
                        profilePicUriFour = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        try {
                            profilePicFileFour = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Four.jpg");
                            if (profilePicFileFour.exists()) {
                                profilePicFileFour.delete();
                            }
                            profilePicFileFour.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriFour);
                        startActivityForResult(intent, requestCode);
                    }
                } else if (items[item].equals(getString(R.string.choose_photo))) {
                    if (requestCode == IMAGE1) {
                        try {
                            profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                            if (profilePicFile.exists()) {
                                profilePicFile.delete();
                            }
                            profilePicFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                    } else if (requestCode == IMAGE2) {
                        try {
                            profilePicFileOne = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_One.jpg");
                            if (profilePicFileOne.exists()) {
                                profilePicFileOne.delete();
                            }
                            profilePicFileOne.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                    } else if (requestCode == IMAGE3) {
                        try {
                            profilePicFileTwo = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Two.jpg");
                            if (profilePicFileTwo.exists()) {
                                profilePicFileTwo.delete();
                            }
                            profilePicFileTwo.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                    } else if (requestCode == IMAGE4) {
                        try {
                            profilePicFileThree = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Three.jpg");
                            if (profilePicFileThree.exists()) {
                                profilePicFileThree.delete();
                            }
                            profilePicFileThree.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                    } else if (requestCode == IMAGE5) {
                        try {
                            profilePicFileFour = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Four.jpg");
                            if (profilePicFileFour.exists()) {
                                profilePicFileFour.delete();
                            }
                            profilePicFileFour.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                    }
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case IMAGE1:
                    Bitmap bitmap1;
                    if (data == null) {
//                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUri);
                        bitmap1 = GlobalMethods.handleSamplingAndRotationBitmap(this, profilePicUri);
                    } else {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    }

                    setImages(bitmap1, profilePicFile, toolbarImage);
                    break;

                case IMAGE2:
                    Bitmap bitmap2;
                    if (data == null) {
//                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriOne);
                        bitmap2 = GlobalMethods.handleSamplingAndRotationBitmap(this, profilePicUriOne);
                    } else {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    }

                    ibPhotoOneDelete.setVisibility(View.VISIBLE);

                    setImages(bitmap2, profilePicFileOne, ivPhotoOne);
                    break;

                case IMAGE3:
                    Bitmap bitmap3;
                    if (data == null) {
//                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriTwo);
                        bitmap3 = GlobalMethods.handleSamplingAndRotationBitmap(this, profilePicUriTwo);
                    } else {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    }

                    ibPhotoTwoDelete.setVisibility(View.VISIBLE);

                    setImages(bitmap3, profilePicFileTwo, ivPhotoTwo);
                    break;

                case IMAGE4:
                    Bitmap bitmap4;
                    if (data == null) {
//                        bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriThree);
                        bitmap4 = GlobalMethods.handleSamplingAndRotationBitmap(this, profilePicUriThree);
                    } else {
                        bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    }

                    ibPhotoThreeDelete.setVisibility(View.VISIBLE);

                    setImages(bitmap4, profilePicFileThree, ivPhotoThree);
                    break;

                case IMAGE5:
                    Bitmap bitmap5;
                    if (data == null) {
//                        bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriFour);
                        bitmap5 = GlobalMethods.handleSamplingAndRotationBitmap(this, profilePicUriFour);
                    } else {
                        bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    }

                    ibPhotoFourDelete.setVisibility(View.VISIBLE);

                    setImages(bitmap5, profilePicFileFour, ivPhotoFour);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImages(Bitmap mBitmap, File pictureUrl, ImageView imageView) {
        try {
            try {
                FileOutputStream out = new FileOutputStream(pictureUrl);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MyApp.picasso.invalidate(pictureUrl);
            MyApp.picasso
                    .load(pictureUrl)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .fit().centerCrop()
                    .transform(new RoundedCornersTransformation(10, 0))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<BreedListResponseModel.BreedListBean> alBreed = new ArrayList<>();
    private ArrayAdapter<String> breedAdapter;
    private ArrayAdapter<String> agesAdapter;
    private ArrayAdapter<String> genderAdapter;
    private ArrayAdapter<String> statusAdapter;
    private ArrayAdapter<String> vaccinationsAdapter;
    private ArrayAdapter<String> willingToBreedAdapter;
    private String[] ages;
    private String[] gender;
    private String[] status;
    private String[] vaccinations;
    private String[] willingToBreeds;

    private void callBreedListAPI() {
        if (Helper.isCheckInternet(FullDogProfileActivity.this)) {
            pDialog = new ProgressDialog(FullDogProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.breedListAPI(breedListRequest(), responseCallbackBreedList);
        }
    }

    private BreedListRequestModel breedListRequest() {
        BreedListRequestModel breedListRequest = new BreedListRequestModel();
        breedListRequest.setUserId(user.getUserId());
        breedListRequest.setAccessToken(user.getAccessToken());
        return breedListRequest;
    }

    ResponseCallback responseCallbackBreedList = new ResponseCallback() {
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
                        setBreedName();
                        setAge();
                        setGender();
                        setStatus();
                        setVaccinations();
                        setBreeding();
                    } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                        AlertDialogUtility.showAlert(FullDogProfileActivity.this, response.getMessage(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(FullDogProfileActivity.this, SignInActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, FullDogProfileActivity.this);
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

    private void setBreedName() {
        try {
            BreedListResponseModel.BreedListBean breedListBean = new BreedListResponseModel.BreedListBean();
            breedListBean.setBreedName(getResources().getString(R.string.select_breed));
            alBreed.add(breedListBean);
            final int alSize = alBreed.size() - 1;

            ArrayList<String> alBreedName = new ArrayList<>();
            for (int i = 0; i <= alSize; i++) {
                String s = alBreed.get(i).getBreedName();
                alBreedName.add(s);
            }

            breedAdapter = new ArrayAdapter<String>(FullDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, alBreedName) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBreedName.setAdapter(breedAdapter);
            spinnerBreedName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    breedName = alBreed.get(position).getBreedName();
                    breedId = alBreed.get(position).getBreedId();
                    tvBreedName.setText(alBreed.get(position).getBreedName());
                    if (position != alSize) {
                        tvBreedName.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvBreedName.setTextColor(getResources().getColor(R.color.text_hint_color));
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
                        spinnerBreedName.setSelection(i);
                    }
                }
            } else {
                spinnerBreedName.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAge() {
        try {
            ages = getResources().getStringArray(R.array.ages);
            final int alSize = ages.length - 1;

            agesAdapter = new ArrayAdapter<String>(FullDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, ages) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            agesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAge.setAdapter(agesAdapter);
            spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dogAge = position;
                    tvAgeValue.setText(ages[position]);
                    if (position != alSize) {
                        tvAgeValue.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvAgeValue.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null) {
                for (int i = 0; i < ages.length; i++) {
                    if (dog.getDogAge() == i) {
                        spinnerAge.setSelection(i);
                    }
                }
            } else {
                spinnerAge.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGender() {
        try {
            gender = getResources().getStringArray(R.array.gender_full_dog);
            final int alSize = gender.length - 1;

            genderAdapter = new ArrayAdapter<String>(FullDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, gender) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGender.setAdapter(genderAdapter);
            spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dogGender = position + 1;
                    tvGenderName.setText(gender[position]);
                    if (position != alSize) {
                        tvGenderName.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvGenderName.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null) {
                for (int i = 0; i < gender.length; i++) {
                    if (dog.getGender() == i + 1) {
                        spinnerGender.setSelection(i);
                    }
                }
            } else {
                spinnerGender.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatus() {
        try {
            status = getResources().getStringArray(R.array.status_full_dog);
            final int alSize = status.length - 1;

            statusAdapter = new ArrayAdapter<String>(FullDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, status) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStatus.setAdapter(statusAdapter);
            spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dogIsDesexed = position + 1;
                    tvStatusValue.setText(status[position]);
                    if (position != alSize) {
                        tvStatusValue.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvStatusValue.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null) {
                for (int i = 0; i < status.length; i++) {
                    if (dog.getGender() == i + 1) {
                        spinnerStatus.setSelection(i);
                    }
                }
            } else {
                spinnerStatus.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVaccinations() {
        try {
            vaccinations = getResources().getStringArray(R.array.vaccinations);
            final int alSize = vaccinations.length - 1;

            vaccinationsAdapter = new ArrayAdapter<String>(FullDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, vaccinations) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            vaccinationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerVaccinations.setAdapter(vaccinationsAdapter);
            spinnerVaccinations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dogVaccinations = vaccinations[position];
                    tvVaccinationName.setText(vaccinations[position]);
                    if (position != alSize) {
                        tvVaccinationName.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvVaccinationName.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null && dog.getVaccinations() != null) {
                for (int i = 0; i < vaccinations.length; i++) {
                    if (dog.getVaccinations().equalsIgnoreCase(vaccinations[i])) {
                        spinnerVaccinations.setSelection(i);
                    }
                }
            } else {
                spinnerVaccinations.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBreeding() {
        try {
            willingToBreeds = getResources().getStringArray(R.array.willing_to_breed);
            final int alSize = willingToBreeds.length - 1;

            willingToBreedAdapter = new ArrayAdapter<String>(FullDogProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, willingToBreeds) {
                @Override
                public int getCount() {
                    return alSize;
                }
            };

            willingToBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerWillingToBreed.setAdapter(willingToBreedAdapter);
            spinnerWillingToBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    willingToBreed = position;
                    tvWillingToBreedValue.setText(willingToBreeds[position]);
                    if (position != alSize) {
                        tvWillingToBreedValue.setTextColor(getResources().getColor(R.color.text_color));
                    } else {
                        tvWillingToBreedValue.setTextColor(getResources().getColor(R.color.text_hint_color));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (dog != null) {
                for (int i = 0; i < willingToBreeds.length; i++) {
                    if (dog.getIsWillingToBreed() == i) {
                        spinnerWillingToBreed.setSelection(i);
                    }
                }
            } else {
                spinnerWillingToBreed.setSelection(alSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
