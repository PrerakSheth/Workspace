package com.patchpets.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.patchpets.R;
import com.patchpets.model.User;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.Constants;
import com.patchpets.utils.LogM;

import static com.patchpets.utils.Constants.SPLASH_SCREEN_DELAY;

public class SplashActivity extends AppCompatActivity {

    private String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                LogM.e(newToken);
                SessionManager.getInstance().setDeviceToken(SplashActivity.this, newToken);
            }
        });

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, Constants.PERMISSION_ALL);
            return;
        }

        startSplash();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == Constants.PERMISSION_ALL) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_permission_denied), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                startSplash();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SessionManager.getInstance().getSession(SplashActivity.this)) {
                    User user = SessionManager.getInstance().getUser(SplashActivity.this);
                    if (user.getUserType() == Constants.DOG_OWNER) {
                        if (user.getFirstName().length() > 0 && user.getLastName().length() > 0) {
                            startActivitys(HomeActivity.class);
                        } else {
                            startActivitys(SetupProfileActivity.class);
                        }
                    } else if (user.getUserType() == Constants.BUSINESS_USER) {
                        if (user.getBusinessName().length() > 0) {
                            startActivitys(HomeActivity.class);
                        } else {
                            startActivitys(SetupBusinessProfileActivity.class);
                        }
                    } else {
                        startActivitys(HomeActivity.class);
                    }
                } else {
                    startActivitys(SignInActivity.class);
                }
            }
        }, SPLASH_SCREEN_DELAY);
    }

    private void startActivitys(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }

    // check runtime permissions
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
