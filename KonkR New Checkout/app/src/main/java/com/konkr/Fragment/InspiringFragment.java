package com.konkr.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Activities.ProfileActivity;
import com.konkr.Adapters.InspiringAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.Inspiring;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.FragmentInspiringBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 6/20/2018.
 */

public class InspiringFragment extends Fragment implements OnRecyclerViewItemClickListener {
    private FragmentInspiringBinding binding;
    private RecyclerView rvInspiring;
    private InspiringAdapter adapter;
    private MyTextView tvEmpty;
    private List<ExpressionList.Admiring> admiringList;
    private List<ExpressionList.Goal> goalList;
    public ArrayList<ExpressionList.Inspiring> insList;

    private LinearLayoutManager linearLayoutManager;
  //  private ArrayList<Inspiring> inspiringArrayList;


    public InspiringFragment() {
    }

    public static InspiringFragment newInstance(ArrayList<ExpressionList.Inspiring> inspiringList) {
        InspiringFragment fragment = new InspiringFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GlobalData.INSPIRING_ARRAY_LIST, inspiringList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspiring, container, false);
        View view = binding.getRoot();
        rvInspiring = binding.rvInspiring;
        tvEmpty=binding.tvEmpty;
        setLayoutManger();
        // getGoalData();
        setGoalAdapter();

        return view;
    }

    private void setGoalAdapter() {
        LogM.LogE("size of inspiring===>" + insList);
        adapter = new InspiringAdapter(getActivity(), insList,this);
        rvInspiring.setAdapter(adapter);

        if(insList.size()>0){
            rvInspiring.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }else{
            rvInspiring.setVisibility(View.GONE);
          //  tvEmpty.setVisibility(View.VISIBLE);
        }


    }

    private void getGoalData() {

//        if (inspiringArrayList == null) {
//
//            inspiringArrayList = new ArrayList<> ();
//
//
//
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "John Doe",4));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "Stanley Johnsons",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "Jeff Doe",2));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile),"Dave Anderson",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "Jim Brinholz",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile),"John Doe",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "Stanley Johnsons",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "Jeff Doe",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile),"Dave Anderson",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile), "Jim Brinholz",1));
//            inspiringArrayList.add (new Inspiring ((R.drawable.user_profile),"John Doe",1));
//
//
//
//
//        }
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvInspiring.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            insList = getArguments().getParcelableArrayList(GlobalData.INSPIRING_ARRAY_LIST);
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        LogM.LogE("position clicked +==>"+position);
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(GlobalData.OTHER_USER_ID, insList.get(position).getUserId());
        getContext().startActivity(intent);
    }
}
