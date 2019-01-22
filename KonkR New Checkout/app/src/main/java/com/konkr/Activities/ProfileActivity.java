package com.konkr.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.konkr.Adapters.BreakfastTabAdapter;
import com.konkr.Adapters.DinnerTabAdapter;
import com.konkr.Adapters.ImageListAdpater;
import com.konkr.Adapters.LunchTabAdapter;
import com.konkr.Adapters.MiMealProfileAdapter;
import com.konkr.Adapters.MiSuppliAdapter;
import com.konkr.Adapters.SnackTabAdapter;
import com.konkr.Fragment.MyPlayListFragment;
import com.konkr.Fragment.MyWorkoutsFragment;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Interfaces.ProfilePlaylistCommunicator;
import com.konkr.Models.CommonMessageStatus;
import com.konkr.Models.IntroductoryVideo;
import com.konkr.Models.Meals;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.SpotifyModel;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.CustomViewPager;
import com.konkr.Utils.FileHandeling;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyClass;
import com.konkr.Utils.MyTextView;
import com.konkr.Utils.PermissionsHelper;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityProfileBinding;
import com.vincent.videocompressor.VideoCompress;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, MiSuppliAdapter.ItemClickListener, ProfilePlaylistCommunicator, OnRecyclerViewItemClickListener,MiMealProfileAdapter.ProfileMealClick{

    private ActivityProfileBinding binding;
    private ImageButton ibBack;
    private TextView tvFollow, tvUserName, tvFollowersCount, tvSubscribersCount,
            tvTabProfile, tvTabMeals, tvTabTraining, tvTabSupps, tvFollowers, tvSubscribers;
    private SimpleDraweeView ivUserProfile;
    private ImageButton ibBadge, ibMedia;
    int check = 0;
    private String userName;
    private ArrayList<SpotifyModel.Item> playList = new ArrayList<>();
    private int[] navLabels = {
            R.string.tab_my_workouts,
            R.string.tab_my_playlist,
    };
    // Profile
    private NestedScrollView svProfile;
    private ConstraintLayout clMeals, clTraining, clSupps, clImages;
    private TextView tvAgeValue, tvGenderValue, tvCurrentTrainingGoalsValue;
    private ImageView ivEctomorph, ivMesomorph, ivEndomorph;
    private TextView tvEctomorph, tvMesomorph, tvEndomorph;
    private TextView tvWeight, tvHeight, tvFat, tvBioDesc;
    private ImageButton ib1, ib2, ib3, ib4, ib5, ib6;
    private LinearLayout llSocialLinks;
    private RecyclerView rvMiSuppliment;
    private RecyclerView rvMiMeal, rvLunch, rvSnack, rvDinner, rvImages;
    private MyTextView tvMiSupplementEmpty, tvMiMealEmpty, tvBreakfast, tvLunch, tvSnack, tvDinner, tvNumberOfMealsCount, tvImages;
    private MyTextView tvViewAll, tvViewAllMeal;
    private static MyTextView tvViewAllTraining;
    private SimpleDraweeView ivBreakFastMealPhoto, ivLunchMealPhoto, ivSnackMealPhoto, ivDinnerMealPhoto;//-------------------------
    private Activity context;

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private int myplaylistClick=0;

    ArrayList<UserDetails.UserDetailsBean> alUserDetails = new ArrayList<>();
    UserDetails.UserDetailsBean mealList;
    UserDetails.UserDetailsBean userDetailsBean;


    ArrayList<UserDetails.UserDetailsBean.SupplementsBean> alSupplement = new ArrayList<>();
    private ArrayList<MiSuppliment.SupplementsBean> alSupplementDummy = new ArrayList<>();
    //    ArrayList<UserDetails.UserDetailsBean.SocialLinksBean> alSocialLinks = new ArrayList<>();
    ArrayList<UserDetails.UserDetailsBean.SocialLinksBean> alTempSocialLinks = new ArrayList<>();
    //    String[] links = new String[6];
    ArrayList<Links> arrayList;

//    private ArrayList<UserDetails.UserDetailsBean.MealsBreakfastBean> mealsBreakfastBeanArrayList = new ArrayList<>();
//    private ArrayList<UserDetails.UserDetailsBean.MealsLunchBean> mealsLunchBeanArrayList = new ArrayList<>();
//    private ArrayList<UserDetails.UserDetailsBean.MealsSnacksBean> mealsSnacksBeanArrayList = new ArrayList<>();
//    private ArrayList<UserDetails.UserDetailsBean.MealsDinnerBean> mealsDinnerBeanArrayList = new ArrayList<>();

    private ArrayList<ArrayList<UserDetails.UserDetailsBean.Meal>> alMeal = new ArrayList<>();

    private RecyclerView rvMeals;


    private ArrayList<UserDetails.UserDetailsBean.ImageArrayBean> imageArrayBeanArrayList;
    private ImageListAdpater imageListAdpater;

    private MiSuppliAdapter suppAdapter;
    private BreakfastTabAdapter breakfastTabAdapter;
    private LunchTabAdapter lunchTabAdapter;
    private SnackTabAdapter snackTabAdapter;
    private DinnerTabAdapter dinnerTabAdapter;
    private MiMealProfileAdapter mealAdapter;


    private View snackBarView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager meallinearLayoutManager;

    int otherUserId = 0;
    String isFollowing;
    String spotifyId="";
    int isSubscribeForUser;

    private MyTextView tvImageEmpty;
    private MyTextView tvAddIntroductoryVideo;

    private String base64Thumb = "";
    private int spliteCount;
    private ArrayList<String> fileNameArr;
    private FileHandeling fileHandeling;
    private int totalSplite = 1;
    private ConstraintLayout clVideoView, clAddIntroductoryVideo;
    private SimpleDraweeView ivIntroductoryVideo;
    private int isSubscribed = 0;
    private ImageButton ibEdit;
    private MyTextView tvSubscribe;

    ViewPagerAdapter adapter;
    ScrollView scrollViewMain;
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        snackBarView = findViewById(android.R.id.content);
        context = ProfileActivity.this;
        bindViews();
        setListener();
        setLayoutManger();
        tvTabProfile.performClick();
        otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
        LogM.LogE("other User Id================>" + otherUserId);
        getNotificationIntentData();
//        callUserDetails();
        setAdapter();
//        if (otherUserId == 0) {
//            tvFollow.setVisibility(View.GONE);
//        } else {
//            tvFollow.setVisibility(View.VISIBLE);
//        }

        if (SessionManager.getUserId(context) == otherUserId || otherUserId == 0) {
            tvFollow.setVisibility(View.GONE);
            ibEdit.setVisibility(View.VISIBLE);
            tvSubscribe.setVisibility(View.GONE);
        } else {
            tvFollow.setVisibility(View.VISIBLE);
            ibEdit.setVisibility(View.GONE);
            tvSubscribe.setVisibility(View.VISIBLE);
        }

        tabLayout.setupWithViewPager(viewPager);


        tvBioDesc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.getId() == R.id.tvBioDesc) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });




//        if (getIntent().getExtras() != null) {
        if (getIntent().getParcelableArrayListExtra(GlobalData.ARG_MUSICLIST) != null) {
            playList = getIntent().getParcelableArrayListExtra(GlobalData.ARG_MUSICLIST);
            LogM.LogE("PROFILE SIZE::::: " + playList.size());
        }
//        }

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(GlobalData.FROM) != null) {
                if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.MEAL)) {
                    tvTabMeals.performClick();
//                    tabLayout.getTabAt(1).select();
                } else if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.SUPPLEMENT)) {
                    tvTabSupps.performClick();
//                    tabLayout.getTabAt(4).select();
                } else if (getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.WORKOUT)) {
                    tvTabTraining.performClick();
//                    tabLayout.getTabAt(3).select();
                }
//                else {
//                    tvTabProfile.performClick();
//                }
            }
        }
        setTabListner();


