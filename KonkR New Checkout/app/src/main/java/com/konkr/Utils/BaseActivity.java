package com.konkr.Utils;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Android on 7/25/2018.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions,
                                           final int[] grantResults) {
        if (requestCode == PermissionsHelper.REQUEST_CODE_ASK_PERMISSIONS) {
            PermissionsHelper.setGrantResult(grantResults);
        }
    }
}
