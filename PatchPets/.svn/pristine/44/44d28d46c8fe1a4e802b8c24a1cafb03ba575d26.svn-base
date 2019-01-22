package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.patchpets.Adapters.PartnersAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityPartnersBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.PartnerListRequest;
import com.patchpets.model.responseModel.PartnerListResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

import java.util.ArrayList;

public class PartnersActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPartnersBinding binding;
    private HeaderBar headerBar;
    private RecyclerView rvPartners;
    private TextView tvEmpty;
    private PartnersAdapter partnersAdapter;
    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private ArrayList<PartnerListResponse.PartnerListBean> alPartners = new ArrayList<>();
    private User user;
    private View snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_partners);
        user = SessionManager.getInstance().getUser(PartnersActivity.this);
        snackBar = findViewById(android.R.id.content);

        bindViews();
        setHeaderBar();
        setListener();
        setAdapter();
        callPartnerListAPI();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvPartners = binding.rvPartners;
        tvEmpty = binding.tvEmpty;
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.partners);
    }

    private void setListener() {
        headerBar.ibRight.setOnClickListener(this);
        headerBar.ibLeft.setOnClickListener(this);
    }

    private void setAdapter() {
        partnersAdapter = new PartnersAdapter(PartnersActivity.this, alPartners);
        rvPartners.setLayoutManager(new GridLayoutManager(this, 2));
        rvPartners.setAdapter(partnersAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;
        }
    }

    private void callPartnerListAPI() {
        if (Helper.isCheckInternet(PartnersActivity.this)) {
            pDialog = new ProgressDialog(PartnersActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.partnerListAPI(partnerListRequestModel(), responseCallback);
        }
    }

    private PartnerListRequest partnerListRequestModel() {
        PartnerListRequest partnerListRequest = new PartnerListRequest();
        partnerListRequest.setUserId(user.getUserId());
        partnerListRequest.setAccessToken(user.getAccessToken());
        return partnerListRequest;
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                PartnerListResponse response = (PartnerListResponse) object;
                if (response.getStatus() == 1) {
                    alPartners.clear();
                    alPartners.addAll(response.getPartnerList());
                    partnersAdapter.notifyDataSetChanged();
                    if (alPartners.size() > 0) {
                        rvPartners.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        rvPartners.setVisibility(View.GONE);
                    }
                } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                    AlertDialogUtility.showAlert(PartnersActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(PartnersActivity.this, SignInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, PartnersActivity.this);
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvPartners.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void ResponseFailCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    };
}
