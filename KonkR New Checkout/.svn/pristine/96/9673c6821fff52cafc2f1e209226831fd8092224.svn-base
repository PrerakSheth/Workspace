package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.konkr.R;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.ActivitySocialSharingBinding;

public class SocialSharingActivity extends Activity implements View.OnClickListener {
    private Headerbar headerBar;
    private MyTextView tvViaSms;
    private MyTextView tvShareToFacebook;

    private ActivitySocialSharingBinding binding;
    CallbackManager callbackManager;
//    ShareDialog shareDialog;

    private static final String TAG = "ShareOnFacebook";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_sharing);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        bindViews();
        setListener();
        setHeaderBar();

//        FacebookCallback<Sharer.Result> () {
//            @Override
//            public void onSuccess(Sharer.Result result) {}
//
//            @Override
//            public void onCancel() {}
//
//            @Override
//            public void onError(FacebookException error) {}
//        });

    }

    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            tvViaSms.setOnClickListener(this);
            tvShareToFacebook.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            tvViaSms = binding.tvViaSms;
            tvShareToFacebook = binding.tvShareToFacebook;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.title_social_sharing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnLeftOne:
                    finish();
                    break;

                case R.id.tvViaSms:
                    Intent intentViaSms = new Intent(SocialSharingActivity.this, ContactListActivity.class);
                    startActivity(intentViaSms);
                    break;

                case R.id.tvShareToFacebook:
//                    shareDialog = new ShareDialog(this);
//
//                    if (ShareDialog.canShow(ShareLinkContent.class)) {
//                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                                .setContentUrl(Uri.parse("https://play.google.com/store?hl=en"))
//                                .build();
//                        shareDialog.show(linkContent);
//                        MessageDialog.show(SocialSharingActivity.this, linkContent);
//                    }

//                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(_activity)
//                            .setApplicationName("TestApp")
//                            .build();

                    ShareDialog shareDialog = new ShareDialog(this);
                    callbackManager = CallbackManager.Factory.create();
                    shareDialog.registerCallback(callbackManager, new

                            FacebookCallback<Sharer.Result>() {
                                @Override
                                public void onSuccess(Sharer.Result result) {
                                    Log.d(TAG, "onSuccess: ");
                                    Toast.makeText(SocialSharingActivity.this, "You have shared successfully.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel() {
                                    Log.d(TAG, "onCancel: ");
//                                    Toast.makeText(SocialSharingActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(FacebookException error) {
                                    Log.d(TAG, "onError: ");
//                                    Toast.makeText(SocialSharingActivity.this, "onError" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("KonkR")
                                .setContentDescription("This my description")
                                .setContentUrl(Uri.parse("https://play.google.com/store?hl=en"))
                                .build();

                        shareDialog.show(linkContent);
                    }

//                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(activity)
//                            .setLink("https://www.google.com")
//                            .setApplicationName("name of app")
//                            .build();
//                    uiHelper.trackPendingDialogCall(shareDialog.present());
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
