package com.konkr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.konkr.Adapters.CardListAdapter;
import com.konkr.Models.CardList;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityCardListBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class CardListActivity extends AppCompatActivity implements View.OnClickListener, CardListAdapter.ItemClickListener {
    private Headerbar headerBar;
    private MyTextView tvEmptyView;
    private RecyclerView cardListRecyclerView;
    private ActivityCardListBinding binding;
    private LinearLayoutManager linearLayoutManager;
    private CardListAdapter cardLisAdapter;
    private Activity context;
    private ArrayList<CardList.CardListBean> cardDetails = new ArrayList<>();
    private String donation;
    double premiumAmount;
    int subscriptionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView (R.layout.activity_card_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_list);
        context = CardListActivity.this;

        bindViews();
        setHeaderBar();
        setListener();
        setLayoutManger();
        getIntentData();
        setAdapter();
        CallCardListAPI();

    }

    private void getIntentData() {
        if (getIntent().getExtras() != null) {
//            donation = getIntent().getStringExtra(GlobalData.DONATION);
//            LogM.LogV("cardListActivity" + "donation" + donation);
//            if (donation != null) {
//                headerBar.tvTitle.setText(R.string.select_your_card_title);
//            } else {
//                headerBar.tvTitle.setText(R.string.set_up_card_title);
//            }

            if(getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.DONATION)){
                donation = getIntent().getStringExtra(GlobalData.DONATION);
                LogM.LogV("cardListActivity" + "donation" + donation);
                headerBar.tvTitle.setText(R.string.select_your_card_title);

            }
            else if (getIntent().getStringExtra(GlobalData.IS_FROM).equalsIgnoreCase(GlobalData.PREMIUM_MEMBERSHIP)){
                premiumAmount=getIntent().getDoubleExtra(GlobalData.SUBSCRIPTION_AMOUNT,0);
                subscriptionId=getIntent().getIntExtra(GlobalData.SUBSCRIPTION_ID,0);

                headerBar.tvTitle.setText(R.string.select_your_card_title);
            }

            else{
                headerBar.tvTitle.setText(R.string.set_up_card_title);
            }
        }

        }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setLayoutManger() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        cardListRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void bindViews() {

        try {
            headerBar = binding.headerBar;
            cardListRecyclerView = binding.cardListRecyclerView;
            tvEmptyView = binding.tvEmpty;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.ibtnRightTwo.setVisibility(View.VISIBLE);
            headerBar.ibtnRightTwo.setImageResource(R.drawable.add);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.set_up_card_title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            headerBar.ibtnRightOne.setOnClickListener(this);
            headerBar.ibtnRightTwo.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                LogM.LogV("value of donation >" + donation);
                onBackPressed();
//                if (donation != null) {
//                    Intent intent = new Intent(context, DonationsActivity.class);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//

//                finish();
                break;
            case R.id.ibtnRightTwo:

                if (donation != null && cardDetails.size() < 1) {
                    Intent donationIntent = new Intent(context, SetUpCardActivity.class);
                    donationIntent.putExtra(GlobalData.PAY_WITHOUT_ADD_CARD, donation);
//                    donationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(donationIntent);
                } else if(premiumAmount!=0 && cardDetails.size()<1){
                    Intent subscriptionIntent = new Intent(context, SetUpCardActivity.class);
                    subscriptionIntent.putExtra(GlobalData.PAY_SUBS_WITHOUT_ADD_CARD, premiumAmount);
                    subscriptionIntent.putExtra(GlobalData.SUBSCRIPTION_ID, subscriptionId);
                    startActivity(subscriptionIntent);
                }else {
                    startActivityForResult(new Intent(context, SetUpCardActivity.class), 111);
                }
                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (donation != null) {
//            Intent intent = new Intent(context, DonationsActivity.class);
////            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//
//        finish();
//    }

    private void CallCardListAPI() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_CARD_LIST.PARAM_USER_ID, SessionManager.getUserId(CardListActivity.this));
            jsonObject.put(WebField.GET_CARD_LIST.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(CardListActivity.this));


            LogM.LogE("Request : CardList : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_CARD_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : CardList : " + jsonObject.toString());
                    final CardList cardList = new Gson().fromJson(String.valueOf(jsonObject), CardList.class);
                    if (isSuccess) {

//                        CardList dd= new CardList();
//                        dd.setCardList (jsonObject.getString (GlobalData.USER_ID));
//                        dd.setCardList (cardList.getCardList ());
                        cardDetails.clear();
                        cardDetails.addAll(cardList.getCardList());

                        if (cardDetails.size() > 0) {
                            cardLisAdapter.notifyDataSetChanged();
                            cardListRecyclerView.setVisibility(View.VISIBLE);
//                            setAdapter ();
                            tvEmptyView.setVisibility(View.GONE);
                        } else {
                            tvEmptyView.setVisibility(View.VISIBLE);
                            cardListRecyclerView.setVisibility(View.GONE);
                        }
                    }

                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {

        cardLisAdapter = new CardListAdapter(this, context, cardDetails);
        cardListRecyclerView.setAdapter(cardLisAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
//            String da=data.getData ().
            CallCardListAPI();
        }
    }

    @Override
    public void onItemClick(View view, int pos) {
        Intent intent = new Intent(context, SetUpCardActivity.class);
        intent.putExtra(GlobalData.CARD_INFO, cardDetails.get(pos));
        intent.putExtra(GlobalData.DONATION, donation);
        intent.putExtra(GlobalData.PAY_SUBS_USING_SLECT_CARD,premiumAmount);
        intent.putExtra(GlobalData.SUBSCRIPTION_ID, subscriptionId);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, 111);
    }

    @Override
    public void onCardNumberClick(View view, int pos) {

        Intent intent = new Intent(context, SetUpCardActivity.class);
        intent.putExtra(GlobalData.CARD_INFO, cardDetails.get(pos));
        intent.putExtra(GlobalData.DONATION, donation);
        intent.putExtra(GlobalData.PAY_SUBS_USING_SLECT_CARD,premiumAmount);
        intent.putExtra(GlobalData.SUBSCRIPTION_ID, subscriptionId);
//        intent...FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, 111);
    }


}
