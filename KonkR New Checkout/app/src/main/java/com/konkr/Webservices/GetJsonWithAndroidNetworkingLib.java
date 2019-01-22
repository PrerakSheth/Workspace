package com.konkr.Webservices;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.konkr.Activities.SignInActivity;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;

import org.json.JSONObject;


public class GetJsonWithAndroidNetworkingLib extends AsyncTask<String, JSONObject, JSONObject> {
    private OnUpdateListener onUpdateListener;
    private Activity context;
    private JSONObject jsonObject;
    private int intDialogShow = 0;
    private ProgressDialog progressDialog;
    private String url;

    public GetJsonWithAndroidNetworkingLib(Activity context, JSONObject jsonObject, String url,
                                           int intDialogShow, OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
        this.jsonObject = jsonObject;
        this.intDialogShow = intDialogShow;
        this.url = url;
        this.context = context;
        LogM.LogV("URL :: " + url);
        LogM.LogV("REQUEST :: " + jsonObject);
    }

    @Override
    protected void onPreExecute() {
        if (!ConnectivityDetector.isConnectingToInternet(context)) {
            AlertDialogUtility.showInternetAlert(context);
            return;
        }
        if (intDialogShow == 1) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.please_wait));
            progressDialog.setTitle("");
            progressDialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... param) {
        try {
            JSONObject jobjTemp = new JSONObject(jsonObject.toString().replace("\\n", "\n"));
            AndroidNetworking.post(url)
                    .addJSONObjectBody(jobjTemp)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                LogM.LogV("RESPONSE :: " + response);

                                if (response != null) {
                                    if (response.getString(WebField.STATUS).equals("1")) {
                                        onUpdateListener.onUpdateComplete(response, true);
                                    } else if (response.getString(WebField.STATUS).equals("0")) {
                                        onUpdateListener.onUpdateComplete(response, false);
                                        if (response.getString(WebField.MESSAGE).equalsIgnoreCase(GlobalData.YOUR_ACCESS_TOKEN_IS_EXPIRE)) {
                                            if (response.getString(WebField.TOKENEXPIRED).equalsIgnoreCase("1")) {
                                                SessionManager.clearCredential(context);
                                                FragmentManager fm = context.getFragmentManager(); // or 'getSupportFragmentManager();'
                                                int count = fm.getBackStackEntryCount();
                                                for (int i = 0; i < count; ++i) {
                                                    fm.popBackStack();
                                                }
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = new Intent(context, SignInActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        context.startActivity(intent);
                                                        context.finish();
                                                    }
                                                }, GlobalData.DELAY_TIME_LOGOUT);
                                            }
                                        }
                                        if (response.getString(WebField.MESSAGE).equalsIgnoreCase(GlobalData.USER_BLOCK_ALERT)) {
                                            SessionManager.clearCredential(context);
                                            FragmentManager fm = context.getFragmentManager(); // or 'getSupportFragmentManager();'
                                            int count = fm.getBackStackEntryCount();
                                            for (int i = 0; i < count; ++i) {
                                                fm.popBackStack();
                                            }
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(context, SignInActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                    context.finish();
                                                }
                                            }, GlobalData.DELAY_TIME_LOGOUT);

                                        }
                                    } else if (response.getString(WebField.STATUS).equals("2")) {
                                        onUpdateListener.onUpdateComplete(response, true);
                                    } else {
                                        onUpdateListener.onUpdateComplete(response, false);

                                        // If user is block by admin panel.
                                        if (response.getString(WebField.MESSAGE).equalsIgnoreCase(GlobalData.USER_BLOCK_ALERT)) {
                                            // If use is block and try to login then not pass intent to login otherwise pass intent to SignUp screen.
//                                            if (!url.equalsIgnoreCase(WebField.URL_USER_LOGIN)) {
//                                                SessionManager.clearCredential(context);
//                                                Intent intent = new Intent(context, LoginActivity.class);
//                                                context.startActivity(intent);
//                                            }
                                        } else if (response.getString(WebField.MESSAGE).equalsIgnoreCase(GlobalData.USER_NOT_EXIST)) { // If user is delete from admin panel
//                                            SessionManager.clearCredential(context);
//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (intDialogShow == 1) {
                                    progressDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonResult) {
        super.onPostExecute(jsonResult);
    }


}