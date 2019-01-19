package com.patchpets.Activitys;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityFullDogProfileBinding;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.responseModel.AddDogToProfileResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.Helper;
import com.patchpets.utils.RoundedCornersTransformation;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.Keys;
import com.patchpets.webservices.ResponseCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.patchpets.utils.Constants.PERMISSION_REQUEST_CAMERA;

public class FullDogProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFullDogProfileBinding binding;

    private TextView tvAddOtherProfile;
    private ImageView ivDone, toolbarImage;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private TextView tvGenderName, tvStatusValue, tvVaccinationName, tvAgeValue, tvBreedName, tvAboutName;

    private ImageView ivPhotoOne, ivPhotoTwo, ivPhotoThree, ivPhotoFour;

    private String dogName;
    private String breedName;
    private String dogSize;
    private int dogAge, breedId, dogGender = 1, dogIsDesexed = 1;
    String dogVaccinations, dogDescription, dogInstaLink;

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
    }

    private void getIntents() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.ADDDOGPROFILE)) {
                dog = getIntent().getParcelableExtra(Constants.DOG_DETAILS);
                dogId = getIntent().getIntExtra(Constants.DOG_ID, 0);

                if (dog != null) {
                    if (dog.getDogProfilePic() != null) {
                        Picasso.get()
                                .load(dog.getDogProfilePic())
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .fit().centerCrop()
                                .transform(new RoundedCornersTransformation(5, 0))
                                .into(toolbarImage);

                        new ImageDownloaderTask().execute(dog.getDogProfilePic());
                    }
                    if (dog.getDogPics() != null) {
                        for (int i = 0; i < dog.getDogPics().size(); i++) {
                            int dogImageSize = dog.getDogPics().size();

                            switch (dogImageSize) {
                                case 4:
                                    if (dog.getDogPics().get(3) != null) {
                                        Picasso.get()
                                                .load(dog.getDogPics().get(3))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(5, 0))
                                                .into(ivPhotoThree);
                                        new ImageDownloaderTaskFour().execute(dog.getDogPics().get(3));
                                    }
                                case 3:
                                    if (dog.getDogPics().get(2) != null) {
                                        Picasso.get()
                                                .load(dog.getDogPics().get(2))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(5, 0))
                                                .into(ivPhotoTwo);
                                        new ImageDownloaderTaskThree().execute(dog.getDogPics().get(2));
                                    }
                                case 2:
                                    if (dog.getDogPics().get(1) != null) {
                                        Picasso.get()
                                                .load(dog.getDogPics().get(1))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(5, 0))
                                                .into(ivPhotoTwo);
                                        new ImageDownloaderTaskTwo().execute(dog.getDogPics().get(1));
                                    }
                                case 1:
                                    if (dog.getDogPics().get(0) != null) {
                                        Picasso.get()
                                                .load(dog.getDogPics().get(0))
                                                .placeholder(R.drawable.camera)
                                                .error(R.drawable.camera)
                                                .fit().centerCrop()
                                                .transform(new RoundedCornersTransformation(5, 0))
                                                .into(ivPhotoOne);
                                        new ImageDownloaderTaskOne().execute(dog.getDogPics().get(0));
                                    }
                            }
                        }

                    }
                }
                dogName = getIntent().getStringExtra(Constants.SP_DOGNAME);
                breedName = getIntent().getStringExtra(Constants.SP_BREED_NAME);
                tvBreedName.setText(breedName);
                breedId = getIntent().getIntExtra(Constants.SP_BREED_ID, 0);
                dogAge = getIntent().getIntExtra(Constants.SP_AGE, 0);
                if (dogAge == 0) {
                    tvAgeValue.setText("<1");
                } else {
                    tvAgeValue.setText("" + dogAge);
                }
                dogSize = getIntent().getStringExtra(Constants.SP_SIZE);
