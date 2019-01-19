package com.patchpets.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.patchpets.model.User;

public class SessionManager {

    private static final String APP_PREF = "com.patchpets";
    private static final String DEVICE_TOKEN_PREF = "device_token_pref";
    private static final String REMEMBER_PREF = "remember_pref";

    private static SessionManager mSession;

    public static SessionManager getInstance() {
        if (mSession == null) {
            mSession = new SessionManager();
        }
        return mSession;
    }

    public void clearCredential(Context context) {
        try {
            Editor editor = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDeviceToken(Context context, String strDeviceToken) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(DEVICE_TOKEN_PREF, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(SessionKeys.DEVICE_TOKEN, strDeviceToken);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDeviceToken(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(DEVICE_TOKEN_PREF, Context.MODE_PRIVATE);
            return pref.getString(SessionKeys.DEVICE_TOKEN, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setSession(Context context, boolean session) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putBoolean(SessionKeys.SESSION, session);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getSession(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
            return pref.getBoolean(SessionKeys.SESSION, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveUser(Context context, User user) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(REMEMBER_PREF, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putInt(SessionKeys.USER_ID, user.getUserId());
            editor.putString(SessionKeys.ACCESS_TOKEN, user.getAccessToken());
            editor.putString(SessionKeys.FIRST_NAME, user.getFirstName());
            editor.putString(SessionKeys.LAST_NAME, user.getLastName());
            editor.putString(SessionKeys.EMAIL, user.getEmail());
            editor.putInt(SessionKeys.LOGIN_TYPE, user.getLoginType());
            editor.putInt(SessionKeys.USER_TYPE, user.getUserType());
            editor.putString(SessionKeys.QR_CODE_URL, user.getQrCodeUrl());
            editor.putString(SessionKeys.PROFILE_PIC, user.getProfilePic());
            editor.putBoolean(SessionKeys.REMEMBER_ME, user.isRememberMe());
            editor.putInt(SessionKeys.IS_PROFILE_SETUP, user.getIsProfileSetup());
            editor.putString(SessionKeys.BUSINESS_NAME, user.getBusinessName());
            editor.putString(SessionKeys.CONTACT_NUMBER, user.getContactNo());
            editor.putString(SessionKeys.ABOUT_BUSINESS, user.getAboutBusiness());
            editor.putString(SessionKeys.SERVICES, user.getServices());
            editor.putString(SessionKeys.INSTA_LINK, user.getInstaLink());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(Context context) {
        try {
            User user = new User();
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(REMEMBER_PREF, Context.MODE_PRIVATE);
            Editor editor;
            editor = pref.edit();
            user.setUserId(pref.getInt(SessionKeys.USER_ID, 0));
            user.setAccessToken(pref.getString(SessionKeys.ACCESS_TOKEN, ""));
            user.setFirstName(pref.getString(SessionKeys.FIRST_NAME, ""));
            user.setLastName(pref.getString(SessionKeys.LAST_NAME, ""));
            user.setEmail(pref.getString(SessionKeys.EMAIL, ""));
            user.setLoginType(pref.getInt(SessionKeys.LOGIN_TYPE, 0));
            user.setUserType(pref.getInt(SessionKeys.USER_TYPE, 2)); //1=Dog Owner,  0=Business User
            user.setQrCodeUrl(pref.getString(SessionKeys.QR_CODE_URL, ""));
            user.setProfilePic(pref.getString(SessionKeys.PROFILE_PIC, ""));
            user.setRememberMe(pref.getBoolean(SessionKeys.REMEMBER_ME, false));
            user.setIsProfileSetup(pref.getInt(SessionKeys.IS_PROFILE_SETUP, 0));
            user.setBusinessName(pref.getString(SessionKeys.BUSINESS_NAME, ""));
            user.setContactNo(pref.getString(SessionKeys.CONTACT_NUMBER, ""));
            user.setAboutBusiness(pref.getString(SessionKeys.ABOUT_BUSINESS, ""));
            user.setServices(pref.getString(SessionKeys.SERVICES, ""));
            user.setInstaLink(pref.getString(SessionKeys.INSTA_LINK, ""));
            editor.apply();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}