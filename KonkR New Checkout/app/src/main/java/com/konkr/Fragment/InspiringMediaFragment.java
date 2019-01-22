package com.konkr.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Adapters.InspiringAdapter;
import com.konkr.Adapters.InspiringMediaAdapter;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.ExpressionMedia;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.FragmentInspiringBinding;

import java.util.ArrayList;
import java.util.List;

public class InspiringMediaFragment extends Fragment {
    private FragmentInspiringBinding binding;
    private RecyclerView rvInspiring;
    private InspiringMediaAdapter adapter;
    private MyTextView tvEmpty;
    private List<ExpressionList.Admiring> admiringList;
    private List<ExpressionList.Goal> goalList;
    public ArrayList<ExpressionMedia.InspiringBean> insList;

    private LinearLayoutManager linearLayoutManager;

    //  private ArrayList<Inspiring> inspiringArrayList;


    public InspiringMediaFragment() {
    }

    public static InspiringMediaFragment newInstance(ArrayList<ExpressionMedia.InspiringBean> inspiringList) {
        InspiringMediaFragment fragment = new InspiringMediaFragment();
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
        tvEmpty = binding.tvEmpty;
        setLayoutManger();
        // getGoalData();
        setGoalAdapter();

        return view;
    }

    private void setGoalAdapter() {
        LogM.LogE("size of inspiring===>" + insList);
        adapter = new InspiringMediaAdapter(getActivity(), insList);
        rvInspiring.setAdapter(adapter);

        if (insList.size() > 0) {
            rvInspiring.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            rvInspiring.setVisibility(View.GONE);
            // tvEmpty.setVisibility(View.VISIBLE);
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
}
