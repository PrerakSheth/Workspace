package com.patchpets.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patchpets.Adapters.FilterAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityFilterBinding;
import com.patchpets.interfaces.OnRemove;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity implements OnRemove, View.OnClickListener {

    private ActivityFilterBinding binding;
    private ImageView iv_clear;
    private HeaderBar headerBar;
    private RippleBackground rippleBackground;
    private LinearLayout viewhalfcircle;
    private RecyclerView rvBreed, rvAge, rvGender, rvStatus, rvWillingToBreed, rvDistance, rvSize;
    private ArrayList<String> breedList, ageList, genderList, statusList, willingToBreed, distanceList, sizeList;
    private FilterAdapter breedAdapter, ageAdapter, genderAdapter, statusAdapter, willingToBreedAdapter, distanceAdapter, sizeOfDogAdapter;
    private TextView tvBreed, tvAge, tvGender, tvStatus, tvWillingToBreed, tvDistance, tvSizeOfDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        bindViews();
        setData();
        setListener();
        setVisibility();
        setAdapter();

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        TextView Views = new TextView(this);
        Views.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width / 2));
        viewhalfcircle.addView(Views);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        headerBar.tvTitle.setText(getResources().getString(R.string.filter_by));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
        rvBreed = binding.rvBreed;
        rvAge = binding.rvAge;
        rvGender = binding.rvGender;
        rvStatus = binding.rvStatus;
        rvWillingToBreed = binding.rvWillingToBreed;
        rvDistance = binding.rvDistance;
        rvSize = binding.rvSize;
        tvBreed = binding.tvBreed;
        tvAge = binding.tvAge;
        tvGender = binding.tvGender;
        tvStatus = binding.tvStatus;
        tvWillingToBreed = binding.tvWillingToBreed;
        tvDistance = binding.tvDistance;
        tvSizeOfDog = binding.tvSizeofdog;
        iv_clear = binding.ivClear;
        viewhalfcircle = binding.viewhalfcircle;
        rippleBackground = binding.content;
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        tvBreed.setOnClickListener(this);
        tvAge.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        tvStatus.setOnClickListener(this);
        tvWillingToBreed.setOnClickListener(this);
        tvDistance.setOnClickListener(this);
        tvSizeOfDog.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
    }

    public void setData() {
        breedList = getIntent().getStringArrayListExtra(Constants.BREED_LIST);
        ageList = getIntent().getStringArrayListExtra(Constants.AGE_LIST);
        genderList = getIntent().getStringArrayListExtra(Constants.GENDER_LIST);
        statusList = getIntent().getStringArrayListExtra(Constants.STATUS_LIST);
        willingToBreed = getIntent().getStringArrayListExtra(Constants.WILLING_TO_BREED_LIST);
        distanceList = getIntent().getStringArrayListExtra(Constants.DISTANCE_LIST);
        sizeList = getIntent().getStringArrayListExtra(Constants.SIZE_LIST);
    }

    public void setVisibility() {
        checkviewVisibility(breedList, rvBreed);
        checkviewVisibility(ageList, rvAge);
        checkviewVisibility(genderList, rvGender);
        checkviewVisibility(statusList, rvStatus);
        checkviewVisibility(willingToBreed, rvWillingToBreed);
        checkviewVisibility(distanceList, rvDistance);
        checkviewVisibility(sizeList, rvSize);
        //checkBreedVisibility();
        // checkageVisibility();

        // checkGenderVisibility();
        // checkStatusVisibility();
        // checkDistanceVisibility();
        // checkSizeVisibility();
    }

    public void setAdapter() {
        setBreedAdapter();
        setAgeAdapter();
        setGenderAdapter();
        setStatusAdapter();
        setBreedingAdapter();
        setDistanceAdapter();
        setSizeAdapter();
    }

    public void setSizeAdapter() {
        rvSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sizeOfDogAdapter = new FilterAdapter(sizeList, this, Constants.SIZE);
        rvSize.setAdapter(sizeOfDogAdapter);
    }

    public void setDistanceAdapter() {
        rvDistance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        distanceAdapter = new FilterAdapter(distanceList, this, Constants.DISTANCE);
        rvDistance.setAdapter(distanceAdapter);
    }

    public void setStatusAdapter() {
        rvStatus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        statusAdapter = new FilterAdapter(statusList, this, Constants.STATUS);
        rvStatus.setAdapter(statusAdapter);
    }

    public void setBreedingAdapter() {
        rvWillingToBreed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        willingToBreedAdapter = new FilterAdapter(willingToBreed, this, Constants.BREEDING);
        rvWillingToBreed.setAdapter(willingToBreedAdapter);
    }

    public void setGenderAdapter() {
        rvGender.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        genderAdapter = new FilterAdapter(genderList, this, Constants.GENDER);
        rvGender.setAdapter(genderAdapter);
    }

    public void setAgeAdapter() {
        rvAge.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ageAdapter = new FilterAdapter(ageList, this, Constants.AGE);
        rvAge.setAdapter(ageAdapter);
    }

    public void setBreedAdapter() {
        rvBreed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        breedAdapter = new FilterAdapter(breedList, this, Constants.BREEDS);
        rvBreed.setAdapter(breedAdapter);
    }

    public void checkviewVisibility(ArrayList<String> mydata, RecyclerView rv) {
        if (mydata.size() <= 0) {
            rv.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.VISIBLE);
        }
    }

