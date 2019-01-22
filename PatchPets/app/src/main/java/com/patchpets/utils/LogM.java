package com.patchpets.utils;

import android.util.Log;

public class LogM {
    public static void e(String message) {
        if (message != null) {
            Log.e("->", message);
        }
    }

    public static void v(String message) {
        if (message != null) {
            Log.v("->", message);
        }
    }

    public static void i(String message) {
        if (message != null) {
            Log.i("->", message);
        }
    }

    public static void d(String message) {
        if (message != null) {
            Log.d("->", message);
        }
    }
}
