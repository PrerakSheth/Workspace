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
import com.konkr.Adapters.AdmiringAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.Admiring;
import com.konkr.Models.ExpressionList;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.FragmentAdmiringBinding;

import java.util.ArrayList;

/**
 * Created by Android on 6/20/2018.
 */

public class AdmiringFragment extends Fragment implements OnRecyclerViewItemClickListener {
    private FragmentAdmiringBinding binding;
    private RecyclerView rvAdmiring;
    private AdmiringAdapter adapter;
    private MyTextView tvEmpty;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ExpressionList.Admiring> admiringArrayList= new ArrayList<>();

    public AdmiringFragment() {
    }

    public static AdmiringFragment newInstance(ArrayList<ExpressionList.Admiring> admiringArrayList) {
        AdmiringFragment fragment = new AdmiringFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GlobalData.ADMIRING_ARRAY_LIST, admiringArrayList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admiring, container, false);
        View view = binding.getRoot();
        rvAdmiring = binding.rvAdmiring;
        tvEmpty=binding.tvEmpty;
        setLayoutManger();
        // getGoalData();
        setGoalAdapter();

        return view;
    }

    private void setGoalAdapter() {
        LogM.LogE("size of admiring===>"+admiringArrayList.size());
        adapter = new AdmiringAdapter(getActivity(), admiringArrayList,this);
        rvAdmiring.setAdapter(adapter);

        if(admiringArrayList.size()>0){
            rvAdmiring.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }else{
            rvAdmiring.setVisibility(View.GONE);
            //tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            admiringArrayList .addAll( getArguments().getParcelableArrayList(GlobalData.ADMIRING_ARRAY_LIST));
        }
    }

    private void getGoalData() {

//        if (admiringArrayList == null) {
//
//            admiringArrayList = new ArrayList<> ();
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "John Doe",4));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "Stanley Johnsons",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "Jeff Doe",2));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile),"Dave Anderson",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "Jim Brinholz",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile),"John Doe",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "Stanley Johnsons",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "Jeff Doe",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile),"Dave Anderson",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile), "Jim Brinholz",1));
//            admiringArrayList.add (new Admiring ((R.drawable.user_profile),"John Doe",1));
//
//
//        }
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvAdmiring.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        LogM.LogE("position clicked +==>"+position);
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(GlobalData.OTHER_USER_ID, admiringArrayList.get(position).getUserId());
        getContext().startActivity(intent);
    }
}