//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                viewPager.reMeasureCurrentPage(viewPager.getCurrentItem());
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private void getNotificationIntentData() {
        if (getIntent().getExtras() != null && getIntent().getStringExtra(GlobalData.FROM) != null &&
                getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.ADD_MEAL_NOTIFICATION)) {
            tvTabMeals.performClick();
        } else if (getIntent().getExtras() != null && getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.ADD_SUPPLEMENT_NOTIFICATION)) {
            tvTabSupps.performClick();
        } else if (getIntent().getExtras() != null && getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.ADD_WORKOUT_NOTIFICATION)) {
            tvTabTraining.performClick();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        llSocialLinks.removeAllViews();
//        if(a==0){
            callUserDetails();
//        }



    }

    private void setAdapter() {
        suppAdapter = new MiSuppliAdapter(ProfileActivity.this, context, alSupplementDummy, alSupplement);
        rvMiSuppliment.setAdapter(suppAdapter);
    }

    @Override
    public void updateList(ViewPagerAdapter adapter) {
        MyPlayListFragment fragment = (MyPlayListFragment) adapter.getItem(1);
        if ((SessionManager.getSpotifyToken(context).equalsIgnoreCase(null)) || SessionManager.getSpotifyToken(context).isEmpty()) {
            LogM.LogE("Spotify Token==>" + SessionManager.getSpotifyToken(context));
//                            Intent intent = new Intent(context, ConnectSpotify.class);
//                            intent.putExtra(GlobalData.FROM, GlobalData.PROFILE_ACTIVITY);
//                            intent.putExtra(GlobalData.SPOTIFY_ID, spotifyId);
//                            intent.putExtra(GlobalData.OTHER_USER_ID,otherUserId);
//                            LogM.LogE("otheruse id "+otherUserId);

//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //  startActivity(intent);
            if (fragment != null) {
                fragment.passOtheruserId(otherUserId);
            }
        } else {
            //  get Play list API call

            if (fragment != null) {
                fragment.updatePlayList(spotifyId);
            } else {

            }
        }


    }