//    public void checkStatusVisibility() {
//        if (statusList.size() <= 0) {
//            rvStatus.setVisibility(View.GONE);
//        } else {
//            rvStatus.setVisibility(View.VISIBLE);
//
//        }
//    }
//
//    public void checkDistanceVisibility() {
//        if (distanceList.size() <= 0) {
//            rvDistance.setVisibility(View.GONE);
//        } else {
//            rvDistance.setVisibility(View.VISIBLE);
//
//        }
//    }
//
//    public void checkSizeVisibility() {
//        if (sizeList.size() <= 0) {
//            rvSize.setVisibility(View.GONE);
//        } else {
//            rvSize.setVisibility(View.VISIBLE);
//
//        }
//    }
//
//    public void checkGenderVisibility() {
//        if (genderList.size() <= 0) {
//            rvGender.setVisibility(View.GONE);
//        } else {
//            rvGender.setVisibility(View.VISIBLE);
//
//        }
//    }
//
//    public void checkBreedVisibility() {
//        if (breedList.size() <= 0) {
//            rvBreed.setVisibility(View.GONE);
//        } else {
//            rvBreed.setVisibility(View.VISIBLE);
//
//        }
//    }

    public void checkageVisibility() {
        if (ageList.size() <= 0) {
            rvAge.setVisibility(View.GONE);
        } else {
            rvAge.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    setBackPressData();
                    break;
                case R.id.tv_breed:
                    moveToDetail(Constants.BREEDS, breedList);
                    break;
                case R.id.tv_age:
                    moveToDetail(Constants.AGE, ageList);
                    break;
                case R.id.tv_gender:
                    moveToDetail(Constants.GENDER, genderList);
                    break;
                case R.id.tv_status:
                    moveToDetail(Constants.STATUS, statusList);
                    break;
                case R.id.tvWillingToBreed:
                    moveToDetail(Constants.BREEDING, willingToBreed);
                    break;
                case R.id.tv_distance:
                    moveToDetail(Constants.DISTANCE, distanceList);
                    break;
                case R.id.tv_sizeofdog:
                    moveToDetail(Constants.SIZE, sizeList);
                    break;
                case R.id.iv_clear:
                    clearData();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveToDetail(String type, ArrayList<String> list) {
        Intent i = new Intent(getApplicationContext(), FilterDetailActivity.class);
        i.putExtra(Constants.TYPE, type);
        i.putStringArrayListExtra(Constants.LIST_DATA, list);
        startActivityForResult(i, 102);
    }

    @Override
    public void onBackPressed() {
        setBackPressData();
        // super.onBackPressed();
    }

    public void setBackPressData() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(Constants.BREED_LIST, breedList);
        intent.putStringArrayListExtra(Constants.AGE_LIST, ageList);
        intent.putStringArrayListExtra(Constants.GENDER_LIST, genderList);
        intent.putStringArrayListExtra(Constants.STATUS_LIST, statusList);
        intent.putStringArrayListExtra(Constants.WILLING_TO_BREED_LIST, willingToBreed);
        intent.putStringArrayListExtra(Constants.DISTANCE_LIST, distanceList);
        intent.putStringArrayListExtra(Constants.SIZE_LIST, sizeList);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDataRemove(int pos, String type) {
        if (type.equalsIgnoreCase(Constants.BREEDS)) {
            breedList.remove(pos);
            breedAdapter.notifyDataSetChanged();
            //checkBreedVisibility();
            checkviewVisibility(breedList, rvBreed);
        } else if (type.equalsIgnoreCase(Constants.AGE)) {
            ageList.remove(pos);
            ageAdapter.notifyDataSetChanged();
            checkageVisibility();
        } else if (type.equalsIgnoreCase(Constants.GENDER)) {
            genderList.remove(pos);
            genderAdapter.notifyDataSetChanged();
            checkviewVisibility(genderList, rvGender);
            //checkGenderVisibility();
        } else if (type.equalsIgnoreCase(Constants.STATUS)) {
            statusList.remove(pos);
            statusAdapter.notifyDataSetChanged();
            checkviewVisibility(statusList, rvStatus);
            //checkStatusVisibility();
        } else if (type.equalsIgnoreCase(Constants.BREEDING)) {
            willingToBreed.remove(pos);
            willingToBreedAdapter.notifyDataSetChanged();
            checkviewVisibility(willingToBreed, rvWillingToBreed);
            //checkStatusVisibility();
        } else if (type.equalsIgnoreCase(Constants.DISTANCE)) {
            distanceList.remove(pos);
            distanceAdapter.notifyDataSetChanged();
            checkviewVisibility(distanceList, rvDistance);
            // checkDistanceVisibility();
        } else if (type.equalsIgnoreCase(Constants.SIZE)) {
            sizeList.remove(pos);
            sizeOfDogAdapter.notifyDataSetChanged();
            checkviewVisibility(sizeList, rvSize);
            // checkSizeVisibility();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String type = data.getExtras().getString(Constants.TYPE);

            if (type.equalsIgnoreCase(Constants.BREEDS)) {
                breedList.clear();
                breedList.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                // checkBreedVisibility();
                checkviewVisibility(breedList, rvBreed);
                breedAdapter.notifyDataSetChanged();
            } else if (type.equalsIgnoreCase(Constants.AGE)) {
                ageList.clear();
                ageList.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                checkageVisibility();
                ageAdapter.notifyDataSetChanged();
            } else if (type.equalsIgnoreCase(Constants.GENDER)) {
                genderList.clear();
                genderList.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                checkviewVisibility(genderList, rvGender);
                // checkGenderVisibility();
                genderAdapter.notifyDataSetChanged();
            } else if (type.equalsIgnoreCase(Constants.STATUS)) {
                statusList.clear();
                statusList.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                checkviewVisibility(statusList, rvStatus);
                //checkStatusVisibility();
                statusAdapter.notifyDataSetChanged();
            } else if (type.equalsIgnoreCase(Constants.BREEDING)) {
                willingToBreed.clear();
                willingToBreed.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                checkviewVisibility(willingToBreed, rvWillingToBreed);
                //checkStatusVisibility();
                willingToBreedAdapter.notifyDataSetChanged();
            } else if (type.equalsIgnoreCase(Constants.DISTANCE)) {
                distanceList.clear();
                distanceList.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                checkviewVisibility(distanceList, rvDistance);
                //checkDistanceVisibility();
                distanceAdapter.notifyDataSetChanged();
            } else if (type.equalsIgnoreCase(Constants.SIZE)) {
                sizeList.clear();
                sizeList.addAll(data.getStringArrayListExtra(Constants.LIST_DATA));
                //  Toast.makeText(getApplicationContext(), "" + breedList.size(), Toast.LENGTH_SHORT).show();
                checkviewVisibility(sizeList, rvSize);
                //checkSizeVisibility();
                sizeOfDogAdapter.notifyDataSetChanged();
            }
        }
    }

    public void clearData() {
        breedList.clear();
        ageList.clear();
        genderList.clear();
        statusList.clear();
        willingToBreed.clear();
        distanceList.clear();
        sizeList.clear();
        setVisibility();
    }
}
