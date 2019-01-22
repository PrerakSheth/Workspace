package com.konkr.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konkr.Fragment.MyPlayListFragment;
import com.konkr.Fragment.MyWorkoutsFragment;
import com.konkr.Interfaces.SetUpProfileCommunicator;
import com.konkr.Models.SpotifyModel;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.databinding.ActivityMiTrainingProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class MiTrainingProfileActivity extends AppCompatActivity implements View.OnClickListener, SetUpProfileCommunicator {
    private ActivityMiTrainingProfileBinding binding;
    private Headerbar headerBar;
    private MiTrainingProfileActivity context;
    private ArrayList<SpotifyModel.Item> playList = new ArrayList<>();// this is empty
    private TabLayout tabLayout;

    private String isFrom;
    int otherUserId;
    String spotifyId;

    private ViewPager viewPager;
    private int[] navLabels = {
            R.string.tab_my_workouts,
            R.string.tab_my_playlist,
    };
    MiTrainingProfileActivity.ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mi_training_profile);
        context = MiTrainingProfileActivity.this;


        bindView();
        setHeaderBar();
        setListener();
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        customTabText();
        wrapTabIndicatorToTitle(tabLayout, 100, 50);
        setTabListner();
        getIntentData();
    }

    private void getIntentData() {

        try {

            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.CONNECTSPOTIFY)) {

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       tabLayout.getTabAt(1).select();
                   }
               },100) ;

            }
        } catch (Exception e){

        }
    }

    @Override
    public void updateListSetUpProfile(MiTrainingProfileActivity.ViewPagerAdapter adapter) {
        MyPlayListFragment fragment = (MyPlayListFragment) adapter.getItem(1);
        if (fragment != null) {
            fragment.updatePlayList(SessionManager.getSpotifyUserId(context));
        } else {

        }
    }


    private void customTabText() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.tab_nav_training, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.tvtab);
            tab_label.setText(getResources().getString(navLabels[i]));
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    }

    private void bindView() {
        headerBar = binding.headerBar;
        tabLayout = binding.tabs;
        viewPager = binding.viewPager;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
        headerBar.ibtnRightTwo.setImageResource(R.drawable.add);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.mi_training);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        headerBar.ibtnRightTwo.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MiTrainingProfileActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MyWorkoutsFragment.newInstance(0, 0), getResources().getString(R.string.tab_my_workouts));
        adapter.addFragment(MyPlayListFragment.newInstance(0, playList,"",GlobalData.SETUP_TRAINING,0,"your"), getResources().getString(R.string.tab_my_playlist));
        viewPager.setAdapter(adapter);
    }

    private void setTabListner() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        headerBar.ibtnRightTwo.setVisibility(View.GONE);
                        if ((SessionManager.getSpotifyToken(context).equalsIgnoreCase(null)) || SessionManager.getSpotifyToken(context).isEmpty()) {
//                            LogM.LogE("Spotify Token==>" + SessionManager.getSpotifyToken(context));
//                            Intent intent = new Intent(context, ConnectSpotify.class);
//                            intent.putExtra(GlobalData.FROM, GlobalData.SETUP_TRAINING);
//                            startActivityForResult(intent, GlobalData.SETUP_PROFILE_REQUEST);
                        } else {
                            //  get Play list API call
                            updateListSetUpProfile(adapter);
                        }
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalData.SETUP_PROFILE_REQUEST && resultCode == RESULT_OK) {
            MyPlayListFragment fragment = (MyPlayListFragment) adapter.getItem(1);
            if (fragment != null) {
                fragment.updatePlayList("");
            } else {

            }
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

    public class ViewPagerAdapter extends FragmentPagerAdapter {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;
            case R.id.ibtnRightTwo:
                Intent miTrainingIntent = new Intent(MiTrainingProfileActivity.this, MiTrainingActivity.class);
                startActivity(miTrainingIntent);
                break;

        }
    }
}
