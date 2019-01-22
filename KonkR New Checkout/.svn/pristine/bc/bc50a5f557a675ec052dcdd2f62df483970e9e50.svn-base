package com.konkr.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.konkr.Adapters.HorizontalVideoPhotoAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.ExpressionOnFeed;
import com.konkr.Models.HomeFeed;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.MiTrainingAddWorkout;
import com.konkr.Models.MyWorkouts;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityMyWorkoutDetailsBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyWorkoutDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnRecyclerViewItemClickListener {

    private ActivityMyWorkoutDetailsBinding binding;
    private ProgressDialog progressDialog;
    private Headerbar headerBar;
    private RecyclerView rvVideoPhoto;
    private TextView tvWorkoutCategory, tvWorkoutType, tvWorkoutName, tvTime;

    private HorizontalVideoPhotoAdapter adapter;
    private ArrayList<MiTrainingAddWorkout> alWorkoutImagesAndVideos = new ArrayList<>();
    private MyWorkouts.WorkoutsBean myWorkout;
    private MyWorkouts.WorkoutsBean.WorkoutDurationBean myWorkoutDuration;
    private List<MyWorkouts.WorkoutsBean.WorkoutMediaBean> myWorkoutMedia;

    private HomeFeed.HomeFeedsBean homefeedObject;
    private HomeFeed.HomeFeedsBean.WorkoutsBean myWorkoutHome;
    private HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutDurationBean myWorkoutDurationHome;
    private ArrayList<HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean> myWorkoutMediaHome;

    int workoutId;
    String profileId;
    String itemId;
    int otherUserId;

    private SimpleDraweeView ivLike, ivComment, ivGoal, ivAdmiring, ivInspiring;
    private MyTextView tvLikeCount;
    private MyTextView tvCommentCount;
    private boolean isAlreadyLiked;

    private ConstraintLayout rowCom;

    private boolean isExpressVisible;
    String homeFeedId;
    int commentCount;
    int commentCountOnMedia;

    int goal, inspiring, admiring;

    private ConstraintLayout clExpression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_workout_details);
        bindViews();
        setListener();
        setData();

        if ((SessionManager.getIsProfileSetup(MyWorkoutDetailsActivity.this)).equalsIgnoreCase("0")) {
            clExpression.setVisibility(View.GONE);
        } else {
            clExpression.setVisibility(View.VISIBLE);
        }

        if (myWorkoutMedia != null) {
            for (int i = 0; i < myWorkoutMedia.size(); i++) {
                MiTrainingAddWorkout addMedia = new MiTrainingAddWorkout();
                if (myWorkoutMedia.get(i).getMediaType().equalsIgnoreCase("3")) {
                    addMedia.setVideoThumbImage(myWorkoutMedia.get(i).getVideoThumbImage());
                    addMedia.setMediaType(Integer.parseInt(myWorkoutMedia.get(i).getMediaType()));
                    addMedia.setVideoURL(myWorkoutMedia.get(i).getUrl());
                    addMedia.setItemId(Integer.parseInt(myWorkoutMedia.get(i).getItemId()));
                } else {
                    addMedia.setImageURL(myWorkoutMedia.get(i).getUrl());
                    addMedia.setMediaType(Integer.parseInt(myWorkoutMedia.get(i).getMediaType()));
                    addMedia.setItemId(Integer.parseInt(myWorkoutMedia.get(i).getItemId()));
                }
                workoutId = myWorkout.getWorkoutId();
                addMedia.setWorkoutId(myWorkout.getWorkoutId());
                alWorkoutImagesAndVideos.add(addMedia);
            }
        }
        if (myWorkoutMediaHome != null) {
            for (int i = 0; i < myWorkoutMediaHome.size(); i++) {
                MiTrainingAddWorkout addMedia = new MiTrainingAddWorkout();
                if (myWorkoutMediaHome.get(i).getMediaType().equalsIgnoreCase("3")) {
                    addMedia.setVideoThumbImage(myWorkoutMediaHome.get(i).getVideoThumbImage());
                    addMedia.setMediaType(Integer.parseInt(myWorkoutMediaHome.get(i).getMediaType()));
                    addMedia.setVideoURL(myWorkoutMediaHome.get(i).getUrl());
                    addMedia.setItemId(Integer.parseInt(myWorkoutMediaHome.get(i).getItemId()));
                } else {
                    addMedia.setImageURL(myWorkoutMediaHome.get(i).getUrl());
                    addMedia.setMediaType(Integer.parseInt(myWorkoutMediaHome.get(i).getMediaType()));
                    addMedia.setItemId(Integer.parseInt(myWorkoutMediaHome.get(i).getItemId()));
                }
                addMedia.setWorkoutId(myWorkoutHome.getWorkoutId());
                alWorkoutImagesAndVideos.add(addMedia);
            }
        }


        adapter = new HorizontalVideoPhotoAdapter(MyWorkoutDetailsActivity.this, alWorkoutImagesAndVideos, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyWorkoutDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvVideoPhoto.setLayoutManager(mLayoutManager);
        rvVideoPhoto.setAdapter(adapter);

        if (rvVideoPhoto.getVisibility() == View.GONE && alWorkoutImagesAndVideos.size() > 0) {
            rvVideoPhoto.setVisibility(View.VISIBLE);
        }


        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkoutDetailsActivity.this, CommentsActivity.class);
                intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                intent.putExtra(GlobalData.INITIAL_COUNT, commentCount);
                MyWorkoutDetailsActivity.this.startActivityForResult(intent, GlobalData.COMMENT_TRAINING_REQ);
            }
        });

        tvCommentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myReciclerClickListener.onCommentClick(view, position);
                Intent intent = new Intent(MyWorkoutDetailsActivity.this, CommentsActivity.class);
                intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                MyWorkoutDetailsActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalData.COMMENT_TRAINING_REQ && resultCode == RESULT_OK && data != null) {
            data.getIntExtra(GlobalData.LATEST_COUNT, 0);
            LogM.LogE("Updated count" + data.getIntExtra(GlobalData.LATEST_COUNT, 0));
            if (Integer.parseInt(tvCommentCount.getText().toString().trim()) < data.getIntExtra(GlobalData.LATEST_COUNT, 0))
                tvCommentCount.setText("" + data.getIntExtra(GlobalData.LATEST_COUNT, 0));
        }

        if (requestCode == GlobalData.COMMENT_TRAINING_DETAILS_TO_INDIVIDUAL_REQ && resultCode == RESULT_OK && data != null) {
            int commentCount = 0;
            int expresssionCount = 0;

            commentCount = Integer.parseInt(data.getStringExtra(GlobalData.LATEST_COUNT));
            expresssionCount = Integer.parseInt(data.getStringExtra(GlobalData.EXPRESSION_COUNT));

            int goal, inspiring, admiring;
            goal = data.getIntExtra(GlobalData.GOAL, 0);
            inspiring = data.getIntExtra(GlobalData.INSPIRING, 0);
            admiring = data.getIntExtra(GlobalData.ADMIRING, 0);
            int itemId = alWorkoutImagesAndVideos.get(data.getIntExtra(GlobalData.POSITION, 0)).getItemId();

            LogM.LogE("Updated count" + data.getStringExtra(GlobalData.LATEST_COUNT));
            if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.WORKOUT) && getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
                myWorkoutMedia.set(data.getIntExtra(GlobalData.POSITION, 0), new MyWorkouts.WorkoutsBean.WorkoutMediaBean(String.valueOf(itemId),expresssionCount, commentCount, admiring, goal, inspiring));
                adapter.notifyDataSetChanged();
            }
            if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.HOME_FEED) && getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
                myWorkoutMediaHome.set(data.getIntExtra(GlobalData.POSITION, 0), new HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean(String.valueOf(itemId), expresssionCount, commentCount, admiring, goal, inspiring));
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
//            headerBar.ibtnRightTwo.setVisibility(View.GONE);
//            headerBar.ibtnRightTwo.setImageResource(R.drawable.edit_black);
            headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
            headerBar.ibtnRightTwo.setImageResource(R.drawable.delete);
            rvVideoPhoto = binding.rvVideoPhoto;
            tvWorkoutCategory = binding.tvWorkoutCategory;
            tvWorkoutType = binding.tvWorkoutType;
            tvWorkoutName = binding.tvWorkoutName;
            tvTime = binding.tvTime;

            tvLikeCount = binding.tvLikeCount;
            tvCommentCount = binding.tvCommentCount;
            ivGoal = binding.ivGoal;
            ivAdmiring = binding.ivAdmiring;
            ivInspiring = binding.ivInspiring;
            rowCom = binding.rowCom;
            ivComment = binding.ivComment;
            ivLike = binding.ivLike;
            clExpression = binding.clExpression;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            headerBar.ibtnRightTwo.setOnClickListener(this);
            ivLike.setOnClickListener(this);
            ivComment.setOnClickListener(this);
            tvLikeCount.setOnClickListener(this);
            ivGoal.setOnClickListener(this);
            ivInspiring.setOnClickListener(this);
            ivAdmiring.setOnClickListener(this);
            ivComment.setOnClickListener(this);
            tvCommentCount.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.WORKOUT)) {
                myWorkout = getIntent().getParcelableExtra("WorkoutDetails");
                myWorkoutDuration = getIntent().getParcelableExtra("WorkoutDuration");
                myWorkoutMedia = getIntent().getParcelableArrayListExtra("WorkoutMedia");

                headerBar.tvTitle.setText(myWorkout.getWorkoutName());
                tvWorkoutCategory.setText(myWorkout.getWorkoutCategoryName());
                tvWorkoutType.setText(myWorkout.getExcerciseName());
                tvWorkoutName.setText(myWorkout.getWorkoutName());
                if (myWorkoutDuration != null) {
                    int time = (myWorkoutDuration.getHour() * 60) + myWorkoutDuration.getMin();
                    tvTime.setText(time + "");
                }
                rvVideoPhoto.setVisibility(View.GONE);
                workoutId = myWorkout.getWorkoutId();
                otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);

                homeFeedId = myWorkout.getHomeFeedId();
                commentCount = myWorkout.getCommentCount();
                tvCommentCount.setText("" + commentCount);
                tvLikeCount.setText("" + myWorkout.getExpressionCount());

                goal = myWorkout.getIs_goals();
                inspiring = myWorkout.getIs_inspiring();
                admiring = myWorkout.getIs_admiring();

                workoutId = myWorkout.getWorkoutId();
                profileId = String.valueOf(myWorkout.getUserId());
                int userId = getIntent().getIntExtra(GlobalData.USER_ID, 0);
                if ((SessionManager.getUserId(MyWorkoutDetailsActivity.this) == userId)) {
                    headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
                } else {
                    headerBar.ibtnRightTwo.setVisibility(View.GONE);
                }
                setLikeButton(goal, admiring, inspiring);
            } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.HOME_FEED)) {
//                intent.putExtra(GlobalData.HOME_FEED_OBJECT, alHomeFeed.get(pos));
                homefeedObject = getIntent().getParcelableExtra(GlobalData.HOME_FEED_OBJECT);
                myWorkoutHome = getIntent().getParcelableExtra("WorkoutDetails");
                myWorkoutDurationHome = getIntent().getParcelableExtra("WorkoutDuration");
                myWorkoutMediaHome = getIntent().getParcelableArrayListExtra("WorkoutMedia");
                headerBar.tvTitle.setText(myWorkoutHome.getWorkoutName());
                tvWorkoutCategory.setText(myWorkoutHome.getWorkoutCategoryName());
                tvWorkoutType.setText(myWorkoutHome.getExcerciseName());
                tvWorkoutName.setText(myWorkoutHome.getWorkoutName());
                if (myWorkoutDurationHome != null) {
                    int time = (myWorkoutDurationHome.getHour() * 60) + myWorkoutDurationHome.getMin();
                    tvTime.setText(time + "");
                }
                rvVideoPhoto.setVisibility(View.GONE);

//
                homeFeedId = getIntent().getStringExtra(GlobalData.HOME_FEED_ID);
                profileId = getIntent().getStringExtra(GlobalData.USER_ID);
                commentCount = homefeedObject.getCommentCount();
                tvCommentCount.setText("" + commentCount);
                tvLikeCount.setText("" + homefeedObject.getExpressionCount());

                if ((SessionManager.getUserId(MyWorkoutDetailsActivity.this) == Integer.parseInt(profileId))) {
                    headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
                } else {
                    headerBar.ibtnRightTwo.setVisibility(View.GONE);
                }

                goal = homefeedObject.getIs_goals();
                inspiring = homefeedObject.getIs_inspiring();
                admiring = homefeedObject.getIs_admiring();

                workoutId = myWorkoutHome.getWorkoutId();
//                profileId = myWorkoutHome.getUserId();
                setLikeButton(goal, admiring, inspiring);
            } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.ADD_WORKOUT_NOTIFICATION)) {
                myWorkout = getIntent().getParcelableExtra("WorkoutDetails");
                myWorkoutDuration = getIntent().getParcelableExtra("WorkoutDuration");
                myWorkoutMedia = getIntent().getParcelableArrayListExtra("WorkoutMedia");

                headerBar.tvTitle.setText(myWorkout.getWorkoutName());
                tvWorkoutCategory.setText(myWorkout.getWorkoutCategoryName());
                tvWorkoutType.setText(myWorkout.getExcerciseName());
                tvWorkoutName.setText(myWorkout.getWorkoutName());
                if (myWorkoutDuration != null) {
                    int time = (myWorkoutDuration.getHour() * 60) + myWorkoutDuration.getMin();
                    tvTime.setText(time + "");
                }
                rvVideoPhoto.setVisibility(View.GONE);
                workoutId = myWorkout.getWorkoutId();
                otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
                if ((SessionManager.getUserId(MyWorkoutDetailsActivity.this) == otherUserId)) {
                    headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
                } else {
                    headerBar.ibtnRightTwo.setVisibility(View.GONE);
                }
                homeFeedId = myWorkout.getHomeFeedId();
                commentCount = myWorkout.getCommentCount();
                tvCommentCount.setText("" + commentCount);
                tvLikeCount.setText("" + myWorkout.getExpressionCount());

                goal = myWorkout.getIs_goals();
                inspiring = myWorkout.getIs_inspiring();
                admiring = myWorkout.getIs_admiring();

                workoutId = myWorkout.getWorkoutId();
//                profileId = myWorkout.getUserId();
                profileId = String.valueOf(myWorkout.getUserId());
                setLikeButton(goal, admiring, inspiring);
            } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.GIVE_EXPRESSIONON_FEED_NOTIFICATION)) {
                myWorkout = getIntent().getParcelableExtra("WorkoutDetails");
                myWorkoutDuration = getIntent().getParcelableExtra("WorkoutDuration");
                myWorkoutMedia = getIntent().getParcelableArrayListExtra("WorkoutMedia");

                headerBar.tvTitle.setText(myWorkout.getWorkoutName());
                tvWorkoutCategory.setText(myWorkout.getWorkoutCategoryName());
                tvWorkoutType.setText(myWorkout.getExcerciseName());
                tvWorkoutName.setText(myWorkout.getWorkoutName());
                if (myWorkoutDuration != null) {
                    int time = (myWorkoutDuration.getHour() * 60) + myWorkoutDuration.getMin();
                    tvTime.setText(time + "");
                }
                rvVideoPhoto.setVisibility(View.GONE);
                workoutId = myWorkout.getWorkoutId();
                otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
                int userId = getIntent().getIntExtra(GlobalData.USER_ID, 0);
                if ((SessionManager.getUserId(MyWorkoutDetailsActivity.this) == userId)) {
                    headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
                } else {
                    headerBar.ibtnRightTwo.setVisibility(View.GONE);
                }
                homeFeedId = myWorkout.getHomeFeedId();
                commentCount = myWorkout.getCommentCount();
                tvCommentCount.setText("" + commentCount);
                tvLikeCount.setText("" + myWorkout.getExpressionCount());

                goal = myWorkout.getIs_goals();
                inspiring = myWorkout.getIs_inspiring();
                admiring = myWorkout.getIs_admiring();

                workoutId = myWorkout.getWorkoutId();
//                profileId = myWorkout.getUserId();
                profileId = String.valueOf(myWorkout.getUserId());
                setLikeButton(goal, admiring, inspiring);
            }
        }
    }

    private void setLikeButton(int goal, int adming, int inspiring) {

        if (goal == 1) {
            ivLike.setImageResource(R.drawable.goal_green);
            isAlreadyLiked = true;
        } else if (adming == 1) {
            ivLike.setImageResource(R.drawable.admiring_green);
            isAlreadyLiked = true;
        } else if (inspiring == 1) {
            ivLike.setImageResource(R.drawable.inspiration_green);
            isAlreadyLiked = true;
        } else {
            ivLike.setImageResource(R.drawable.like);
        }

    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent;
            switch (v.getId()) {
                case R.id.ivComment:
                    intent = new Intent(MyWorkoutDetailsActivity.this, CommentsActivity.class);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                    intent.putExtra(GlobalData.INITIAL_COUNT, commentCount);
                    MyWorkoutDetailsActivity.this.startActivityForResult(intent, GlobalData.COMMENT_TRAINING_REQ);
                    break;
                case R.id.tvCommentCount:
                    intent = new Intent(MyWorkoutDetailsActivity.this, CommentsActivity.class);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                    MyWorkoutDetailsActivity.this.startActivity(intent);
                    break;
                case R.id.ivGoal:
                    callGiveExpressionOnFeed(GlobalData.EXPRESSION_GOAL, 1);
                    break;
                case R.id.ivAdmiring:
                    callGiveExpressionOnFeed(GlobalData.EXPRESSION_ADMIRING, 3);
                    break;
                case R.id.ivInspiring:
                    callGiveExpressionOnFeed(GlobalData.EXPRESSION_INSPIRING, 2);
                    break;
                case R.id.ibtnLeftOne:
                    onBackPressed();
                    break;
                case R.id.ibtnRightTwo:
                    showConfirmationDialog();
                    break;
                case R.id.ivLike:
                    if (!isAlreadyLiked) {
                        if (isExpressVisible) {
                            rowCom.setVisibility(View.GONE);
                            isExpressVisible = false;
                        } else {
                            rowCom.setVisibility(View.VISIBLE);
                            isExpressVisible = true;
                        }
                    } else {
                        if (goal > 0) {
                            callGiveExpressionOnFeed(GlobalData.EXPRESSION_GOAL, 1);
                        }
                        if (inspiring > 0) {
                            callGiveExpressionOnFeed(GlobalData.EXPRESSION_INSPIRING, 2);
                        }
                        if (admiring > 0) {
                            callGiveExpressionOnFeed(GlobalData.EXPRESSION_ADMIRING, 3);
                        }
                    }
                    break;
                case R.id.tvLikeCount:
                    try {
                        if (Integer.parseInt(tvLikeCount.getText().toString().trim()) > 0) {
                            Intent pIntent = new Intent(MyWorkoutDetailsActivity.this, PostExpressionActivity.class);
                            pIntent.putExtra(GlobalData.IS_FROM, GlobalData.DETAILS);
                            pIntent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                            if (goal == 1) {
                                pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_GOAL);
                            } else if (inspiring == 1) {
                                pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_INSPIRING);
                            } else if (admiring == 1) {
                                pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_ADMIRING);
                            }
                            startActivityForResult(pIntent, 132);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    rowCom.setVisibility(View.GONE);
                    isExpressVisible = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGiveExpressionOnFeed(int expression, int FeedPosition) {
        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put(WebField.PARAM_USER_ID, SessionManager.getUserId(MyWorkoutDetailsActivity.this));
            jsonRequest.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(MyWorkoutDetailsActivity.this));
            jsonRequest.put(WebField.PARAM_HOMEFEEDID, homeFeedId);
            jsonRequest.put(WebField.GIVE_EXPRESSION_ON_FEED.PARAM_EXPRESSION, expression);

            Log.e("Details", "Req--" + jsonRequest);

            new GetJsonWithAndroidNetworkingLib(MyWorkoutDetailsActivity.this, jsonRequest, WebField.BASE_URL + WebField.GIVE_EXPRESSION_ON_FEED.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonResponse, boolean isSuccess) {
                    if (isSuccess) {
                        Log.e("Details", "Response" + jsonResponse);
                        ExpressionOnFeed expression = new Gson().fromJson(String.valueOf(jsonResponse), ExpressionOnFeed.class);
                        rowCom.setVisibility(View.GONE);

                        tvLikeCount.setText("" + expression.getExpressionCount());
                        goal = expression.getIs_goals();
                        inspiring = expression.getIs_inspiring();
                        admiring = expression.getIs_admiring();

                        rowCom.setVisibility(View.GONE);
                        isAlreadyLiked = true;
                        if (expression.getStatus() == 2) {
                            if (expression.getIs_goals() == GlobalData.EXPRESSION_GOAL) {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            } else if (expression.getIs_inspiring() == GlobalData.EXPRESSION_INSPIRING) {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            } else if (expression.getIs_admiring() == GlobalData.EXPRESSION_ADMIRING) {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            }
                        } else {
                            if (goal == 1) {
                                goal = 1;
                                ivLike.setImageResource(R.drawable.goal_green);
                            } else if (inspiring == 2) {
                                inspiring = 1;
                                ivLike.setImageResource(R.drawable.inspiration_green);
                            } else if (admiring == 3) {
                                admiring = 1;
                                ivLike.setImageResource(R.drawable.admiring_green);
                            } else {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            }
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showConfirmationDialog() {
        AlertDialogUtility.showConfirmAlert(MyWorkoutDetailsActivity.this, getResources().getString(R.string.delete_added_workout), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callDeleteWorkoutApi();
            }
        });

    }

    private void callDeleteWorkoutApi() {

        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(getApplicationContext()));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(getApplicationContext()));
                jsonObject.put(WebField.DELETE_WORKOUT.WORKOUTID, workoutId);
                LogM.LogE("Request : DELETE Suppliment : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(MyWorkoutDetailsActivity.this, jsonObject, WebField.BASE_URL + WebField.DELETE_WORKOUT.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final MiSuppliment miSupplimentList = new Gson().fromJson(String.valueOf(jsonObject), MiSuppliment.class);
                        if (isSuccess) {
                            LogM.LogE("Response : DELETE Suppliment : " + jsonObject.toString());
                            setResult(RESULT_OK);
                            finish();

//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            }, GlobalData.DELAY_TIME);


                        } else {
//                            AlertDialogUtility.showSnakeBar(miSupplimentList.getMessage(), tvSupplimentDes, contex);
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(MyWorkoutDetailsActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        try {
            MiTrainingAddWorkout workout = alWorkoutImagesAndVideos.get(position);
            if (workout.getMediaType() == GlobalData.MEDIA_TYPE_IMAGE) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("mediaType", workout.getMediaType());
                intent.putExtra("imageUrl", workout.getImageURL());

                if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.WORKOUT)) {
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.WORKOUT);
                    intent.putExtra("WorkoutDetails", myWorkout);
                    MyWorkouts.WorkoutsBean.WorkoutMediaBean workoutMediaBean = myWorkoutMedia.get(position);
                    intent.putExtra("WorkoutMedia", workoutMediaBean);
                } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.HOME_FEED)) {
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.HOME_FEED);
                    intent.putExtra(GlobalData.POSITION, position);
                    intent.putExtra(GlobalData.USER_ID, profileId);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                    intent.putExtra("WorkoutDetails", myWorkoutHome);
                    HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean workoutMediaBeanHome = myWorkoutMediaHome.get(position);
                    intent.putExtra("WorkoutMedia", workoutMediaBeanHome);
                } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.ADD_WORKOUT_NOTIFICATION)) {
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.WORKOUT);
                    intent.putExtra(GlobalData.USER_ID, profileId);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                    intent.putExtra("WorkoutDetails", myWorkout);
                    MyWorkouts.WorkoutsBean.WorkoutMediaBean workoutMediaBean = myWorkoutMedia.get(position);
                    intent.putExtra("WorkoutMedia", workoutMediaBean);
                }
                this.startActivityForResult(intent, GlobalData.COMMENT_TRAINING_DETAILS_TO_INDIVIDUAL_REQ);
            } else if (workout.getMediaType() == GlobalData.MEDIA_TYPE_VIDEO) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("mediaType", workout.getMediaType());
                intent.putExtra("videoUrl", workout.getVideoURL());
                intent.putExtra("videoThumb", workout.getVideoThumbImage());
