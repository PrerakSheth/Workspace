package com.konkr.Activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Fragment.HomeFeedFragment;
import com.konkr.Fragment.MiMealFragment;
import com.konkr.Fragment.MiSupplementFragment;
import com.konkr.Fragment.MiTrainingFragment;
import com.konkr.Fragment.MyPlayListFragment;
import com.konkr.Fragment.NotificationFragment;
import com.konkr.Fragment.PartnersFragment;
import com.konkr.Fragment.SearchUserFragment;
import com.konkr.Fragment.SettingsFragment;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.Meals;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.MyWorkouts;
import com.konkr.Models.SpotifyModel;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityMainBinding;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnRecyclerViewItemClickListener, MiTrainingFragment.FragmentCommunicator {

    private NavigationView navigationView;
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "homefeed";
    private static final String TAG_NOTIFICATION = "notification";
    private static final String TAG_MITRAINING = "mitraining";
    private static final String TAG_MEAL = "mimeal";
    private static final String TAG_MI_SUPPLEMENT = "misupplement";
    private static final String TAG_SEARCH_USER = "searchuser";
    private static final String TAG_PARTNERS = "partners";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private MyTextView toolbarTitle;
    private ImageButton ibToolbarAdd;
    private ImageButton ibToolbarMenu;
    private Handler mHandler;

    MenuItem nav_home_feed;

    SpannableString spannableStringColor = null;

    private ActivityMainBinding binding;

    private MyTextView tvHomeFeed;
    private MyTextView tvMiTraining;
    private MyTextView tvMiMeals;
    private MyTextView tvMiSupplement;
    private MyTextView tvSearchUser;
    private MyTextView tvSettings;
    private MyTextView tvPartners;
    private MyTextView tvNotification;
    private TextView tvNotiCount;


    private MyTextView tvName;
    private SimpleDraweeView ivUserProfile;
    private ImageView ivEdit;
    private ConstraintLayout clBell;
    private Context context;
    public static final int RESULT_CODE_PROFILE_SUPP = 113;
    public static final int RESULT_CODE_EDIT_PROFILE = 123;
    private SimpleDraweeView ivQRCode;
    //private ArrayList<MusicAndVideo> musicList;
    private ArrayList<SpotifyModel.Item> playList;
    private MyTextView tvNotificationCount;
    private int notiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (MyTextView) toolbar.findViewById(R.id.toolbarTitle);
        ibToolbarAdd = toolbar.findViewById(R.id.ibToolbarAdd);
        tvNotiCount = toolbar.findViewById(R.id.tvNotiCount);
        ibToolbarMenu = toolbar.findViewById(R.id.ibToolbarMenu);
        clBell = toolbar.findViewById(R.id.clBell);

        toolbar.setNavigationIcon(null);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(false);
        ibToolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtility.HideKeyboard(context, view);
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        context = MainActivity.this;

        tvName = (MyTextView) findViewById(R.id.tvName);
        ivUserProfile = (SimpleDraweeView) findViewById(R.id.ivUserProfile);
        ivEdit = (ImageView) findViewById(R.id.ivEdit);

        bindViews();
        setListner();
        setData();

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        navigationView = (NavigationView) findViewById(R.id.navigation);

        if (savedInstanceState == null) {
            tvHomeFeed.setTextColor(getResources().getColor(R.color.menu_text_color));
            tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
            tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
            tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
            tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
            tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
            tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
            tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
        }

        View navigationHeader = navigationView.getHeaderView(0);
        ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
//                profileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(profileIntent, RESULT_CODE_PROFILE_SUPP);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent editProfileIntent = new Intent(MainActivity.this, SetUpProfileActivity.class);
                editProfileIntent.putExtra(GlobalData.IS_FROM_PROFILE, 1);
                startActivityForResult(editProfileIntent, RESULT_CODE_EDIT_PROFILE);
            }

        });
        getIntentData();

    }

    private void getIntentData() {
        if (getIntent().getExtras() != null) {
            int notificationType = getIntent().getIntExtra(GlobalData.NOTIFICATION_TYPE, 0);
            int userId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
            int profileId = getIntent().getIntExtra(GlobalData.PROFILE_ID, 0);
            int expression = getIntent().getIntExtra(GlobalData.EXPRESSION, 0);
            LogM.LogE("postab==>" + expression);
            int mediaId = getIntent().getIntExtra(GlobalData.MEDIA_ID, 0);
            int comment_type = getIntent().getIntExtra(GlobalData.COMMENT_TYPE, 0);
            ArrayList<Meals.Meal> mealArrayList = getIntent().getParcelableArrayListExtra(GlobalData.MEALARRAY);
            ArrayList<MiSuppliment.SupplementsBean> supplementsBeanArrayList = getIntent().getParcelableArrayListExtra(GlobalData.SUPP_ARRAY);
            MyWorkouts.WorkoutsBean workout = getIntent().getParcelableExtra(GlobalData.WORKOUT);
            MyWorkouts.WorkoutsBean.WorkoutDurationBean workoutDuration = getIntent().getParcelableExtra("WorkoutDuration");
            List<MyWorkouts.WorkoutsBean.WorkoutMediaBean> workoutMedia = getIntent().getParcelableArrayListExtra("WorkoutMedia");
            UserDetails.UserDetailsBean.ImageArrayBean imageArrayBean = getIntent().getParcelableExtra(GlobalData.Array_List);


            String homeFeed_id = getIntent().getStringExtra(GlobalData.HOME_FEED_ID);
            int expressionFor = getIntent().getIntExtra(GlobalData.EXPRESSION_FOR, 0);

            playList = new ArrayList<>();
            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase("ConnectSpotify")) {
                if (getIntent().getParcelableArrayListExtra("playList") != null) {
                    playList = getIntent().getParcelableArrayListExtra("playList");
                    navItemIndex = 1;
                    CURRENT_TAG = TAG_MITRAINING;
//                    MyPlayListFragment.newInstance(0,playList);
//                    tvMiTraining.performClick();

                }
            }
            Intent intent = null;
            switch (notificationType) {
                case GlobalData.FOLLOWING_YOU: //1
                    intent = new Intent(this, FollowersFollowingActivity.class);
                    intent.putExtra(GlobalData.FROM, GlobalData.FOLLOWING_YOU_NOTIFICATION);
                    startActivity(intent);
                    break;
                case GlobalData.ADD_MEAL: //2
                    Intent mealIntent = new Intent(this, AddMiMealActivity.class);
                    mealIntent.putExtra(GlobalData.FROM, GlobalData.ADD_MEAL_NOTIFICATION);
                    mealIntent.putExtra(GlobalData.MEAL, mealArrayList);
                    mealIntent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    startActivity(mealIntent);
                    break;
                case GlobalData.ADD_WORKOUT: //3
                    Intent workoutIntent = new Intent(this, MyWorkoutDetailsActivity.class);
                    workoutIntent.putExtra(GlobalData.IS_FROM, GlobalData.ADD_WORKOUT_NOTIFICATION);
                    workoutIntent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    workoutIntent.putExtra("WorkoutDetails", workout);
                    workoutIntent.putExtra("WorkoutDuration", workoutDuration);
                    workoutIntent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workoutMedia);
                    startActivity(workoutIntent);
                    break;
                case GlobalData.ADD_SUPPLEMENT: //4
                    Intent suppIntent = new Intent(this, MiSupplementDetailActivity.class);
                    suppIntent.putExtra(GlobalData.FROM, GlobalData.ADD_SUPPLEMENT_NOTIFICATION);
                    suppIntent.putExtra(GlobalData.OTHER_USER_ID, userId);
                    suppIntent.putExtra(GlobalData.SUPP_ARRAY, supplementsBeanArrayList.get(0));
                    startActivity(suppIntent);
                    break;
                case GlobalData.GIVE_EXPRESSIONON_FEED: //5
                    if (workout != null) {
                        intent = new Intent(this, MyWorkoutDetailsActivity.class);
                        intent.putExtra(GlobalData.IS_FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                        intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                        intent.putExtra(GlobalData.USER_ID, userId);
                        intent.putExtra(GlobalData.EXPRESSION, expression);
                        intent.putExtra("WorkoutDetails", workout);
                        intent.putExtra("WorkoutDuration", workoutDuration);
                        intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workoutMedia);
                        startActivity(intent);
                    } else if (supplementsBeanArrayList != null && supplementsBeanArrayList.size() > 0) {
                        intent = new Intent(this, MiSupplementDetailActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                        intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                        intent.putExtra(GlobalData.EXPRESSION, expression);
                        intent.putExtra(GlobalData.SUPP_ARRAY, supplementsBeanArrayList.get(0));
                        startActivity(intent);
                    } else if (mealArrayList != null && mealArrayList.size() > 0) {
                        intent = new Intent(this, AddMiMealActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                        intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                        intent.putExtra(GlobalData.EXPRESSION, expression);
                        intent.putExtra(GlobalData.MEAL, mealArrayList);
                        startActivity(intent);
                    }
                    break;
                case GlobalData.COMMENTS: //6
//                    Intent cmIntent = new Intent(this, CommentsActivity.class);
//                    cmIntent.putExtra(GlobalData.FROM, GlobalData.COMMENTS_NOTIFICATION);
//                    cmIntent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
//                    startActivity(cmIntent);

                    if (workout != null) {
                        intent = new Intent(this, MyWorkoutDetailsActivity.class);
                        intent.putExtra(GlobalData.IS_FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                        intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                        intent.putExtra(GlobalData.EXPRESSION, expression);
                        intent.putExtra("WorkoutDetails", workout);
                        intent.putExtra("WorkoutDuration", workoutDuration);
                        intent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) workoutMedia);
                        startActivity(intent);
                    } else if (supplementsBeanArrayList != null && supplementsBeanArrayList.size() > 0) {
                        intent = new Intent(this, MiSupplementDetailActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                        intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                        intent.putExtra(GlobalData.EXPRESSION, expression);
                        intent.putExtra(GlobalData.SUPP_ARRAY, supplementsBeanArrayList.get(0));
                        startActivity(intent);
                    } else if (mealArrayList != null && mealArrayList.size() > 0) {
                        intent = new Intent(this, AddMiMealActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION);
                        intent.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                        intent.putExtra(GlobalData.EXPRESSION, expression);
                        intent.putExtra(GlobalData.MEAL, mealArrayList);
                        startActivity(intent);
                    }
                    break;
                case GlobalData.VARIFICATION_BADGE: //7
                    Intent vIntent = new Intent(this, VerificationBadgeActivity.class);
                    startActivity(vIntent);
                    break;
                case GlobalData.ADDEXPRESSIONUSERMEDIA: //10
                    Intent pIntent = new Intent(this, FullScreenProfileImages.class);
                    pIntent.putExtra(GlobalData.FROM, GlobalData.NOTIFICATION_MEDIA);
                    pIntent.putExtra(GlobalData.PROFILE_ID, profileId);
                    pIntent.putExtra(GlobalData.EXPRESSION_FOR, String.valueOf(expressionFor));
                    pIntent.putExtra(GlobalData.EXPRESSION, expression);
                    pIntent.putExtra(GlobalData.MEDIA_ID, String.valueOf(mediaId));
                    pIntent.putExtra(GlobalData.EXPRESSION, expression);
                    pIntent.putExtra(GlobalData.EXPRESSION, expression);
                    pIntent.putExtra(GlobalData.Array_List, imageArrayBean);
                    startActivity(pIntent);
                    break;

                case GlobalData.ADDCOMMENTMEDIA: //11
//                    Intent mcIntent = new Intent(this, MediaPhotoCommentActivity.class);
//                    mcIntent.putExtra(GlobalData.COMMENT_TYPE, String.valueOf(comment_type));
//                    mcIntent.putExtra(GlobalData.PROFILE_ID, profileId);
//                    mcIntent.putExtra(GlobalData.MEDIA_ID, String.valueOf(mediaId));
//                    startActivity(mcIntent);
                    Intent commentIntent = new Intent(this, FullScreenProfileImages.class);
                    commentIntent.putExtra(GlobalData.FROM, GlobalData.NOTIFICATION_MEDIA);
                    commentIntent.putExtra(GlobalData.PROFILE_ID, profileId);
                    commentIntent.putExtra(GlobalData.EXPRESSION_FOR, String.valueOf(expressionFor));
                    commentIntent.putExtra(GlobalData.EXPRESSION, expression);
                    commentIntent.putExtra(GlobalData.MEDIA_ID, String.valueOf(mediaId));
                    commentIntent.putExtra(GlobalData.EXPRESSION, expression);
                    commentIntent.putExtra(GlobalData.EXPRESSION, expression);
                    commentIntent.putExtra(GlobalData.Array_List, imageArrayBean);
                    startActivity(commentIntent);
                    break;
                case GlobalData.SUBSCRIBE: //13
                    Intent profile = new Intent(this, ProfileActivity.class);
                    profile.putExtra(GlobalData.OTHER_USER_ID, userId);
                    startActivity(profile);
                    break;
                case GlobalData.ADD_COMMENT_ON_TRAININGMEDIA: //13
                    Intent IntentVideoActivity = new Intent(this, VideoActivity.class);
                    IntentVideoActivity.putExtra(GlobalData.IS_FROM, GlobalData.NOTIFICATION_MEDIA);
                    IntentVideoActivity.putExtra(GlobalData.OTHER_USER_ID, userId);
                    IntentVideoActivity.putExtra("WorkoutDetails", workout);
                    IntentVideoActivity.putExtra("WorkoutMedia", workoutMedia.get(0));

                    if (Integer.parseInt(workoutMedia.get(0).getMediaType()) == GlobalData.MEDIA_TYPE_IMAGE) {
                        IntentVideoActivity.putExtra("mediaType", Integer.parseInt(workoutMedia.get(0).getMediaType()));
                        IntentVideoActivity.putExtra("imageUrl", workoutMedia.get(0).getUrl());
                    } else if (Integer.parseInt(workoutMedia.get(0).getMediaType()) == GlobalData.MEDIA_TYPE_VIDEO) {
                        IntentVideoActivity.putExtra("mediaType", Integer.parseInt(workoutMedia.get(0).getMediaType()));
                        IntentVideoActivity.putExtra("videoUrl", workoutMedia.get(0).getVideoURL());
                    }
                    startActivity(IntentVideoActivity);
                    break;
                case GlobalData.ADD_EXPRESSION_ON_TRAINING_MEDIA: //14
                    Intent expIntentVideoActivity = new Intent(this, VideoActivity.class);
                    expIntentVideoActivity.putExtra(GlobalData.IS_FROM, GlobalData.NOTIFICATION_MEDIA);
//                    expIntentVideoActivity.putExtra(GlobalData.OTHER_USER_ID, userId);
                    expIntentVideoActivity.putExtra(GlobalData.HOME_FEED_ID, homeFeed_id);
                    expIntentVideoActivity.putExtra(GlobalData.PROFILE_ID, profileId);
                    expIntentVideoActivity.putExtra("WorkoutDetails", workout);
                    expIntentVideoActivity.putExtra("WorkoutMedia", workoutMedia.get(0));
                    if (Integer.parseInt(workoutMedia.get(0).getMediaType()) == GlobalData.MEDIA_TYPE_IMAGE) {
                        expIntentVideoActivity.putExtra("mediaType", Integer.parseInt(workoutMedia.get(0).getMediaType()));
                        expIntentVideoActivity.putExtra("imageUrl", workoutMedia.get(0).getUrl());
                    } else if (Integer.parseInt(workoutMedia.get(0).getMediaType()) == GlobalData.MEDIA_TYPE_VIDEO) {
                        expIntentVideoActivity.putExtra("mediaType", Integer.parseInt(workoutMedia.get(0).getMediaType()));
                        expIntentVideoActivity.putExtra("videoUrl", workoutMedia.get(0).getVideoURL());
                    }
//                    expIntentVideoActivity.putExtra("mediaType",  workoutMedia.get(0).getMediaType());
//                    expIntentVideoActivity.putExtra("videoUrl",  workoutMedia.get(0).getVideoThumbImage());
//                    expIntentVideoActivity.putExtra("imageUrl",  workoutMedia.get(0).getUrl());

                    startActivity(expIntentVideoActivity);
                    break;

            }
        }

        loadHomeFragment();
    }

    private void setData() {
        tvName.setText(SessionManager.getFirstName(context) + " " + SessionManager.getLastName(context));

        Uri mUri = Uri.parse(SessionManager.getProfilePic(context));
        Fresco.getImagePipeline().evictFromMemoryCache(mUri);
        Fresco.getImagePipelineFactory().getMainBufferedDiskCache().remove(new SimpleCacheKey(mUri.toString()));
        Fresco.getImagePipelineFactory().getSmallImageFileCache().remove(new SimpleCacheKey(mUri.toString()));
        ivUserProfile.setImageURI(mUri);

        ivQRCode.setImageURI(Uri.parse(SessionManager.getQRCode(context)));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void notificationCount(int notificationcount) {
        Log.d("notificationcountcalled", "" + notificationcount);
        notiCount = notificationcount;

        Log.d("notiCount---->", "" + notiCount);
        if (notiCount > 0) {
            tvNotificationCount.setVisibility(View.VISIBLE);
            tvNotiCount.setVisibility(View.VISIBLE);
            if (notiCount < 10) {
                tvNotificationCount.setText("" + " " + notiCount + " ");
                tvNotiCount.setText("" + " " + notiCount + " ");
            } else {
                tvNotificationCount.setText(" " + notiCount + " ");
                tvNotiCount.setText(" " + notiCount + " ");
            }

        }
    }

    public void notificationCountTitlebar() {

        if (notiCount > 0) {
            ibToolbarAdd.setVisibility(View.VISIBLE);
            ibToolbarAdd.setImageResource(R.drawable.notification);
            tvNotiCount.setVisibility(View.VISIBLE);
            if (notiCount < 10) {
                tvNotiCount.setText("" + " " + notiCount + " ");
            } else {
                tvNotiCount.setText("" + notiCount);
            }

        }
    }

    public void hideAddButton() {
        if (CURRENT_TAG == TAG_MEAL || CURRENT_TAG == TAG_MITRAINING) {
            ibToolbarAdd.setVisibility(View.GONE);
        }
    }

    public void showAddButton() {
        if (CURRENT_TAG == TAG_MEAL || CURRENT_TAG == TAG_MITRAINING) {
            ibToolbarAdd.setVisibility(View.VISIBLE);
        }
    }

    public void checkAddButtonVisibility() {
        /**
         * below lines check and hide the plus signed button
         */
        if (CURRENT_TAG == TAG_MEAL) {
            MiMealFragment fm = (MiMealFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            int count = 0;
            for (int i = 0; i < fm.getAddedMealInfo().length; i++) {

                LogM.LogE("Array Element===>" + i + "  ====>" + fm.getAddedMealInfo()[i]);
                if (fm.getAddedMealInfo()[i] > 0) {
                    count++;
                    LogM.LogE("count=>>>>>>" + count);
                    if (count > 3) {
                        LogM.LogE("=====> you can not go to next acctivity and add button invisible count==>" + count);
                        //  ibToolbarAdd.setVisibility(View.GONE);
                    } else {
                        //  ibToolbarAdd.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

//    private void getIntentData() {
//        String daonation = getIntent().getStringExtra(GlobalData.DONATION);
//        if (daonation != null) {
//            if (getIntent().getStringExtra(GlobalData.DONATION).equalsIgnoreCase(GlobalData.DONATION)) {
//                tvSettings.performClick();
//                daonation = null;
//            }
//        }
//    }

    private void setListner() {
        tvHomeFeed.setOnClickListener(this);
        tvNotification.setOnClickListener(this);
        tvMiTraining.setOnClickListener(this);
        tvMiMeals.setOnClickListener(this);
        tvMiSupplement.setOnClickListener(this);
        tvSearchUser.setOnClickListener(this);
        tvPartners.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        ibToolbarAdd.setOnClickListener(this);
        ivQRCode.setOnClickListener(this);
        clBell.setOnClickListener(this);
    }

    private void bindViews() {
        tvHomeFeed = binding.tvHomeFeed;
        tvNotification = binding.tvNotification;
        tvMiTraining = binding.tvMiTraining;
        tvMiMeals = binding.tvMiMeals;
        tvMiSupplement = binding.tvMiSupplement;
        tvSearchUser = binding.tvSearchUser;
        tvPartners = binding.tvPartners;
        tvSettings = binding.tvSettings;
        ivQRCode = binding.ivQRCode;
        tvNotificationCount = binding.tvNotificationCount;
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setMenuColor(int navigationPostion) {
        Menu menu = navigationView.getMenu();
        SpannableString s = null;
        int positionOfMenuItem = navigationPostion; //or any other postion
        MenuItem item = menu.getItem(positionOfMenuItem);

        LogM.LogE("item-" + item + "-navigationPostion-" + navigationPostion);


        for (int i = 0; i < 6; i++) {

            if (i == navigationPostion) {
                if (navigationPostion == 0) {
                    s = new SpannableString(getResources().getString(R.string.menu_home_feed));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 1) {
                    s = new SpannableString(getResources().getString(R.string.menu_mi_training));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 2) {
                    s = new SpannableString(getResources().getString(R.string.menu_mi_meal));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 3) {
                    s = new SpannableString(getResources().getString(R.string.menu_mi_supplement));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 4) {
                    s = new SpannableString(getResources().getString(R.string.menu_search_user));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 5) {
                    s = new SpannableString(getResources().getString(R.string.menu_settings));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                }
            } else {
                if (navigationPostion == 0) {
                    s = new SpannableString(getResources().getString(R.string.menu_home_feed));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 1) {
                    s = new SpannableString(getResources().getString(R.string.menu_mi_training));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 2) {
                    s = new SpannableString(getResources().getString(R.string.menu_mi_meal));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 3) {
                    s = new SpannableString(getResources().getString(R.string.menu_mi_supplement));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 4) {
                    s = new SpannableString(getResources().getString(R.string.menu_search_user));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setTitle(s);
                }
                if (navigationPostion == 5) {
                    s = new SpannableString(getResources().getString(R.string.menu_settings));
                    s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setTitle(s);
                }
            }
        }
    }

    private Fragment getHomeFragment() {
        clBell.setOnClickListener(null);
        switch (navItemIndex) {
            case 0:
//                if (ibToolbarAdd.getVisibility() == View.GONE) {
                ibToolbarAdd.setVisibility(View.VISIBLE);
                //  tvNotiCount.setVisibility(View.VISIBLE);
                ibToolbarAdd.setImageResource(R.drawable.notification);
                clBell.setOnClickListener(this);
//                }
                HomeFeedFragment homeFragment = new HomeFeedFragment();
                return homeFragment;
            case 1:
//                if (ibToolbarAdd.getVisibility() == View.GONE) {
                ibToolbarAdd.setVisibility(View.VISIBLE);
                ibToolbarAdd.setImageResource(R.drawable.add);
                tvNotiCount.setVisibility(View.GONE);
//                }

                MiTrainingFragment miTrainingFragment = new MiTrainingFragment();
                Bundle args = new Bundle();
                if (playList.size() > 0) {
                    args.putString(GlobalData.FROM, GlobalData.MI_TRAINING);
                    args.putString(GlobalData.SPOTIFY_ID, SessionManager.getSpotifyUserId(context));
                    args.putInt(GlobalData.OTHER_USER_ID, 0);

                }
                args.putParcelableArrayList(GlobalData.ARG_MUSICLIST, playList);
                args.putString(GlobalData.FROM, GlobalData.MI_TRAINING);
                args.putString(GlobalData.SPOTIFY_ID, SessionManager.getSpotifyUserId(context));
                args.putInt(GlobalData.OTHER_USER_ID, 0);
                args.putString(GlobalData.USER_NAME, SessionManager.getFirstName(context));
                miTrainingFragment.setArguments(args);
                return miTrainingFragment;
            case 2:
//                if (ibToolbarAdd.getVisibility() == View.GONE) {
                ibToolbarAdd.setVisibility(View.VISIBLE);
                ibToolbarAdd.setImageResource(R.drawable.add);
                tvNotiCount.setVisibility(View.GONE);
//                }
                MiMealFragment miMealFragment = new MiMealFragment();
                return miMealFragment;
            case 3:

//                if (ibToolbarAdd.getVisibility() == View.GONE) {
                ibToolbarAdd.setVisibility(View.VISIBLE);
                ibToolbarAdd.setImageResource(R.drawable.add);
                tvNotiCount.setVisibility(View.GONE);
//                }
                MiSupplementFragment notificationsFragment = new MiSupplementFragment();
                return notificationsFragment;
            case 4:
                if (ibToolbarAdd.getVisibility() == View.VISIBLE) {
                    ibToolbarAdd.setVisibility(View.GONE);
                    tvNotiCount.setVisibility(View.GONE);
                }
                SearchUserFragment searchUserFragment = new SearchUserFragment();
                return searchUserFragment;
            case 5:
                if (ibToolbarAdd.getVisibility() == View.VISIBLE) {
                    ibToolbarAdd.setVisibility(View.GONE);
                    tvNotiCount.setVisibility(View.GONE);
                }
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            case 6:
                if (ibToolbarAdd.getVisibility() == View.VISIBLE) {
                    ibToolbarAdd.setVisibility(View.GONE);
                    tvNotiCount.setVisibility(View.GONE);
                }
                PartnersFragment partnersFragment = new PartnersFragment();
                return partnersFragment;
            case 7:
                if (ibToolbarAdd.getVisibility() == View.VISIBLE) {
                    ibToolbarAdd.setVisibility(View.GONE);
                    tvNotiCount.setVisibility(View.GONE);
                }
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;
            default:
                return new HomeFeedFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(activityTitles[navItemIndex]);
    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
//        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
//        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
//            drawer.closeDrawers();
//
//            // show or hide the fab button
//            toggleFab();
//            return;
//        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
//        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    @Override
    public void onClick(View view) {
        LogM.LogE("CURRENT_TAG--    " + CURRENT_TAG);
        switch (view.getId()) {
            case R.id.ibToolbarAdd:
                if (!ConnectivityDetector.isConnectingToInternet(MainActivity.this)) {
                    AlertDialogUtility.showInternetAlert(MainActivity.this);
                    return;
                }
                if (CURRENT_TAG == TAG_HOME) {
                    clBell.performClick();
                }
                if (CURRENT_TAG == TAG_MITRAINING) {
                    Intent miTrainingIntent = new Intent(MainActivity.this, MiTrainingActivity.class);
                    startActivity(miTrainingIntent);
                } else if (CURRENT_TAG == TAG_MI_SUPPLEMENT) {
                    Intent intent = new Intent(MainActivity.this, MiSupplimentActivity.class);
                    startActivityForResult(intent, 222);
                } else if (CURRENT_TAG == TAG_MEAL) {
                    MiMealFragment fm = (MiMealFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                    Intent intent = new Intent(MainActivity.this, AddMiMealActivity.class);
                    intent.putExtra(GlobalData.AddMeal, GlobalData.AddMeal);
                    intent.putExtra("myArray", fm.getAddedMealInfo());
                    for (int i = 0; i < fm.getAddedMealInfo().length; i++) {
                        LogM.LogV("selected meal===>" + fm.getAddedMealInfo()[i]);
                    }
//                    startActivityForResult (intent, 222);
                    intent.putExtra("noOfMeal", fm.getUserSelectedMealCount());
                    LogM.LogE("no of meal User wants to add First Time===>" + fm.getUserSelectedMealCount());
                    startActivityForResult(intent, 500);
                }
                return;

            case R.id.tvHomeFeed:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                notiCount = 0;
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;

            case R.id.tvNotification:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotificationCount.setVisibility(View.GONE);
                navItemIndex = 7;
                CURRENT_TAG = TAG_NOTIFICATION;
                break;

            case R.id.tvMiTraining:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                navItemIndex = 1;
                CURRENT_TAG = TAG_MITRAINING;
                break;

            case R.id.tvMiMeals:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                navItemIndex = 2;
                CURRENT_TAG = TAG_MEAL;
                break;

            case R.id.tvMiSupplement:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                navItemIndex = 3;
                CURRENT_TAG = TAG_MI_SUPPLEMENT;
                break;

            case R.id.tvSearchUser:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                navItemIndex = 4;
                CURRENT_TAG = TAG_SEARCH_USER;
                break;

            case R.id.tvPartners:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvPartners.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                navItemIndex = 6;
                CURRENT_TAG = TAG_PARTNERS;
                break;

            case R.id.tvSettings:
                tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                tvNotification.setTextColor(getResources().getColor(R.color.hint_color));
                tvSettings.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                navItemIndex = 5;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case R.id.clBell:
                if (CURRENT_TAG == TAG_HOME) {
//                    startActivity(new Intent(MainActivity.this,NotificationsActivity.class));
//                    finish();
                    tvHomeFeed.setTextColor(getResources().getColor(R.color.hint_color));
                    tvNotification.setTextColor(getResources().getColor(R.color.menu_text_color));
                    tvMiTraining.setTextColor(getResources().getColor(R.color.hint_color));
                    tvMiMeals.setTextColor(getResources().getColor(R.color.hint_color));
                    tvMiSupplement.setTextColor(getResources().getColor(R.color.hint_color));
                    tvSearchUser.setTextColor(getResources().getColor(R.color.hint_color));
                    tvSettings.setTextColor(getResources().getColor(R.color.hint_color));
                    tvPartners.setTextColor(getResources().getColor(R.color.hint_color));
                    tvNotificationCount.setVisibility(View.GONE);
                    navItemIndex = 7;
                    CURRENT_TAG = TAG_NOTIFICATION;
                }
                break;
            case R.id.ivQRCode:
                Intent intent = new Intent(context, FullScreenQRCodeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                return;
            default:
                navItemIndex = 0;

        }
        loadHomeFragment();
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 222 && resultCode == RESULT_OK) {
            MiSupplementFragment fragment = (MiSupplementFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            fragment.update();
        }

        if (requestCode == RESULT_CODE_PROFILE_SUPP && resultCode == RESULT_OK) {
            if (data.getStringExtra(GlobalData.FRAG_NAME_KEY).equalsIgnoreCase(GlobalData.TRAINING)) {
                tvMiTraining.performClick();
            }
            if (data.getStringExtra(GlobalData.FRAG_NAME_KEY).equalsIgnoreCase(GlobalData.SUPPLEMENT)) {
                tvMiSupplement.performClick();
            }
            if (data.getStringExtra(GlobalData.FRAG_NAME_KEY).equalsIgnoreCase(GlobalData.MI_MEAL)) {
                tvMiMeals.performClick();
            }

        }

        if (requestCode == 500 && resultCode == RESULT_OK) {
            MiMealFragment fragment = (MiMealFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            fragment.callGetMiMeal(0);
        }

        if (requestCode == GlobalData.REQ_DELETE_SUPPLIMENT && resultCode == RESULT_OK) {

            MiSupplementFragment fragment = (MiSupplementFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            fragment.update();
        }

        if (requestCode == GlobalData.REQ_FROM_LIKEDETAILS_TO_HOMEFEED && resultCode == RESULT_OK) {
            new HomeFeedFragment().onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == GlobalData.COMMENT_REQ && resultCode == RESULT_OK) {
            int CommentCount = data.getIntExtra(GlobalData.LATEST_COUNT, 0);
            int pos = data.getIntExtra(GlobalData.POSITION, 0);
            HomeFeedFragment fragment = (HomeFeedFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            fragment.updateComment(CommentCount, pos);
        }

        if (requestCode == GlobalData.REQUEST_CODE && resultCode == RESULT_OK) {
            // MiTrainingFragment miTrainingFragment = new MiTrainingFragment();
//            MyPlayListFragment.newInstance(0, playList);
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                LogM.LogE("Main Activity TOKEN==>" + response.getAccessToken());
                SessionManager.setSpotifyToken(context, response.getAccessToken());
            }
            navItemIndex = 1;
            CURRENT_TAG = TAG_MITRAINING;
            MiTrainingFragment fm = (MiTrainingFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            fm.updatePlayListData();
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        LogM.LogE("you have clicked meal  in Main Activity" + position);
    }

    @Override
    public void updateList(MiTrainingFragment.ViewPagerAdapter adapter) {
        MyPlayListFragment fragment = (MyPlayListFragment) adapter.getItem(1);
        if (fragment != null) {
            fragment.updatePlayList(SessionManager.getSpotifyUserId(context));
        } else {

        }
    }

    public void reFreshMimeal() {
        MiMealFragment fragment = (MiMealFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        fragment.reFreshUi();
    }

    public void ClickonFeedName(int position) {
        LogM.LogE("Click On last text Position===>" + position);
        HomeFeedFragment fragment = (HomeFeedFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        fragment.onItemClickListener(tvMiMeals, position);

        //you have to work from here
    }
}
