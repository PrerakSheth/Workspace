package com.konkr.Utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.konkr.R;

/**
 * Created by Android on 28-Feb-18.
 */

public class MyProgressDialog {
    private static ProgressDialog progressBar;

    public static void showProgressDialog(Context context) {
        progressBar = new ProgressDialog(context);
        progressBar.setMessage(context.getString(R.string.please_wait));
        progressBar.setCancelable(false);
        progressBar.show();
    }

    public static void hideProgressDialog() {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }

    }
}
