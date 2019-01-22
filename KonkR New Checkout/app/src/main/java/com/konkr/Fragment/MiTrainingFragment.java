package com.konkr.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konkr.Activities.ConnectSpotify;
import com.konkr.Activities.MainActivity;
import com.konkr.Models.SpotifyModel;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.databinding.FragmentMiTrainingBinding;

import java.util.ArrayList;
import java.util.List;

public class MiTrainingFragment extends Fragment {
    private ArrayList<SpotifyModel.Item> playList = new ArrayList<>();
    private FragmentMiTrainingBinding binding;
    private String userName;
    private TabLayout tabLayout;
    private String isFrom;
    int otherUserId;
    String spotifyId;
    int userId;
    private ViewPager viewPager;
    private int[] navLabels = {
            R.string.tab_my_workouts,
            R.string.tab_my_playlist,
    };
    FragmentCommunicator fragmentCommunicator;
    ViewPagerAdapter adapter;

    public MiTrainingFragment() {
    }

//    public static MiTrainingFragment newInstance(ArrayList<MusicAndVideo> musicListTemp) {
//        MiTrainingFragment fragment = new MiTrainingFragment();
////        Bundle args = new Bundle();
////        args.putParcelableArrayList(ARG_PARAM1, musicList);
////        fragment.setArguments(args);
//        musicList = musicListTemp;
//
//        return fragment;
//    }

    public static MiTrainingFragment newInstance() {
        MiTrainingFragment fragment = new MiTrainingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mi_training, container, false);
        View view = binding.getRoot();
        bindViews();
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        setupViewPager(viewPager);
        customTabText();
        wrapTabIndicatorToTitle(tabLayout, 100, 50);

        if (playList.size() > 0) {
            tabLayout.getTabAt(1).select();

        }
        setTabListner();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            fragmentCommunicator = (FragmentCommunicator) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    private void setTabListner() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ((MainActivity)getActivity()).showAddButton();
                        break;
                    case 1:
                        ((MainActivity)getActivity()).hideAddButton();
                        if ((SessionManager.getSpotifyToken(getActivity()).equalsIgnoreCase(null)) || SessionManager.getSpotifyToken(getActivity()).isEmpty()) {

//                            LogM.LogE("Spotify Token==>" + SessionManager.getSpotifyToken(getActivity()));
//                            Intent intent = new Intent(getActivity(), ConnectSpotify.class);
//                            intent.putExtra(GlobalData.FROM, GlobalData.TRAINING);
//                            startActivity(intent);
                        } else {
                            //  get Play list API call
                            fragmentCommunicator.updateList(adapter);
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

    public interface FragmentCommunicator {
        void updateList(MiTrainingFragment.ViewPagerAdapter adapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            userId=getArguments().getInt(GlobalData.USER_Id);
            spotifyId=getArguments().getString(GlobalData.SPOTIFY_ID);
            isFrom=getArguments().getString(GlobalData.FROM);
            userName=getArguments().getString(GlobalData.USER_NAME);
//            (1, playList,spotifyId,GlobalData.PROFILE_ACTIVITY,otherUserId), getResources().getString(R.string.tab_my_playlist));
            //  } 
          //  LogM.LogV("SIZE::::: " + playList.size());
            LogM.LogV("userId::::: " + userId);
            LogM.LogV("USER NAME in Training Frag::::: " + userName);
            playList = getArguments().getParcelableArrayList(GlobalData.ARG_MUSICLIST);
        }
    }


    private void customTabText() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_nav_training, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.tvtab);
            tab_label.setText(getResources().getString(navLabels[i]));
            // finally publish this custom view to navigation tab
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    }

    private void bindViews() {
        try {
            tabLayout = binding.tabs;
            viewPager = binding.viewPager;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePlayListData() {
//        tabLayout.getTabAt(1).select();
        ((MyPlayListFragment) adapter.getItem(1)).updatePlayList(spotifyId);
//        MyPlayListFragment.newInstance(0, playList);
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter.addFragment(MyWorkoutsFragment.newInstance(0, 0), getResources().getString(R.string.tab_my_workouts));
        adapter.addFragment(MyPlayListFragment.newInstance(0, playList,spotifyId,isFrom,otherUserId, userName), getResources().getString(R.string.tab_my_playlist));
        viewPager.setAdapter(adapter);
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