//////////////////////
    @Override
    public void onItemClickListener(View view, int position) {

///<  ====================fsafsafaf=====================================         >
        LogM.LogE("you have clicked meal yahooo  " + position);
        LogM.LogE("other User Id  " + otherUserId);
        ArrayList<Meals.Meal> alMeal= new ArrayList<>();

        if (alUserDetails.get(position).getMealsBreakfast().size() > 0) {
//            itemClick.onItemClickListener(view, position);

            for(int i=0;i<alUserDetails.get(position).getMealsBreakfast().size();i++){
                alMeal.add(new Meals.Meal(alUserDetails.get(position).getMealsBreakfast().get(i).getMealId(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getMealPhoto(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getMealType(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getFoodName(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getMealName(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getUnit(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getQuantity(),
//                         String homeFeedId, int is_inspiring,
// int is_goals, int is_admiring, int expressionCount, int commentCount
                        alUserDetails.get(position).getMealsBreakfast().get(i).getHomeFeedId(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getIs_inspiring(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getIs_goals(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getIs_admiring(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getExpressionCount(),
                        alUserDetails.get(position).getMealsBreakfast().get(i).getCommentCount()
                ));
            }

            LogM.LogE("size of list"+alMeal.size()+ alMeal.get(0).getFoodName());

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
//            context.startActivity(intent);
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);
        } else if (alUserDetails.get(position).getMealsSnacks().size() > 0) {
            // itemClick.onItemClickListener(view, position);
            for(int i=0;i<alUserDetails.get(position).getMealsSnacks().size();i++){
                alMeal.add(new Meals.Meal(alUserDetails.get(position).getMealsSnacks().get(i).getMealId(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getMealPhoto(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getMealType(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getFoodName(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getMealName(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getUnit(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getQuantity(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getHomeFeedId(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getIs_inspiring(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getIs_goals(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getIs_admiring(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getExpressionCount(),
                        alUserDetails.get(position).getMealsSnacks().get(i).getCommentCount()
                ));
            }

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.MEAL, alMeal);
            intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
            context.startActivity(intent);
        } else if (alUserDetails.get(position).getMealsLunch().size() > 0) {
            // itemClick.onItemClickListener(view, position);
            for(int i=0;i<alUserDetails.get(position).getMealsLunch().size();i++){
                alMeal.add(new Meals.Meal(alUserDetails.get(position).getMealsLunch().get(i).getMealId(),
                        alUserDetails.get(position).getMealsLunch().get(i).getMealPhoto(),
                        alUserDetails.get(position).getMealsLunch().get(i).getMealType(),
                        alUserDetails.get(position).getMealsLunch().get(i).getFoodName(),
                        alUserDetails.get(position).getMealsLunch().get(i).getMealName(),
                        alUserDetails.get(position).getMealsLunch().get(i).getUnit(),
                        alUserDetails.get(position).getMealsLunch().get(i).getQuantity(),
                        alUserDetails.get(position).getMealsLunch().get(i).getHomeFeedId(),
                        alUserDetails.get(position).getMealsLunch().get(i).getIs_inspiring(),
                        alUserDetails.get(position).getMealsLunch().get(i).getIs_goals(),
                        alUserDetails.get(position).getMealsLunch().get(i).getIs_admiring(),
                        alUserDetails.get(position).getMealsLunch().get(i).getExpressionCount(),
                        alUserDetails.get(position).getMealsLunch().get(i).getCommentCount()
                ));
            }
            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);
        } else if (alUserDetails.get(position).getMealsDinner().size() > 0) {

            for(int i=0;i<alUserDetails.get(position).getMealsDinner().size();i++) {
                alMeal.add(new Meals.Meal(alUserDetails.get(position).getMealsDinner().get(i).getMealId(),
                        alUserDetails.get(position).getMealsDinner().get(i).getMealPhoto(),
                        alUserDetails.get(position).getMealsDinner().get(i).getMealType(),
                        alUserDetails.get(position).getMealsDinner().get(i).getFoodName(),
                        alUserDetails.get(position).getMealsDinner().get(i).getMealName(),
                        alUserDetails.get(position).getMealsDinner().get(i).getUnit(),
                        alUserDetails.get(position).getMealsDinner().get(i).getQuantity(),
                        alUserDetails.get(position).getMealsDinner().get(i).getHomeFeedId(),
                        alUserDetails.get(position).getMealsDinner().get(i).getIs_inspiring(),
                        alUserDetails.get(position).getMealsDinner().get(i).getIs_goals(),
                        alUserDetails.get(position).getMealsDinner().get(i).getIs_admiring(),
                        alUserDetails.get(position).getMealsDinner().get(i).getExpressionCount(),
                        alUserDetails.get(position).getMealsDinner().get(i).getCommentCount()
                ));
            }

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID,  otherUserId);
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);
        }

        else if (alUserDetails.get(position).getOtherMealData().size() > 0) {
            // itemClick.onItemClickListener(view, position);
            LogM.LogE("MAIN arrayLIST SIze==>"+ alUserDetails.get(position).getOtherMealData().size());
            for (int j = 0; j < alUserDetails.get(position).getOtherMealData().size(); j++) {
//                alUserDetails.get(position).getOtherMealData()
                LogM.LogE("Inner arrayLIST SIze==>" + alUserDetails.get(position).getOtherMealData().get(j).size());
                for (int i = 0; i < alUserDetails.get(position).getOtherMealData().get(j).size(); i++) {
                    alMeal.add(new Meals.Meal(alUserDetails.get(position).getOtherMealData().get(j).get(i).getMealId(),
                                    alUserDetails.get(position).getOtherMealData().get(j).get(i).getMealPhoto(),
                                    alUserDetails.get(position).getOtherMealData().get(j).get(i).getMealType(),
                                    alUserDetails.get(position).getOtherMealData().get(j).get(i).getFoodName(),
                                    alUserDetails.get(position).getOtherMealData().get(j).get(i).getMealName(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getUnit(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getQuantity(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getHomeFeedId(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getIs_inspiring(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getIs_goals(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getIs_admiring(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getExpressionCount(),
                            alUserDetails.get(position).getOtherMealData().get(j).get(i).getCommentCount()
                    ));
                }
            }

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);

        }


    }

    @Override
    public void mealItemClick(int pos) {
        LogM.LogE("uu cllicked==>");
        ArrayList<Meals.Meal> arrayList= new ArrayList<>();
        if(alMeal.get(pos).size()>0){
            for(int i=0;i<alMeal.get(pos).size();i++){
                arrayList.add(new Meals.Meal(alMeal.get(pos).get(i).getMealId(),
                        alMeal.get(pos).get(i).getMealPhoto(),
                        alMeal.get(pos).get(i).getMealType(),
                        alMeal.get(pos).get(i).getFoodName(),
                        alMeal.get(pos).get(i).getMealName(),
                        alMeal.get(pos).get(i).getUnit(),
                        alMeal.get(pos).get(i).getQuantity(),

                        alMeal.get(pos).get(i).getHomeFeedId(),
                        alMeal.get(pos).get(i).getIs_inspiring(),
                        alMeal.get(pos).get(i).getIs_goals(),
                        alMeal.get(pos).get(i).getIs_admiring(),
                        alMeal.get(pos).get(i).getExpressionCount(),
                        alMeal.get(pos).get(i).getCommentCount()));

            }
        }


        Intent intent= new Intent(context, AddMiMealActivity.class);
        intent.putExtra(GlobalData.MEAL,arrayList);
        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
        context.startActivityForResult(intent,GlobalData.MEAL_DETAIL_REQ);


    }

    class Links {
        String key;
        String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(context);
        rvMiSuppliment.setLayoutManager(linearLayoutManager);
        meallinearLayoutManager = new LinearLayoutManager(context);
        rvMeals.setLayoutManager(meallinearLayoutManager);
    }

    private Uri mImageUri;
    private Bitmap bitmap = null;
    private String strNineImageBase64 = "";
    private String result = "";
    public Uri selectedUri = null;
    private final String strCompressedVideo = "/Video/CompressedVideo";
    public File file;
    private int count = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalData.PREMIUM_MEMBER) {
            callUserDetails();
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == GlobalData.CAPTURE_VIDEO && data != null) {
                try {
                    result = data.getStringExtra("result");
                    selectedUri = Uri.parse(result);
                    compressVideo(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        if (requestCode == GlobalData.MEAL_DETAIL_REQ) {
            callUserDetails();
            return;
        }
    }

    private void compressVideo(final String input) {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            File compressedVideoFolder = new File(Environment.getExternalStorageDirectory().toString() + "/" + GlobalData.APP_NAME + strCompressedVideo);
            if (!compressedVideoFolder.exists()) {
                compressedVideoFolder.mkdir();
            }

            final String outPath = Environment.getExternalStorageDirectory().toString() + "/" + GlobalData.APP_NAME + strCompressedVideo + "/out" + count + GlobalData.VIDEO_FORMATE;

            VideoCompress.compressVideoMedium(input, outPath, new VideoCompress.CompressListener() {

                @Override
                public void onStart() {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getString(R.string.please_wait));
                    progressDialog.setTitle("");
                    progressDialog.show();
                }

                @Override
                public void onSuccess() {
                    file = new File(outPath);
                    spliteFile(file);
                    uploadIntroductoryVideo();
                }

                @Override
                public void onFail() {
                }

                @Override
                public void onProgress(float percent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void spliteFile(File f) {
        try {
            Bitmap bitmap = GlobalMethods.getThumbFromVideo(f.getAbsolutePath());
            base64Thumb = getBase64String(bitmap);
            spliteCount = 0;
            fileNameArr = new ArrayList<>();
            fileHandeling = new FileHandeling(getApplicationContext());
            fileNameArr = fileHandeling.splitFileTobase64(f);
            totalSplite = fileNameArr.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return base64String;
    }

    private void callUserDetails() {
        if (ConnectivityDetector.isConnectingToInternet(context)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                jsonObject.put(WebField.USER_DETAILS.PARAM_OTHERUSERID, otherUserId);
                LogM.LogE("Request : UserDetails : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.USER_DETAILS.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final UserDetails userDetails = new Gson().fromJson(String.valueOf(jsonObject), UserDetails.class);
                        if (isSuccess) {
                            LogM.LogE("Response : UserDetails : " + jsonObject.toString());
                            isSubscribed = userDetails.getUserDetails().getIsSubscribed();
//                            SessionManager.setSubscribed(context, isSubscribed);
                            introductoryVideoURL = userDetails.getUserDetails().getVideoThumb().trim();
                            if (introductoryVideoURL.length() > 0) {
                                tvAddIntroductoryVideo.setVisibility(View.GONE);
                                clVideoView.setVisibility(View.VISIBLE);
                                ivIntroductoryVideo.setImageURI(Uri.parse(userDetails.getUserDetails().getVideoThumbImg().toString()));
                            } else {
                                tvAddIntroductoryVideo.setVisibility(View.VISIBLE);
                                clVideoView.setVisibility(View.GONE);
                                if (otherUserId != 0) {
                                    clAddIntroductoryVideo.setVisibility(View.GONE);
                                }
                            }
                            a=1;
                            alUserDetails.clear();
                            alUserDetails.add(userDetails.getUserDetails());
                            arrayList = new ArrayList<>();
                            Links link1 = new Links();
                            link1.setKey("FB");
                            link1.setValue(userDetails.getUserDetails().getSocialLinks().getFB());
                            arrayList.add(link1);
                            Links link2 = new Links();
                            link2.setKey("Instagram");
                            link2.setValue(userDetails.getUserDetails().getSocialLinks().getInstagram());
                            arrayList.add(link2);
                            Links link3 = new Links();
                            link3.setKey("Snapchat");
                            link3.setValue(userDetails.getUserDetails().getSocialLinks().getSnapchat());
                            arrayList.add(link3);
                            Links link4 = new Links();
                            link4.setKey("Twitter");
                            link4.setValue(userDetails.getUserDetails().getSocialLinks().getTwitter());
                            arrayList.add(link4);
                            Links link5 = new Links();
                            link5.setKey("Youtube");
                            link5.setValue(userDetails.getUserDetails().getSocialLinks().getYoutube());
                            arrayList.add(link5);
                            Links link6 = new Links();
                            link6.setKey("LinkedIn");
                            link6.setValue(userDetails.getUserDetails().getSocialLinks().getLinkedIn());
                            arrayList.add(link6);

                            userDetailsBean = userDetails.getUserDetails();
                            // mealList=userDetails.getUserDetails().

                            alSupplement.clear();
                            alSupplement.addAll(userDetails.getUserDetails().getSupplements());
                            /**
                             * filling Mi meal 4 arraylists
                             *
                             */

                            alMeal.clear();
                            if (userDetails.getUserDetails().getMealsBreakfast().size() > 0)
                                alMeal.add(userDetails.getUserDetails().getMealsBreakfast());
                            if (userDetails.getUserDetails().getMealsLunch().size() > 0)
                                alMeal.add(userDetails.getUserDetails().getMealsLunch());
                            if (userDetails.getUserDetails().getMealsSnacks().size() > 0)
                                alMeal.add(userDetails.getUserDetails().getMealsSnacks());
                            if (userDetails.getUserDetails().getMealsDinner().size() > 0)
                                alMeal.add(userDetails.getUserDetails().getMealsDinner());

                            for (ArrayList<UserDetails.UserDetailsBean.Meal> arrayList : userDetails.getUserDetails().getOtherMealData()) {
                                if (arrayList.size() > 0)
                                    alMeal.add(arrayList);
                            }
                            tvNumberOfMealsCount.setText("" + alMeal.size());
                            if (alMeal.size() > 0) {
                                rvMeals.setVisibility(View.VISIBLE);
                                tvMiMealEmpty.setVisibility(View.GONE);
                                mealAdapter = new MiMealProfileAdapter(context,ProfileActivity.this, alMeal);
                                rvMeals.setAdapter(mealAdapter);
                            } else {
                                tvMiMealEmpty.setVisibility(View.VISIBLE);
                                rvMeals.setVisibility(View.GONE);
                            }
                            spotifyId=userDetails.getUserDetails().getSpotifyUserId();
                            if(spotifyId.isEmpty()){
                                spotifyId="NA";
                            }
                            userName=userDetails.getUserDetails().getFirstName();
                            LogM.LogE("spotifyId==>"+spotifyId);
                            LogM.LogE("spotifyId==>"+userName);
                            LogM.LogE("Size of mela ArrayList=>" + alMeal.size());

                            setupViewPager(viewPager);
                            customTabText();
                            wrapTabIndicatorToTitle(tabLayout, 100, 50);

                            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.CONNECTSPOTIFY)) {
                                if (getIntent().getParcelableArrayListExtra("playList") != null) {
                                    tvTabTraining.performClick();
                                    tabLayout.getTabAt(1).select();
                                    playList = getIntent().getParcelableArrayListExtra("playList");
                                    myplaylistClick=1;
//                tvTabTraining.performClick();
                                }
                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto1().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto1());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto2().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto2());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto3().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto3());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto4().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto4());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto5().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto5());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto6().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto6());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto7().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto7());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto8().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto8());
//                            }
//                            if (userDetails.getUserDetails().getImageArray().getPhoto9().length() > 0) {
//                                imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto9());
//                            }
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto1());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto2());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto3());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto4());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto5());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto6());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto7());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto8());
//                            imageArrayList.add(userDetails.getUserDetails().getImageArray().getPhoto9());
                            ArrayList<UserDetails.UserDetailsBean.ImageArrayBean> imageArrayList = new ArrayList<>();
                            OnRecyclerViewItemClickListener mylistner = new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClickListener(View view, int position) {
                                    LogM.LogE("you clicked the position=====>" + position);
                                    LogM.LogE("expressioncount>" + imageArrayList.get(position).getExpressionCount());

                                    Intent intent = new Intent(context, FullScreenProfileImages.class);
                                    intent.putExtra(GlobalData.FROM, GlobalData.ALBUM);
                                    intent.putExtra(GlobalData.Array_List, imageArrayList.get(position));
//                                    intent.putExtra(GlobalData.mediaId, imageArrayList.get(position).getCommentCount());
//                                    intent.putExtra(GlobalData.commentType, imageArrayList.get(position).);
//                                    intent.putExtra(GlobalData.profileId, imageArrayList.get(position));

                                    startActivity(intent);


                                }
                            };

                            if (userDetails.getUserDetails().getImageArray().size() > 0) {
//                                for (int i = 0; i < userDetails.getUserDetails().getImageArray().size(); i++) {
//                                    imageArrayList.addAll(userDetails.getUserDetails().getImageArray());
//                                }
                                imageArrayList.clear();

                                imageArrayList.addAll(userDetails.getUserDetails().getImageArray());
                                LogM.LogE("position before adpter set count==>" + imageArrayList.get(0).getExpressionCount());

                                rvImages.setVisibility(View.VISIBLE);
                                tvImageEmpty.setVisibility(View.GONE);
                                if (imageListAdpater == null) {
//                                    GridLayoutManager imageListMAnger = new GridLayoutManager(context, 3);
                                    GridLayoutManager imageListMAnger = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
//                                    LinearLayoutManager imageListMAnger = new LinearLayoutManager(context);
                                    rvImages.setLayoutManager(imageListMAnger);
                                    imageListAdpater = new ImageListAdpater(ProfileActivity.this, mylistner, imageArrayList);
//                                       rvImages.setAdapter(imageListAdpater);
                                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.spacing);
                                    rvImages.addItemDecoration(itemDecoration);
                                    rvImages.setAdapter(imageListAdpater);
                                } else {
                                    LogM.LogE("position datasetChanged count==>" + imageArrayList.get(0).getExpressionCount());

                                    //  imageListAdpater.notifyDataSetChanged();
                                    //                                    GridLayoutManager imageListMAnger = new GridLayoutManager(context, 3);
                                    GridLayoutManager imageListMAnger = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
//                                    LinearLayoutManager imageListMAnger = new LinearLayoutManager(context);
                                    rvImages.setLayoutManager(imageListMAnger);
                                    imageListAdpater = new ImageListAdpater(ProfileActivity.this, mylistner, imageArrayList);
//                                       rvImages.setAdapter(imageListAdpater);
//                                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.spacing);
//                                    rvImages.addItemDecoration(itemDecoration);
                                    rvImages.setAdapter(imageListAdpater);


                                }
                            } else {
                                rvImages.setVisibility(View.GONE);
                                tvImageEmpty.setVisibility(View.VISIBLE);
                            }

//                            if (userDetails.getUserDetails().getImageArray().getPhoto1().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto2().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto3().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto4().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto5().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto6().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto7().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto8().length() == 0 &&
//                                    userDetails.getUserDetails().getImageArray().getPhoto9().length() == 0) {
//                                rvImages.setVisibility(View.GONE);
//                                tvImageEmpty.setVisibility(View.VISIBLE);
//                            } else {
//                                rvImages.setVisibility(View.VISIBLE);
//                                tvImageEmpty.setVisibility(View.GONE);
//                                if (imageListAdpater == null) {
////                                    GridLayoutManager imageListMAnger = new GridLayoutManager(context, 3);
//                                    GridLayoutManager imageListMAnger = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
////                                    LinearLayoutManager imageListMAnger = new LinearLayoutManager(context);
//                                    rvImages.setLayoutManager(imageListMAnger);
//                                    imageListAdpater = new ImageListAdpater(ProfileActivity.this, mylistner, imageArrayList);
////                                       rvImages.setAdapter(imageListAdpater);
////                                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.spacing);
////                                    rvImages.addItemDecoration(itemDecoration);
//                                    rvImages.setAdapter(imageListAdpater);
//                                } else {
//                                    imageListAdpater.notifyDataSetChanged();
//                                }
//                            }


                            setData();

                            if (alSupplement.size() > 0) {
//                                if (alSupplement.size() < 4) {
//                                    tvViewAll.setVisibility(View.GONE);
//                                } else {
//                                    tvViewAll.setVisibility(View.VISIBLE);
//                                }
                                adapter.notifyDataSetChanged();
                                rvMiSuppliment.setVisibility(View.VISIBLE);
                                tvMiSupplementEmpty.setVisibility(View.GONE);
                            } else {
                                tvMiSupplementEmpty.setVisibility(View.VISIBLE);
                                rvMiSuppliment.setVisibility(View.INVISIBLE);
                                tvViewAll.setVisibility(View.GONE);
                            }
                            if(myplaylistClick==1){
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateList(adapter);
                                    }
                                },5000);


                            }

                        } else {
                            AlertDialogUtility.showSnakeBar(userDetails.getMessage(), snackBarView, context);
                        }
                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

    private void setData() {
        userName=userDetailsBean.getFirstName();
        tvUserName.setText(userDetailsBean.getFirstName() + " " + userDetailsBean.getLastName());
        //  tvNickNameValue.setText(userDetailsBean.getNickName());
        tvAgeValue.setText(String.valueOf(userDetailsBean.getAge()));
        LogM.LogE("TYPE -- " + userDetailsBean.getBodyType());
        if (userDetailsBean.getGender() == 0) {
            tvGenderValue.setText(getResources().getString(R.string.sp_male));
            if (userDetailsBean.getBodyType().equalsIgnoreCase(getResources().getString(R.string.ectomorph))) {
                ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph_selected));
                ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
            } else if (userDetailsBean.getBodyType().equalsIgnoreCase(getResources().getString(R.string.mesomorph))) {
                ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph_selected));
                ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
            } else if (userDetailsBean.getBodyType().equalsIgnoreCase(getResources().getString(R.string.endomorph))) {
                ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph_selected));
            }
        } else if (userDetailsBean.getGender() == 1) {
            tvGenderValue.setText(getResources().getString(R.string.sp_female));
            if (userDetailsBean.getBodyType().equalsIgnoreCase(getResources().getString(R.string.Female_Ectomorph))) {
                ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph_selected));
                ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
            } else if (userDetailsBean.getBodyType().equalsIgnoreCase(getResources().getString(R.string.Female_Mesomorph))) {
                ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph_selected));
                ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
            } else if (userDetailsBean.getBodyType().equalsIgnoreCase(getResources().getString(R.string.Female_Endomorph))) {
                ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph_selected));
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userDetailsBean.getCurrentTrainingGoals().size(); i++) {
            if (i == userDetailsBean.getCurrentTrainingGoals().size() - 1) {
                sb.append(userDetailsBean.getCurrentTrainingGoals().get(i));
            } else {
                sb.append(userDetailsBean.getCurrentTrainingGoals().get(i) + ", ");
            }
        }

        String wt = "", ht = "", fat = "";
        if (userDetailsBean.getWeight().contains(".00")) {
            wt = userDetailsBean.getWeight().substring(0, userDetailsBean.getWeight().indexOf("."));
//            tvWeight.setText(wt);
            tvWeight.setText(wt + GlobalData.KG);
        } else {
            if (!userDetailsBean.getWeight().equals("")) {
                tvWeight.setText(userDetailsBean.getWeight() + GlobalData.KG);
            }
        }

        if (userDetailsBean.getHeight().contains(".00")) {
            ht = userDetailsBean.getHeight().substring(0, userDetailsBean.getHeight().indexOf("."));
            // tvHeight.setText(ht);
            tvHeight.setText(ht + GlobalData.CM);
        } else {
            if (!userDetailsBean.getHeight().equals("")) {
                tvHeight.setText(userDetailsBean.getHeight() + GlobalData.CM);
            }
        }

        if (userDetailsBean.getBodyFat().contains(".00")) {
            fat = userDetailsBean.getBodyFat().substring(0, userDetailsBean.getBodyFat().indexOf("."));
//            tvFat.setText(fat);
            tvFat.setText(fat + GlobalData.PERCENT);
        } else {
            if (!userDetailsBean.getBodyFat().equals("")) {
                tvFat.setText(userDetailsBean.getBodyFat() + GlobalData.PERCENT);
            }
        }

        tvCurrentTrainingGoalsValue.setText(sb.toString());
