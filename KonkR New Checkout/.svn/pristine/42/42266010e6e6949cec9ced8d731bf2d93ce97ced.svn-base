package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.konkr.Adapters.CommentsAdapter;
import com.konkr.Adapters.CommentsMediaAdapter;
import com.konkr.Models.Comments;
import com.konkr.Models.MeidaPhotoComment;
import com.konkr.Models.SendComment;
import com.konkr.Models.SendMediaPhotoComments;
import com.konkr.Models.TagUsers;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityCommentsBinding;
import com.konkr.databinding.ActivityMediaPhotoCommentBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MediaPhotoCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMediaPhotoCommentBinding binding;
    private Activity context;
    private Headerbar headerBar;
    private MyTextView tvEmpty;
    private MyTextView tvSend;
    private AutoCompleteTextView etComment;
    private RecyclerView commentsRecyclerView;
    private List<MeidaPhotoComment.CommentsBean> alCommentList = new ArrayList<>();


    private CommentsMediaAdapter adapter;
    private View snackBarView;
    private int currentJobPage = 1, pageNumber = 1;
    private boolean loadingCurrentJob = true, isSecondCurrent = false;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager lmTagUser;
    private ProgressBar progressBar;
    private int pageIndex = 1;
    //    private ArrayAdapter<TagUsers.UserDataBean> adpaterTagUser;
//    private ArrayList<TagUsers.UserDataBean> alTagUsers = new ArrayList<>();
    private String mediaId, commentType;
    private int profileId;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_photo_comment);
        context = MediaPhotoCommentActivity.this;

        bindView();
        setListener();
        setHeaderBar();
        getIntentData();
        setLayoutManger();
        callGetCommentsApi();
        setCommentAdapter();


//        commentsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }

