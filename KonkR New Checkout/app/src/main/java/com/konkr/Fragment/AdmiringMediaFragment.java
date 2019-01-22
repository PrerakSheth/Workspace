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

import com.konkr.Adapters.AdmiringAdapter;
import com.konkr.Adapters.AdmiringMediaAdapter;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.ExpressionMedia;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.FragmentAdmiringBinding;

import java.util.ArrayList;

public class AdmiringMediaFragment extends Fragment {
    private FragmentAdmiringBinding binding;
    private RecyclerView rvAdmiring;
    private AdmiringMediaAdapter adapter;
    private MyTextView tvEmpty;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ExpressionMedia.AdmiringBean> admiringArrayList= new ArrayList<>();

    public AdmiringMediaFragment() {
    }

    public static AdmiringMediaFragment newInstance(ArrayList<ExpressionMedia.AdmiringBean> admiringArrayList) {
        AdmiringMediaFragment fragment = new AdmiringMediaFragment();
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
       adapter = new AdmiringMediaAdapter(getActivity(), admiringArrayList);
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
}