//        tvWeight.setText(userDetailsBean.getWeight());
//        tvHeight.setText(userDetailsBean.getHeight());
//        tvFat.setText(userDetailsBean.getBodyFat());
        tvBioDesc.setText(userDetailsBean.getBio());
        tvFollowersCount.setText(String.valueOf(userDetailsBean.getFollowersCount()));
        tvSubscribersCount.setText(String.valueOf(userDetailsBean.getSubscriptionCount()));
        if (userDetailsBean.getProfilePic().length() > 0) {
            ivUserProfile.setImageURI(Uri.parse(userDetailsBean.getProfilePic()));
            ivUserProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenProfileImages.class);
                    intent.putExtra(GlobalData.FROM, GlobalData.PROFILE);
                    intent.putExtra(GlobalData.IMAGE_URL, userDetailsBean.getProfilePic());
                    intent.putExtra(GlobalData.LIKE_COUNT, userDetailsBean.getProfilePicExpressionCount());
                    intent.putExtra(GlobalData.COMMENT_COUNT, userDetailsBean.getProfilePicCommentCount());
                    intent.putExtra(GlobalData.GOAL, userDetailsBean.getIs_goals());
                    intent.putExtra(GlobalData.ADMIRING, userDetailsBean.getIs_admiring());
                    intent.putExtra(GlobalData.INSPIRING, userDetailsBean.getIs_inspiring());
                    intent.putExtra(GlobalData.PROFILE_ID, otherUserId);
                    startActivity(intent);

                }
            });
        }

        String badge = userDetailsBean.getBadge();
        if (badge.equalsIgnoreCase("1")) {
            ibBadge.setVisibility(View.GONE);
        } else if (badge.equalsIgnoreCase("2")) {
            ibBadge.setVisibility(View.VISIBLE);
            ibBadge.setBackground(context.getResources().getDrawable(R.drawable.celebrity));
        } else if (badge.equalsIgnoreCase("3")) {
            ibBadge.setVisibility(View.VISIBLE);
            ibBadge.setBackground(context.getResources().getDrawable(R.drawable.inflencer));
        } else if (badge.equalsIgnoreCase("4")) {
            ibBadge.setVisibility(View.VISIBLE);
            ibBadge.setBackground(context.getResources().getDrawable(R.drawable.sponsor));
        }

        isFollowing = userDetailsBean.getIsFollowing() + "";

        if (isFollowing.equalsIgnoreCase("0")) {
            tvFollow.setText(getResources().getString(R.string.follow));
            tvFollow.setTextColor(context.getResources().getColor(R.color.white));
            tvFollow.setBackground(getResources().getDrawable(R.drawable.originalfollow_bg));
        } else if (isFollowing.equalsIgnoreCase("1")) {
            tvFollow.setText(getResources().getString(R.string.following));
            tvFollow.setTextColor(context.getResources().getColor(R.color.menu_text_color));
            tvFollow.setBackground(getResources().getDrawable(R.drawable.following_bg_one));
        }

        isSubscribeForUser = userDetailsBean.getIsSubscribedForUser();

        if (isSubscribeForUser == 0) {
            tvSubscribe.setText(getResources().getString(R.string.subscribe));
            tvSubscribe.setTextColor(context.getResources().getColor(R.color.white));
            tvSubscribe.setBackground(getResources().getDrawable(R.drawable.originalfollow_bg));
        } else if (isSubscribeForUser == 1) {
            tvSubscribe.setText(getResources().getString(R.string.unsubscribe));
            tvSubscribe.setTextColor(context.getResources().getColor(R.color.menu_text_color));
            tvSubscribe.setBackground(getResources().getDrawable(R.drawable.following_bg_one));
        }


        float width = getScreenWidth(context);
        int cardWidth = (int) convertDpToPixel(((width) / 7), context);

        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(cardWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView ivFb = new ImageView(context);
        ivFb.setImageDrawable(getResources().getDrawable(R.drawable.fb));
        ImageView ivInstagram = new ImageView(context);
        ivInstagram.setImageDrawable(getResources().getDrawable(R.drawable.instagram));
        ImageView ivSnapchat = new ImageView(context);
        ivSnapchat.setImageDrawable(getResources().getDrawable(R.drawable.sapchat));
        ImageView ivTwitter = new ImageView(context);
        ivTwitter.setImageDrawable(getResources().getDrawable(R.drawable.twitter));
        ImageView ivYoutube = new ImageView(context);
        ivYoutube.setImageDrawable(getResources().getDrawable(R.drawable.youtube));
        ImageView ivLinkedin = new ImageView(context);
        ivLinkedin.setImageDrawable(getResources().getDrawable(R.drawable.linkedin));

//        for (int i = 0; i < alSocialLinks.size(); i++) {
//            alSocialLinks.remove(i);
//            alSocialLinks.add(1, alSocialLinks);
//            if (alSocialLinks.get(i).getFB().length() > 0) {
//                ivFb.setImageDrawable(getResources().getDrawable(R.drawable.fb));
////                llSocialLinks.addView(ivFb, imParams);
//            } else {
//                ivFb.setImageDrawable(getResources().getDrawable(R.drawable.fb_grey));
//
//            }
//        }

        Links temp;

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (arrayList.get(i).getValue().isEmpty() && !arrayList.get(j).getValue().isEmpty()) {
                    temp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, temp);
                }
            }
        }
        LogM.LogE("linksize==>" + arrayList.size());
        LogM.LogE("1st position==>" + arrayList.get(0).value);
        LogM.LogE("2st position==>" + arrayList.get(1).value);
        LogM.LogE("3st position==>" + arrayList.get(2).value);
        LogM.LogE("4st position==>" + arrayList.get(3).value);

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getKey().equalsIgnoreCase("FB")) {
                if (!arrayList.get(i).getValue().isEmpty()) {
                    ivFb.setImageDrawable(getResources().getDrawable(R.drawable.fb));
                    ivFb.setEnabled(true);
                } else {
                    ivFb.setImageDrawable(getResources().getDrawable(R.drawable.fb_grey));
                    ivFb.setEnabled(false);
                }
                llSocialLinks.addView(ivFb, imParams);
            } else if (arrayList.get(i).getKey().equalsIgnoreCase("Instagram")) {
                if (!arrayList.get(i).getValue().isEmpty()) {
                    ivInstagram.setImageDrawable(getResources().getDrawable(R.drawable.instagram));
                    ivInstagram.setEnabled(true);
                } else {
                    ivInstagram.setImageDrawable(getResources().getDrawable(R.drawable.instagram_grey));
                    ivInstagram.setEnabled(false);
                }
                llSocialLinks.addView(ivInstagram, imParams);
            } else if (arrayList.get(i).getKey().equalsIgnoreCase("Snapchat")) {
                if (!arrayList.get(i).getValue().isEmpty()) {
                    ivSnapchat.setImageDrawable(getResources().getDrawable(R.drawable.sapchat));
                    ivSnapchat.setEnabled(true);
                } else {
                    ivSnapchat.setImageDrawable(getResources().getDrawable(R.drawable.sapchat_grey));
                    ivSnapchat.setEnabled(false);
                }
                llSocialLinks.addView(ivSnapchat, imParams);
            } else if (arrayList.get(i).getKey().equalsIgnoreCase("Twitter")) {
                if (!arrayList.get(i).getValue().isEmpty()) {
                    ivTwitter.setImageDrawable(getResources().getDrawable(R.drawable.twitter));
                    ivTwitter.setEnabled(true);
                } else {
                    ivTwitter.setImageDrawable(getResources().getDrawable(R.drawable.twitter_grey));
                    ivTwitter.setEnabled(false);
                }
                llSocialLinks.addView(ivTwitter, imParams);
            } else if (arrayList.get(i).getKey().equalsIgnoreCase("Youtube")) {
                if (!arrayList.get(i).getValue().isEmpty()) {
                    ivYoutube.setImageDrawable(getResources().getDrawable(R.drawable.youtube));
                    ivYoutube.setEnabled(true);
                } else {
                    ivYoutube.setImageDrawable(getResources().getDrawable(R.drawable.youtube_grey));
                    ivYoutube.setEnabled(false);
                }
                llSocialLinks.addView(ivYoutube, imParams);
            } else if (arrayList.get(i).getKey().equalsIgnoreCase("LinkedIn")) {
                if (!arrayList.get(i).getValue().isEmpty()) {
                    ivLinkedin.setImageDrawable(getResources().getDrawable(R.drawable.linkedin));
                    ivLinkedin.setEnabled(true);
                } else {
                    ivLinkedin.setImageDrawable(getResources().getDrawable(R.drawable.linkedin_grey));
                    ivLinkedin.setEnabled(false);
                }
                llSocialLinks.addView(ivLinkedin, imParams);
            }
        }

        ivFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getKey().equalsIgnoreCase("FB")) {
                        if (!arrayList.get(i).getValue().isEmpty()) {
                            Intent intent = new Intent(context, WebviewActivity.class);
                            intent.putExtra(GlobalData.URL, arrayList.get(i).getValue());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                        }
                    }
                }
            }
        });

        ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getKey().equalsIgnoreCase("Instagram")) {
                        if (!arrayList.get(i).getValue().isEmpty()) {
                            Intent intent = new Intent(context, WebviewActivity.class);
                            intent.putExtra(GlobalData.URL, arrayList.get(i).getValue());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        ivSnapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getKey().equalsIgnoreCase("Snapchat")) {
                        if (!arrayList.get(i).getValue().isEmpty()) {
                            Intent intent = new Intent(context, WebviewActivity.class);
                            intent.putExtra(GlobalData.URL, arrayList.get(i).getValue());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getKey().equalsIgnoreCase("Twitter")) {
                        if (!arrayList.get(i).getValue().isEmpty()) {
                            Intent intent = new Intent(context, WebviewActivity.class);
                            intent.putExtra(GlobalData.URL, arrayList.get(i).getValue());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        ivYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getKey().equalsIgnoreCase("Youtube")) {
                        if (!arrayList.get(i).getValue().isEmpty()) {
                            Intent intent = new Intent(context, WebviewActivity.class);
                            intent.putExtra(GlobalData.URL, arrayList.get(i).getValue());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        ivLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getKey().equalsIgnoreCase("LinkedIn")) {
                        if (!arrayList.get(i).getValue().isEmpty()) {
                            Intent intent = new Intent(context, WebviewActivity.class);
                            intent.putExtra(GlobalData.URL, arrayList.get(i).getValue());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

//        llSocialLinks.addView(ivFb, imParams);
//        llSocialLinks.addView(ivInstagram, imParams);
//        llSocialLinks.addView(ivSnapchat, imParams);
//        llSocialLinks.addView(ivTwitter, imParams);
//        llSocialLinks.addView(ivYoutube, imParams);
//        llSocialLinks.addView(ivLinkedin, imParams);
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;
    }

    public static float getScreenWidth(Context context) {
        float width = (float) 360.0;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels / displayMetrics.density;
        return width;
    }

    private void bindViews() {
        try {
            scrollViewMain = binding.scrollViewMain;
            ibBack = binding.ibBack;
            ivUserProfile = binding.ivUserProfile;
            tvFollow = binding.tvFollow;
            tvUserName = binding.tvUserName;
            tvFollowersCount = binding.tvFollowersCount;
            tvSubscribersCount = binding.tvSubscribersCount;
            tvSubscribers = binding.tvSubscribers;
            tvFollowers = binding.tvFollowers;
            tvTabProfile = binding.tvTabProfile;
            tvTabMeals = binding.tvTabMeals;
            tvTabTraining = binding.tvTabTraining;
            tvTabSupps = binding.tvTabSupps;
            ibBadge = binding.ibBadge;
            ibMedia = binding.ibMedia;

            // Profile
            svProfile = binding.svProfile;
            clMeals = binding.clMeals;
            clTraining = binding.clTraining;
            clSupps = binding.clSupps;
            // tvNickNameValue = binding.tvNickNameValue;
            tvAgeValue = binding.tvAgeValue;
            tvGenderValue = binding.tvGenderValue;
            tvCurrentTrainingGoalsValue = binding.tvCurrentTrainingGoalsValue;
            ivEctomorph = binding.ivEctomorph;
            ivMesomorph = binding.ivMesomorph;
            ivEndomorph = binding.ivEndomorph;
            tvEctomorph = binding.tvEctomorph;
            tvMesomorph = binding.tvMesomorph;
            tvEndomorph = binding.tvEndomorph;
            tvWeight = binding.tvWeight;
            tvHeight = binding.tvHeight;
            tvFat = binding.tvFat;
            tvBioDesc = binding.tvBioDesc;
            ib1 = binding.ib1;
            ib2 = binding.ib2;
            ib3 = binding.ib3;
            ib4 = binding.ib4;
            ib5 = binding.ib5;
            ib6 = binding.ib6;

            rvMiSuppliment = binding.rvMiSuppliment;
            tvMiSupplementEmpty = binding.tvMiSupplementEmpty;
            tvViewAll = binding.tvViewAll;
            llSocialLinks = binding.llSocialLinks;

            tabLayout = binding.tabs;
            viewPager = binding.viewPager;

            tvViewAllTraining = binding.tvViewAllTraining;

            tvMiMealEmpty = binding.tvMiMealEmpty;
//            rvMiMeal = binding.rvMiMeal;
//            rvLunch = binding.rvLunch;
//            rvSnack = binding.rvSnack;
//            rvDinner = binding.rvDinner;
//            ivBreakFastMealPhoto = binding.ivBreakFastMealPhoto;
//            ivLunchMealPhoto = binding.ivLunchMealPhoto;
//            ivSnackMealPhoto = binding.ivSnackMealPhoto;
//            ivDinnerMealPhoto = binding.ivDinnerMealPhoto;
//            tvDinner = binding.tvDinner;
//            tvSnack = binding.tvSnack;
//            tvLunch = binding.tvLunch;
//            tvBreakfast = binding.tvBreakfast;
            rvMeals = binding.rvMeals;
            tvNumberOfMealsCount = binding.tvNumberOfMealsCount;
            clImages = binding.clImages;
            rvImages = binding.rvImages;
            tvImages = binding.tvImages;
            tvViewAllMeal = binding.tvViewAllMeal;
            tvImageEmpty = binding.tvImageEmpty;
            tvAddIntroductoryVideo = binding.tvAddIntroductoryVideo;
            clVideoView = binding.clVideoView;
            clAddIntroductoryVideo = binding.clAddIntroductoryVideo;
            ivIntroductoryVideo = binding.ivIntroductoryVideo;
            ibEdit = binding.ibEdit;
            tvSubscribe = binding.tvSubscribe;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        try {
            tvSubscribe.setOnClickListener(this);
            ibBack.setOnClickListener(this);
            tvFollow.setOnClickListener(this);
            tvUserName.setOnClickListener(this);
            tvFollowersCount.setOnClickListener(this);
//            tvSubscribersCount.setOnClickListener(this);
            tvTabProfile.setOnClickListener(this);
            tvTabMeals.setOnClickListener(this);
            tvTabTraining.setOnClickListener(this);
            tvTabSupps.setOnClickListener(this);
            ibMedia.setOnClickListener(this);
            tvFollowers.setOnClickListener(this);
//            tvSubscribers.setOnClickListener(this);
            //mi meal
            tvViewAllMeal.setOnClickListener(this);
            tvMiMealEmpty.setOnClickListener(this);

            // Profile
            ib1.setOnClickListener(this);
            ib2.setOnClickListener(this);
            ib3.setOnClickListener(this);
            ib4.setOnClickListener(this);
            ib5.setOnClickListener(this);
            ib6.setOnClickListener(this);
            tvViewAll.setOnClickListener(this);
            tvViewAllTraining.setOnClickListener(this);
            tvAddIntroductoryVideo.setOnClickListener(this);
            ivIntroductoryVideo.setOnClickListener(this);
            ibEdit.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customTabText() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            ConstraintLayout tab = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.tab_nav_training, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.tvtab);
            tab_label.setText(getResources().getString(navLabels[i]));
            // finally publish this custom view to navigation tab
            tabLayout.getTabAt(i).setCustomView(tab);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.ibBack:
                    onBackPressed();
                    break;

                case R.id.tvTabProfile:
                    tvTabProfile.setBackground(getResources().getDrawable(R.drawable.top_bottom_border));
                    tvTabMeals.setBackgroundColor(getResources().getColor(R.color.white));
                    ibMedia.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabTraining.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabSupps.setBackgroundColor(getResources().getColor(R.color.white));

                    svProfile.setVisibility(View.VISIBLE);
                    clMeals.setVisibility(View.GONE);
                    clTraining.setVisibility(View.GONE);
                    clSupps.setVisibility(View.GONE);
                    clImages.setVisibility(View.GONE);
                    break;

                case R.id.tvTabMeals:
                    scrollViewMain.smoothScrollTo(0, 0);
                    tvTabProfile.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabMeals.setBackground(getResources().getDrawable(R.drawable.top_bottom_border));
                    ibMedia.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabTraining.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabSupps.setBackgroundColor(getResources().getColor(R.color.white));

                    svProfile.setVisibility(View.GONE);
                    clMeals.setVisibility(View.VISIBLE);
                    clTraining.setVisibility(View.GONE);
                    clSupps.setVisibility(View.GONE);
                    clImages.setVisibility(View.GONE);
                    break;

                case R.id.ibMedia:
                    scrollViewMain.smoothScrollTo(0, scrollViewMain.getTop());
                    tvTabProfile.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabMeals.setBackgroundColor(getResources().getColor(R.color.white));
                    ibMedia.setBackground(getResources().getDrawable(R.drawable.top_bottom_border));
                    tvTabTraining.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabSupps.setBackgroundColor(getResources().getColor(R.color.white));

                    svProfile.setVisibility(View.GONE);
                    clMeals.setVisibility(View.GONE);
                    clImages.setVisibility(View.VISIBLE);
                    clTraining.setVisibility(View.GONE);
                    clSupps.setVisibility(View.GONE);
                    break;

                case R.id.tvTabTraining:
                    tvTabProfile.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabMeals.setBackgroundColor(getResources().getColor(R.color.white));
                    ibMedia.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabTraining.setBackground(getResources().getDrawable(R.drawable.top_bottom_border));
                    tvTabSupps.setBackgroundColor(getResources().getColor(R.color.white));

                    svProfile.setVisibility(View.GONE);
                    clMeals.setVisibility(View.GONE);
                    clTraining.setVisibility(View.VISIBLE);
                    clSupps.setVisibility(View.GONE);
                    clImages.setVisibility(View.GONE);
                    break;

                case R.id.tvTabSupps:
                    scrollViewMain.smoothScrollTo(0, scrollViewMain.getTop());
                    tvTabProfile.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabMeals.setBackgroundColor(getResources().getColor(R.color.white));
                    ibMedia.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabTraining.setBackgroundColor(getResources().getColor(R.color.white));
                    tvTabSupps.setBackground(getResources().getDrawable(R.drawable.top_bottom_border));

                    svProfile.setVisibility(View.GONE);
                    clMeals.setVisibility(View.GONE);
                    clImages.setVisibility(View.GONE);
                    clTraining.setVisibility(View.GONE);
                    clSupps.setVisibility(View.VISIBLE);
                    break;

                case R.id.ib1:
                    break;

                case R.id.ib2:
                    break;

                case R.id.ib3:
                    break;

                case R.id.ib4:
                    break;

                case R.id.ib5:
                    break;

                case R.id.ib6:
                    break;

                case R.id.tvViewAllTraining:
                    Intent trainingIntent = new Intent(context, MiTrainingProfileActivity.class);
                    startActivity(trainingIntent);
//                    Intent intentTraining = new Intent();
//                    intentTraining.putExtra(GlobalData.FRAG_NAME_KEY, GlobalData.TRAINING);
//                    setResult(RESULT_OK, intentTraining);
//                    finish();
                    break;

                case R.id.tvViewAll:
                    Intent supplimentIntent = new Intent(context, MiSupplimentProfileActivity.class);
                    LogM.LogE("Profile otheruserId" + otherUserId);
                    supplimentIntent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                    startActivity(supplimentIntent);
//                    Intent resultIntent = new Intent();
//                    resultIntent.putExtra(GlobalData.FRAG_NAME_KEY, GlobalData.SUPPLEMENT);
//                    setResult(RESULT_OK, resultIntent);
//                    finish();
                    break;

                case R.id.tvFollowersCount:
                    if (userDetailsBean.getFollowersCount() > 0) {
                        intent = new Intent(context, FollowersFollowingActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.FOLLOWERS);
                        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    break;

                case R.id.tvFollowers:
                    if (userDetailsBean.getFollowersCount() > 0) {
                        intent = new Intent(context, FollowersFollowingActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.FOLLOWERS);
                        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    break;

                case R.id.tvSubscribersCount:
                    if (userDetailsBean.getFollowingCount() > 0) {
                        intent = new Intent(context, FollowersFollowingActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.FOLLOWING);
                        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    break;

                case R.id.tvSubscribers:
                    if (userDetailsBean.getFollowingCount() > 0) {
                        intent = new Intent(context, FollowersFollowingActivity.class);
                        intent.putExtra(GlobalData.FROM, GlobalData.FOLLOWING);
                        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    break;

                case R.id.tvFollow:
                    if (isFollowing.equalsIgnoreCase("0")) {
                        callFollowUnFollowAPI(1);
                    } else if (isFollowing.equalsIgnoreCase("1")) {
                        callFollowUnFollowAPI(0);
                    }
                    break;

                case R.id.tvSubscribe:
                    if (isSubscribeForUser == 0) {
                        callSubscribeForUser(1);
                    } else if (isSubscribeForUser == 1) {
                        callSubscribeForUser(0);
                    }
                    break;

                case R.id.tvViewAllMeal:
//                    Intent profileResultIntent = new Intent();
//                    profileResultIntent.putExtra(GlobalData.FRAG_NAME_KEY, GlobalData.MI_MEAL);
//                    setResult(RESULT_OK, profileResultIntent);
                    Intent mealDetailsIntent = new Intent(context, MealDetailsActivity.class);
                    mealDetailsIntent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                    startActivity(mealDetailsIntent);
//                    tvViewAllMeal.setVisibility(View.GONE);
//                   if(mealsSnacksBeanArrayList.size()>0) {
//                       ivSnackMealPhoto.setVisibility(View.VISIBLE);
//                       tvSnack.setVisibility(View.VISIBLE);
//                       rvSnack.setVisibility(View.VISIBLE);
//                   }
//                   if(mealsDinnerBeanArrayList.size()>0){
//                       ivDinnerMealPhoto.setVisibility(View.VISIBLE);
//                       tvDinner.setVisibility(View.VISIBLE);
//                       rvDinner.setVisibility(View.VISIBLE);
//                   }
                    break;

                case R.id.tvAddIntroductoryVideo:
                    spliteCount = 0;
                    totalSplite = 1;
                    if (!ConnectivityDetector.isConnectingToInternet(context)) {
                        AlertDialogUtility.showInternetAlert(context);
                        return;
                    }
                    PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}).checkPermissions(ProfileActivity.this, new PermissionsHelper.OnPermissionResult() {
                        @Override
                        public void onGranted() {
                            if (isSubscribed == 1) {
                                GlobalData.CAMERA_ID = 0;
                                Intent intent = new Intent(context, CustomCameraActivity.class);
                                startActivityForResult(intent, GlobalData.CAPTURE_VIDEO);
                            } else {
                                Intent intent = new Intent(context, PremiumMemberShipActivity.class);
                                startActivityForResult(intent, GlobalData.PREMIUM_MEMBER);
                            }
                        }

                        @Override
                        public void notGranted() {
                        }
                    });
                    break;

                case R.id.ivIntroductoryVideo:
                    if (introductoryVideoURL.trim().length() > 0) {
                        Intent intentVideo = new Intent(getApplicationContext(), VideoActivity.class);
                        intentVideo.putExtra("mediaType", 3);
                        intentVideo.putExtra("videoUrl", introductoryVideoURL);
                        startActivity(intentVideo);
                    }
                    break;

                case R.id.ibEdit:
                    isEdit = true;
                    tvAddIntroductoryVideo.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callFollowUnFollowAPI(final int folloUnfollow) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.FOLLOW_UNFOLLOW.PARAM_FOLLOWORUNFOLLOW, folloUnfollow);
            jsonObject.put(WebField.FOLLOW_UNFOLLOW.PARAM_OTHERUSERID, otherUserId);

            LogM.LogE("Request : followunfollow : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.FOLLOW_UNFOLLOW.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : followunfollow  : " + jsonObject.toString());
//                    CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                    if (isSuccess) {
                        CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                        if (folloUnfollow == 0) {
                            tvFollow.setText(getResources().getString(R.string.follow));
                            tvFollow.setTextColor(context.getResources().getColor(R.color.white));
                            tvFollow.setBackground(getResources().getDrawable(R.drawable.originalfollow_bg));
                            isFollowing = "0";
                            callUserDetails();
                        } else {
                            tvFollow.setText(getResources().getString(R.string.following));
                            tvFollow.setTextColor(context.getResources().getColor(R.color.menu_text_color));
                            tvFollow.setBackground(getResources().getDrawable(R.drawable.following_bg_one));
                            isFollowing = "1";
                            callUserDetails();
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callSubscribeForUser(final int isSubscribeForUser) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.SUBSCRIBEFORUSER.SUBSCRIBEORUNSUBSCRIBE, isSubscribeForUser);
            jsonObject.put(WebField.PARAM_OTHERUSERID, otherUserId);

            LogM.LogE("Request : followunfollow : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SUBSCRIBEFORUSER.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : followunfollow  : " + jsonObject.toString());
//                    CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                    if (isSuccess) {
                        CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                        callUserDetails();
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        Intent intent = new Intent(context, MiSupplementDetailActivity.class);
        intent.putExtra(GlobalData.FROM, GlobalData.PROFILE_ACTIVITY);
        intent.putExtra(GlobalData.SUPP_INFO, alSupplement.get(pos));
        intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // MiTraining --- START ---
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

      //  intent.putExtra(GlobalData.FROM, GlobalData.PROFILE_ACTIVITY);
//        intent.putExtra(GlobalData.SPOTIFY_ID, spotifyId);
//        intent.putExtra(GlobalData.OTHER_USER_ID,otherUserId);


        adapter.addFragment(MyWorkoutsFragment.newInstance(1, otherUserId), getResources().getString(R.string.tab_my_workouts));

       // if (SessionManager.getUserId(context) == otherUserId || otherUserId == 0) {
            adapter.addFragment(MyPlayListFragment.newInstance(1, playList,spotifyId,GlobalData.PROFILE_ACTIVITY,otherUserId,userName), getResources().getString(R.string.tab_my_playlist));
      //  }
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    tvViewAllTraining.setVisibility(View.GONE);
                }
//                else{
//                    tvViewAllTraining.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
                tabView.setMinimumWidth(0);
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        setMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        setMargin(layoutParams, internalMargin, externalMargin);
                    } else {
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

    public static void showHideTraining(int status) {
        try {
            if (tvViewAllTraining != null) {
                if (status == 1) {
                    tvViewAllTraining.setVisibility(View.GONE);
                } else {
                    tvViewAllTraining.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // MiTraining --- END ---

    private String base64 = "";
    private MyClass myClass = new MyClass();
    private boolean isEdit = false;
    private String introductoryVideoURL = "";
    private ProgressDialog progressDialog;

    private String getBase64(int index) {
        String base64_ = null;
        try {
            File f = new File(myClass.makeDir(context), fileNameArr.get(index));
            base64_ = fileHandeling.getBase64FromByteArray(fileHandeling.convertFileToByteArray(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64_;
    }

    private void uploadIntroductoryVideo() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.setTitle("");
                progressDialog.show();
            }

            if (spliteCount < totalSplite) {
                base64 = getBase64(spliteCount);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.ACCESS_TOKEN, SessionManager.getAccessToken(context));
            if (spliteCount == totalSplite - 1) {
                jsonObject.put(WebField.UPLOAD_USER_VIDEO.IS_COMPLETED, 1);
            } else {
                jsonObject.put(WebField.UPLOAD_USER_VIDEO.IS_COMPLETED, 0);
            }
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.IS_EDIT, isEdit ? 1 : 0);
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.ITEM_THUMB, base64Thumb);
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.WORKOUT_SOURCE_DATA, base64);

            LogM.LogE("Request : UploadUserVideo : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.UPLOAD_USER_VIDEO.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : UploadUserVideo : " + jsonObject.toString());
                    isEdit = false;
                    IntroductoryVideo mIntroductoryVideo = new Gson().fromJson(String.valueOf(jsonObject), IntroductoryVideo.class);
                    if (isSuccess) {
                        spliteCount++;
                        if (spliteCount < totalSplite) {
                            uploadIntroductoryVideo();
                        } else {
                            introductoryVideoURL = mIntroductoryVideo.getVideoURL();
                            if (introductoryVideoURL.length() > 0) {
                                tvAddIntroductoryVideo.setVisibility(View.GONE);
                                clVideoView.setVisibility(View.VISIBLE);
                                Uri mUri = Uri.parse(mIntroductoryVideo.getVideoThumbImage().toString());
                                Fresco.getImagePipeline().evictFromMemoryCache(mUri);
                                Fresco.getImagePipelineFactory().getMainBufferedDiskCache().remove(new SimpleCacheKey(mUri.toString()));
                                Fresco.getImagePipelineFactory().getSmallImageFileCache().remove(new SimpleCacheKey(mUri.toString()));
                                ivIntroductoryVideo.setImageURI(mUri);
                            } else {
                                tvAddIntroductoryVideo.setVisibility(View.VISIBLE);
                                clVideoView.setVisibility(View.GONE);
                            }
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        AlertDialogUtility.showSnakeBar(mIntroductoryVideo.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/// selecting TAB....
    private void setTabListner() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:

                    updateList(adapter);


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


}
