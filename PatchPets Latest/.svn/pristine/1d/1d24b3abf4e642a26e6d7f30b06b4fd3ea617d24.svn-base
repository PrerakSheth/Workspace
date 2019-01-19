package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.patchpets.Adapters.CardListAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityCardListBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.CardListRequest;
import com.patchpets.model.responseModel.CardListResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

import java.util.ArrayList;

public class CardListActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private ActivityCardListBinding binding;
    private HeaderBar headerBar;
    private TextView tvEmpty;
    private RecyclerView rvCardList;
    private CardListAdapter cardLisAdapter;
    private APIRequest apiRequest;
    private ProgressDialog pDialog;
    private ArrayList<CardListResponse.CardListBean> alCards = new ArrayList<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_list);
        user = SessionManager.getInstance().getUser(CardListActivity.this);

        bindViews();
        setHeaderBar();
        setListener();
        setAdapter();
        callCardListAPI();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvCardList = binding.rvCardList;
        tvEmpty = binding.tvEmpty;
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.ibRight.setVisibility(View.VISIBLE);
        headerBar.ibRight.setImageResource(R.drawable.add);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.setup_card);
    }

    private void setListener() {
        headerBar.ibRight.setOnClickListener(this);
        headerBar.ibLeft.setOnClickListener(this);
    }

    private void setAdapter() {
        cardLisAdapter = new CardListAdapter(this, alCards, this);
        rvCardList.setLayoutManager(new LinearLayoutManager(this));
        rvCardList.setAdapter(cardLisAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibRight:
                Intent intent = new Intent(CardListActivity.this, CardDetailsActivity.class);
                intent.putExtra(Constants.FROM, Constants.ADD);
                startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CARD);
                break;

            case R.id.ibLeft:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(CardListActivity.this, CardDetailsActivity.class);
        intent.putExtra(Constants.EDIT_CARD, alCards.get(position));
        intent.putExtra(Constants.FROM, Constants.EDIT);
        startActivityForResult(intent, Constants.REQUEST_CODE_EDIT_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ADD_CARD || requestCode == Constants.REQUEST_CODE_EDIT_CARD) {
            if (resultCode == RESULT_OK) {
                callCardListAPI();
            }
        }
    }

    private void callCardListAPI() {
        if (Helper.isCheckInternet(CardListActivity.this)) {
            pDialog = new ProgressDialog(CardListActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.cardListAPI(cardListRequest(), responseCallback);
        }
    }

    private CardListRequest cardListRequest() {
        CardListRequest cardListRequest = new CardListRequest();
        cardListRequest.setUserId(user.getUserId());
        cardListRequest.setAccessToken(user.getAccessToken());
        return cardListRequest;
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                CardListResponse cardListResponse = (CardListResponse) object;
                if (cardListResponse.getStatus() == 1) {
                    alCards.clear();
                    alCards.addAll(cardListResponse.getCardList());
                    cardLisAdapter.notifyDataSetChanged();
                    if (alCards.size() > 0) {
                        rvCardList.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        rvCardList.setVisibility(View.GONE);
                    }
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
