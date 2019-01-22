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
import com.konkr.Adapters.FollowingListAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.FollowingList;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.FragmentFollowingBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class FollowingFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private View snackBarView;
    private FragmentFollowingBinding binding;
    private RecyclerView rvFollowing;
    private MyTextView tvNoFollowingFound;
    private FollowingListAdapter adapter;
    private ArrayList<FollowingList.FollowingListBean> alFollowing = new ArrayList<>();
    int otherUserId = 0;

    public FollowingFragment() {
    }

    public static FollowingFragment newInstance() {
        FollowingFragment fragment = new FollowingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false);
        View view = binding.getRoot();
        snackBarView = view.findViewById(android.R.id.content);

        rvFollowing = binding.rvFollowing;
        tvNoFollowingFound = binding.tvNoFollowingFound;
        tvNoFollowingFound.setVisibility(View.GONE);

        adapter = new FollowingListAdapter(getActivity(), alFollowing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFollowing.setLayoutManager(mLayoutManager);
        rvFollowing.setAdapter(adapter);

        otherUserId = getActivity().getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);

        callFollowingList();
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

    private void callFollowingList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(getActivity()));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(getActivity()));
            jsonObject.put(WebField.PARAM_OTHERUSERID, otherUserId);
            LogM.LogE("Request : FollowingList : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(getActivity(), jsonObject, WebField.BASE_URL + WebField.FOLLOWING_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : FollowingList : " + jsonObject.toString());
                    FollowingList following = new Gson().fromJson(String.valueOf(jsonObject), FollowingList.class);
                    if (isSuccess) {
                        alFollowing.clear();
                        if (following.getFollowingList().size() > 0) {
                            tvNoFollowingFound.setVisibility(View.GONE);
                            rvFollowing.setVisibility(View.VISIBLE);

                            alFollowing.addAll(following.getFollowingList());
                        } else {
                            tvNoFollowingFound.setVisibility(View.VISIBLE);
                            rvFollowing.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        AlertDialogUtility.showSnakeBar(following.getMessage(), snackBarView, getActivity());
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
