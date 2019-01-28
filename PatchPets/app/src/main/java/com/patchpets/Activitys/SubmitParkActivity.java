package com.patchpets.Activitys;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patchpets.R;
import com.patchpets.databinding.ActivitySubmitParkBinding;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.skyfishjy.library.RippleBackground;

public class SubmitParkActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ActivitySubmitParkBinding binding;
    private SupportMapFragment mapFragment;
    private HeaderBar headerBar;
    private ImageView ivPaw;
    private EditText etSearch;
    private ImageButton ibSearch;
    private RippleBackground rippleBackground;
    private LinearLayout viewhalfcircle;
    private View viewMovable;
    private Animation animation;
    private boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_submit_park);
        bindViews();

        rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        setListener();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        TextView Views = new TextView(this);
        Views.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, width / 2));
        viewhalfcircle.addView(Views);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ivPaw = binding.ivPaw;
        etSearch = binding.etSearch;
        ibSearch = binding.ibSearch;
        viewhalfcircle = binding.viewhalfcircle;
        headerBar.tvTitle.setText(getResources().getString(R.string.submit_a_park));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ivPaw.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latlongs = new LatLng(-37.814, 144.96332);
        mMap.addMarker(new MarkerOptions().position(latlongs).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlongs));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlongs, Constants.MAP_ZOOM_LEVEL));
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.iv_paw:
                    final Dialog dialog = new Dialog(this, R.style.FullScreenDialogStyle);
                    dialog.setContentView(R.layout.dialog_submit_park);

                    Button btnSubmit = dialog.findViewById(R.id.btn_submit);
                    final TextView tvDogPark = dialog.findViewById(R.id.tv_dogpark);
                    final TextView tvOffLeash = dialog.findViewById(R.id.tv_offLeash);
                    viewMovable = dialog.findViewById(R.id.viewMovable);
                    tvDogPark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tvDogPark.setTextSize(19);
                            tvOffLeash.setTextSize(13);
                            tvDogPark.setTextColor(getResources().getColor(R.color.colorPrimary));
                            tvOffLeash.setTextColor(getResources().getColor(R.color.email_text));
                            if (!check) {
                                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
                                viewMovable.startAnimation(animation);
                            }
                            check = true;
                        }
                    });
                    tvOffLeash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tvOffLeash.setTextSize(19);
                            tvDogPark.setTextSize(13);
                            tvOffLeash.setTextColor(getResources().getColor(R.color.colorPrimary));
                            tvDogPark.setTextColor(getResources().getColor(R.color.email_text));
                            if (check) {
                                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
                                viewMovable.startAnimation(animation);
                            }
                            check = false;
                        }
                    });
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent i = new Intent(SubmitParkActivity.this, ExploreActivity.class);
                            startActivity(i);
                        }
                    });
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
    }
}
