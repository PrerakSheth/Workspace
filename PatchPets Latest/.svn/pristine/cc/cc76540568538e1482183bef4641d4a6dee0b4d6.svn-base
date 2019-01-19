package com.patchpets.Activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.patchpets.R;
import com.patchpets.databinding.ActivitySocialSharingBinding;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.LogM;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

public class SocialSharingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySocialSharingBinding binding;
    private HeaderBar headerBar;
    private TextView tvViaSms;
    private TextView tvShareToFacebook;
    private TextView tvShareToInstagram;

    private LoginButton facebookLogin;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_sharing);
        callbackManager = CallbackManager.Factory.create();

        bindViews();
        setHeaderBar();
        setListener();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        facebookLogin = binding.facebookLogin;
        tvViaSms = binding.tvViaSms;
        tvShareToFacebook = binding.tvShareToFacebook;
        tvShareToInstagram = binding.tvShareToInstagram;
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.social_sharing);
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        tvViaSms.setOnClickListener(this);
        tvShareToFacebook.setOnClickListener(this);
        tvShareToInstagram.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;

            case R.id.tvShareToFacebook:
                if (isFacebookLoggedIn()) {
                    facebookLogin.clearPermissions();
                    facebookLogin.setPublishPermissions(Arrays.asList("publish_actions"));
                    showShareDialog();
                } else {
                    facebookLogin();
                }
                break;

            case R.id.tvViaSms:
                Intent signInIntent = new Intent(SocialSharingActivity.this, ViaSMSActivity.class);
                startActivity(signInIntent);
                break;
        }
    }

    private boolean isFacebookLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException error) {
            error.printStackTrace();
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            onBackPressed();
        }
    };

    private void showShareDialog() {
        try {
            ShareDialog shareDialog = new ShareDialog(SocialSharingActivity.this);
            shareDialog.registerCallback(callbackManager, shareCallback);
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                  .setQuote("This may be used to replace setTitle and setDescription.")
                    .setContentUrl(Uri.parse(getResources().getString(R.string.facebook_share_link)))
                    .build();
            shareDialog.show(linkContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookLogin() {
        try {
            facebookLogin.setReadPermissions(Arrays.asList("email"));
            printKeyHash();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    facebookLogin.clearPermissions();
                    facebookLogin.setPublishPermissions(Arrays.asList("publish_actions"));
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            showShareDialog();
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,first_name,last_name,name,email,gender,birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    onBackPressed();
                }

                @Override
                public void onError(FacebookException exception) {
                    exception.printStackTrace();
                    onBackPressed();
                }
            });
            facebookLogin.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printKeyHash() {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                final PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
                final Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
                final MessageDigest md = MessageDigest.getInstance("SHA");
                for (Signature signature : signatures) {
                    md.update(signature.toByteArray());
                    final String signatureBase64 = new String(Base64.encode(md.digest(), Base64.DEFAULT));
                    LogM.e("KeyHash : " + signatureBase64);
                }
            } else {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (android.content.pm.Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    LogM.e("KeyHash : " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
