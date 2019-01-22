package com.konkr.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.ActivityFullScreenQrcodeBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FullScreenQRCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private SimpleDraweeView ivQRCode;
    ActivityFullScreenQrcodeBinding binding;
    private Context context;
    private MyTextView tvSave;
    private Headerbar headerBar;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 108;
    private boolean permission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_screen_qrcode);
        context = FullScreenQRCodeActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        bindViews();
        setHeaderBar();
        setData();
        headerBar.tvRight.setOnClickListener(this);
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvRight.setVisibility(View.VISIBLE);
            headerBar.tvRight.setText(R.string.save);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.your_qr_code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        ivQRCode.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        ivQRCode.setImageURI(Uri.parse(SessionManager.getQRCode(context)));
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ivQRCode = binding.ivQRCode;
        headerBar.ibtnLeftOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ibtnLeftOne:
                finish();
                break;
            case R.id.tvRight:
                if (permission) {
                    Bitmap bitmap = getBitmapFromURL(SessionManager.getQRCode(context), context);
                    try {
                        SaveImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    checkPermission();
                }

//                Bitmap bitmap = getBitmapFromURL(SessionManager.getQRCode(context), context);
//                try {
//                    SaveImage(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    private void checkPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FullScreenQRCodeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FullScreenQRCodeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)

                {
                    ActivityCompat.requestPermissions(FullScreenQRCodeActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CAMERA);


                } else {
//                    showPicProfileDialog();
                    Bitmap bitmap = getBitmapFromURL(SessionManager.getQRCode(context), context);
                    try {
                        SaveImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                permission = false;
            } else {
                permission = true;
//                showPicProfileDialog();
                Bitmap bitmap = getBitmapFromURL(SessionManager.getQRCode(context), context);
                try {
                    SaveImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Bitmap getBitmapFromURL(String src, Context context) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Drawable drawableget = context.getResources().getDrawable(R.drawable.ic_launcher_background);
            Bitmap bitmap = ((BitmapDrawable) drawableget).getBitmap();
            e.printStackTrace();
            return bitmap;
        }
    } // Author: silentnuke

    private void SaveImage(Bitmap bitmap) throws IOException {

        // Assume block needs to be inside a Try/Catch block.
        String path = Environment.getExternalStorageDirectory().toString();
        Integer counter = 0;
        File file = new File(path, "KonkR.png"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
        FileOutputStream fOut = new FileOutputStream(file);

        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        fOut.flush(); // Not really required
        fOut.close(); // do not forget to close the stream

        MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
//        Toast.makeText(context, "Your QR code is saved.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