//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                int lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
//                if (currentJobPage == 0) {
//                } else {
//                    if (lastVisibleItem == totalItemCount && loadingCurrentJob) {
//                        progressBar.setVisibility(View.VISIBLE);
//                        isSecondCurrent = true;
//                        pageIndex += 1;
//                        currentJobPage = 0;
//                        // callGetCommentsApi();
//                    }
//                }
//            }
//        });
    }

    private void getIntentData() {

        if (getIntent().getExtras() != null) {
            commentType = getIntent().getStringExtra(GlobalData.COMMENT_TYPE);
            mediaId = getIntent().getStringExtra(GlobalData.MEDIA_ID);
            profileId = getIntent().getIntExtra(GlobalData.PROFILE_ID, 0);
        }
    }


    private void setCommentAdapter() {
        LogM.LogE("size" + alCommentList.size());
//        adapter = new CommentsMediaAdapter(context, alCommentList);
//        commentsRecyclerView.setAdapter(adapter);
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
            jsonObject.put(WebField.GET_COMMENT_MEDIA.MEDIA_ID, mediaId);
            jsonObject.put(WebField.GET_COMMENT_MEDIA.COMMENT_TYPE, commentType);
            jsonObject.put(WebField.GET_COMMENT_MEDIA.PROFILE_ID, profileId);
//            jsonObject.put(WebField.PARAM_PAGEINDEX, pageIndex);
//            jsonObject.put(WebField.PARAM_PAGECOUNT, GlobalData.PAGE_ITEM_COUNT);

            LogM.LogE("Request : commentlist : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_COMMENT_MEDIA.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : commentlist  : " + jsonObject.toString());
                    if (isSuccess) {
                        MeidaPhotoComment comment = new Gson().fromJson(String.valueOf(jsonObject), MeidaPhotoComment.class);
                        alCommentList.clear();
                        if (comment.getComments().size() > 0) {


                            alCommentList.addAll(comment.getComments());
                            adapter = new CommentsMediaAdapter(context, alCommentList);
                            commentsRecyclerView.setAdapter(adapter);
                            commentsRecyclerView.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                            commentsRecyclerView.smoothScrollToPosition(adapter.getItemCount());
                        } else {
                            commentsRecyclerView.setVisibility(View.GONE);
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
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
//
//                        currentJobPage = 1;
//                        loadingCurrentJob = true;
//                        if (comment.getComments().size() == 0) {
//                            loadingCurrentJob = false;
////                            return;
//                        }
//                        if (isSecondCurrent) {
//                            alCommentList.addAll(comment.getComments());
//                            progressBar.setVisibility(View.GONE);
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            alCommentList.clear();
//                            alCommentList.addAll(comment.getComments());
//                            if (alCommentList != null && alCommentList.size() != 0) {
//                                tvEmpty.setVisibility(View.GONE);
//                                commentsRecyclerView.setVisibility(View.VISIBLE);
//                                progressBar.setVisibility(View.GONE);
//                                setLayoutManger("commnetlist");
//                                setCommentAdapter();
//                            } else {
//                                tvEmpty.setVisibility(View.VISIBLE);
//                                commentsRecyclerView.setVisibility(View.GONE);
//                            }
                        // }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setLayoutManger() {

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        commentsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setHeaderBar() {

        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.comments_title);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnLeftOne:
                Intent intent = new Intent();
                intent.putExtra(GlobalData.COMMENT_COUNT, alCommentList.size());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tvSend:
                if (etComment.getText().toString().trim().isEmpty()) {
                    etComment.requestFocus();
                    AlertDialogUtility.showSnakeBar(getString(R.string.please_enter_comment), etComment, this);
                } else {
                    KeyboardUtility.HideKeyboard(context, v);
                    callAddCommentMediaAPI();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(GlobalData.COMMENT_COUNT, alCommentList.size());
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    private void callAddCommentMediaAPI() {

        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }


            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.ADD_COMMENT_MEDIA.MEDIA_ID, mediaId);
            jsonObject.put(WebField.ADD_COMMENT_MEDIA.COMMENT, etComment.getText().toString().trim());
            jsonObject.put(WebField.ADD_COMMENT_MEDIA.COMMENT_TYPE, commentType);
            jsonObject.put(WebField.ADD_COMMENT_MEDIA.PROFILE_ID, profileId);

            LogM.LogE("Request : ADD comment :" + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADD_COMMENT_MEDIA.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : ADD Comment  : " + jsonObject.toString());
                    if (isSuccess) {
                        etComment.setText("");
                        SendMediaPhotoComments comment = new Gson().fromJson(String.valueOf(jsonObject), SendMediaPhotoComments.class);
                        LogM.LogE("Response : id  : " + comment.getUserCommentData().getUserId());

                        //alCommentList.add(new MeidaPhotoComment.CommentsBean(SessionManager.getUserId(context), SessionManager.getProfilePic(context), SessionManager.getFirstName(context), SessionManager.getLastName(context), "0", etComment.getText().toString()));
                        alCommentList.add(new MeidaPhotoComment.CommentsBean(comment.getUserCommentData().getUserId(), comment.getUserCommentData().getProfilePic(), comment.getUserCommentData().getFirstName(), comment.getUserCommentData().getLastName(), comment.getUserCommentData().getBadge(), comment.getUserCommentData().getComment()));
//                        LogM.LogE("Response : id  : " + comment.getCommentsOnThisFeed().getUserId());
//                        LogM.LogE("Response : ADD Comment  : " + comment.getCommentsOnThisFeed().getProfilePic());
//                        LogM.LogE("Response : ADD Comment  : " + comment.getCommentsOnThisFeed().getFirstName()+"  "+ comment.getCommentsOnThisFeed().getLastName()+" "+ comment.getCommentsOnThisFeed().getBadge()+"  "+comment.getCommentsOnThisFeed().getComment());

                        //  callGetCommentsApi();
                        if (alCommentList.size() == 1) {
                            commentsRecyclerView.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                            adapter = new CommentsMediaAdapter(context, alCommentList);
                            commentsRecyclerView.setAdapter(adapter);
                        }

                        LogM.LogE("size after adding comment 1 " + alCommentList.size());

//
//                         alCommentList.add(new MeidaPhotoComment.CommentsBean(comment.getCommentsOnThisFeed().getUserId(), comment.getCommentsOnThisFeed().getProfilePic(), comment.getCommentsOnThisFeed().getFirstName(), comment.getCommentsOnThisFeed().getLastName(), comment.getCommentsOnThisFeed().getBadge(), comment.getCommentsOnThisFeed().getComment()));
                        adapter.notifyDataSetChanged();
                        commentsRecyclerView.smoothScrollToPosition(adapter.getItemCount());


                        //                        adapter = new CommentsMediaAdapter(context, alCommentList);
//                        commentsRecyclerView.setAdapter(adapter);
//                        if (alCommentList != null && alCommentList.size() != 0) {
//                            tvEmpty.setVisibility(View.GONE);
//                            commentsRecyclerView.setVisibility(View.VISIBLE);
//                            progressBar.setVisibility(View.GONE);
//                            setLayoutManger("sendcomment");
//                            setCommentAdapter();
//                        } else {
//                            adapter.notifyDataSetChanged();
//                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
