package com.konkr.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.konkr.Models.SetUpProfile;
import com.konkr.Models.SignIn;
import com.konkr.Models.SignUp;
import com.konkr.Utils.GlobalData;
import com.konkr.Webservices.WebField;

public class SessionManager {
    private static final String PREF_NAME = "KonkR";

    public static void saveUserDetails(Context context, SignUp vo) {
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(GlobalData.USER_ID, vo.getUserDetails().getUserId());
            editor.putString(GlobalData.ACCESS_TOKEN, vo.getUserDetails().getAccessToken());
            editor.putString(GlobalData.PARAM_EMAIL_ID, vo.getUserDetails().getEmail());
//            editor.putString(GlobalData.IS_PROFILE_SETUP, vo.getUserDetails().getIsProfileSetup());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSignInDetails(Context context, SignIn vo) {
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(GlobalData.USER_ID, vo.getUserDetails().getUserId());
            editor.putString(GlobalData.ACCESS_TOKEN, vo.getUserDetails().getAccessToken());
            editor.putString(GlobalData.FIRSTNAME, vo.getUserDetails().getFirstName());
            editor.putString(GlobalData.LASTNAME, vo.getUserDetails().getLastName());
            editor.putString(GlobalData.PROFILEPIC, vo.getUserDetails().getProfilePic());
            editor.putString(GlobalData.QRCODE, vo.getUserDetails().getQrcodeURL());
            editor.putInt(GlobalData.LOGIN_TYPE, vo.getUserDetails().getLoginType());
//            editor.putString(GlobalData.IS_PROFILE_SETUP, vo.getUserDetails().getIsProfileSetup());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSetUpProfile(Context context, SetUpProfile vo) {
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(GlobalData.FIRSTNAME, vo.getUserData().getFirstName());
            editor.putString(GlobalData.LASTNAME, vo.getUserData().getLastName());
            editor.putString(GlobalData.PROFILEPIC, vo.getUserData().getProfilePic());
            editor.putString(GlobalData.QRCODE, vo.getUserData().getQrcodeURL());
            editor.putString(GlobalData.IS_PROFILE_SETUP, vo.getUserData().getIsProfileSetup());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int getUserId(Context context) {
        int userId = 0;
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            userId = preferences.getInt(GlobalData.USER_ID, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static int getLoginType(Context context) {
        int loginType = 0;
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            loginType = preferences.getInt(GlobalData.LOGIN_TYPE, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginType;
    }

    public static String getAccessToken(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.ACCESS_TOKEN, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEmailId(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.PARAM_EMAIL_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getFirstName(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.FIRSTNAME, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getLastName(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.LASTNAME, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getProfilePic(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.PROFILEPIC, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getQRCode(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.QRCODE, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIsProfileSetup(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(GlobalData.IS_PROFILE_SETUP, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setIsProfileSetup(Context context, String isProfileSetUp) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(GlobalData.IS_PROFILE_SETUP, isProfileSetUp);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void saveFirstName(Context context, String firstName) {
//        try {
//            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//            Editor editor;
//            editor = prefDeviceToken.edit();
//            editor.putString(GlobalData.FIRSTNAME, firstName);
//            editor.apply();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void saveLastName(Context context, String lastName) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(GlobalData.LASTNAME, lastName);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveProfilePic(Context context, String profilePic) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(GlobalData.PROFILEPIC, profilePic);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLogin(Context context, boolean value) {
        try {
            SharedPreferences prefSignupData = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = prefSignupData.edit();
            editor.putBoolean(GlobalData.IS_LOGIN, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getLogin(Context context) {
        boolean blAutoSave = false;
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            blAutoSave = pref.getBoolean(GlobalData.IS_LOGIN, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blAutoSave;
    }

    public static void isSpotifySkip(Context context, boolean value) {
        try {
            SharedPreferences prefSignupData = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = prefSignupData.edit();
            editor.putBoolean(GlobalData.IS_SPOTIFY, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getSpotifySkip(Context context) {
        boolean blAutoSave = false;
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            blAutoSave = pref.getBoolean(GlobalData.IS_SPOTIFY, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blAutoSave;
    }

    public static void setSubscribed(Context context, int isSubscribed) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor = prefDeviceToken.edit();
            editor.putInt(WebField.IS_SUBSCRIBED, isSubscribed);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getSubscribed(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getInt(WebField.IS_SUBSCRIBED, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setDeviceToken(Context context, String strDeviceToken) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(WebField.DEVICE_TOKEN, strDeviceToken);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDeviceToken(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(WebField.DEVICE_TOKEN, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    //<---------------

    public static void setSpotifyToken(Context context, String strDeviceToken) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(WebField.SPOTIFY_TOKEN, strDeviceToken);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSpotifyToken(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(WebField.SPOTIFY_TOKEN, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setSpotifyUserId(Context context, String spotifyUserId) {
        try {
            SharedPreferences prefDeviceToken = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor;
            editor = prefDeviceToken.edit();
            editor.putString(WebField.SPOTIFY_ID, spotifyUserId);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getSpotifyUserId(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return pref.getString(WebField.SPOTIFY_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void clearSpotifyUserId(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            pref.edit().remove(WebField.SPOTIFY_ID).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearSpotifyToken(Context context) {
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
             pref.edit().remove(WebField.SPOTIFY_TOKEN).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clearCredential(Context context) {
        try {
            Editor editor = context.getSharedPreferences(PREF_NAME, 0).edit();
            editor.clear();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}