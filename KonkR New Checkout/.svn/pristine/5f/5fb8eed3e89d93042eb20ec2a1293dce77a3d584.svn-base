package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.konkr.Fragment.AdmiringFragment;
import com.konkr.Fragment.AdmiringMediaFragment;
import com.konkr.Fragment.GoalFragment;
import com.konkr.Fragment.GoalMediaFragment;
import com.konkr.Fragment.InspiringFragment;
import com.konkr.Fragment.InspiringMediaFragment;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.ExpressionMedia;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityPostExpressionBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostExpressionForMediaActivityActivity extends AppCompatActivity implements View.OnClickListener {

    private Headerbar headerBar;
    private ActivityPostExpressionBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int homeFeedId;
    public ArrayList<ExpressionMedia.AdmiringBean> admiringList = new ArrayList<>();
    public ArrayList<ExpressionMedia.GoalsBean> goalList = new ArrayList<>();
    public ArrayList<ExpressionMedia.InspiringBean> inspiringList = new ArrayList<>();
    private int[] navIcons = {
            R.drawable.goal,
            R.drawable.admiring,
            R.drawable.inspiration,
    };
    private int[] navLabels = {
            R.string.tab_goal,
            R.string.tab_admiring,
            R.string.tab_inspiring,
    };
    PostExpressionForMediaActivityActivity context;
    private int intFeedPosition, intLikeCount;
    private int expression;

    private String mediaId, commentType, expressionFor;
    private int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_expression_for_media_activity);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_expression);
        context = PostExpressionForMediaActivityActivity.this;
        bindViews();
        getIntentData();
        setHeaderBar();
        setListener();
        callGetExpressionList();
    }

    private void getIntentData() {
        try {
            if (getIntent().getExtras() != null) {
                //  homeFeedId = Integer.parseInt(getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
                // intFeedPosition = getIntent().getIntExtra("position", 0);
                profileId = (getIntent().getIntExtra(GlobalData.PROFILE_ID, 0));
                mediaId = (getIntent().getStringExtra(GlobalData.MEDIA_ID));
                expressionFor = (getIntent().getStringExtra(GlobalData.EXPRESSION_FOR));
                expression = getIntent().getIntExtra(GlobalData.EXPRESSION, 0);
                if (getIntent().getIntExtra(GlobalData.EXPRESSION, 0) != 0) {
                    int expression = getIntent().getIntExtra(GlobalData.EXPRESSION, 0);
                    if (expression == 1) {
                        tabLayout.getTabAt(0).select();
                    } else if (expression == 2) {
                        tabLayout.getTabAt(1).select();
                    } else if (expression == 3) {
                        tabLayout.getTabAt(3).select();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetExpressionList() {


        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }

            JSONObject jsonObject = new JSONObject();

            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.GET_EXPRESSION_MEDIA.MEDIA_ID, mediaId);
            jsonObject.put(WebField.GET_EXPRESSION_MEDIA.EXPRESSION_FOR, expressionFor);
            jsonObject.put(WebField.GET_EXPRESSION_MEDIA.PROFILE_ID, profileId);


            LogM.LogE("Request : Media Expression : " + jsonObject.toString());
            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_EXPRESSION_MEDIA.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Media Expression: " + jsonObject.toString());
                    if (isSuccess) {
                        ExpressionMedia express = new Gson().fromJson(String.valueOf(jsonObject), ExpressionMedia.class);


                        intLikeCount = express.getGoals().size() + express.getAdmiring().size() + express.getInspiring().size();
                        goalList.addAll(express.getGoals());
                        admiringList.addAll(express.getAdmiring());
                        inspiringList.addAll(express.getInspiring());
                        tabLayout.setupWithViewPager(viewPager);
                        setupViewPager(viewPager);
                        customTabIcons();
                        wrapTabIndicatorToTitle(tabLayout, 0, 0);
                        LogM.LogE("final postab====>" + expression);
                        if (expression == GlobalData.EXPRESSION_GOAL) {
                            tabLayout.getTabAt(0).select();
                        } else if (expression == GlobalData.EXPRESSION_INSPIRING) {
                            tabLayout.getTabAt(2).select();
                        } else if (expression == GlobalData.EXPRESSION_ADMIRING) {
                            tabLayout.getTabAt(1).select();
                        }

                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        tabLayout = binding.tabs;
        viewPager = binding.viewPager;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.post_expression_title);
    }

    private void setupViewPager(ViewPager viewPager) {
        PostExpressionForMediaActivityActivity.ViewPagerAdapter adapter = new PostExpressionForMediaActivityActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GoalMediaFragment.newInstance(goalList), getResources().getString(R.string.tab_goal));
        adapter.addFragment(AdmiringMediaFragment.newInstance(admiringList), getResources().getString(R.string.tab_admiring));
        adapter.addFragment(InspiringMediaFragment.newInstance(inspiringList), getResources().getString(R.string.tab_inspiring));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
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

    private void customTabIcons() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.nav_tab, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.tvtab);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.ivTabIcon);
            tab_label.setText(getResources().getString(navLabels[i]));

            tab_icon.setImageResource(navIcons[i]);
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:

//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("position", intFeedPosition);
//                returnIntent.putExtra("likeCount", intLikeCount);
//                setResult(Activity.RESULT_OK, returnIntent);

                finish();
                break;
        }
    }
}
