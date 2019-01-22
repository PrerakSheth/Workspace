package com.konkr.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.SearchSuppliment;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.Base64;
import com.konkr.Utils.BaseActivity;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Utils.PermissionsHelper;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityMiSupplimentBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MiSupplimentActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ActivityMiSupplimentBinding binding;
    private SimpleDraweeView ivSupplimentPhoto;
    private Activity context;
    private Headerbar headerBar;
    private View snackBarView;
    private Uri mImageUri;
    private Uri uriSupplimentPic;
    private Bitmap bitmap;
    private String strProPicBase64;
    private static final int PHOTO_PICK = 243, PHOTO_CLICK = 891, PIC_CROP = 152;
    ;
    MyTextView tvDesCharRemaining;
    private MyEditText etSupplimentDes;
    private AutoCompleteTextView etSupplimentName;
    private String strSupplimentName, strSupplimentDes;
    private ArrayList<SearchSuppliment.SupplimentSearchDataBean> supplimentSearchDataBeanArrayList = new ArrayList<>();
    private ArrayAdapter<SearchSuppliment.SupplimentSearchDataBean> adapter;
    Spinner spinner;
    private MyTextView tvAddPhoto;
    private ImageView ivPhoto;
    private boolean flag = true;
    private String resourceId = "";
    private File filepath, cropfilepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mi_suppliment);
        context = MiSupplimentActivity.this;
        snackBarView = findViewById(android.R.id.content);
        context = MiSupplimentActivity.this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        bindView();
        setListener();
        setHeaderBar();

        setTextCount();

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,restaurants);

        etSupplimentDes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.etSupplimentDes) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });


        etSupplimentName.setOnItemClickListener(this);

        adapter = new ArrayAdapter<SearchSuppliment.SupplimentSearchDataBean>(context, android.R.layout.simple_dropdown_item_1line, supplimentSearchDataBeanArrayList);
        adapter.setNotifyOnChange(true);
    }

    private void setTextCount() {

        etSupplimentDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvDesCharRemaining.setText(1000 - s.toString().length() + "");

            }


        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // create Toast with user selected value
//        Toast.makeText(context, "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
        flag = false;
        // Toast.makeText (context, "Selected Item is: \t" + item, Toast.LENGTH_LONG).show ();

        etSupplimentName.setText(item);
        resourceId = supplimentSearchDataBeanArrayList.get(position).getResourceId();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag = true;
            }

        }, DELAY * 2);

    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvRight.setVisibility(View.VISIBLE);
        headerBar.tvRight.setText(getResources().getString(R.string.done));
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.mi_suppliment_title);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        ivSupplimentPhoto.setOnClickListener(this);
        headerBar.tvRight.setOnClickListener(this);
        etSupplimentName.addTextChangedListener(textWatcher);
        etSupplimentName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    etSupplimentName.dismissDropDown(); // this line will close drop down  on click of search
                    callSearchSupplementApi(etSupplimentName.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }
