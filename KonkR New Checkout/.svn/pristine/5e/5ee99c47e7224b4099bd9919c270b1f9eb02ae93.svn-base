package com.konkr.Activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konkr.Fragment.FollowersFragment;
import com.konkr.Fragment.FollowingFragment;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.databinding.ActivityFollowersFollowingBinding;

import java.util.ArrayList;
import java.util.List;

public class FollowersFollowingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFollowersFollowingBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Headerbar headerBar;

    private int[] navLabels = {
            R.string.tab_followers,
            R.string.tab_following,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_followers_following);

        bindViews();
        setListener();
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        customTabText();
        wrapTabIndicatorToTitle(tabLayout, 100, 50);
        setHeaderBar();
        if (getIntent() != null) {
            if (getIntent().getStringExtra(GlobalData.FROM) != null) {
                if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.FOLLOWERS)) {
                    tabLayout.getTabAt(0).select();
                } else if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.FOLLOWING)) {
                    tabLayout.getTabAt(1).select();
                }
                else if(getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.FOLLOWING_YOU_NOTIFICATION)){
                    tabLayout.getTabAt(0).select();
                }
            }
        }
    }

    private void customTabText() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_nav_training, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.tvtab);
            tab_label.setText(getResources().getString(navLabels[i]));
            // finally publish this custom view to navigation tab
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    }


    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.title_follow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            tabLayout = binding.tabs;
            viewPager = binding.viewPager;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        FollowersFollowingActivity.ViewPagerAdapter adapter = new FollowersFollowingActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FollowersFragment(), getResources().getString(R.string.tab_followers));
        adapter.addFragment(new FollowingFragment(), getResources().getString(R.string.tab_following));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        setMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        setMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        setMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void setMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }
}
