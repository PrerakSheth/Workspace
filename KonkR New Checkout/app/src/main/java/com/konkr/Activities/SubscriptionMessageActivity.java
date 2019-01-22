package com.konkr.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.konkr.R;
import com.konkr.databinding.ActivitySubscriptionMessageBinding;

public class SubscriptionMessageActivity extends AppCompatActivity implements View.OnClickListener{
ActivitySubscriptionMessageBinding binding;
ConstraintLayout mainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_subscription_message);
       initUi();
       setListnet();

    }

    private void setListnet() {
        mainScreen.setOnClickListener(this);
    }

    private void initUi() {

        mainScreen=binding.mainScreen;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainScreen:
                Intent intent = new Intent(SubscriptionMessageActivity.this, PremiumMemberShipActivity.class);
                // intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("backFromPremiumSubscription", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                finishAffinity();
                startActivity(intent);
                finish();
            break;
        }
    }
}