//        etSupplimentName.addTextChangedListener (new TextWatcher () {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if(timer != null)
//                    timer.cancel();
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                try {
//                    if (editable.toString().trim().length() > 0) {
//                        LogM.LogV("Before timer Searched --------- " + editable.toString().trim());
//                        timer = new Timer();
//                        timer.schedule(new TimerTask () {
//                            @Override
//                            public void run() {
//                                LogM.LogV("run--------- " + editable.toString().trim());
//                                if(editable.toString().trim().length() > 0) {
//                                    LogM.LogV("After timer Searched --------- " + editable.toString().trim());
//                                   callSearchSupplementApi (editable.toString().trim());
//                                }
//                            }
//
//                        }, DELAY);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//    }

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (timer != null)
                timer.cancel();

        }


        @Override
        public void afterTextChanged(final Editable editable) {
            try {
                if (editable.toString().trim().length() > 0) {
                    LogM.LogV("Before timer Searched Suppliment--------- " + editable.toString().trim());
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            LogM.LogV("run--------- " + editable.toString().trim());
//                            if (editable.toString ().contains (" ")) {
                            // s.toString().charAt(s.length()-1
                            //     if(editable.toString ().charAt (editable.toString ().length() - 1)==' '){
                            if (flag) {

                                Looper.prepare();
                                LogM.LogV("After timer Searched Suppliment--------- " + editable.toString().trim());
                                callSearchSupplementApi(editable.toString().trim());
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


    private void bindView() {
        headerBar = binding.headerBar;
        ivSupplimentPhoto = binding.ivSupplimentPhoto;
        etSupplimentName = binding.etSupplimentName;
        etSupplimentDes = binding.etSupplimentDes;
        spinner = binding.spinner;
        tvDesCharRemaining = binding.tvRemainingTextsuppDes;
        tvAddPhoto = binding.tvAddPhoto;
        ivPhoto = binding.ivPhoto;

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/MavenPro-Regular.ttf");
        etSupplimentName.setTypeface(font);

        //etSupplimentName.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etSupplimentName.setImeActionLabel(getString(R.string.search), KeyEvent.KEYCODE_SEARCH);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.ivSupplimentPhoto:
                PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}).checkPermissions(MiSupplimentActivity.this, new PermissionsHelper.OnPermissionResult() {

                    @Override
                    public void onGranted() {
                        openAlertForPhoto();
                    }

                    @Override
                    public void notGranted() {

                    }
                });
                break;
            case R.id.tvRight:
                KeyboardUtility.HideKeyboard(context, view);
                validation();
                break;
        }
    }


    private void callAddSupplementApi() {

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.ADD_SUPPLEMENT.SUPP_NAME, strSupplimentName);
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                jsonObject.put(WebField.ADD_SUPPLEMENT.SUPP_DETAILS, strSupplimentDes);
                jsonObject.put(WebField.ADD_SUPPLEMENT.SUPP_RESOURCE_ID, resourceId);
                jsonObject.put(WebField.ADD_SUPPLEMENT.SUPP_PHOTO, strProPicBase64);

                LogM.LogE("Request : Mi Suppliment : " + jsonObject.toString());
                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADD_SUPPLEMENT.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final MiSuppliment miSupplimentList = new Gson().fromJson(String.valueOf(jsonObject), MiSuppliment.class);
                        if (isSuccess) {
                            LogM.LogE("Response : Mi Suppliment : " + jsonObject.toString());
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            AlertDialogUtility.showAlert(context, miSupplimentList.getMessage()
                            );
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    /**
     * this Api call is used for auto suggestion
     *
     * @param charSequence
     */
    private void callSearchSupplementApi(String charSequence) {

        LogM.LogE("foodname filed" + charSequence);

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {
                KeyboardUtility.HideKeyboard(this, etSupplimentName);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                jsonObject.put(WebField.SEARCH_SUPPLEMENT.FOOD_NAME, charSequence);

                LogM.LogE("Request : SEARCH Suppliment : " + jsonObject.toString());
                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SEARCH_SUPPLEMENT.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final SearchSuppliment miSupplimentList = new Gson().fromJson(String.valueOf(jsonObject), SearchSuppliment.class);
                        if (isSuccess) {
                            LogM.LogE("Response : SEARCH Suppliment : " + jsonObject.toString());

                            supplimentSearchDataBeanArrayList.clear();
                            supplimentSearchDataBeanArrayList.addAll(miSupplimentList.getSupplimentSearchData());


                            // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            etSupplimentName.setThreshold(1);//will start working from first character
                            //setting the adapter data into the
                            adapter = new ArrayAdapter<SearchSuppliment.SupplimentSearchDataBean>(context, android.R.layout.simple_dropdown_item_1line, supplimentSearchDataBeanArrayList);

                            etSupplimentName.setAdapter(adapter);
                            etSupplimentName.showDropDown();
//                            supplimentSearchDataBeanArrayList.
//                            adapter.notifyDataSetChanged ();
                            LogM.LogE("Size of list" + supplimentSearchDataBeanArrayList.size());

                        } else {
                            AlertDialogUtility.showSnakeBar(miSupplimentList.getMessage(), snackBarView, context);
                        }

                    }
                }).execute();


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }


    private void openAlertForPhoto() {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MiSupplimentActivity.this)
                    .setTitle(getResources().getString(R.string.app_name)).setMessage("Choose Supplement Photo");


            builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    pickSupplimentPicFromGallary();
                    LogM.LogE("you have clicked camera");
                }
            });

            builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    pickSupplimentPicFromCamera();
                }
            });
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        final CharSequence[] items = {getString(R.string.camera), getString(R.string.gallery)};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MiSupplimentActivity.this);
//        builder.setTitle(getString(R.string.training_video_photo));
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals(getString(R.string.camera))) {
//                    pickSupplimentPicFromCamera();
//
//                    LogM.LogE("you have clicked camera");
//
//                } else if (items[item].equals(getString(R.string.gallery))) {
//                    pickSupplimentPicFromGallary();
//                }
//                dialog.dismiss();
//            }
//        });
//        builder.show();

    }

    private void pickSupplimentPicFromGallary() {
        try {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            uriSupplimentPic = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (android.os.Build.VERSION.SDK_INT > 10) {
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            }
            startActivityForResult(intent, PHOTO_PICK);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void pickSupplimentPicFromCamera() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, PHOTO_CLICK);

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, PHOTO_CLICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GlobalMethods.checkPicturesFolder();
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_PICK && resultCode == RESULT_OK && data != null) {
//            Log.e("Image captured", "Image captured");
            ivPhoto.setVisibility(View.GONE);
            tvAddPhoto.setVisibility(View.GONE);

            mImageUri = data.getData();

            if (mImageUri.toString().contains("content://com.google.android.apps.photos.content/0/")) {
                filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sent_" + System.currentTimeMillis() + ".png");
            } else if (mImageUri.toString().contains("content://com.google.android.apps.photos.content/1/")) {
                filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sent_" + System.currentTimeMillis() + ".mp4");
            }
            if (filepath != null) {
                mImageUri = getImageContentUri(context, filepath);
                if (mImageUri != null) {
                    performCrop(mImageUri);
                } else {
                    AlertDialogUtility.showAlert(context, "Please Select correct image");
                }
            } else {
                // Log.e("path", "" + filePath);
                if (mImageUri != null) {
                    performCrop(mImageUri);
                } else {
                    AlertDialogUtility.showAlert(context, "Please Select correct image");
                }
            }

//            ivSupplimentPhoto.setImageURI(mImageUri);

//            Log.e("Image captured", "Image captured");

//            mImageUri = data.getData();
//            ivSupplimentPhoto.setImageURI(mImageUri);
//            try {
//                performCrop(mImageUri);
//                bitmap = getBitmapFromUri(mImageUri);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            strProPicBase64 = encodeTobase64(bitmap);
//            LogM.LogE(" base 64" + strProPicBase64);


        } else if (requestCode == PHOTO_CLICK && resultCode == RESULT_OK) {
            ivPhoto.setVisibility(View.GONE);
            tvAddPhoto.setVisibility(View.GONE);
//            Log.e("Video captured", "Video captured");
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            mImageUri = getImageUri(photo);
//            performCrop(mImageUri);

            if (mImageUri != null) {
                performCrop(mImageUri);
            }

//            ivSupplimentPhoto.setImageURI(mImageUri);
//            strProPicBase64 = encodeTobase64(photo);
//            LogM.LogE(" base 64" + strProPicBase64);
//            Log.e("Video captured", "Video captured");
//            Bitmap photo = (Bitmap) data.getExtras ().get ("data");
//            mImageUri = getImageUri (photo);
//            ivSupplimentPhoto.setImageURI (mImageUri);
//            strProPicBase64 = encodeTobase64 (photo);
//            LogM.LogE (" base 64" + strProPicBase64);
        } else if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    Bitmap thePic = getBitmapFromFile(cropfilepath);
                    if (thePic != null) {
                        converToBase64(thePic);
//                        GlobalData.loginViaFacebook = false;  // it is used to save new selected photon from device into base64.
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ivPhoto.setVisibility(View.VISIBLE);
            tvAddPhoto.setVisibility(View.VISIBLE);
        }

    }

    private void converToBase64(Bitmap bitmap) {
        String strFilePathForFresco = "file://" + cropfilepath.getAbsolutePath();
        ivSupplimentPhoto.setImageURI(getImageContentUri(context, cropfilepath));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] dataTest = bos.toByteArray();
        strProPicBase64 = Base64.encodeBytes(dataTest);
        Log.d("strBase64 ", " strBase64 ========== " + strProPicBase64);
    }

    private Bitmap getBitmapFromFile(File photoPath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(photoPath.getAbsolutePath(), options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

//    private String encodeTobase64(Bitmap image) {
//        Bitmap immagex = image;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//        LogM.LogE(imageEncoded);
//        return imageEncoded;
//    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {

        ParcelFileDescriptor parcelFileDescriptor =
                MiSupplimentActivity.this.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void validation() {
        strSupplimentName = etSupplimentName.getText().toString().trim();
        strSupplimentDes = etSupplimentDes.getText().toString().trim();
        if (strProPicBase64 == null) {
            AlertDialogUtility.showSnakeBar(getString(R.string.valid_suppliment_photo), etSupplimentName, this);
        } else if (strSupplimentName.length() == 0) {
            etSupplimentName.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.valid_suppliment_name), etSupplimentName, this);
        } else if (strSupplimentDes.length() == 0) {
            etSupplimentDes.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.valid_suppliment_des), etSupplimentDes, this);
        } else {
            callAddSupplementApi();
        }
    }

    private void performCrop(Uri UriCamera) {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri

            cropIntent.setDataAndType(UriCamera, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 1024);
            cropIntent.putExtra("outputY", 1024);
            // retrieve data on return
//			cropIntent.putExtra("return-data", false);
            cropfilepath = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            + "/sent_"
                            + System.currentTimeMillis()
                            + ".jpg");

            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropfilepath));
            cropIntent.putExtra("output", Uri.fromFile(cropfilepath));
            cropIntent.putExtra("return-data", false);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
