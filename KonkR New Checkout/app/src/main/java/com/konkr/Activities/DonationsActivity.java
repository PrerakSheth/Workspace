package com.konkr.Activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.konkr.R;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.ActivityDonationsBinding;

public class DonationsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDonationsBinding binding;
    private Headerbar headerBar;
    private Context context;
    private MyTextView proceedBtn;
    private MyEditText etDonation;
    private String strDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_donations);

        context = DonationsActivity.this;

        bindViews();
        setHeaderBar();
        setListener();

        KeyboardUtility.HideKeyboard(context, etDonation);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        proceedBtn.setOnClickListener(this);
    }

    private void bindViews() {

        try {
            headerBar = binding.headerBar;
            proceedBtn = binding.proceedBtn;
            etDonation = binding.etDonation;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.donations_title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
//                onBackPressed();
//                Intent intentBack = new Intent(context, MainActivity.class);
//                intentBack.putExtra (GlobalData.DONATION,GlobalData.DONATION);
//                intentBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intentBack);
//                finish();
//                if (getIntent().getBooleanExtra("backFromDonations", false)) {
//                    Intent intentBack = new Intent(context, MainActivity.class);
//                    intentBack.putExtra(GlobalData.DONATION, GlobalData.DONATION);
////                    intentBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intentBack);
//                    finish();
//                } else {
//                    finish();
//                }
                KeyboardUtility.HideKeyboard(context, view);
                onBackPressed();
                break;

            case R.id.proceedBtn:
                KeyboardUtility.HideKeyboard(context, proceedBtn);
                if (validation()) {
                    Intent intent = new Intent(context, CardListActivity.class);
                    intent.putExtra(GlobalData.IS_FROM,GlobalData.DONATION);
                    intent.putExtra(GlobalData.DONATION, etDonation.getText().toString().trim());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    finish ();
                }
                break;
        }
    }

    private boolean validation() {
        strDonation = etDonation.getText().toString().trim();
        if (strDonation.length() == 0) {
            etDonation.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.enter_donation_amount), etDonation, this);
            return false;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed ();
//        Intent intentBack = new Intent(context, MainActivity.class);
//        intentBack.putExtra (GlobalData.DONATION,GlobalData.DONATION);
//        intentBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intentBack);
//        finish ();
//    }
}