//                intent.putExtra(GlobalData.WORKOUT_ID, getIntent().getIntExtra(GlobalData.WORKOUT_ID, 0));
//                intent.putExtra(GlobalData.PROFILE_ID, getIntent().getIntExtra(GlobalData.PROFILE_ID, 0));
                if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.WORKOUT)) {
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.WORKOUT);
                    intent.putExtra("WorkoutDetails", myWorkout);
                    MyWorkouts.WorkoutsBean.WorkoutMediaBean workoutMediaBean = myWorkoutMedia.get(position);
                    intent.putExtra("WorkoutMedia", workoutMediaBean);
//                    intent.putExtra(GlobalData.ITEM_ID, myWorkoutMedia.get(position).getItemId());
//                    intent.putExtra(GlobalData.LIKE_COUNT, myWorkoutMedia.get(position).getExpressionCount());
//                    intent.putExtra(GlobalData.COMMENT_COUNT, myWorkoutMedia.get(position).getCommentCount());
//                    intent.putExtra(GlobalData.GOAL, myWorkoutMedia.get(position).getIs_goals());
//                    intent.putExtra(GlobalData.ADMIRING, myWorkoutMedia.get(position).getIs_admiring());
//                    intent.putExtra(GlobalData.INSPIRING, myWorkoutMedia.get(position).getIs_inspiring());
//                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.HOME_FEED)) {
//                    intent.putExtra(GlobalData.ITEM_ID, myWorkoutMediaHome.get(position).getItemId());
//                    intent.putExtra(GlobalData.LIKE_COUNT, myWorkoutMediaHome.get(position).getExpressionCount());
//                    intent.putExtra(GlobalData.COMMENT_COUNT, myWorkoutMediaHome.get(position).getCommentCount());
//                    intent.putExtra(GlobalData.GOAL, goal);
//                    intent.putExtra(GlobalData.ADMIRING, admiring);
//                    intent.putExtra(GlobalData.INSPIRING, inspiring);
//                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.HOME_FEED);
                    intent.putExtra(GlobalData.USER_ID, profileId);
                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                    intent.putExtra("WorkoutDetails", myWorkoutHome);
                    HomeFeed.HomeFeedsBean.WorkoutsBean.WorkoutMediaBean workoutMediaBeanHome = myWorkoutMediaHome.get(position);
                    intent.putExtra("WorkoutMedia", workoutMediaBeanHome);
                } else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.ADD_WORKOUT_NOTIFICATION)) {
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.WORKOUT);
                    intent.putExtra("WorkoutDetails", myWorkout);
                    MyWorkouts.WorkoutsBean.WorkoutMediaBean workoutMediaBean = myWorkoutMedia.get(position);
                    intent.putExtra("WorkoutMedia", workoutMediaBean);
//                    intent.putExtra(GlobalData.ITEM_ID, myWorkoutMedia.get(position).getItemId());
//                    intent.putExtra(GlobalData.LIKE_COUNT, myWorkoutMedia.get(position).getExpressionCount());
//                    intent.putExtra(GlobalData.COMMENT_COUNT, myWorkoutMedia.get(position).getCommentCount());
//                    intent.putExtra(GlobalData.GOAL, myWorkoutMedia.get(position).getIs_goals());
//                    intent.putExtra(GlobalData.ADMIRING, myWorkoutMedia.get(position).getIs_admiring());
//                    intent.putExtra(GlobalData.INSPIRING, myWorkoutMedia.get(position).getIs_inspiring());
//                    intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);
                }
                this.startActivityForResult(intent, GlobalData.COMMENT_TRAINING_DETAILS_TO_INDIVIDUAL_REQ);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
