package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.google.android.gms.common.internal.GmsLogger;
import com.konkr.Adapters.CommentsAdapter;
import com.konkr.Adapters.TagUserAdapter;
import com.konkr.Models.Comments;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.SearchSuppliment;
import com.konkr.Models.SendComment;
import com.konkr.Models.TagUsers;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityCommentsBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCommentsBinding binding;
    private Activity context;
    private Headerbar headerBar;
    private MyTextView tvEmpty;
    private MyTextView tvSend;
    private AutoCompleteTextView etComment;
    private RecyclerView commentsRecyclerView;
    private ArrayList<Comments.CommentsOnThisFeedBean> alCommentList = new ArrayList<>();
    private CommentsAdapter adapter;
    private View snackBarView;
    private int currentJobPage = 1, pageNumber = 1;
    private boolean loadingCurrentJob = true, isSecondCurrent = false;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager lmTagUser;
    private ProgressBar progressBar;
    private int pageIndex = 1;
    private ArrayAdapter<TagUsers.UserDataBean> adpaterTagUser;
    private ArrayList<TagUsers.UserDataBean> alTagUsers = new ArrayList<>();
//    private TagUserAdapter tagUserAdapter;
//    private RecyclerView rvTagUser;
// --------------------------------------LP100711017AU----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView (R.layout.activity_comments);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        context = CommentsActivity.this;
        snackBarView = findViewById(android.R.id.content);

        bindView();
        setListener();
        setHeaderBar();
        if (getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
            if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.INDIVIDUAL_MEDIA)) {
                callGetCommentsOnMediaApi();
            }
        } else {
            callGetCommentsApi();
        }
//        getTagUsers();
        setCommentAdapter();

////      Make links in the EditText clickable
//        etComment.setMovementMethod(LinkMovementMethod.getInstance());
//
////      Setup my Spannable with clickable URLs
//        Spannable spannable = new SpannableString("http://blog.danlew.net");
//        Linkify.addLinks(spannable, Linkify.WEB_URLS);
//
////        The fix: Append a zero-width space to the Spannable
////        CharSequence text = TextUtils.concat(spannable, "\u200B");
//
////        Use it!
//        etComment.setText(text);

        commentsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                if (currentJobPage == 0) {
                } else {
                    if (lastVisibleItem == totalItemCount && loadingCurrentJob) {
                        progressBar.setVisibility(View.VISIBLE);
                        isSecondCurrent = true;
                        pageIndex += 1;
                        currentJobPage = 0;
//                        if (getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
//                            if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.INDIVIDUAL_MEDIA)) {
//                                callGetCommentsOnMediaApi();
//                            }
//                        } else {
                            callGetCommentsApi();
//                        }
                    }
                }
            }
        });
    }

