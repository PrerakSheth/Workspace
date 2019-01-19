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
import com.patchpets.databinding.ActivitySetUpProfileBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.ProfileRequest;
import com.patchpets.model.responseModel.ProfileResponse;
import com.patchpets.model.responseModel.SetUpDogOwnerProfileResponse;
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

public class SetupProfileActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private ActivitySetUpProfileBinding binding;
    private HeaderBar headerBar;
    private ImageView ivProfile, ivAdd;
    private EditText etFirstName, etLastName;
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
        user = SessionManager.getInstance().getUser(SetupProfileActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up_profile);
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
        ivAdd = binding.ivAdd;
        rippleBackground = binding.content;
        etFirstName = binding.etFirstName;
        etLastName = binding.etLastName;
        clMain = binding.clMain;
        clBottomSheet = binding.clBottomSheet;
        rvDogs = binding.rvDogs;
        tvEmpty = binding.tvEmpty;
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
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
        headerBar.tvTitle.setText(getResources().getString(R.string.setup_proile));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
        headerBar.ibRight.setImageDrawable(getResources().getDrawable(R.drawable.facebook));

        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
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

        adapter = new DogsGridImageAdapter(SetupProfileActivity.this, this, alDogs);
        rvDogs.setLayoutManager(new GridLayoutManager(this, 3));
        rvDogs.setAdapter(adapter);
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
            KeyboardUtility.hideKeyboard(SetupProfileActivity.this, view);
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.ibRight:
                    hideBottomSheet();
                    if (isValid()) {
                        callSetUpDogOwnerProfile();
                    }
                    break;

                case R.id.ivProfile:
                    hideBottomSheet();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SetupProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        selectImage();
                    }
                    break;

                case R.id.ivAdd:
                    hideBottomSheet();
                    if (user.getFirstName().equals(etFirstName.getText().toString().trim()) && user.getLastName().equals(etLastName.getText().toString().trim()) && previousBitmap.sameAs(currentBitmap)) {
                        rippleBackground.stopRippleAnimation();
                        Intent iAddDogProfile = new Intent(SetupProfileActivity.this, AddDogProfileActivity.class);
                        iAddDogProfile.putExtra(Constants.DOG_ID, 0);
                        startActivity(iAddDogProfile);
                    } else {
                        AlertDialogUtility.showSnakeBar(getResources().getString(R.string.please_save_profile_first), snackBar, SetupProfileActivity.this);
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
        Intent iAddDog = new Intent(this, AddDogProfileActivity.class);
        iAddDog.putExtra(Constants.DOG_DETAILS, alDogs.get(position));
        startActivity(iAddDog);
    }

    private boolean isValid() {
        try {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();

            if (firstName.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_first_name), snackBar, SetupProfileActivity.this);
                etFirstName.requestFocus();
                return false;
            }

            if (lastName.length() == 0) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_last_name), snackBar, SetupProfileActivity.this);
                etLastName.requestFocus();
                return false;
            }

            if (profilePicFile == null) {
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.empty_profile_pic), snackBar, SetupProfileActivity.this);
                return false;
            }

            request.addFormDataPart(Keys.USER_ID, "" + user.getUserId());
            request.addFormDataPart(Keys.ACCESS_TOKEN, user.getAccessToken());
            request.addFormDataPart(Keys.DEVICE_TYPE, "" + Constants.DEVICE_TYPE);
            request.addFormDataPart(Keys.DEVICE_TOKEN, SessionManager.getInstance().getDeviceToken(SetupProfileActivity.this));
            request.addFormDataPart(Keys.FIRST_NAME, firstName);
            request.addFormDataPart(Keys.LAST_NAME, lastName);
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
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_photo), getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(SetupProfileActivity.this);
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
                    profilePicFile = new File(GlobalMethods.makeCameraDir(), getResources().getString(R.string.app_name) + ".jpg");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), GALLERY);
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
            currentBitmap = null;
            if (requestCode == CAMERA) {
                currentBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicUri);
            } else if (requestCode == GALLERY) {
                currentBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            }

            if (currentBitmap == null) {
                return;
            }

