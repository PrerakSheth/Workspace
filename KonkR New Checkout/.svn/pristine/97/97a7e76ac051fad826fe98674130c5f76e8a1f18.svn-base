package com.konkr.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konkr.R;
import com.konkr.databinding.FragmentPostExpressionBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 6/20/2018.
 */

public class PostExpressionFragment extends android.support.v4.app.Fragment {
    private FragmentPostExpressionBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] navIcons = {
            R.drawable.inspiration,
            R.drawable.goal,
            R.drawable.admiring,

    };
    private int[] navLabels = {
            R.string.tab_inspiring,
            R.string.tab_goal,
            R.string.tab_admiring,
    };
    public PostExpressionFragment() {
    }

    public static PostExpressionFragment newInstance() {
        PostExpressionFragment fragment = new PostExpressionFragment ();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_expression, container, false);
        View view = binding.getRoot();
        bindViews();
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        //setupTabIcons();
        go();
        wrapTabIndicatorToTitle(tabLayout, 0, 0);

        return view;
    }

    private void go() {
        for (int i = 0; i < tabLayout.getTabCount (); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from (getActivity ()).inflate (R.layout.nav_tab, null);

            // get child TextView and ImageView from this layout for the icon and label
            TextView tab_label = (TextView) tab.findViewById (R.id.tvtab);
            ImageView tab_icon = (ImageView) tab.findViewById (R.id.ivTabIcon);

            // set the label text by getting the actual string value by its id
            // by getting the actual resource value `getResources().getString(string_id)`
            tab_label.setText (getResources ().getString (navLabels[i]));

            // set the home to be active at first
                tab_icon.setImageResource (navIcons[i]);


            // finally publish this custom view to navigation tab
            tabLayout.getTabAt (i).setCustomView (tab);
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.inspiration);
        tabLayout.getTabAt(1).setIcon(R.drawable.goal);
        tabLayout.getTabAt(2).setIcon(R.drawable.admiring);
    }
    private void bindViews() {
        tabLayout = binding.tabs;
        viewPager = binding.viewPager;
    }

    private void setupViewPager(ViewPager viewPager) {
        PostExpressionFragment.ViewPagerAdapter adapter = new PostExpressionFragment.ViewPagerAdapter (getChildFragmentManager());
        adapter.addFragment(new InspiringFragment (), getResources().getString(R.string.tab_inspiring));
        adapter.addFragment(new GoalFragment(), getResources().getString(R.string.tab_goal));
        adapter.addFragment(new AdmiringFragment(), getResources().getString(R.string.tab_admiring));
        viewPager.setAdapter(adapter);
    }

    /**
     * Here we are creating View Pager Adapter class
     */

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<> ();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
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
