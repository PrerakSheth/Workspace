package com.patchpets.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patchpets.Activitys.FavouritesDogActivity;
import com.patchpets.Adapters.DogParksAdapter;
import com.patchpets.R;
import com.patchpets.databinding.FragmentDogParksBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.utils.Constants;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.utils.LogM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DogParksFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, OnItemClickListener, View.OnClickListener {

    private FragmentDogParksBinding binding;
    private EditText etSearchPark;
    private Spinner spinnerSearchDistance;
    private ConstraintLayout clSearchDistanceSpinner;
    private TextView tvDistance;
    private ImageButton ibSpinnerArrow;
    private SwitchCompat switchMapEnable;
    private RecyclerView rvDogParks;
    private DogParksAdapter mAdapter;
    private List<String> alDistance = new ArrayList<>();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    public DogParksFragment() {
    }

    public static DogParksFragment newInstance() {
        DogParksFragment fragment = new DogParksFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dog_parks, container, false);
        View view = binding.getRoot();
        bindViews();
        setListener();
        setDistanceSpinner();

        mAdapter = new DogParksAdapter(getActivity(), this);
        rvDogParks.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDogParks.setAdapter(mAdapter);
        rvDogParks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KeyboardUtility.hideKeyboard(getActivity(), recyclerView);
            }
        });

        switchMapEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rvDogParks.setVisibility(View.GONE);
                } else {
                    rvDogParks.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private void bindViews() {
        etSearchPark = binding.etSearchPark;
        spinnerSearchDistance = binding.spinnerSearchDistance;
        switchMapEnable = binding.switchMapEnable;
        rvDogParks = binding.rvDogParks;
        tvDistance = binding.tvDistance;
        clSearchDistanceSpinner = binding.clSearchDistanceSpinner;

        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void setListener() {
        clSearchDistanceSpinner.setOnClickListener(this);
    }

    private void setDistanceSpinner() {
//        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.item_spinner_map_search_distance, distances);
//        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown_map_search_distance);
//        spinnerSearchDistance.setAdapter(langAdapter);
        String[] mapDistance = getResources().getStringArray(R.array.mapDistance);
        alDistance = new ArrayList<>(Arrays.asList(mapDistance));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner_map_search_distance, alDistance);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_map_search_distance);
        spinnerSearchDistance.setAdapter(dataAdapter);
        spinnerSearchDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvDistance.setText(alDistance.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            mMap.setOnMarkerClickListener(this);
            LatLng latlongs = new LatLng(-37.814, 144.96332);
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.814, 144.96332)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.green_paw))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.805575, 144.956949)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.green_paw))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.818557, 144.958686)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.green_paw))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.809604, 144.972157)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.green_paw))));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlongs));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlongs, Constants.MAP_ZOOM_LEVEL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LogM.e("" + marker.getId());
        Intent iDogParkList = new Intent(getActivity(), FavouritesDogActivity.class);
        iDogParkList.putExtra(Constants.FROM_DOG_PARK, Constants.IS_FROM_DOG_PARK);
        getActivity().startActivity(iDogParkList);
        return false;
    }

    @Override
    public void onItemClicked(int position) {
        Intent iDogParkList = new Intent(getActivity(), FavouritesDogActivity.class);
        iDogParkList.putExtra(Constants.FROM_DOG_PARK, Constants.IS_FROM_DOG_PARK);
        getActivity().startActivity(iDogParkList);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.clSearchDistanceSpinner:
                    spinnerSearchDistance.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
