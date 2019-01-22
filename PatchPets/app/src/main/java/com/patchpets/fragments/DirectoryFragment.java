package com.patchpets.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.patchpets.Activitys.BusinessProfileActivity;
import com.patchpets.Adapters.DirectoryAdapter;
import com.patchpets.R;
import com.patchpets.databinding.FragmentDirectoryBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.utils.GlobalMethods;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.utils.LogM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, OnItemClickListener, View.OnClickListener {

    private FragmentDirectoryBinding binding;
    private EditText etSearchBusiness;
    private Spinner spinnerCategory;
    private ConstraintLayout clCategorySpinner;
    private TextView tvCategory;
    private ImageButton ibSpinnerArrow;
    private SwitchCompat switchMapEnable;
    private List<String> alCategory = new ArrayList<>();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private RecyclerView rvDirectory;
    private DirectoryAdapter mAdapter;

    public DirectoryFragment() {
    }

    public static DirectoryFragment newInstance() {
        DirectoryFragment fragment = new DirectoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_directory, container, false);
        View view = binding.getRoot();
        bindViews();
        setListener();
        setDistanceSpinner();

        mAdapter = new DirectoryAdapter(getActivity(), this);
        rvDirectory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDirectory.setAdapter(mAdapter);
        rvDirectory.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    rvDirectory.setVisibility(View.GONE);
                } else {
                    rvDirectory.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private void bindViews() {
        etSearchBusiness = binding.etSearchBusiness;
        clCategorySpinner = binding.clCategorySpinner;
        spinnerCategory = binding.spinnerCategory;
        tvCategory = binding.tvCategory;
        switchMapEnable = binding.switchMapEnable;
        rvDirectory = binding.rvDirectory;

        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void setListener() {
        clCategorySpinner.setOnClickListener(this);
    }

    private void setDistanceSpinner() {
        try {
            String[] mapDistance = getResources().getStringArray(R.array.category);
            alCategory = new ArrayList<>(Arrays.asList(mapDistance));
            final int alSize = alCategory.size() - 1;

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner_map_search_distance, alCategory) {
                @Override
                public int getCount() {
                    return (alSize);
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    return super.getView(position, convertView, parent);
                }
            };
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_map_search_distance);
            spinnerCategory.setAdapter(dataAdapter);
            spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    tvCategory.setText(alCategory.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerCategory.setSelection(alSize);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.clCategorySpinner:
                    spinnerCategory.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent iBusinessProfile = new Intent(getActivity(), BusinessProfileActivity.class);
        getActivity().startActivity(iBusinessProfile);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LogM.e("" + marker.getId());
        Intent iBusinessProfile = new Intent(getActivity(), BusinessProfileActivity.class);
        getActivity().startActivity(iBusinessProfile);
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            mMap.setOnMarkerClickListener(this);
            LatLng latlongs = new LatLng(-37.814, 144.96332);
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.814, 144.96332)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.directory_map_icon))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.805575, 144.956949)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.directory_map_icon))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.818557, 144.958686)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.directory_map_icon))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(-37.809604, 144.972157)).icon(BitmapDescriptorFactory.fromBitmap(GlobalMethods.getMapMarker(getActivity(), R.drawable.directory_map_icon))));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlongs));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlongs, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
