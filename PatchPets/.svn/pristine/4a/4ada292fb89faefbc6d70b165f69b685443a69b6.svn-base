package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patchpets.Adapters.BusinessProfileServiceAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityBusinessProfileBinding;
import com.patchpets.model.User;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;

import java.util.ArrayList;
import java.util.List;

public class BusinessProfileActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private HeaderBar headerBar;
    private ActivityBusinessProfileBinding binding;
    private BusinessProfileServiceAdapter serviceAdapter;
    private RecyclerView rvService;
    private List<String> serviceList = new ArrayList<>();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_profile);
        user = SessionManager.getInstance().getUser(BusinessProfileActivity.this);

        bindViews();
        setListener();
        setData();

        rvService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        serviceAdapter = new BusinessProfileServiceAdapter(serviceList);
        rvService.setAdapter(serviceAdapter);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvService = binding.rvServices;
        headerBar.tvTitle.setText(getResources().getString(R.string.business_profile));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
//        headerBar.ibRight.setImageDrawable(getResources().getDrawable(R.drawable.edit));
        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
    }

    public void setData() {
        serviceList.add("Pet Spa");
        serviceList.add("Training");
        serviceList.add("Veterinary");
        serviceList.add("Pet Spa");
        serviceList.add("Training");
        serviceList.add("Veterinary");
        serviceList.add("Pet Spa");
        serviceList.add("Training");
        serviceList.add("Veterinary");
        serviceList.add("Pet Spa");
        serviceList.add("Training");
        serviceList.add("Veterinary");
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.ibRight:
                    Intent iSetupBusinessProfile = new Intent(BusinessProfileActivity.this, SetupBusinessProfileActivity.class);
                    startActivity(iSetupBusinessProfile);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latlongs = new LatLng(-37.814, 144.96332);
        mMap.addMarker(new MarkerOptions().position(latlongs).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlongs));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlongs, Constants.MAP_ZOOM_LEVEL));
    }
}