//    private void getTagUsers() {
//
//        etComment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                LogM.LogE("on text changed " + s.toString());
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                LogM.LogE("befoer text changed  " + s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                tvDesCharRemaining.setText(1000 - s.toString().length() + "");
//                LogM.LogE("Afeter " + s.toString());
//                String string = s.toString();
//                if (string.contains("@")) {
//                    LogM.LogE("Afeter end @ " + s.toString());
//
//                    String finalUserName = string.substring(string.indexOf("@") + 1);
//                    if (!finalUserName.isEmpty()) {
//                        callGetTagUsers(s.toString());
//                    }
//                }
//            }
//        });
//    }

    private void callGetTagUsers(String userName) {
        if (ConnectivityDetector.isConnectingToInternet(context)) {
            try {
                String finalUserName = userName.substring(userName.indexOf("@") + 1);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                jsonObject.put(WebField.TAG_USER.USERNAME, finalUserName);

                LogM.LogE("Request : Mi Suppliment : " + jsonObject.toString());
                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.TAG_USER.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final TagUsers tagUsers = new Gson().fromJson(String.valueOf(jsonObject), TagUsers.class);
                        if (isSuccess) {
                            alTagUsers.clear();
                            alTagUsers.addAll(tagUsers.getUserData());
//                            tagUserAdapter = new TagUserAdapter(context, alTagUsers);

                            etComment.setAdapter(adpaterTagUser);
                            etComment.showDropDown();
                            LogM.LogE("Size of list" + alTagUsers.size());
                            LogM.LogE("Response : Mi Suppliment : " + jsonObject.toString());
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


    private void setLayoutManger(String from) {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        if (from.equalsIgnoreCase("sendcomment")) {
            linearLayoutManager.scrollToPosition(alCommentList.size() - 1);
        }
        commentsRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    private void bindView() {
        headerBar = binding.headerBar;
        tvEmpty = binding.tvEmpty;
        tvSend = binding.tvSend;
        etComment = binding.etComment;
        progressBar = binding.progressBar;
        commentsRecyclerView = binding.commentsRecyclerView;
//        rvTagUser = binding.rvTagUser;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.comments_title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                KeyboardUtility.HideKeyboard(context, view);

                int updatedCount;
                int prevCount = (getIntent().getIntExtra(GlobalData.INITIAL_COUNT, 0));
                int position = getIntent().getIntExtra(GlobalData.POSITION, 0);
                if (alCommentList.size() > prevCount) {
                    updatedCount = alCommentList.size();
                    Intent intent = new Intent();
                    intent.putExtra(GlobalData.LATEST_COUNT, updatedCount);
                    intent.putExtra(GlobalData.POSITION, position);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED);
                }

                finish();
                break;

            case R.id.tvSend:
                if (etComment.getText().toString().trim().isEmpty()) {
                    etComment.requestFocus();
                    AlertDialogUtility.showSnakeBar(getString(R.string.please_enter_comment), etComment, this);
                } else {
                    KeyboardUtility.HideKeyboard(context, view);
                    if (getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
                        if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.INDIVIDUAL_MEDIA)) {
                            callSendCommentOnMediaAPI();
                        }
                    } else {
                        callSendCommentAPI();
                    }
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {

//        KeyboardUtility.HideKeyboard(context, view);

        int updatedCount;
        int prevCount = (getIntent().getIntExtra(GlobalData.INITIAL_COUNT, 0));
        int position = getIntent().getIntExtra(GlobalData.POSITION, 0);
        if (alCommentList.size() > prevCount) {
            updatedCount = alCommentList.size();
            Intent intent = new Intent();
            intent.putExtra(GlobalData.LATEST_COUNT, updatedCount);
            intent.putExtra(GlobalData.POSITION, position);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
        super.onBackPressed();
    }

    private void callSendCommentAPI() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.SEND_COMMENT.PARAM_COMMENT, etComment.getText().toString());
            if (getIntent().getStringExtra(GlobalData.HOME_FEED_ID) != null) {
                jsonObject.put(WebField.GET_COMMENT.PARAM_HOMEFEEDID, getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
            }

            LogM.LogE("Request : send comment : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SEND_COMMENT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : send Comment  : " + jsonObject.toString());
                    if (isSuccess) {
                        SendComment comment = new Gson().fromJson(String.valueOf(jsonObject), SendComment.class);
                        etComment.setText("");

//                        callGetCommentsApi();

                        SendComment.CommentsOnThisFeedBean sendComment = comment.getCommentsOnThisFeed();

                        Comments.CommentsOnThisFeedBean commentOnFeed = new Comments.CommentsOnThisFeedBean();
                        commentOnFeed.setUserId(sendComment.getUserId());
                        commentOnFeed.setComment(sendComment.getComment());
                        commentOnFeed.setBadge(sendComment.getBadge());
                        commentOnFeed.setFirstName(sendComment.getFirstName());
                        commentOnFeed.setLastName(sendComment.getLastName());
                        commentOnFeed.setProfilePic(sendComment.getProfilePic());
                        alCommentList.add(commentOnFeed);

                        if (alCommentList != null && alCommentList.size() != 0) {
                            tvEmpty.setVisibility(View.GONE);
                            commentsRecyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            setLayoutManger("sendcomment");
                            setCommentAdapter();
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    {
//        "profileId" :"23",
//            "accessToken" :"zmKrtup42eVpMSx7uhWLF5ZcQwSNcdKMzve20E72S2sFVZMphI",
//            "userId" :4,
//            "homeFeedId" :39,
//            "workoutId" :20,
//            "itemId" :"2"
//    }

    private void callSendCommentOnMediaAPI() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.ADD_COMMENT_ON_MEDIA.PARAM_COMMENT, etComment.getText().toString());
//            if (getIntent().getStringExtra(GlobalData.HOME_FEED_ID) != null) {
//                jsonObject.put(WebField.ADD_COMMENT_ON_MEDIA.PARAM_HOMEFEEDID, getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
//            }
//            if (getIntent().getStringExtra(GlobalData.WORKOUT_ID) != null) {
//                jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_WORKOUTID, getIntent().getIntExtra(GlobalData.WORKOUT_ID, 0));
//            }
//            if (getIntent().getStringExtra(GlobalData.PROFILE_ID) != null) {
//                jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_PROFILE_ID, getIntent().getIntExtra(GlobalData.PROFILE_ID, 0));
//            }
//            if (getIntent().getStringExtra(GlobalData.ITEM_ID) != null) {
//                jsonObject.put(WebField.ADD_COMMENT_ON_MEDIA.PARAM_ITEM_ID, getIntent().getStringExtra(GlobalData.ITEM_ID));
//            }

            if (getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
                if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.INDIVIDUAL_MEDIA)) {
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_HOMEFEEDID, getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_WORKOUTID, getIntent().getIntExtra(GlobalData.WORKOUT_ID, 0));
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_PROFILE_ID, getIntent().getStringExtra(GlobalData.PROFILE_ID));
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_ITEM_ID, getIntent().getStringExtra(GlobalData.ITEM_ID));
                }
            }

            LogM.LogE("Request : send comment on media : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADD_COMMENT_ON_MEDIA.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : send Comment on media : " + jsonObject.toString());
                    if (isSuccess) {
                        SendComment comment = new Gson().fromJson(String.valueOf(jsonObject), SendComment.class);
                        etComment.setText("");

//                        callGetCommentsApi();

                        SendComment.CommentsOnThisFeedBean sendComment = comment.getCommentsOnThisFeed();

                        Comments.CommentsOnThisFeedBean commentOnFeed = new Comments.CommentsOnThisFeedBean();
                        commentOnFeed.setUserId(sendComment.getUserId());
                        commentOnFeed.setComment(sendComment.getComment());
                        commentOnFeed.setBadge(sendComment.getBadge());
                        commentOnFeed.setFirstName(sendComment.getFirstName());
                        commentOnFeed.setLastName(sendComment.getLastName());
                        commentOnFeed.setProfilePic(sendComment.getProfilePic());
                        alCommentList.add(commentOnFeed);

                        if (alCommentList != null && alCommentList.size() != 0) {
                            tvEmpty.setVisibility(View.GONE);
                            commentsRecyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            setLayoutManger("sendcomment");
                            setCommentAdapter();
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetCommentsOnMediaApi() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
//            if (getIntent().getStringExtra(GlobalData.HOME_FEED_ID) != null) {
//                jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_HOMEFEEDID, getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
//            }
//            if (getIntent().getStringExtra(GlobalData.WORKOUT_ID) != null) {
//                jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_WORKOUTID, getIntent().getIntExtra(GlobalData.WORKOUT_ID, 0));
//            }
//            if (getIntent().getStringExtra(GlobalData.PROFILE_ID) != null) {
//                jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_PROFILE_ID, getIntent().getIntExtra(GlobalData.PROFILE_ID, 0));
//            }
//            if (getIntent().getStringExtra(GlobalData.ITEM_ID) != null) {
//                jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_ITEM_ID, getIntent().getStringExtra(GlobalData.ITEM_ID));
//            }
            if (getIntent().getStringExtra(GlobalData.IS_FROM) != null) {
                if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.INDIVIDUAL_MEDIA)) {
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_HOMEFEEDID, getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_WORKOUTID, getIntent().getIntExtra(GlobalData.WORKOUT_ID, 0));
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_PROFILE_ID, getIntent().getStringExtra(GlobalData.PROFILE_ID));
                    jsonObject.put(WebField.GET_COMMENT_ON_MEDIA.PARAM_ITEM_ID, getIntent().getStringExtra(GlobalData.ITEM_ID));
                }
            }

//            jsonObject.put(WebField.PARAM_PAGEINDEX, pageIndex);
//            jsonObject.put(WebField.PARAM_PAGECOUNT, GlobalData.PAGE_ITEM_COUNT);

            LogM.LogE("Request : commentlistOnMedia : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_COMMENT_ON_MEDIA.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : commentlistOnMedia  : " + jsonObject.toString());
                    if (isSuccess) {
                        Comments comment = new Gson().fromJson(String.valueOf(jsonObject), Comments.class);
//                        alCommentList = comment.getCommentsOnThisFeed();
//
//                        if (alCommentList.size() > 0) {
//                            commentsRecyclerView.setVisibility(View.VISIBLE);
//                            tvEmpty.setVisibility(View.GONE);
//                            setLayoutManger();
//                            setCommentAdapter();
//                        } else {
//                            commentsRecyclerView.setVisibility(View.GONE);
//                            tvEmpty.setVisibility(View.VISIBLE);
//                        }

                        currentJobPage = 1;
                        loadingCurrentJob = true;
                        if (comment.getCommentsOnThisFeed().size() == 0) {
                            loadingCurrentJob = false;
//                            return;
                        }
                        if (isSecondCurrent) {
                            alCommentList.addAll(comment.getCommentsOnThisFeed());
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        } else {
                            alCommentList.clear();
                            alCommentList.addAll(comment.getCommentsOnThisFeed());
                            if (alCommentList != null && alCommentList.size() != 0) {
                                tvEmpty.setVisibility(View.GONE);
                                commentsRecyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                setLayoutManger("commnetlist");
                                setCommentAdapter();
                            } else {
                                tvEmpty.setVisibility(View.VISIBLE);
                                commentsRecyclerView.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetCommentsApi() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            if (getIntent().getStringExtra(GlobalData.HOME_FEED_ID) != null) {
                jsonObject.put(WebField.GET_COMMENT.PARAM_HOMEFEEDID, getIntent().getStringExtra(GlobalData.HOME_FEED_ID));
            }
            jsonObject.put(WebField.PARAM_PAGEINDEX, pageIndex);
            jsonObject.put(WebField.PARAM_PAGECOUNT, GlobalData.PAGE_ITEM_COUNT);

            LogM.LogE("Request : commentlist : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_COMMENT.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : commentlist  : " + jsonObject.toString());
                    if (isSuccess) {
                        Comments comment = new Gson().fromJson(String.valueOf(jsonObject), Comments.class);
//                        alCommentList = comment.getCommentsOnThisFeed();
//
//                        if (alCommentList.size() > 0) {
//                            commentsRecyclerView.setVisibility(View.VISIBLE);
//                            tvEmpty.setVisibility(View.GONE);
//                            setLayoutManger();
//                            setCommentAdapter();
//                        } else {
//                            commentsRecyclerView.setVisibility(View.GONE);
//                            tvEmpty.setVisibility(View.VISIBLE);
//                        }

                        currentJobPage = 1;
                        loadingCurrentJob = true;
                        if (comment.getCommentsOnThisFeed().size() == 0) {
                            loadingCurrentJob = false;
//                            return;
                        }
                        if (isSecondCurrent) {
                            alCommentList.addAll(comment.getCommentsOnThisFeed());
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        } else {
                            alCommentList.clear();
                            alCommentList.addAll(comment.getCommentsOnThisFeed());
                            if (alCommentList != null && alCommentList.size() != 0) {
                                tvEmpty.setVisibility(View.GONE);
                                commentsRecyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                setLayoutManger("commnetlist");
                                setCommentAdapter();
                            } else {
                                tvEmpty.setVisibility(View.VISIBLE);
                                commentsRecyclerView.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCommentAdapter() {
        LogM.LogE("size" + alCommentList.size());
        adapter = new CommentsAdapter(context, alCommentList);
        commentsRecyclerView.setAdapter(adapter);
    }
}
