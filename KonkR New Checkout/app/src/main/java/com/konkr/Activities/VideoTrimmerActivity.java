package com.konkr.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.konkr.R;
import com.konkr.Utils.GlobalData;

import java.io.File;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class VideoTrimmerActivity extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {
    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);

//        GlobalMethods.addAnalytics(getApplication(), getClass().getSimpleName());
        Intent extraIntent = getIntent();

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + GlobalData.APP_NAME + "/Video");
        if (!myDir.exists())
            myDir.mkdirs();

        String path = "";

        if (extraIntent != null) {
            path = extraIntent.getStringExtra(GlobalData.EXTRA_VIDEO_PATH);
        }

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.trimming_progress));

        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(30);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setOnK4LVideoListener(this);
            mVideoTrimmer.setDestinationPath(myDir.getAbsolutePath() + "/");
            mVideoTrimmer.setVideoURI(Uri.parse(path));
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(VideoTrimmerActivity.this,"Cropped Success", Toast.LENGTH_SHORT).show();
            }
        });

        Intent returnIntent = new Intent(VideoTrimmerActivity.this, MiTrainingActivity.class);
        returnIntent.putExtra("result", uri.toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setDataAndType(uri, "video/mp4");
//        startActivity(intent);
//        finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(VideoTrimmerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(VideoTrimmerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
