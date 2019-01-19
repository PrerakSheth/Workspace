package com.patchpets.Activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.patchpets.R;
import com.patchpets.databinding.ActivityAdvertisementBinding;

public class AdvertisementActivity extends AppCompatActivity {

    private ActivityAdvertisementBinding binding;
    private ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_advertisement);
        ibClose = binding.ibClose;
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
