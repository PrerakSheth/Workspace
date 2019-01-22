package com.konkr.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Activities.MyWorkoutDetailsActivity;
import com.konkr.Adapters.FollowersListAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.FollowersList;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.FragmentFollowersBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class FollowersFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private View snackBarView;
    private FragmentFollowersBinding binding;
    private RecyclerView rvFollower;
    private MyTextView tvNoFollowerFound;
    private FollowersListAdapter adapter;
    private ArrayList<FollowersList.FollowersListBean> alFollowers = new ArrayList<>();
    int otherUserId = 0;

    public FollowersFragment() {
    }

    public static FollowersFragment newInstance() {
        FollowersFragment fragment = new FollowersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_followers, container, false);
        View view = binding.getRoot();
        snackBarView = view.findViewById(android.R.id.content);

        rvFollower = binding.rvFollower;
        tvNoFollowerFound = binding.tvNoFollowerFound;
        tvNoFollowerFound.setVisibility(View.GONE);

        adapter = new FollowersListAdapter(getActivity(), alFollowers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFollower.setLayoutManager(mLayoutManager);
        rvFollower.setAdapter(adapter);

        otherUserId = getActivity().getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);

        callFollowersList();
        return view;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        try {
            Intent myWorkoutDetailsIntent = new Intent(getActivity(), MyWorkoutDetailsActivity.class);
            startActivity(myWorkoutDetailsIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callFollowersList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(getActivity()));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(getActivity()));
            jsonObject.put(WebField.PARAM_OTHERUSERID, otherUserId);
            LogM.LogE("Request : FollowersList : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(getActivity(), jsonObject, WebField.BASE_URL + WebField.FOLLOWERS_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : FollowersList : " + jsonObject.toString());
                    FollowersList followersList = new Gson().fromJson(String.valueOf(jsonObject), FollowersList.class);
                    if (isSuccess) {
                        alFollowers.clear();
                        if (followersList.getFollowersList().size() > 0) {
                            tvNoFollowerFound.setVisibility(View.GONE);
                            rvFollower.setVisibility(View.VISIBLE);

                            alFollowers.addAll(followersList.getFollowersList());
                        } else {
                            tvNoFollowerFound.setVisibility(View.VISIBLE);
                            rvFollower.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        AlertDialogUtility.showSnakeBar(followersList.getMessage(), snackBarView, getActivity());
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
