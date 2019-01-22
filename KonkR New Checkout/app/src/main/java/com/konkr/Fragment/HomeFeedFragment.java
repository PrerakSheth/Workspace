package com.konkr.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.konkr.Activities.AddMiMealActivity;
import com.konkr.Activities.MainActivity;
import com.konkr.Activities.MiSupplementDetailActivity;
import com.konkr.Activities.MyWorkoutDetailsActivity;
import com.konkr.Activities.PostExpressionActivity;
import com.konkr.Adapters.HomeFeedAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.HomeFeed;
import com.konkr.Models.Meals;
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
import com.konkr.databinding.FragmentHomeFeedBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFeedFragment extends Fragment
        implements HomeFeedAdapter.MyReciclerClickListener, SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHomeFeedBinding binding;
    private RecyclerView rvHomeFeed;
    private MyTextView tvEmpty;
    private HomeFeedAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<HomeFeed.HomeFeedsBean> homeFeedArrayList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Activity context;
    private View snackBarView;

    private OnFragmentInteractionListener mListener;

    private boolean loadingCurrentJob = true, isSecondCurrent = false;
    private ProgressBar progressBar;
    private int currentJobPage = 1, pageNumber = 1;
    private int pageIndex = 1;
    private int totalRecord;

    private OnRecyclerViewItemClickListener itemClick;
    private int notificationCount;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFeedFragment newInstance(String param1, String param2) {
        HomeFeedFragment fragment = new HomeFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnRecyclerViewItemClickListener) {
            itemClick = (OnRecyclerViewItemClickListener) activity;
        } else {
            throw new RuntimeException(context.toString() + " must implement CallFragmentInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_home_feed, container, false);


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_feed, container, false);
        View view = binding.getRoot();
        context = getActivity();
        rvHomeFeed = binding.rvHomeFeed;
        mSwipeRefreshLayout = binding.swipeRefresh;
        tvEmpty = binding.tvEmpty;
        progressBar = binding.progressBar;
        snackBarView = getActivity().findViewById(android.R.id.content);
        setLayoutManger();
        setListener();
        ((MainActivity) getActivity()).notificationCountTitlebar();
//        getHomeFeedData ();
//        setHomeFeedAdapter();
//        callHomeFeedAPI();

//        callHomeFeedAPI();

//        rvHomeFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                int lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
//                if (currentJobPage == 0) {
//                } else {
//                    if (lastVisibleItem == totalItemCount && loadingCurrentJob) {
//                        isSecondCurrent = true;
//                        pageIndex += 1;
//                        currentJobPage = 0;
//                        if (homeFeedArrayList.size() > 0) {
//                            if (homeFeedArrayList.size() != totalRecord) {
//                                progressBar.setVisibility(View.VISIBLE);
//                                callHomeFeedAPI();
//                            }
//                        }
//
//                    }
//                }
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        onRefresh();

        rvHomeFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        isSecondCurrent = true;
                        pageIndex += 1;
                        currentJobPage = 0;
                        if (homeFeedArrayList.size() > 0) {
                            if (homeFeedArrayList.size() != totalRecord) {
                                progressBar.setVisibility(View.VISIBLE);
                                callHomeFeedAPI();
                            }
                        }

                    }
                }
            }
        });
        super.onResume();

    }

    private void callHomeFeedAPI() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.PARAM_PAGEINDEX, pageIndex);
            jsonObject.put(WebField.PARAM_PAGECOUNT, GlobalData.PAGE_ITEM_COUNT);

            LogM.LogE("Request : HomeFeed : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(getActivity(), jsonObject, WebField.BASE_URL + WebField.HOME_FEED.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : HomeFeed  : " + jsonObject.toString());
                    HomeFeed homeFeed = new Gson().fromJson(String.valueOf(jsonObject), HomeFeed.class);
                    if (isSuccess) {
//                        homeF/eedArrayList.clear();
//                        homeFeedArrayList.addAll(homeFeed.getHomeFeeds());
                        LogM.LogE("homeFeedArrayList size: " + homeFeed.getHomeFeeds().size());
                        totalRecord = homeFeed.getTotalRecords();
                        notificationCount = homeFeed.getNotificationCount();
                        ((MainActivity) getActivity()).notificationCount(notificationCount);


//                        if (homeFeedArrayList.size() > 0) {
////                            adapter.notifyDataSetChanged();
//                            rvHomeFeed.setVisibility(View.VISIBLE);
//                            tvEmpty.setVisibility(View.GONE);
//                            setHomeFeedAdapter();
//                        } else {
//                            tvEmpty.setVisibility(View.VISIBLE);
//                            rvHomeFeed.setVisibility(View.GONE);
//                        }

                        currentJobPage = 1;
                        loadingCurrentJob = true;
                        if (homeFeed.getHomeFeeds().size() == 0) {
                            loadingCurrentJob = false;
                            progressBar.setVisibility(View.GONE);
//                            return;
                        }
                        if (isSecondCurrent) {
//                            alTempSearchUser.addAll(user.getUserSearchList());
                            homeFeedArrayList.addAll(homeFeed.getHomeFeeds());
                            progressBar.setVisibility(View.GONE);
//                            setAdapter();
                            adapter.notifyDataSetChanged();
                        } else {
                            homeFeedArrayList.clear();
                            homeFeedArrayList.addAll(homeFeed.getHomeFeeds());
                            if (homeFeedArrayList != null && homeFeedArrayList.size() != 0) {
                                tvEmpty.setVisibility(View.GONE);
                                rvHomeFeed.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                setHomeFeedAdapter();
                            } else {
                                tvEmpty.setVisibility(View.VISIBLE);
                                rvHomeFeed.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        AlertDialogUtility.showSnakeBar(homeFeed.getMessage(), snackBarView, getActivity());
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setHomeFeedAdapter() {
        adapter = new HomeFeedAdapter(this, this, context, homeFeedArrayList);
        rvHomeFeed.setAdapter(adapter);
    }


    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvHomeFeed.setLayoutManager(linearLayoutManager);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, int pos) {
        LogM.LogE("you have clicked " + pos);
    }

    @Override
    public void onPostexpressionClick(View view, int pos, int count) {
        try {
            if (count > 0) {
                Intent intent = new Intent(context, PostExpressionActivity.class);
                intent.putExtra(GlobalData.IS_FROM, GlobalData.DETAILS);
                intent.putExtra("position", pos);
                intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedArrayList.get(pos).getHomeFeedId());
                if (homeFeedArrayList.get(pos).getIs_goals() == 1) {
                    intent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_GOAL);
                } else if (homeFeedArrayList.get(pos).getIs_inspiring() == 1) {
                    intent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_INSPIRING);
                } else if (homeFeedArrayList.get(pos).getIs_admiring() == 1) {
                    intent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_ADMIRING);
                }
                getActivity().startActivityForResult(intent, 132);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == GlobalData.REQ_FROM_LIKEDETAILS_TO_HOMEFEED) {
                if (resultCode == Activity.RESULT_OK) {

                    int intFeedPosition = data.getIntExtra("position", 0);
                    homeFeedArrayList.get(intFeedPosition).setExpressionCount(data.getIntExtra("likeCount", 0));
                    adapter.notifyItemChanged(intFeedPosition);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateComment(int count, int pos) {
        homeFeedArrayList.get(pos).setCommentCount(count);
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void onLikeClick(View view, int pos) {
        LogM.LogE("you have clicked " + pos);
//        for (int i = 0; i < homeFeedArrayList.size(); i++) {
//            if (pos == i) {
//                if (homeFeedArrayList.get(i).getExpressionCount() == 1) {
//                    homeFeedArrayList.get(i).setExpressionCount(0);
//                    adapter.notifyItemChanged(i);
//                } else {
//                    homeFeedArrayList.get(i).setExpressionCount(1);
//                    adapter.notifyItemChanged(i);
//                }
//            }
//        }
    }

    @Override
    public void onCommentClick(View view, int pos) {
        LogM.LogE("you have clicked " + pos);
        for (int i = 0; i < homeFeedArrayList.size(); i++) {
            if (pos == i) {
                homeFeedArrayList.get(i).setCommentCount(homeFeedArrayList.get(i).getCommentCount());
                adapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        isSecondCurrent = false;
        callHomeFeedAPI();
//        setHomeFeedAdapter();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        LogM.LogE("you have clicked meal  " + position);
        ArrayList<Meals.Meal> alMeal = new ArrayList<>();

        if (homeFeedArrayList.get(position).getMealsBreakfast().size() > 0) {


            for (int i = 0; i < homeFeedArrayList.get(position).getMealsBreakfast().size(); i++) {

                alMeal.add(new Meals.Meal(homeFeedArrayList.get(position).getMealsBreakfast().get(i).getMealId(),
                        homeFeedArrayList.get(position).getMealsBreakfast().get(i).getMealPhoto(),
                        homeFeedArrayList.get(position).getMealsBreakfast().get(i).getMealType(),
                        homeFeedArrayList.get(position).getMealsBreakfast().get(i).getFoodName(),
                        homeFeedArrayList.get(position).getMealsBreakfast().get(i).getMealName(),
                        homeFeedArrayList.get(position).getMealsBreakfast().get(i).getUnit(),
                        homeFeedArrayList.get(position).getMealsBreakfast().get(i).getQuantity(),

                        homeFeedArrayList.get(position).getHomeFeedId(),
                        homeFeedArrayList.get(position).getIs_inspiring(),
                        homeFeedArrayList.get(position).getIs_goals(),
                        homeFeedArrayList.get(position).getIs_admiring(),
                        homeFeedArrayList.get(position).getExpressionCount(),
                        homeFeedArrayList.get(position).getCommentCount()

                ));
            }

            LogM.LogE("size of list" + alMeal.size() + alMeal.get(0).getFoodName());

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, Integer.parseInt(homeFeedArrayList.get(position).getUserId()));
//            context.startActivity(intent);
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);
        } else if (homeFeedArrayList.get(position).getMealsSnacks().size() > 0) {
            // itemClick.onItemClickListener(view, position);
            for (int i = 0; i < homeFeedArrayList.get(position).getMealsSnacks().size(); i++) {
                alMeal.add(new Meals.Meal(homeFeedArrayList.get(position).getMealsSnacks().get(i).getMealId(),
                        homeFeedArrayList.get(position).getMealsSnacks().get(i).getMealPhoto(),
                        homeFeedArrayList.get(position).getMealsSnacks().get(i).getMealType(),
                        homeFeedArrayList.get(position).getMealsSnacks().get(i).getFoodName(),
                        homeFeedArrayList.get(position).getMealsSnacks().get(i).getMealName(),
                        homeFeedArrayList.get(position).getMealsSnacks().get(i).getUnit(),
                        homeFeedArrayList.get(position).getMealsSnacks().get(i).getQuantity(),
                        homeFeedArrayList.get(position).getHomeFeedId(),
                        homeFeedArrayList.get(position).getIs_inspiring(),
                        homeFeedArrayList.get(position).getIs_goals(),
                        homeFeedArrayList.get(position).getIs_admiring(),
                        homeFeedArrayList.get(position).getExpressionCount(),
                        homeFeedArrayList.get(position).getCommentCount()
                ));
            }

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.MEAL, alMeal);
            intent.putExtra(GlobalData.OTHER_USER_ID, Integer.parseInt(homeFeedArrayList.get(position).getUserId()));
            context.startActivity(intent);
        } else if (homeFeedArrayList.get(position).getMealsLunch().size() > 0) {
            // itemClick.onItemClickListener(view, position);
            for (int i = 0; i < homeFeedArrayList.get(position).getMealsLunch().size(); i++) {
                alMeal.add(new Meals.Meal(homeFeedArrayList.get(position).getMealsLunch().get(i).getMealId(),
                        homeFeedArrayList.get(position).getMealsLunch().get(i).getMealPhoto(),
                        homeFeedArrayList.get(position).getMealsLunch().get(i).getMealType(),
                        homeFeedArrayList.get(position).getMealsLunch().get(i).getFoodName(),
                        homeFeedArrayList.get(position).getMealsLunch().get(i).getMealName(),
                        homeFeedArrayList.get(position).getMealsLunch().get(i).getUnit(),
                        homeFeedArrayList.get(position).getMealsLunch().get(i).getQuantity(),
                        homeFeedArrayList.get(position).getHomeFeedId(),
                        homeFeedArrayList.get(position).getIs_inspiring(),
                        homeFeedArrayList.get(position).getIs_goals(),
                        homeFeedArrayList.get(position).getIs_admiring(),
                        homeFeedArrayList.get(position).getExpressionCount(),
                        homeFeedArrayList.get(position).getCommentCount()
                ));
            }
            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, Integer.parseInt(homeFeedArrayList.get(position).getUserId()));
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);
        } else if (homeFeedArrayList.get(position).getMealsDinner().size() > 0) {

            int goal = homeFeedArrayList.get(position).getIs_goals();
            int admiring = homeFeedArrayList.get(position).getIs_admiring();
            int inspiring = homeFeedArrayList.get(position).getIs_inspiring();
            String homeFeedId = homeFeedArrayList.get(position).getHomeFeedId();

            for (int i = 0; i < homeFeedArrayList.get(position).getMealsDinner().size(); i++) {
                alMeal.add(new Meals.Meal(homeFeedArrayList.get(position).getMealsDinner().get(i).getMealId(),
                        homeFeedArrayList.get(position).getMealsDinner().get(i).getMealPhoto(),
                        homeFeedArrayList.get(position).getMealsDinner().get(i).getMealType(),
                        homeFeedArrayList.get(position).getMealsDinner().get(i).getFoodName(),
                        homeFeedArrayList.get(position).getMealsDinner().get(i).getMealName(),
                        homeFeedArrayList.get(position).getMealsDinner().get(i).getUnit(),
                        homeFeedArrayList.get(position).getMealsDinner().get(i).getQuantity(),
                        homeFeedArrayList.get(position).getHomeFeedId(),
                        homeFeedArrayList.get(position).getIs_inspiring(),
                        homeFeedArrayList.get(position).getIs_goals(),
                        homeFeedArrayList.get(position).getIs_admiring(),
                        homeFeedArrayList.get(position).getExpressionCount(),
                        homeFeedArrayList.get(position).getCommentCount()
                ));
            }

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, Integer.parseInt(homeFeedArrayList.get(position).getUserId()));
            intent.putExtra(GlobalData.MEAL, alMeal);
            intent.putExtra(GlobalData.GOAL, goal);
            intent.putExtra(GlobalData.ADMIRING, admiring);
            intent.putExtra(GlobalData.INSPIRING, inspiring);
            intent.putExtra(GlobalData.HOME_FEED_ID, homeFeedId);

            context.startActivity(intent);
        } else if (homeFeedArrayList.get(position).getOtherMealData().size() > 0) {
            // itemClick.onItemClickListener(view, position);
            LogM.LogE("MAIN arrayLIST SIze==>" + homeFeedArrayList.get(position).getOtherMealData().size());
            for (int j = 0; j < homeFeedArrayList.get(position).getOtherMealData().size(); j++) {
//                homeFeedArrayList.get(position).getOtherMealData()
                LogM.LogE("Inner arrayLIST SIze==>" + homeFeedArrayList.get(position).getOtherMealData().get(j).size());
                for (int i = 0; i < homeFeedArrayList.get(position).getOtherMealData().get(j).size(); i++) {
                    alMeal.add(new Meals.Meal(homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getMealId(),
                            homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getMealPhoto(),
                            homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getMealType(),
                            homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getFoodName(),
                            homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getMealName(),
                            homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getUnit(),
                            homeFeedArrayList.get(position).getOtherMealData().get(j).get(i).getQuantity(),
                            homeFeedArrayList.get(position).getHomeFeedId(),
                            homeFeedArrayList.get(position).getIs_inspiring(),
                            homeFeedArrayList.get(position).getIs_goals(),
                            homeFeedArrayList.get(position).getIs_admiring(),
                            homeFeedArrayList.get(position).getExpressionCount(),
                            homeFeedArrayList.get(position).getCommentCount()
                    ));
                }
            }

            Intent intent = new Intent(context, AddMiMealActivity.class);
            intent.putExtra(GlobalData.OTHER_USER_ID, Integer.parseInt(homeFeedArrayList.get(position).getUserId()));
            intent.putExtra(GlobalData.MEAL, alMeal);
            context.startActivity(intent);

        } else if (homeFeedArrayList.get(position).getSupplements().getSuppId() != 0) {
            Intent intent = new Intent(context, MiSupplementDetailActivity.class);
            intent.putExtra(GlobalData.FROM, GlobalData.HOME_FEED);
            intent.putExtra(GlobalData.USER_ID, homeFeedArrayList.get(position).getUserId());
            intent.putExtra(GlobalData.SUPP_OBJECT, homeFeedArrayList.get(position));
            context.startActivity(intent);
        } else if (homeFeedArrayList.get(position).getWorkouts().getWorkoutId() != 0) {
            Intent myWorkoutDetailsIntent = new Intent(context, MyWorkoutDetailsActivity.class);
            myWorkoutDetailsIntent.putExtra(GlobalData.IS_FROM, GlobalData.HOME_FEED);
            myWorkoutDetailsIntent.putExtra(GlobalData.HOME_FEED_OBJECT, homeFeedArrayList.get(position));
            myWorkoutDetailsIntent.putExtra(GlobalData.USER_ID, homeFeedArrayList.get(position).getUserId());
            myWorkoutDetailsIntent.putExtra(GlobalData.HOME_FEED_ID, homeFeedArrayList.get(position).getHomeFeedId());
            myWorkoutDetailsIntent.putExtra("WorkoutDetails", homeFeedArrayList.get(position).getWorkouts());
            myWorkoutDetailsIntent.putExtra("WorkoutDuration", homeFeedArrayList.get(position).getWorkouts().getWorkoutDuration());
            myWorkoutDetailsIntent.putParcelableArrayListExtra("WorkoutMedia", (ArrayList<? extends Parcelable>) homeFeedArrayList.get(position).getWorkouts().getWorkoutMedia());
            context.startActivity(myWorkoutDetailsIntent);
        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
