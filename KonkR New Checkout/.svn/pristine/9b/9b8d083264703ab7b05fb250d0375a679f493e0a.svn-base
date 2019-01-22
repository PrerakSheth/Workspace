package com.konkr.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.konkr.Models.AddExtraPhoto;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.PermissionsHelper;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityFullScreenBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class FullScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFullScreenBinding binding;
    private ImageView ivFullImage;
    private ImageView ivEdit;
    private ImageView ivBack;

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 108;
    private static final int SELECT_GALLERY_PIC = 101;
    private static final int SELECT_CAMERA_PIC = 99;
    private Activity context;
    String photoUrl;
    int position;
    int mediaId;
    int sentPosition;

    private File filepath, cropfilepath;
    private static final int PIC_CROP = 152;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_screen);
        context = FullScreenActivity.this;
        bindViews();
        getIntents();
        setListner();
    }

    private void getIntents() {
        photoUrl = getIntent().getStringExtra(GlobalData.PHOTO_URL);
        position = getIntent().getIntExtra(GlobalData.PHOTO_POSITION, 0);
        mediaId = Integer.parseInt(getIntent().getStringExtra((GlobalData.MEDIA_ID)));
        LogM.LogE("photoUrl ---" + photoUrl);
        Glide.with(this).load(Uri.parse(photoUrl)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivFullImage);
//        ivFullImage.setImageURI(Uri.parse(photoUrl));
    }

    private void bindViews() {
        ivFullImage = binding.ivFullImage;
        ivEdit = binding.ivEdit;
        ivBack = binding.ivBack;
    }

    private void setListner() {
        ivEdit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEdit:
//                showPicProfileDialog();
                showPopup(view);
                break;
            case R.id.ivBack:
//                showPicProfileDialog();
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.fullscreen_profile_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuEdit:
                        checkPermission();
//                        showPicProfileDialog();
                        break;
                    case R.id.menuMakeProfilePic:
                        Intent intent = new Intent();
                        intent.putExtra(GlobalData.FROM, GlobalData.MAKE_PROFILE_PIC);
                        intent.putExtra(GlobalData.PHOTO_URL, photoUrl);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void showPicProfileDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(FullScreenActivity.this)
                    .setTitle(getResources().getString(R.string.app_name)).setMessage("Choose profile pic");

            builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent,
//                            "Select Picture"), SELECT_GALLERY_PIC);
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    }
                    startActivityForResult(intent, SELECT_GALLERY_PIC);
                }
            });

            builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    openCamera();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
        }
    }

    private void checkPermission() {
        try {
            PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}).checkPermissions(context, new PermissionsHelper.OnPermissionResult() {

                @Override
                public void onGranted() {
//                    showPicProfileDialog();
                    showPicProfileDialog();
                }

                @Override
                public void notGranted() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, SELECT_CAMERA_PIC);
    }

    private Uri mImageUri;
    private Bitmap bitmap = null;
    private String strProPicBase64 = "";
    private String strNineImageBase64 = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FullScreenActivity.this.RESULT_OK) {
            // user chose an image from the gallery
            // loadAsync(data.getData());
            if (requestCode == SELECT_GALLERY_PIC) {
//                mImageUri = data.getData();
//                try {
//                    bitmap = getBitmapFromUri(mImageUri);
//                    strProPicBase64 = encodeTobase64(bitmap);
//                    callAddExtraPhoto(position);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                mImageUri = data.getData();
                try {
                    bitmap = getBitmapFromUri(mImageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
            } else if (requestCode == SELECT_CAMERA_PIC) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                mImageUri = getImageUri(photo);
                performCrop(mImageUri);
            } else if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
                try {
                    if (data != null) {
                        Bitmap thePic = getBitmapFromFile(cropfilepath);
                        if (thePic != null) {
//                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                            imagePipeline.evictFromCache(getImageUri(thePic));
//                            imagePipeline.clearCaches();
                            strProPicBase64 = encodeTobase64(thePic);
                            callAddExtraPhoto(position);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
        } catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
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

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        LogM.LogE(imageEncoded);
        return imageEncoded;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void callAddExtraPhoto(int position) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_USERID, SessionManager.getUserId(context));
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_BASE64, strProPicBase64);
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_PHOTO_POSITION, position);
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_MEDIA_ID, mediaId);

            LogM.LogE("Request : AddExtraPhoto : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADD_EXTRA_PHOTO.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : AddExtraPhoto : " + jsonObject.toString());

                        AddExtraPhoto addExtraPhoto = new Gson().fromJson(String.valueOf(jsonObject), AddExtraPhoto.class);
                        photoUrl = addExtraPhoto.getImageArray().get(0).getImage();
                        sentPosition = addExtraPhoto.getPhotoPosition();

//                        Intent intent = new Intent();
//                        intent.putExtra(GlobalData.FROM, GlobalData.MAKE_PROFILE_PIC);
//                        intent.putExtra(GlobalData.PHOTO_URL, photoUrl);
//                        setResult(RESULT_OK, intent);
                        LogM.LogE("edited URL=============>"+photoUrl);
                        Intent intent = new Intent();
                        intent.putExtra(GlobalData.FROM, GlobalData.PHOTO_POSITION);
                        intent.putExtra(GlobalData.PHOTO_POSITION, sentPosition);
                        intent.putExtra(GlobalData.PHOTO_URL, photoUrl);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