//            ExifInterface exif = new ExifInterface(profilePicFile.getAbsolutePath());
//            int orientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_NORMAL);
//
//            int angle = 0;
//
//            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
//                angle = 90;
//            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
//                angle = 180;
//            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
//                angle = 270;
//            }
//
//            Matrix mat = new Matrix();
//            mat.postRotate(angle);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
//
//            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(profilePicFile), null, options);
//            currentBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);
//            ByteArrayOutputStream outstudentstreamOutputStream = new ByteArrayOutputStream();
//            currentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstudentstreamOutputStream);

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
                        ActivityCompat.requestPermissions(SetupProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
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

    private void callSetUpDogOwnerProfile() {
        if (Helper.isCheckInternet(SetupProfileActivity.this)) {
            pDialog = new ProgressDialog(SetupProfileActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.setUpDogOwnerProfileAPI(request.build(), responseCallback);
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
                    SetUpDogOwnerProfileResponse response = (SetUpDogOwnerProfileResponse) object;
                    if (response.getStatus() == 1) {
                        user.setFirstName(response.getUserData().getFirstName());
                        user.setLastName(response.getUserData().getLastName());
                        user.setProfilePic(response.getUserData().getProfilePic());
                        user.setIsProfileSetup(response.getUserData().getIsProfileSetup());
                        previousBitmap = currentBitmap;
                        SessionManager.getInstance().saveUser(SetupProfileActivity.this, user);
                        SessionManager.getInstance().setSession(SetupProfileActivity.this, true);
                    } else {
                        AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, SetupProfileActivity.this);
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
        if (Helper.isCheckInternet(SetupProfileActivity.this)) {
            pDialog = new ProgressDialog(SetupProfileActivity.this);
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
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, SetupProfileActivity.this);
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

//    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
//        try {
//            ExifInterface ei = new ExifInterface(image_absolute_path);
//            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    return rotate(bitmap, 90);
//
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    return rotate(bitmap, 180);
//
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    return rotate(bitmap, 270);
//
//                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
//                    return flip(bitmap, true, false);
//
//                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
//                    return flip(bitmap, false, true);
//
//                default:
//                    return bitmap;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static Bitmap rotate(Bitmap bitmap, float degrees) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degrees);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
//
//    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
//        Matrix matrix = new Matrix();
//        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private String getPath(Context ctx, Uri uri) {
//        String ret = "";
//        if (isAboveKitKat()) {
//            ret = getUriRealPathAboveKitkat(ctx, uri);
//        } else {
//            ret = getImageRealPath(getContentResolver(), uri, null);
//        }
//        return ret;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private String getUriRealPathAboveKitkat(Context ctx, Uri uri) {
//        String ret = "";
//
//        if (ctx != null && uri != null) {
//            if (isContentUri(uri)) {
//                if (isGooglePhotoDoc(uri.getAuthority())) {
//                    ret = uri.getLastPathSegment();
//                } else {
//                    ret = getImageRealPath(getContentResolver(), uri, null);
//                }
//            } else if (isFileUri(uri)) {
//                ret = uri.getPath();
//            } else if (isDocumentUri(ctx, uri)) {
//                String documentId = DocumentsContract.getDocumentId(uri);
//                String uriAuthority = uri.getAuthority();
//
//                if (isMediaDoc(uriAuthority)) {
//                    String idArr[] = documentId.split(":");
//                    if (idArr.length == 2) {
//                        String docType = idArr[0];
//                        String realDocId = idArr[1];
//                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                        if ("image".equals(docType)) {
//                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                        } else if ("video".equals(docType)) {
//                            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                        } else if ("audio".equals(docType)) {
//                            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                        }
//
//                        String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;
//                        ret = getImageRealPath(getContentResolver(), mediaContentUri, whereClause);
//                    }
//                } else if (isDownloadDoc(uriAuthority)) {
//                    Uri downloadUri = Uri.parse("content://downloads/public_downloads");
//                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));
//                    ret = getImageRealPath(getContentResolver(), downloadUriAppendId, null);
//                } else if (isExternalStoreDoc(uriAuthority)) {
//                    String idArr[] = documentId.split(":");
//                    if (idArr.length == 2) {
//                        String type = idArr[0];
//                        String realDocId = idArr[1];
//                        if ("primary".equalsIgnoreCase(type)) {
//                            ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
//                        }
//                    }
//                }
//            }
//        }
//        return ret;
//    }
//
//    /* Check whether current android os version is bigger than kitkat or not. */
//    private boolean isAboveKitKat() {
//        boolean ret = false;
//        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//        return ret;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private boolean isDocumentUri(Context ctx, Uri uri) {
//        boolean ret = false;
//        if (ctx != null && uri != null) {
//            ret = DocumentsContract.isDocumentUri(ctx, uri);
//        }
//        return ret;
//    }
//
//    private boolean isContentUri(Uri uri) {
//        boolean ret = false;
//        if (uri != null) {
//            String uriSchema = uri.getScheme();
//            if ("content".equalsIgnoreCase(uriSchema)) {
//                ret = true;
//            }
//        }
//        return ret;
//    }
//
//    private boolean isFileUri(Uri uri) {
//        boolean ret = false;
//        if (uri != null) {
//            String uriSchema = uri.getScheme();
//            if ("file".equalsIgnoreCase(uriSchema)) {
//                ret = true;
//            }
//        }
//        return ret;
//    }
//
//
//    private boolean isExternalStoreDoc(String uriAuthority) {
//        boolean ret = false;
//
//        if ("com.android.externalstorage.documents".equals(uriAuthority)) {
//            ret = true;
//        }
//        return ret;
//    }
//
//    private boolean isDownloadDoc(String uriAuthority) {
//        boolean ret = false;
//        if ("com.android.providers.downloads.documents".equals(uriAuthority)) {
//            ret = true;
//        }
//        return ret;
//    }
//
//    private boolean isMediaDoc(String uriAuthority) {
//        boolean ret = false;
//        if ("com.android.providers.media.documents".equals(uriAuthority)) {
//            ret = true;
//        }
//        return ret;
//    }
//
//    private boolean isGooglePhotoDoc(String uriAuthority) {
//        boolean ret = false;
//        if ("com.google.android.apps.photos.content".equals(uriAuthority)) {
//            ret = true;
//        }
//        return ret;
//    }
//
//    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause) {
//        String ret = "";
//        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
//
//        if (cursor != null) {
//            boolean moveToFirst = cursor.moveToFirst();
//            if (moveToFirst) {
//                String columnName = MediaStore.Images.Media.DATA;
//
//                if (uri == MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
//                    columnName = MediaStore.Images.Media.DATA;
//                } else if (uri == MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
//                    columnName = MediaStore.Audio.Media.DATA;
//                } else if (uri == MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
//                    columnName = MediaStore.Video.Media.DATA;
//                }
//                int imageColumnIndex = cursor.getColumnIndex(columnName);
//                ret = cursor.getString(imageColumnIndex);
//            }
//        }
//        return ret;
//    }
}
