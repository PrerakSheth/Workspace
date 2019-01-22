package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.konkr.R;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.databinding.ActivitySocialSharingViaSmsBinding;

import java.util.ArrayList;

public class SocialSharingViaSmsActivity extends Activity implements View.OnClickListener {
    private Headerbar headerBar;
    private MyEditText etWriteYourMessage;
    private TextView tvFiveHundredCharacter;
    private TextView tvSend;
    private String strMessage;

    private ActivitySocialSharingViaSmsBinding binding;

    ArrayList<String> alNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_sharing_via_sms);

        bindViews();
        setHeaderBar();
        setListener();
        getIntents();

        etWriteYourMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvFiveHundredCharacter.setText(500 - s.toString().length() + "");

            }
        });
    }

    private void getIntents() {
        LogM.LogE("getIntents" + getIntent().getExtras().getStringArrayList(GlobalData.CONTACT_NAME));
        if (getIntent().getExtras().getStringArrayList(GlobalData.CONTACT_NAME) != null) {
            alNames = getIntent().getExtras().getStringArrayList(GlobalData.CONTACT_NAME);
        }
        LogM.LogE("alNames" + alNames.size());
    }


    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            tvSend.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            etWriteYourMessage = binding.etWriteYourMessage;
            tvFiveHundredCharacter = binding.tvFiveHundredCharacter;
            tvSend = binding.tvSend;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.title_message);
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

                case R.id.tvSend:
                    setValidation();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValidation() {
        try {
            strMessage = etWriteYourMessage.getText().toString().trim();

            if (strMessage.length() == 0) {
                etWriteYourMessage.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.er_write_your_message), etWriteYourMessage, this);
            } else {
//                AlertDialogUtility.showAlert(SocialSharingViaSmsActivity.this, "Progress");
                LogM.LogE("NAMES" + alNames.size());
                StringBuffer sb = new StringBuffer();
                for (int i=0; i<alNames.size(); i++){
                    sb.append(alNames.get(i) + ", ");
                }
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sb.toString()));
                smsIntent.putExtra("sms_body", strMessage);
                startActivity(smsIntent);

//                sendSMS();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void sendSMS() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
//        {
//            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19
//
//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
//            sendIntent.setType("text/plain");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");
//
//            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
//            // any app that support this intent.
//            {
//                sendIntent.setPackage(defaultSmsPackageName);
//            }
//            startActivity(sendIntent);
//
//        }
//        else // For early versions, do what worked for you before.
//        {
//            LogM.LogE("NAMES" + alNames.size());
//            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + alNames));
//            smsIntent.putExtra("sms_body", strMessage);
//            startActivity(smsIntent);
//        }
//    }


}
