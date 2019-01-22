package com.konkr.Fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.konkr.Activities.PartnersActivity;
import com.konkr.Adapters.AdCardAdapter;
import com.konkr.Adapters.PartnersAdapter;
import com.konkr.Models.Advertisement;
import com.konkr.Models.Partners;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityPartnersBinding;
import com.konkr.databinding.FragmentPartnersBinding;
import com.konkr.databinding.FragmentSettingsBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartnersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartnersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartnersFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

//    private Headerbar headerBar;
    private MyTextView tvEmptyView;
    private RecyclerView partnersRecyclerView;
    private FragmentPartnersBinding binding;
    private GridLayoutManager gridLayoutManager;
    private PartnersAdapter partnersAdapter;
    private Activity context;
    private ArrayList<Partners.PartnerListBean> partnerListBeanArrayList = new ArrayList<>();
    private ViewPager viewpager;
    private ImageView ivClose;
    AdCardAdapter adapterAdCard;
    private Timer timer = new Timer();

    private ArrayList<Advertisement.AdvertiseListBean> alAdvertisment = new ArrayList<>();
    int currentPage = 0;
    //    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    private View snackBarView;

    public PartnersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartnersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartnersFragment newInstance(String param1, String param2) {
        PartnersFragment fragment = new PartnersFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_partners, container, false);
        View view = binding.getRoot();
        context = getActivity();

        snackBarView = context.findViewById(android.R.id.content);
        bindViews();
        setHeaderBar();
        setListener();
        setGridLayoutManger();
        setAdapter();
        callgetPartnerListApi();

        callAdvertisment();

        return view;
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

    private void callAdvertisment() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.ADVERTISEMENT.PARAM_SCREENPOSITION, 0);
            LogM.LogE("Request : Advertisment : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADVERTISEMENT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Advertisment : " + jsonObject.toString());
                    Advertisement advertisment = new Gson().fromJson(String.valueOf(jsonObject), Advertisement.class);
                    if (isSuccess) {
                        if (advertisment.getAdvertiseList().size() > 0) {
                            viewpager.setVisibility(View.VISIBLE);
                            ivClose.setVisibility(View.VISIBLE);

                            alAdvertisment.addAll(advertisment.getAdvertiseList());
                            adapterAdCard = new AdCardAdapter(context, alAdvertisment, 0);
                            viewpager.setAdapter(adapterAdCard);

                            /*After setting the adapter use the timer */
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == alAdvertisment.size() - 1) {
                                        currentPage = 0;
                                    }
                                    viewpager.setCurrentItem(currentPage++, true);
                                }
                            };

//                            timer = new Timer(); // This will create a new Thread
                            timer.schedule(new TimerTask() { // task to be scheduled

                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, DELAY_MS, PERIOD_MS);
                        } else {
                            viewpager.setVisibility(View.GONE);
                            ivClose.setVisibility(View.GONE);
                        }
                        adapterAdCard.notifyDataSetChanged();
                    } else {
                        AlertDialogUtility.showSnakeBar(advertisment.getMessage(), snackBarView, context);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setGridLayoutManger() {

        gridLayoutManager = new GridLayoutManager(context, 2);
        partnersRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void bindViews() {
//        headerBar = binding.headerBar;
        partnersRecyclerView = binding.partnersRecyclerView;
        tvEmptyView = binding.tvEmpty;
        ivClose = binding.ivClose;
        viewpager = binding.viewpager;
    }

    private void setHeaderBar() {
//        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
//        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
//        headerBar.tvTitle.setVisibility(View.VISIBLE);
//        headerBar.tvTitle.setText(R.string.partners_title);
    }

    private void setListener() {

//        headerBar.ibtnLeftOne.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ibtnLeftOne:
//                finish();
//                break;
            case R.id.ivClose:
                viewpager.setVisibility(View.GONE);
                ivClose.setVisibility(View.GONE);
                break;
        }
    }

    private void callgetPartnerListApi() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_PARTNER_LIST.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.GET_PARTNER_LIST.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));

            LogM.LogE("Request : PartnerList : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_PARTNER_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : PartnerList : " + jsonObject.toString());
                    final Partners partners = new Gson().fromJson(String.valueOf(jsonObject), Partners.class);
                    if (isSuccess) {
                        partnerListBeanArrayList.clear();
                        partnerListBeanArrayList.addAll(partners.getPartnerList());

                        if (partnerListBeanArrayList.size() > 0) {
                            partnersAdapter.notifyDataSetChanged();
                            partnersRecyclerView.setVisibility(View.VISIBLE);
//                            setAdapter ();
                            tvEmptyView.setVisibility(View.GONE);
                        } else {
                            tvEmptyView.setVisibility(View.VISIBLE);
                            partnersRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        AlertDialogUtility.showSnakeBar(partners.getMessage(), partnersRecyclerView, context);
                    }

                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        partnersAdapter = new PartnersAdapter(context, partnerListBeanArrayList);
        partnersRecyclerView.setAdapter(partnersAdapter);
//        partnersRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2,
//                getResources().getDimensionPixelSize(R.dimen.recycler_view_item_width)));
    }
}
