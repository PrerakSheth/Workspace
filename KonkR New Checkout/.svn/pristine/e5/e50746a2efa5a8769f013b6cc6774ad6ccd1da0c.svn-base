package com.konkr.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Activities.MyWorkoutDetailsActivity;
import com.konkr.Activities.ProfileActivity;
import com.konkr.Adapters.MyWorkoutsAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.MyWorkouts;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.FragmentMyWorkoutsBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyWorkoutsFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int isFromProfile;
    private View snackBarView;
    private FragmentMyWorkoutsBinding binding;
    private RecyclerView rvMyWorkouts;
    private MyTextView tvNoWorkoutFound;
    private MyWorkoutsAdapter adapter;
    int otherUserId;
    private ArrayList<MyWorkouts.WorkoutsBean> alMyWorkouts = new ArrayList<>();

    public MyWorkoutsFragment() {
    }

    public static MyWorkoutsFragment newInstance(int isFromProfile, int otherUserId) {
        MyWorkoutsFragment fragment = new MyWorkoutsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, isFromProfile);
        args.putInt(ARG_PARAM2, otherUserId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFromProfile = getArguments().getInt(ARG_PARAM1, 0);
            otherUserId = getArguments().getInt(ARG_PARAM2, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_workouts, container, false);
        View view = binding.getRoot();
        snackBarView = view.findViewById(android.R.id.content);

        rvMyWorkouts = binding.rvMyWorkouts;
        tvNoWorkoutFound = binding.tvNoWorkoutFound;
        tvNoWorkoutFound.setVisibility(View.GONE);

        adapter = new MyWorkoutsAdapter(getActivity(), alMyWorkouts, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMyWorkouts.setLayoutManager(mLayoutManager);
        rvMyWorkouts.setAdapter(adapter);

//        callGetMyWorkouts();
        return view;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        try {
            MyWorkouts.WorkoutsBean myWorkout = alMyWorkouts.get(position);
            Intent myWorkoutDetailsIntent = new Intent(getActivity(), MyWorkoutDetailsActivity.class);
            myWorkoutDetailsIntent.putExtra(GlobalData.IS_FROM, GlobalData.WORKOUT);
            myWorkoutDetailsIntent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
            myWorkoutDetailsIntent.putExtra(GlobalData.USER_ID, alMyWorkouts.get(position).getUserId());
            myWorkoutDetailsIntent.putExtra("WorkoutDetails", myWorkout);
            myWorkoutDetailsIntent.putExtra("WorkoutDuration", myWorkout.getWorkoutDuration());
            myWorkoutDetailsIntent.putExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) myWorkout.getWorkoutMedia());
//            myWorkoutDetailsIntent.putExtra(GlobalData.POSITION, position);

//            myWorkoutDetailsIntent.putExtra(GlobalData.PROFILE_ID, myWorkout.getUserId());
//            myWorkoutDetailsIntent.putExtra(GlobalData.ITEM_ID, myWorkout.getWorkoutMedia().get(position).getItemId());
//            myWorkoutDetailsIntent.putExtra(GlobalData.WORKOUT_ID, myWorkout.getWorkoutId());
//
//            myWorkoutDetailsIntent.putExtra(GlobalData.LIKE_COUNT, myWorkout.getExpressionCount());
//            myWorkoutDetailsIntent.putExtra(GlobalData.COMMENT_COUNT, myWorkout.getCommentCount());
//            myWorkoutDetailsIntent.putExtra(GlobalData.GOAL, myWorkout.getIs_goals());
//            myWorkoutDetailsIntent.putExtra(GlobalData.ADMIRING, myWorkout.getIs_admiring());
//            myWorkoutDetailsIntent.putExtra(GlobalData.INSPIRING, myWorkout.getIs_inspiring());
//            myWorkoutDetailsIntent.putExtra(GlobalData.HOME_FEED_ID, myWorkout.getWorkoutMedia().get(0).getHomeFeedId());

            startActivity(myWorkoutDetailsIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!ConnectivityDetector.isConnectingToInternet(getActivity())) {
            AlertDialogUtility.showInternetAlert(getActivity());
            return;
        }
        callGetMyWorkouts();
    }

    private void callGetMyWorkouts() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_MY_WORKOUT.USER_ID, SessionManager.getUserId(getActivity()));
            jsonObject.put(WebField.GET_MY_WORKOUT.ACCESS_TOKEN, SessionManager.getAccessToken(getActivity()));
            jsonObject.put(WebField.PARAM_OTHERUSERID, otherUserId);

            LogM.LogE("Request : GetMyWorkouts : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(getActivity(), jsonObject, WebField.BASE_URL + WebField.GET_MY_WORKOUT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetMyWorkouts : " + jsonObject.toString());
                    MyWorkouts myWorkouts = new Gson().fromJson(String.valueOf(jsonObject), MyWorkouts.class);
                    if (isSuccess) {
                        alMyWorkouts.clear();
                        if (myWorkouts.getWorkouts().size() > 0) {
                            tvNoWorkoutFound.setVisibility(View.GONE);
                            rvMyWorkouts.setVisibility(View.VISIBLE);


                            LogM.LogE("is from===>" + isFromProfile);

                            if (isFromProfile == 1 && myWorkouts.getWorkouts().size() > GlobalData.PROFILE_MI_SIZE) {
                                for (int i = 0; i < GlobalData.PROFILE_MI_SIZE; i++) {
                                    alMyWorkouts.add(myWorkouts.getWorkouts().get(i));
                                    ProfileActivity.showHideTraining(1);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                alMyWorkouts.addAll(myWorkouts.getWorkouts());
                                adapter.notifyDataSetChanged();
                                ProfileActivity.showHideTraining(0);
                            }
                        } else {
                            tvNoWorkoutFound.setVisibility(View.VISIBLE);
                            rvMyWorkouts.setVisibility(View.GONE);
                            ProfileActivity.showHideTraining(0);
                        }
//                        adapter.notifyDataSetChanged();
                    } else {
                        AlertDialogUtility.showSnakeBar(myWorkouts.getMessage(), snackBarView, getActivity());
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
