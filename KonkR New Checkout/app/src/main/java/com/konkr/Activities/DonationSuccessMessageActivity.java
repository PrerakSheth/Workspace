package com.konkr.Activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.databinding.ActivityDonationSuccessMessageBinding;

public class DonationSuccessMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityDonationSuccessMessageBinding binding;
    private TextView tvOne, tvMesage;
    private ImageView ivImage;
    private ConstraintLayout screen;
    private String amount, msg;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_donation_success_message);
        context = DonationSuccessMessageActivity.this;


        binViews();
        setListener();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            amount = getIntent().getStringExtra(GlobalData.DONATION);
            int donationAmount = Integer.parseInt(amount);
            msg = getIntent().getStringExtra(GlobalData.MESSAGE);
            LogM.LogV("messageString ->" + msg);
            LogM.LogV("donation Amount-> " + amount);
            tvMesage.setText(msg);

            if (donationAmount >= 100 && donationAmount < 250) {
                ivImage.setBackgroundResource(R.drawable.bronze);
            } else if (donationAmount >= 250 && donationAmount < 500) {
                ivImage.setBackgroundResource(R.drawable.silver);
            } else if (donationAmount >= 500 && donationAmount < 1000) {
                ivImage.setBackgroundResource(R.drawable.gold);
            } else if (donationAmount >= 1000) {
                ivImage.setBackgroundResource(R.drawable.platinum);
            } else {
                ivImage.setVisibility(View.GONE);
                tvOne.setVisibility(View.GONE);
            }

        }
    }

    private void binViews() {
        tvMesage = binding.tvMessage;
        ivImage = binding.ivImage;
        tvOne = binding.tvOne;
        screen = binding.screen;
    }

    private void setListener() {
        screen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.screen:
                Intent intent = new Intent(context, DonationsActivity.class);
                // intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("backFromDonations", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                finishAffinity();
                startActivity(intent);
                finish();
                break;
        }
    }
}
