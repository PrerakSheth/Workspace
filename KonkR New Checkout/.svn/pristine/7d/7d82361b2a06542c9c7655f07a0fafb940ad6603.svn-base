package com.konkr.Activities;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.Advertisement;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.ActivityAdvertismentDetailsBinding;

public class AdvertismentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MyTextView tvCompanyNameValue;
    private MyTextView tvEmailValue;
    private MyTextView tvMobileValue;
    private SimpleDraweeView ivParnerAd;
    private SimpleDraweeView ivSearchAd;
    private AdvertismentDetailsActivity contex;
    private Headerbar headerBar;
    ActivityAdvertismentDetailsBinding binding;
    private Advertisement.AdvertiseListBean advertiseListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_advertisment_details);
        contex = AdvertismentDetailsActivity.this;

        bindViews();
        setData();
        setHeaderBar();
        headerBar.ibtnLeftOne.setOnClickListener(this);
    }

    private void setData() {
        if (getIntent().getStringExtra(GlobalData.FROM) != null) {
            if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.SEARCH_ADVERTISEMENT)) {
                advertiseListBean = getIntent().getExtras().getParcelable(GlobalData.ADVERTISEMENT);

                tvCompanyNameValue.setText(advertiseListBean.getCompanyName());
                tvEmailValue.setText(advertiseListBean.getEmail());
                tvMobileValue.setText(advertiseListBean.getPhone());
                ivParnerAd.setVisibility(View.GONE);
                ivSearchAd.setVisibility(View.VISIBLE);
                ivSearchAd.setImageURI(Uri.parse(advertiseListBean.getLogo()));
            }

            if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.PARTNER_ADVERTISEMENT)) {
                advertiseListBean = getIntent().getExtras().getParcelable(GlobalData.ADVERTISEMENT);

                tvCompanyNameValue.setText(advertiseListBean.getCompanyName());
                tvEmailValue.setText(advertiseListBean.getEmail());
                tvMobileValue.setText(advertiseListBean.getPhone());
                ivSearchAd.setVisibility(View.GONE);
                ivParnerAd.setVisibility(View.VISIBLE);
                ivParnerAd.setImageURI(Uri.parse(advertiseListBean.getLogo()));
            }
        }
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(advertiseListBean.getCampaignName());
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        tvCompanyNameValue = binding.tvCompanyNameValue;
        tvEmailValue = binding.tvEmailValue;
        tvMobileValue = binding.tvMobileValue;
        ivParnerAd = binding.ivParnerAd;
        ivSearchAd = binding.ivSearchAd;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;
        }
    }
}