//                tvAgeValue.setText("" + dogAge);
                dogGender = getIntent().getIntExtra(Constants.SP_GENDER, 0);
                if (dogGender == 1) {
                    tvGenderName.setText(getResources().getString(R.string.male));
                } else if (dogGender == 2) {
                    tvGenderName.setText(getResources().getString(R.string.female));
                }
                dogIsDesexed = getIntent().getIntExtra(Constants.SP_DESEXED, 0);
                if (dogIsDesexed == 1) {
                    tvStatusValue.setText(getResources().getString(R.string.sp_desexed));
                } else if (dogIsDesexed == 2) {
                    tvStatusValue.setText(getResources().getString(R.string.sp_sexed));
                }
                dogVaccinations = getIntent().getStringExtra(Constants.SP_VACCINATIONS);
                tvVaccinationName.setText(dogVaccinations);
                dogDescription = getIntent().getStringExtra(Constants.DESCRIPTION);
                if (dogDescription.equalsIgnoreCase("")) {
                    dogDescription = "";
                    tvAboutName.setText("-");
                } else {
                    dogDescription = dogDescription;
                    tvAboutName.setText(dogDescription);
                }
                dogInstaLink = getIntent().getStringExtra(Constants.SP_INSTALINK);
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
        tvAgeValue = binding.tvAgeValue;
        tvBreedName = binding.tvBreedName;
        tvAboutName = binding.tvAboutName;

        ivPhotoOne = binding.ivPhotoOne;
        ivPhotoTwo = binding.ivPhotoTwo;
        ivPhotoThree = binding.ivPhotoThree;
        ivPhotoFour = binding.ivPhotoFour;

        coordinatorLayout = binding.coordinatorLayout;
        appBarLayout = binding.appBarLayout;
        collapsingToolbar = binding.collapsingToolbar;
    }

    private void setListener() {
        tvAddOtherProfile.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        toolbarImage.setOnClickListener(this);
        ivPhotoOne.setOnClickListener(this);
        ivPhotoTwo.setOnClickListener(this);
        ivPhotoThree.setOnClickListener(this);
        ivPhotoFour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent;
            switch (v.getId()) {
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

                case R.id.ivDone:
//                    intent = new Intent(FullDogProfileActivity.this, HomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
                    if (isValid()) {
                        callAddDoProfile();
                    }
                    break;
            }
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
//            if (profilePicUri == null) {
//                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_profile_pic), snackBar, FullDogProfileActivity.this);
//                return false;
//            }

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
            request.addFormDataPart(Keys.VACCINATIONS, dogVaccinations);
            request.addFormDataPart(Keys.DOGDESC, dogDescription);
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
                    request.addFormDataPart(Keys.DOGOTHERPICS1, getResources().getString(R.string.app_name) + "_One" + ".jpg", profilePicOne);
                }
            }

            if (profilePicFileTwo != null) {
                RequestBody profilePicTwo = RequestBody.create(MediaType.parse("image/*"), profilePicFileTwo);
                if (profilePicTwo != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS2, getResources().getString(R.string.app_name) + "_Two" + ".jpg", profilePicTwo);
                }
            }

            if (profilePicFileThree != null) {
                RequestBody profilePicThree = RequestBody.create(MediaType.parse("image/*"), profilePicFileThree);
                if (profilePicThree != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS3, getResources().getString(R.string.app_name) + "_Three" + ".jpg", profilePicThree);
                }
            }

            if (profilePicFileFour != null) {
                RequestBody profilePicFour = RequestBody.create(MediaType.parse("image/*"), profilePicFileFour);
                if (profilePicFour != null) {
                    request.addFormDataPart(Keys.DOGOTHERPICS4, getResources().getString(R.string.app_name) + "_Four" + ".jpg", profilePicFour);
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
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
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

        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileOne = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_One" + ".jpg");
            if (profilePicFileOne.exists()) {
                profilePicFileOne.delete();
            }

            if (profilePicFileOne == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileOne);
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
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

        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileTwo = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_Two" + ".jpg");
            if (profilePicFileTwo.exists()) {
                profilePicFileTwo.delete();
            }

            if (profilePicFileTwo == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileTwo);
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
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

        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileThree = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_Three" + ".jpg");
            if (profilePicFileThree.exists()) {
                profilePicFileThree.delete();
            }

            if (profilePicFileThree == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileThree);
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
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

        @Override
        protected void onPostExecute(Bitmap result) {
            profilePicFileFour = new File(GlobalMethods.makeCameraDir(), R.string.app_name + "_Four" + ".jpg");
            if (profilePicFileFour.exists()) {
                profilePicFileFour.delete();
            }

            if (profilePicFileFour == null)
                return;

            try {
                FileOutputStream out = new FileOutputStream(profilePicFileFour);
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
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
                        profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUri);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE2) {
                        profilePicUriOne = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        profilePicFileOne = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_One" + ".jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriOne);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE3) {
                        profilePicUriTwo = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        profilePicFileTwo = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Two" + ".jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriTwo);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE4) {
                        profilePicUriThree = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        profilePicFileThree = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Three" + ".jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriThree);
                        startActivityForResult(intent, requestCode);
                    } else if (requestCode == IMAGE5) {
                        profilePicUriFour = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        profilePicFileFour = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Four" + ".jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUriFour);
                        startActivityForResult(intent, requestCode);
                    }
                } else if (items[item].equals(getString(R.string.choose_photo))) {

//                    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
//                    profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_gallery_" + IMAGE1 + ".jpg");
                    if (requestCode == IMAGE1) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                        profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                    } else if (requestCode == IMAGE2) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                        profilePicFileOne = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_One" + ".jpg");
                    } else if (requestCode == IMAGE3) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                        profilePicFileTwo = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Two" + ".jpg");
                    } else if (requestCode == IMAGE4) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                        profilePicFileThree = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Three" + ".jpg");
                    } else if (requestCode == IMAGE5) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), requestCode);
                        profilePicFileFour = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + "_Four" + ".jpg");
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
                // Profile Image
                case IMAGE1:
                    Bitmap bitmap1;
                    if (data == null) {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUri);
                    } else {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        profilePicUri = GlobalMethods.getImageUri(FullDogProfileActivity.this, bitmap1);
                    }

                    setImages(bitmap1, profilePicFile, toolbarImage, profilePicUri);
                    break;

                case IMAGE2:
                    Bitmap bitmap2;
                    if (data == null) {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriOne);
                    } else {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        profilePicUriOne = GlobalMethods.getImageUri(FullDogProfileActivity.this, bitmap2);
                    }

                    setImages(bitmap2, profilePicFileOne, ivPhotoOne, profilePicUriOne);
                    break;

                case IMAGE3:
                    Bitmap bitmap3;
                    if (data == null) {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriTwo);
                    } else {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        profilePicUriTwo = GlobalMethods.getImageUri(FullDogProfileActivity.this, bitmap3);
                    }

                    setImages(bitmap3, profilePicFileTwo, ivPhotoTwo, profilePicUriTwo);
                    break;

                case IMAGE4:
                    Bitmap bitmap4;
                    if (data == null) {
                        bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriThree);
                    } else {
                        bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        profilePicUriThree = GlobalMethods.getImageUri(FullDogProfileActivity.this, bitmap4);
                    }

                    setImages(bitmap4, profilePicFileThree, ivPhotoThree, profilePicUriThree);
                    break;

                case IMAGE5:
                    Bitmap bitmap5;
                    if (data == null) {
                        bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUriFour);
                    } else {
                        bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        profilePicUriFour = GlobalMethods.getImageUri(FullDogProfileActivity.this, bitmap5);
                    }

                    setImages(bitmap5, profilePicFileFour, ivPhotoFour, profilePicUriFour);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImages(Bitmap mBitmap, File pictureUrl, ImageView imageView, Uri imageURI) {
        try {
            try {
                FileOutputStream out = new FileOutputStream(pictureUrl);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Picasso.get()
                    .load(imageURI)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .fit().centerCrop()
                    .transform(new RoundedCornersTransformation(5, 0))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}