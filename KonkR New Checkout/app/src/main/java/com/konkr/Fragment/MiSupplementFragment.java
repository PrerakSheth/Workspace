package com.konkr.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konkr.Activities.MiSupplementDetailActivity;
import com.konkr.Adapters.MiSuppliAdapter;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.UserDetails;
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
import com.konkr.databinding.FragmentMiSupplementBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MiSupplementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiSupplementFragment extends Fragment implements MiSuppliAdapter.ItemClickListener {

    private FragmentMiSupplementBinding binding;
    private RecyclerView rvMiSuppliment;
    private MyTextView tvEmptyView;
    private MiSuppliAdapter adapter;
    private View snackBarView;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout rvBckground;
    private ArrayList<MiSuppliment.SupplementsBean> supplementsBeanArrayList = new ArrayList<>();
    ArrayList<UserDetails.UserDetailsBean.SupplementsBean> alSupplementDummy = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MiSupplementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiSupplementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiSupplementFragment newInstance(String param1, String param2) {
        MiSupplementFragment fragment = new MiSupplementFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mi_supplement, container, false);

        View view = binding.getRoot();


        rvMiSuppliment = binding.rvMiSuppliment;
        tvEmptyView = binding.tvEmpty;
        rvBckground = binding.rvBckground;
        snackBarView = getActivity().findViewById(android.R.id.content);

//        String from = getArguments().getString(GlobalData.FROM);
//
//        if (from != null) {
//            headerbar.setVisibility(View.VISIBLE);
//        }

        setLayoutManger();
//        callGetMySupplementsApi();
        setAdapter();
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        callGetMySupplementsApi();
    }

    private void setAdapter() {
        adapter = new MiSuppliAdapter(MiSupplementFragment.this, getActivity(), supplementsBeanArrayList, alSupplementDummy);
        rvMiSuppliment.setAdapter(adapter);

    }

    private void callGetMySupplementsApi() {

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId(getActivity()));
                jsonObject.put(WebField.SET_UP_CARD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(getActivity()));
                LogM.LogE("Request : Mi Suppliment : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(getActivity(), jsonObject, WebField.BASE_URL + WebField.GET_MY_SUPPLEMENTS.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final MiSuppliment miSupplimentList = new Gson().fromJson(String.valueOf(jsonObject), MiSuppliment.class);
                        if (isSuccess) {
                            LogM.LogE("Response : Mi Suppliment : " + jsonObject.toString());
                            supplementsBeanArrayList.clear();
                            supplementsBeanArrayList.addAll(miSupplimentList.getSupplements());

                            if (supplementsBeanArrayList.size() > 0) {
                                adapter.notifyDataSetChanged();
                                rvMiSuppliment.setVisibility(View.VISIBLE);
                                tvEmptyView.setVisibility(View.GONE);
//                                rvBckground.setVisibility(View.VISIBLE);
                            } else {
                                tvEmptyView.setVisibility(View.VISIBLE);
                                rvMiSuppliment.setVisibility(View.GONE);
//                                rvBckground.setVisibility(View.GONE);
                            }


                        } else {
                            AlertDialogUtility.showSnakeBar(miSupplimentList.getMessage(), snackBarView, getActivity());
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(getActivity(), GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    private void setLayoutManger() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvMiSuppliment.setLayoutManager(linearLayoutManager);
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
        Intent intent = new Intent(getActivity(), MiSupplementDetailActivity.class);
        intent.putExtra(GlobalData.FROM, GlobalData.SUPP_LIST);
        intent.putExtra(GlobalData.OTHER_USER_ID, SessionManager.getUserId(getActivity()));
        intent.putExtra(GlobalData.SUPP_INFO, supplementsBeanArrayList.get(pos));
        getActivity().startActivityForResult(intent, GlobalData.REQ_DELETE_SUPPLIMENT);
    }

    public void update() {
        supplementsBeanArrayList.clear();
        callGetMySupplementsApi();

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
