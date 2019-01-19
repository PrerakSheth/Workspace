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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.patchpets.Adapters.DogsGridImageAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityBusinessSetupProfileBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.ProfileRequest;
import com.patchpets.model.responseModel.ProfileResponse;
import com.patchpets.model.responseModel.SetUpBusinessProfileResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.CircleTransform;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.utils.LogM;
import com.patchpets.utils.MyApp;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.Keys;
import com.patchpets.webservices.ResponseCallback;
import com.skyfishjy.library.RippleBackground;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.patchpets.utils.Constants.CAMERA;
import static com.patchpets.utils.Constants.GALLERY;
import static com.patchpets.utils.Constants.PERMISSION_REQUEST_CAMERA;

public class SetupBusinessProfileActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private ActivityBusinessSetupProfileBinding binding;
    private EditText etBusinessName, etContactNumber, etAbout, etServices;
    private ImageView ivAdd, ivProfile;
    private HeaderBar headerBar;
    private RippleBackground rippleBackground;
    private RecyclerView rvDogs;
    private TextView tvEmpty;
    private ConstraintLayout clMain, clBottomSheet;
    private View snackBar;
    private BottomSheetBehavior bottomSheetBehavior;

    private User user;
    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private MultipartBody.Builder request = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private Uri profilePicUri;
    private File profilePicFile = null;

    private Bitmap previousBitmap = null, currentBitmap = null;

    private DogsGridImageAdapter adapter;
    private ArrayList<DogDetails> alDogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SessionManager.getInstance().getUser(SetupBusinessProfileActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_setup_profile);
        snackBar = findViewById(android.R.id.content);

        bindViews();
        setListener();
        setData();

        bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
        callGetUserProfileAPI();
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ivProfile = binding.ivProfile;
        etBusinessName = binding.etBusinessName;
        etContactNumber = binding.etContactNumber;
        etAbout = binding.etAbout;
        etServices = binding.etServices;
        rippleBackground = binding.content;
        ivAdd = binding.ivAdd;
        clBottomSheet = binding.clBottomSheet;
        rvDogs = binding.rvDogs;
        tvEmpty = binding.tvEmpty;
        clMain = binding.clMain;
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        clBottomSheet.setOnClickListener(this);

        clMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideBottomSheet();
                return false;
            }
        });
    }

    private void setData() {
        try {
            headerBar.tvTitle.setText(getResources().getString(R.string.setup_proile));
            headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
            headerBar.ibRight.setImageDrawable(getResources().getDrawable(R.drawable.facebook));

            etBusinessName.setText(user.getBusinessName());
            etContactNumber.setText(user.getContactNo());
            etAbout.setText(user.getAboutBusiness());
            etServices.setText(user.getServices());
            if (user.getProfilePic() != null && user.getProfilePic().length() > 0) {
                MyApp.picasso
                        .load(user.getProfilePic())
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .fit().centerCrop()
                        .rotate(90)
                        .transform(new CircleTransform())
                        .into(ivProfile);
                new ImageDownloaderTask().execute(user.getProfilePic());
            }

            adapter = new DogsGridImageAdapter(SetupBusinessProfileActivity.this, this, alDogs);
            rvDogs.setLayoutManager(new GridLayoutManager(this, 3));
            rvDogs.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View view, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED:
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View view, float v) {
        }
    };

    @Override
    public void onClick(View view) {
        try {
            KeyboardUtility.hideKeyboard(SetupBusinessProfileActivity.this, view);
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.ibRight:
                    hideBottomSheet();
                    if (isValid()) {
                        callSetUpBusinessUserProfile();
                    }
                    break;

                case R.id.ivProfile:
                    hideBottomSheet();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SetupBusinessProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage();
                    }
                    break;

                case R.id.ivAdd:
                    hideBottomSheet();
                    if (user.getBusinessName().equals(etBusinessName.getText().toString().trim())
                            && user.getContactNo().equals(etContactNumber.getText().toString().trim())
                            && user.getAboutBusiness().equals(etAbout.getText().toString().trim())
                            && user.getServices().equals(etServices.getText().toString().trim())
                            && previousBitmap.sameAs(currentBitmap)) {
                        rippleBackground.stopRippleAnimation();
                        Intent iAddDogProfile = new Intent(SetupBusinessProfileActivity.this, AddDogProfileActivity.class);
                        startActivity(iAddDogProfile);
                    } else {
                        AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_save_profile_first), snackBar, SetupBusinessProfileActivity.this);
                    }
                    break;

                case R.id.clBottomSheet:
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        showBottomSheet();
                    } else {
                        hideBottomSheet();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideBottomSheet() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void showBottomSheet() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onItemClicked(int position) {
        LogM.e(position + " position clicked");
//        Intent iAddDog = new Intent(this, AddDogProfileActivity.class);
//        iAddDog.putExtra(Constants.DOG_DETAILS, alDogs.get(position));
//        startActivity(iAddDog);
    }

    private boolean isValid() {
        try {
            String businessName = etBusinessName.getText().toString().trim();
            String contactNumber = etContactNumber.getText().toString().trim();
            String about = etAbout.getText().toString().trim();
            String services = etServices.getText().toString().trim();

            if (businessName.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_business_name), snackBar, SetupBusinessProfileActivity.this);
                etBusinessName.requestFocus();
                return false;
            }

            if (contactNumber.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_contact_number), snackBar, SetupBusinessProfileActivity.this);
                etContactNumber.requestFocus();
                return false;
            }

            if (about.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_about), snackBar, SetupBusinessProfileActivity.this);
                etAbout.requestFocus();
                return false;
            }

            if (services.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_services), snackBar, SetupBusinessProfileActivity.this);
                etServices.requestFocus();
                return false;
            }

            if (profilePicFile == null) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_profile_pic), snackBar, SetupBusinessProfileActivity.this);
                return false;
            }

            request.addFormDataPart(Keys.USER_ID, "" + user.getUserId());
            request.addFormDataPart(Keys.ACCESS_TOKEN, user.getAccessToken());
            request.addFormDataPart(Keys.DEVICE_TYPE, "" + Constants.DEVICE_TYPE);
            request.addFormDataPart(Keys.DEVICE_TOKEN, SessionManager.getInstance().getDeviceToken(SetupBusinessProfileActivity.this));
            request.addFormDataPart(Keys.BUSINESS_NAME, businessName);
            request.addFormDataPart(Keys.CONTACT_NO, contactNumber);
            request.addFormDataPart(Keys.ABOUT_BUSINESS, about);
            request.addFormDataPart(Keys.SERVICES, services);
            request.addFormDataPart(Keys.INSTA_LINK, "www.instagram.com/test");

            if (profilePicFile != null) {
                RequestBody profilePic = RequestBody.create(MediaType.parse("image/*"), profilePicFile);
                if (profilePic != null) {
                    request.addFormDataPart(Keys.PROFILE_PIC, getResources().getString(R.string.app_name) + ".jpg", profilePic);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void selectImage() {
        try {
            final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_photo), getString(R.string.cancel)};

            AlertDialog.Builder builder = new AlertDialog.Builder(SetupBusinessProfileActivity.this);
            builder.setTitle(getString(R.string.profile_pic));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals(getString(R.string.take_photo))) {
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        profilePicUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, profilePicUri);
                        startActivityForResult(intent, CAMERA);
                    } else if (items[item].equals(getString(R.string.choose_photo))) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), GALLERY);
                        profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                    } else if (items[item].equals(getString(R.string.cancel))) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            currentBitmap = null;
            if (requestCode == CAMERA) {
                currentBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUri);
            } else if (requestCode == GALLERY) {
                currentBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            }

            if (currentBitmap == null) {
                return;
            }

            try {
                FileOutputStream out = new FileOutputStream(profilePicFile);
                currentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MyApp.picasso
                    .load(profilePicFile)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .fit().centerCrop()
                    .rotate(90)
                    .transform(new CircleTransform())
                    .into(ivProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == PERMISSION_REQUEST_CAMERA) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SetupBusinessProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                        return;
                    }
                    selectImage();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_permission_denied), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callSetUpBusinessUserProfile() {
        if (Helper.isCheckInternet(SetupBusinessProfileActivity.this)) {
            pDialog = new ProgressDialog(SetupBusinessProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.setUpBusinessUserProfileAPI(request.build(), responseCallback);
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
                    SetUpBusinessProfileResponse response = (SetUpBusinessProfileResponse) object;
                    if (response.getStatus() == 1) {
                        user.setBusinessName(response.getUserData().getBusinessName());
                        user.setProfilePic(response.getUserData().getProfilePic());
                        user.setIsProfileSetup(response.getUserData().getIsProfileSetup());
                        user.setContactNo(response.getUserData().getContactNo());
                        user.setAboutBusiness(response.getUserData().getAboutBusiness());
                        user.setServices(response.getUserData().getCategory());
                        previousBitmap = currentBitmap;
                        SessionManager.getInstance().saveUser(SetupBusinessProfileActivity.this, user);
                        SessionManager.getInstance().setSession(SetupBusinessProfileActivity.this, true);
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, SetupBusinessProfileActivity.this);
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
                previousBitmap = result;
                currentBitmap = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void callGetUserProfileAPI() {
        if (Helper.isCheckInternet(SetupBusinessProfileActivity.this)) {
            pDialog = new ProgressDialog(SetupBusinessProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.getUserProfileAPI(profileRequest(), responseCallbackProfile);
        }
    }

    private ProfileRequest profileRequest() {
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setUserId(user.getUserId());
        profileRequest.setAccessToken(user.getAccessToken());
        profileRequest.setOppUserId(user.getUserId());
        return profileRequest;
    }

    ResponseCallback responseCallbackProfile = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                ProfileResponse response = (ProfileResponse) object;
                if (response.getStatus() == 1) {
                    alDogs.clear();
                    for (int i = 0; i < response.getUserData().getDogList().size(); i++) {
                        DogDetails dog = new DogDetails();
                        dog.setDogId(response.getUserData().getDogList().get(i).getDogId());
                        dog.setDogProfilePic(response.getUserData().getDogList().get(i).getDogProfilePic());
                        dog.setDogName(response.getUserData().getDogList().get(i).getDogName());
                        dog.setDogBreed(response.getUserData().getDogList().get(i).getDogBreed());
                        dog.setDogAge(response.getUserData().getDogList().get(i).getDogAge());
                        dog.setGender(response.getUserData().getDogList().get(i).getGender());
                        dog.setIsDesexed(response.getUserData().getDogList().get(i).getIsDesexed());
                        dog.setIsFavourite(response.getUserData().getDogList().get(i).getIsFavourite());
                        dog.setDogSize(response.getUserData().getDogList().get(i).getDogSize());
                        dog.setLastActiveTime(response.getUserData().getDogList().get(i).getLastActiveTime());
                        dog.setVaccinations(response.getUserData().getDogList().get(i).getVaccinations());
                        dog.setDogDesc(response.getUserData().getDogList().get(i).getDogDesc());
                        dog.setDogInstaLink(response.getUserData().getDogList().get(i).getDogInstaLink());

                        ArrayList<String> dogPics = new ArrayList<>();
                        for (int k = 0; k < response.getUserData().getDogList().get(i).getDogOtherPics().size(); k++) {
                            dogPics.add(response.getUserData().getDogList().get(i).getDogOtherPics().get(k));
                        }
                        dog.setDogPics(dogPics);
                        alDogs.add(dog);
                    }
                    adapter.notifyDataSetChanged();
                    if (alDogs.size() > 0) {
                        rvDogs.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        rvDogs.setVisibility(View.GONE);
                        tvEmpty.setVisibility(View.VISIBLE);
                    }
                } else {
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, SetupBusinessProfileActivity.this);
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