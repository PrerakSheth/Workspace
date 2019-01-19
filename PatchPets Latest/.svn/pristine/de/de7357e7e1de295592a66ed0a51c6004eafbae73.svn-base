package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityTermsConditionBinding;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.skyfishjy.library.RippleBackground;

public class TermsConditionActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityTermsConditionBinding binding;
    private HeaderBar headerBar;
    ImageView ivchecked;
    TextView tv_understandandaccept, tv_termscondition;
    Button btn_continue;
    boolean check;
    View viewTxtBg;
    RippleBackground rippleBackground;
    ConstraintLayout layout_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_condition);
        rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        bindViews();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ivchecked = binding.ivchecked;
        btn_continue = binding.btnContinue;
        tv_understandandaccept = binding.tvUnderstandandaccept;
        tv_termscondition = binding.tvTermscondition;
        viewTxtBg = binding.viewTxtBg;
        layout_bottom = binding.layoutBottom;
        headerBar.tvTitle.setText(getResources().getString(R.string.label_termscondition));
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.SETTINGS)) {
            layout_bottom.setVisibility(View.GONE);
        }
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ivchecked.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    finish();
                    break;

                case R.id.ivchecked:
                    if (!check) {
                        check = true;
                        rippleBackground.stopRippleAnimation();
                        btn_continue.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                        tv_understandandaccept.setTextColor(getResources().getColor(android.R.color.black));
                        tv_termscondition.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        ivchecked.setImageResource(R.drawable.select_contact_check_green);
                        viewTxtBg.setVisibility(View.VISIBLE);
                    } else {
                        check = false;
                        rippleBackground.startRippleAnimation();
                        btn_continue.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        tv_understandandaccept.setTextColor(getResources().getColor(R.color.text_hint_color));
                        tv_termscondition.setTextColor(getResources().getColor(R.color.text_hint_color));
                        ivchecked.setImageResource(R.drawable.select_contact_check_grey);
                        viewTxtBg.setVisibility(View.GONE);
                    }
                    break;

                case R.id.btn_continue:
                    if (check) {
                        rippleBackground.stopRippleAnimation();
                        if (getIntent().getIntExtra(Constants.USER_TYPE, 2) == Constants.BUSINESS_USER) {
                            Intent Intent = new Intent(TermsConditionActivity.this, SetupBusinessProfileActivity.class);
                            startActivity(Intent);
                        } else {
                            Intent Intent = new Intent(TermsConditionActivity.this, SetupProfileActivity.class);
                            startActivity(Intent);
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
