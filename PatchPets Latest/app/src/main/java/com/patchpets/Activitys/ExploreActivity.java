package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.patchpets.Adapters.AdvertisementAdapter;
import com.patchpets.Adapters.ViewPagerAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityExploreBinding;
import com.patchpets.fragments.DirectoryFragment;
import com.patchpets.fragments.DogParksFragment;
import com.patchpets.utils.ClickableViewPager;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;

public class ExploreActivity extends FragmentActivity implements View.OnClickListener {

    private ActivityExploreBinding binding;
    private HeaderBar headerBar;
    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;
    private ClickableViewPager vpAdvertisement;
    private int pagerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_explore);

        bindViews();
        setListener();

        pagerIndex = getIntent().getIntExtra(Constants.EXPLORE_DIRECTORY_TAB_NUMBER, 0);
        tabs.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        customTabText();
        tabs.getTabAt(pagerIndex).select();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        appBar = binding.appBar;
        tabs = binding.tabs;
        viewPager = binding.viewPager;
        vpAdvertisement = binding.vpAdvertisement;

        headerBar.tvTitle.setText(getResources().getString(R.string.explore));
        headerBar.ibSwitchBtn.setVisibility(View.GONE);
        headerBar.ibFilter.setVisibility(View.GONE);
        headerBar.ibRight.setVisibility(View.GONE);
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DogParksFragment.newInstance(), getResources().getString(R.string.tab_dog_parks));
        adapter.addFragment(DirectoryFragment.newInstance(), getResources().getString(R.string.tab_directory));
        viewPager.setAdapter(adapter);

        int[] mResources = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5
        };
        AdvertisementAdapter mAdsAdapter = new AdvertisementAdapter(ExploreActivity.this, mResources);
        vpAdvertisement.startAutoScroll();
        vpAdvertisement.setInterval(3000);
        vpAdvertisement.setCycle(true);
        vpAdvertisement.setAdapter(mAdsAdapter);
        vpAdvertisement.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent iAdvertisement = new Intent(ExploreActivity.this, AdvertisementActivity.class);
                startActivity(iAdvertisement);
            }
        });
    }

    private int[] navLabels = {
            R.string.tab_dog_parks,
            R.string.tab_directory,
    };

    private void customTabText() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from(ExploreActivity.this).inflate(R.layout.tab_nav_explore, null);
            TextView tab_label = tab.findViewById(R.id.tvTab);
            tab_label.setText(getResources().getString(navLabels[i]));
            tabs.getTabAt(i).setCustomView(tab);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
