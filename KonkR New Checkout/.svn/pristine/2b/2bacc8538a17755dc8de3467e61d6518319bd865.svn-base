package com.konkr.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Activities.ProfileActivity;
import com.konkr.Adapters.GoalAdapter;
import com.konkr.Adapters.GoalMediaAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.ExpressionMedia;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.FragmentGoalBinding;

import java.util.ArrayList;

public class GoalMediaFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private FragmentGoalBinding binding;
    private RecyclerView rvGoal;
    private GoalMediaAdapter adapter;
    private MyTextView tvEmpty;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ExpressionMedia.GoalsBean> goalArrayList;

    public GoalMediaFragment() {
    }
    public static GoalMediaFragment newInstance(ArrayList<ExpressionMedia.GoalsBean> goalsList) {
        GoalMediaFragment fragment = new GoalMediaFragment();
        Bundle bundle= new Bundle();
        bundle.putParcelableArrayList(GlobalData.GOAL_ARRAY_LIST,goalsList);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_goal, container, false);
        View view = binding.getRoot();
        rvGoal = binding.rvGoal;
        tvEmpty=binding.tvEmpty;
        setLayoutManger();
        // getGoalData();
        setGoalAdapter();

        return view;
    }
    private void setGoalAdapter() {

        LogM.LogE("size of goal===>"+goalArrayList.size());
       adapter = new GoalMediaAdapter (getActivity (), goalArrayList,this);
        rvGoal.setAdapter (adapter);

        if(goalArrayList.size()>0){
            rvGoal.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }else{
            rvGoal.setVisibility(View.GONE);
            // tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            goalArrayList= getArguments().getParcelableArrayList(GlobalData.GOAL_ARRAY_LIST);
        }
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager (getActivity ());
        rvGoal.setLayoutManager (linearLayoutManager);
    }

//     on click of item-------------
    @Override
    public void onItemClickListener(View view, int position) {

        LogM.LogE("position clicked +==>"+position);
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(GlobalData.OTHER_USER_ID, goalArrayList.get(position).getUserId());
        getContext().startActivity(intent);
    }
}
