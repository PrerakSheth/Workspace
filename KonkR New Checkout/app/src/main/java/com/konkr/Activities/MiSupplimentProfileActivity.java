package com.konkr.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.konkr.Adapters.MiSuppliAdapter;
import com.konkr.Fragment.MiSupplementFragment;
import com.konkr.Models.MiSuppliment;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityMiSupplimentProfileBinding;

import org.json.JSONObject;

import java.util.ArrayList;

public class MiSupplimentProfileActivity extends AppCompatActivity implements View.OnClickListener, MiSuppliAdapter.ItemClickListener {

    ActivityMiSupplimentProfileBinding binding;
    Headerbar headerBar;
    private RecyclerView rvMiSuppliment;
    private MyTextView tvEmptyView;
    private MiSuppliAdapter adapter;
    private View snackBarView;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout rvBckground;
    private ArrayList<MiSuppliment.SupplementsBean> supplementsBeanArrayList = new ArrayList<>();
    ArrayList<UserDetails.UserDetailsBean.SupplementsBean> alSupplementDummy = new ArrayList<>();
    private MiSupplimentProfileActivity context;
    int otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mi_suppliment_profile);
        context = MiSupplimentProfileActivity.this;
        bindView();
        otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
        setHeaderBar();
        setListener();
        setLayoutManger();
        callGetMySupplementsApi();
        setAdapter();
    }

    private void bindView() {
        headerBar = binding.headerBar;
        tvEmptyView = binding.tvEmpty;
        rvMiSuppliment = binding.rvMiSuppliment;
        rvBckground = binding.rvBckground;
        snackBarView = context.findViewById(android.R.id.content);
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        if (otherUserId == 0) {
            headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
            headerBar.ibtnRightTwo.setImageResource(R.drawable.add);
        } else {
            headerBar.ibtnRightTwo.setVisibility(View.GONE);
        }
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.mi_suppliment_title);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);
        headerBar.ibtnRightTwo.setOnClickListener(this);
    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(context);
        rvMiSuppliment.setLayoutManager(linearLayoutManager);
    }


    private void callGetMySupplementsApi() {

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                jsonObject.put(WebField.PARAM_OTHERUSERID, otherUserId);
                LogM.LogE("Request : Mi Suppliment : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_MY_SUPPLEMENTS.MODE, 1, new OnUpdateListener() {
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
                            } else {
                                tvEmptyView.setVisibility(View.VISIBLE);
                                rvMiSuppliment.setVisibility(View.GONE);
                            }

                        } else {
                            AlertDialogUtility.showSnakeBar(miSupplimentList.getMessage(), snackBarView, context);
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    private void setAdapter() {
        adapter = new MiSuppliAdapter(MiSupplimentProfileActivity.this, context, supplementsBeanArrayList, alSupplementDummy);
        rvMiSuppliment.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;
            case R.id.ibtnRightTwo:
                Intent intent = new Intent(context, MiSupplimentActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 222);
                break;

        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        Intent intent = new Intent(context, MiSupplementDetailActivity.class);
        intent.putExtra(GlobalData.FROM, GlobalData.SUPP_LIST);
        intent.putExtra(GlobalData.OTHER_USER_ID, SessionManager.getUserId(MiSupplimentProfileActivity.this));
        intent.putExtra(GlobalData.SUPP_INFO, supplementsBeanArrayList.get(pos));
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 990);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222 && resultCode == RESULT_OK) {
            callGetMySupplementsApi();
        } else if (requestCode == 990 && resultCode == RESULT_OK) {
            callGetMySupplementsApi();
        }
    }
}
