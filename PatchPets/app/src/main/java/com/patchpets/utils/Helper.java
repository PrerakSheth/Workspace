package com.patchpets.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Helper {

    private static boolean isInternetAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static boolean isCheckInternet(Context context) {
        if (isInternetAvailable(context)) {
            return true;
        } else {
            AlertDialogUtility.showInternetAlert(context);
            return false;
        }
    }
}
