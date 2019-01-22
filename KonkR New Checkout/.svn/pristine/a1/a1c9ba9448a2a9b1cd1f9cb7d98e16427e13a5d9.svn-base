package com.konkr.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.konkr.R;
import com.konkr.Utils.GlobalData;

import java.io.File;
import java.io.IOException;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

public class CustomCameraActivity extends AppCompatActivity {
    private Camera myCamera;
    private MyCameraSurfaceView myCameraSurfaceView;
    private MediaRecorder mediaRecorder;
    private ProgressBar progress;
    private AppCompatTextView txtCurrTime;
    public static int orientation;
    private AppCompatImageView myButton, imgCamera;
    private File myDir;
    FrameLayout myCameraPreview;
    private boolean isRecording = false;
    private int size = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);

        if (getIntent().getExtras() != null)
            size = getIntent().getIntExtra(GlobalData.SIZE, 0);

        myButton = (AppCompatImageView) findViewById(R.id.imgPlayPause);
        imgCamera = (AppCompatImageView) findViewById(R.id.imgCamera);
        txtCurrTime = (AppCompatTextView) findViewById(R.id.txtCurrTime);
        myCameraPreview = (FrameLayout) findViewById(R.id.videoview);

        progress = (ProgressBar) findViewById(R.id.progress);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecording) {
                    myButton.setImageResource(R.drawable.ic_stop);
                    isRecording = true;
                    record();
                } else {
                    isRecording = false;
                    stopRecord();
                }
            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecording) {
                    changeCamera();
                }
            }
        });
    }

    private void record() {
        try {
            if (!prepareMediaRecorder()) {
                Toast.makeText(CustomCameraActivity.this, "Failed", Toast.LENGTH_LONG).show();
                finish();
            }
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Thread t = new Thread() {
            int val = 0;

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        if (!isRecording) {
                            Intent returnIntent = new Intent(CustomCameraActivity.this, MiTrainingActivity.class);
                            returnIntent.putExtra("result", myDir.getAbsolutePath() + "/Video" + size + ".mp4");
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }

                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                val++;
                                if (val >= 10) {
                                    txtCurrTime.setText("00:" + val);
                                    if (val >= 30) {
                                        Intent returnIntent = new Intent(CustomCameraActivity.this, MiTrainingActivity.class);
                                        returnIntent.putExtra("result", myDir.getAbsolutePath() + "/Video" + size + ".mp4");
                                        setResult(Activity.RESULT_OK, returnIntent);
                                        finish();
                                    }
                                } else {
                                    txtCurrTime.setText("00:0" + val);
                                }
                                progress.setProgress(val);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    private void stopRecord() {
        try {
            mediaRecorder.stop();
            releaseMediaRecorder();
            Intent returnIntent = new Intent(CustomCameraActivity.this, MiTrainingActivity.class);
            returnIntent.putExtra("result", myDir.getAbsolutePath() + "/Video" + size + ".mp4");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkPermission() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 108);
            } else {
                start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            switch (requestCode) {
                case 108: {
                    if (grantResults.length > 0 &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                        start();
                    } else {
                        onBackPressed();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            myDir = new File(root + "/" + GlobalData.APP_NAME + "/Video/CompressedVideo");

            if (!myDir.exists())
                myDir.mkdirs();

            myCamera = getCameraInstance();
            if (myCamera == null) {
                Toast.makeText(CustomCameraActivity.this, "Fail to get Camera", Toast.LENGTH_LONG).show();
            }
            myCamera.setDisplayOrientation(90);
            myCameraSurfaceView = new MyCameraSurfaceView(this, myCamera, CustomCameraActivity.this);
            myCameraPreview.removeAllViews();
            myCameraPreview.addView(myCameraSurfaceView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeCamera() {
        try {
            if (GlobalData.CAMERA_ID == 0) {
                GlobalData.CAMERA_ID = 1;
            } else {
                GlobalData.CAMERA_ID = 0;
            }
            releaseCamera();
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(GlobalData.CAMERA_ID); // attempt to get a Camera instance
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }


    private boolean prepareMediaRecorder() {
        myCamera = getCameraInstance();
        setCameraDisplayOrientation(this, 0, myCamera);
        mediaRecorder = new MediaRecorder();
        myCamera.unlock();
        mediaRecorder.setCamera(myCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));
        mediaRecorder.setOutputFile(myDir.getAbsolutePath() + "/Video" + size + ".mp4");
        mediaRecorder.setMaxDuration(30000); // Set max duration 60 sec.
//        mediaRecorder.setMaxFileSize(500000000); // Set max file size 50Mb
        mediaRecorder.setPreviewDisplay(myCameraSurfaceView.getHolder().getSurface());
        if (GlobalData.CAMERA_ID == 0) {
            mediaRecorder.setOrientationHint(CustomCameraActivity.orientation);
        } else {
            mediaRecorder.setOrientationHint(270);
        }
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();
        releaseCamera();
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            myCamera.lock();
        }
    }

    private void releaseCamera() {
        if (myCamera != null) {
            myCamera.release();
            myCamera = null;
        }
    }

    public class MyCameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;
        private Activity mActivity;

        public MyCameraSurfaceView(Context context, Camera camera, Activity activity) {
            super(context);
            mCamera = camera;
            mActivity = activity;
            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            try {
                setCameraDisplayOrientation(mActivity, 0, mCamera);
                previewCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void previewCamera() {
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        CustomCameraActivity.orientation = result;
        camera.setDisplayOrientation(result);
    }
}