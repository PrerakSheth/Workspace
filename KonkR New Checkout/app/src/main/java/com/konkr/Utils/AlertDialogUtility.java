package com.konkr.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.konkr.R;

public class AlertDialogUtility {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnakeBar(String message, View view, Context context) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        snackbar.show();
    }

    public static void showError(Context context, View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.error_bg));
        snackbar.show();
    }

    public static void showAlert(Context context, String msg) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(context.getString(R.string.app_name)).setMessage(msg).setCancelable(false).setNeutralButton("OK", null).show();
    }

    public static void showInternetAlert(Context context) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(GlobalData.STR_INTERNET_ALERT_TITLE).
                setMessage(GlobalData.STR_INETRNET_ALERT_MESSAGE)
                .setCancelable(false).setNeutralButton("OK", null).show();
    }

    public static void CustomAlert(Context context, String title, String message, String Positive_text, String Negative_text, DialogInterface.OnClickListener PositiveListener, DialogInterface.OnClickListener NegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(Positive_text, PositiveListener).setNegativeButton(Negative_text, NegativeListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showAlertWithOnlyYesOption(Context context, String msg, DialogInterface.OnClickListener onYesClick) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(context.getString(R.string.app_name))
                .setMessage(msg).setCancelable(false)
                .setPositiveButton("OK", onYesClick).show();
    }

    //
    public static void showConfirmAlert(Context context, String msg, DialogInterface.OnClickListener onYesClick) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(context.getString(R.string.app_name))
                .setMessage(msg).setCancelable(false).setNegativeButton("NO", null)
                .setPositiveButton("YES", onYesClick).show();
    }

    //
    public static void showLogoutAlert(Context context, String msg, DialogInterface.OnClickListener onYesClick) {
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name)).setMessage(msg)
                .setCancelable(false).setNegativeButton("Cancel", null)
                .setPositiveButton("Log out", onYesClick).show();
    }
}
