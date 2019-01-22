package com.konkr.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.konkr.Models.CardList;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.FourDigitCardFormatWatcher;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MonthYearPicker;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivitySetUpCardBinding;
import com.stripe.android.model.Card;

import org.json.JSONException;
import org.json.JSONObject;

public class SetUpCardActivity extends Activity implements View.OnClickListener {
    /**
     * This Activity will allow us to Add Card and also Show to added Card Details
     */
    private Headerbar headerBar;
    private ActivitySetUpCardBinding binding;
    private MyEditText etNameOnCard, etCardNumber, etCvvCode;
    private Spinner spinMonth, spinYear;
    private MyTextView addCardBtn, payNowBtn, tvYear, tvMonth;
    private String strEtCardNumber, strEtCvvCode, strEtNameOnCard;
    private String strSpinMonth, strSpinYear, strExpiry;
    private ImageView ivCard;
    private String amount;
    private int subscriptionId;
    int currentYear;
    int currentMonth;
    int cardId = 0;
    Activity context;
    CardList.CardListBean cardListBean;
    Card card;
    private MonthYearPicker myp;
    String payNowWithouAddCard;
    double paySubsWithouAddCard;
    double paySubsSlectCard;
    String strCardNoOriginal;
    String cardBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = DataBindingUtil.setContentView (this, R.layout.activity_set_up_card);
        context = SetUpCardActivity.this;


        bindView ();
        setListener ();
        setHeaderBar ();
        getIntentData ();

        etCardNumber.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                card = new Card (charSequence.toString (), 0, 0, "123");
                LogM.LogV ("BRAND: " + card.getBrand ());
                if (card.getBrand () == null) {
                    ivCard.setImageDrawable (null);
                    return;
                }
                if (card.getBrand ().equalsIgnoreCase ("Visa")) {
                    ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.visa));
                }

                if (card.getBrand ().equalsIgnoreCase ("MasterCard")) {
                    ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.mastercard));
                }
                if (card.getBrand ().equalsIgnoreCase ("American Express")) {
                    ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.amex));
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getIntentData() {
        if (getIntent ().getExtras () != null) {

            cardListBean = getIntent ().getExtras ().getParcelable (GlobalData.CARD_INFO);
            amount = getIntent ().getStringExtra (GlobalData.DONATION);// donation Amount
            payNowWithouAddCard = getIntent ().getStringExtra (GlobalData.PAY_WITHOUT_ADD_CARD);
            subscriptionId=getIntent ().getIntExtra(GlobalData.SUBSCRIPTION_ID,0);
            paySubsWithouAddCard=getIntent().getDoubleExtra(GlobalData.PAY_SUBS_WITHOUT_ADD_CARD,0);
            paySubsSlectCard=getIntent().getDoubleExtra(GlobalData.PAY_SUBS_USING_SLECT_CARD,0);
            LogM.LogV ("cardsetup" + "subscriptionId " + subscriptionId);
            LogM.LogV ("cardsetup" + "payNowWithouAddCard " + payNowWithouAddCard);
            LogM.LogV ("cardsetup" + "paySubsWithouAddCard " + paySubsWithouAddCard);
            LogM.LogV ("cardsetup" + "paySubsSlectCard " + paySubsSlectCard);
            LogM.LogV ("cardsetup" + "donation " + amount);


            if (amount != null && cardListBean != null) {
                etNameOnCard.setFocusable (false);
                etNameOnCard.setEnabled (false);
                etCardNumber.setFocusable (false);
                etCardNumber.setEnabled (false);
                tvMonth.setFocusable (false);
                tvMonth.setEnabled (false);
                tvYear.setFocusable (false);
                tvYear.setEnabled (false);
                headerBar.ibtnRightTwo.setVisibility (View.GONE);
                addCardBtn.setText (R.string.pay_now);
                headerBar.tvTitle.setText (R.string.donate_us_title);
            }else if(paySubsSlectCard>0 && cardListBean!=null){
                etNameOnCard.setFocusable (false);
                etNameOnCard.setEnabled (false);
                etCardNumber.setFocusable (false);
                etCardNumber.setEnabled (false);
                tvMonth.setFocusable (false);
                tvMonth.setEnabled (false);
                tvYear.setFocusable (false);
                tvYear.setEnabled (false);
                headerBar.ibtnRightTwo.setVisibility (View.GONE);
                addCardBtn.setText (R.string.pay_now);
                headerBar.tvTitle.setText (R.string.premium_subscription_title);
            }
            else if(paySubsWithouAddCard>0 && cardListBean==null){
                headerBar.ibtnRightTwo.setVisibility (View.GONE);
                addCardBtn.setText (R.string.pay_now);
                headerBar.tvTitle.setText (R.string.premium_subscription_title);
            }
            if (payNowWithouAddCard != null) {
                headerBar.tvTitle.setText (R.string.donate_us_title);
                headerBar.ibtnRightTwo.setVisibility (View.GONE);
                headerBar.tvTitle.setText (R.string.donate_us_title);
                addCardBtn.setText (R.string.pay_now);
            }
            if (cardListBean != null) {
                cardId = cardListBean.getCardId ();
                etNameOnCard.setText ("" + cardListBean.getCardName ());

                strCardNoOriginal = cardListBean.getCardNumber ();
                int numDigits = strCardNoOriginal.length ();
                if (numDigits == 12) {
                    etCardNumber.setText (strCardNoOriginal.substring (0, 4).replaceAll (".", "\u2022") + " " + strCardNoOriginal.substring (4, 8).replaceAll (".", "\u2022") + " " + strCardNoOriginal.substring (8, strCardNoOriginal.length ()));
                } else if (numDigits > 16) {
                    etCardNumber.setText (strCardNoOriginal.substring (0, 4).replaceAll (".", "\u2022") + " " + strCardNoOriginal.substring (4, 8).replaceAll (".", "\u2022") + " " + strCardNoOriginal.substring (8, 12).replaceAll (".", "\u2022")
                            + " " + strCardNoOriginal.substring (12, 16).replaceAll (".", "\u2022") + "" + strCardNoOriginal.substring (16, strCardNoOriginal.length ()));
                } else if (numDigits > 12) {
                    etCardNumber.setText (strCardNoOriginal.substring (0, 4).replaceAll (".", "\u2022") + " " + strCardNoOriginal.substring (4, 8).replaceAll (".", "\u2022") + " " + strCardNoOriginal.substring (8, 12).replaceAll (".", "\u2022")
                            + " " + strCardNoOriginal.substring (12, strCardNoOriginal.length ()));
                }

                String[] splitDate = cardListBean.getExpiry ().split ("/");
                tvMonth.setText (splitDate[0]);
                tvYear.setText (splitDate[1]);

                Card card = new Card (cardListBean.getCardNumber (), 0, 0, "123");
                LogM.LogV ("BRAND: " + card.getBrand ());
                if (card.getBrand () == null) {
                    ivCard.setImageDrawable (null);
                    return;
                }
                if (card.getBrand ().equalsIgnoreCase ("Visa")) {
                    ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.visa));
                }

                if (card.getBrand ().equalsIgnoreCase ("MasterCard")) {
                    ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.mastercard));
                }
                if (card.getBrand ().equalsIgnoreCase ("American Express")) {
                    ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.american_express));
                }

            }

            if (cardListBean != null && amount == null && paySubsWithouAddCard==0 && paySubsSlectCard==0) {
                cardId = cardListBean.getCardId ();
                headerBar.tvTitle.setText (R.string.card_detail_title);
                headerBar.ibtnRightTwo.setImageResource (R.drawable.delete);
                addCardBtn.setText (R.string.update_card);

            }
        } else {
            headerBar.ibtnRightTwo.setVisibility (View.GONE);
        }
    }


    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener (this);
            headerBar.ibtnRightTwo.setOnClickListener (this);
            etCardNumber.addTextChangedListener (new FourDigitCardFormatWatcher ());
            addCardBtn.setOnClickListener (this);
            payNowBtn.setOnClickListener (this);
            tvYear.setOnClickListener (this);
            tvMonth.setOnClickListener (this);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void bindView() {
        try {
            headerBar = binding.headerBar;
            etNameOnCard = binding.etNameOnCard;
            etCardNumber = binding.etCardNo;
            etCvvCode = binding.etCvvCode;
            spinMonth = binding.spinMonth;
            spinYear = binding.spinYear;
            tvMonth = binding.tvMonth;
            tvYear = binding.tvYear;
            addCardBtn = binding.addCardBtn;
            payNowBtn = binding.payNowBtn;
            ivCard = binding.ivCard;
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility (View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource (R.drawable.back);
            headerBar.ibtnRightTwo.setVisibility (View.VISIBLE);
            headerBar.ibtnRightTwo.setImageResource (R.drawable.delete);
            headerBar.tvTitle.setVisibility (View.VISIBLE);
            headerBar.tvTitle.setText (R.string.add_card_title);

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onBackPressed() {
        setResult (RESULT_CANCELED);
        super.onBackPressed ();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId ()) {
                case R.id.ibtnLeftOne:
                    setResult (RESULT_CANCELED);
                    finish ();
                    break;

                case R.id.addCardBtn:
                    validation (v.getId ());
                    break;
//                case R.id.payNowBtn:
//                    validation (v.getId ());
//                    break;

                case R.id.tvYear:
                    clickOnMonthOrYear ();
                    break;
                case R.id.tvMonth:
                    clickOnMonthOrYear ();
                    break;

                case R.id.ibtnRightTwo:
                    showConfirmationDialog ();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void clickOnMonthOrYear() {
        try {
            LogM.LogV ("etExpDate: " + tvMonth.getText ().toString ().trim () + " & " + tvYear.getText ().toString ().trim ());
            if (tvMonth.getText ().toString ().trim ().contains ("Month") && tvYear.getText ().toString ().trim ().contains ("Year")) {
                myp = new MonthYearPicker (SetUpCardActivity.this);
                myp.build (new DialogInterface.OnClickListener () {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogM.LogV (myp.getSelectedMonthName () + " &&& " + myp.getSelectedYear ());
                        tvMonth.setText (myp.getSelectedMonthName ());
                        tvYear.setText (myp.getSelectedYear () + "");
                        //  etExpDate.setText(myp.getSelectedMonthName() + "/" + myp.getSelectedYear()); // here date formate set by screen shot.
                    }
                }, null);

                myp.show ();
            } else {

                showMonthYearDialog (Integer.parseInt (tvMonth.getText ().toString ().trim ()) - 1,
                        Integer.parseInt (tvYear.getText ().toString ().trim ()));  // open month and year picker for expiry date.
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void showMonthYearDialog(int intSelectedMonth, int intSelectedYear) {
        try {
            LogM.LogV (intSelectedMonth + " ** " + intSelectedYear);
            myp = new MonthYearPicker (this);
            myp.build (intSelectedMonth, intSelectedYear, new DialogInterface.OnClickListener () {

                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    etExpDate.setText(myp.getSelectedMonthName() + "/" + myp.getSelectedYear()); // here date formate set by screen shot.
                    tvMonth.setText (myp.getSelectedMonthName ());
                    tvYear.setText (myp.getSelectedYear () + "");
                }
            }, null);

            myp.show ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    private void showConfirmationDialog() {

        AlertDialogUtility.showConfirmAlert (SetUpCardActivity.this, getResources ().getString (R.string.delete_added_card), new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callDeleteCardAPI ();
            }
        });

    }

    private void validation(int viewId) {
        try {

            if (etCardNumber.getText ().toString ().trim ().contains ("\u2022")) {
                strEtCardNumber = cardListBean.getCardNumber ();
                cardBrand="Valid";
            } else {
                strEtCardNumber = etCardNumber.getText ().toString ().trim ();
                strEtCardNumber = strEtCardNumber.replaceAll ("\\s+", "");
                card = new Card (strEtCardNumber.toString (), 0, 0, "123");
                cardBrand=card.getBrand ();
            }

            strEtCvvCode = etCvvCode.getText ().toString ().trim ();
            strSpinMonth = tvMonth.getText ().toString ().trim ();
            strSpinYear = tvYear.getText ().toString ().trim ();
            strEtNameOnCard = etNameOnCard.getText ().toString ().trim ();

            if (strEtNameOnCard.length () == 0) {
                etNameOnCard.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.name_on_card_filed), etCardNumber, this);
            } else if (strEtCardNumber.length () == 0) {
                etCardNumber.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.card_number_null), etCardNumber, this);
            } else if (strEtCardNumber.length () < 12) {
                etCardNumber.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.valid_card_number), etCardNumber, this);
            }

            else if (strSpinMonth.equalsIgnoreCase (getResources ().getString (R.string.month)) || strSpinYear.equalsIgnoreCase (getResources ().getString (R.string.year))) {
                tvMonth.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.empty_expirydate), spinMonth, this);
            } else if (strEtCvvCode.length () < 3) {
                etCvvCode.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.valid_cvv), etCvvCode, this);
            }
            else {
                KeyboardUtility.HideKeyboard (SetUpCardActivity.this, etCardNumber);
                updateDetail (viewId);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private void updateDetail(final int viewId) {
        try {
            LogM.LogV ("Card Details: " + strEtCardNumber + " && " + Integer.parseInt (strSpinMonth) + " && " + Integer.parseInt (strSpinYear) + " && " + strEtCvvCode);
            callStripe (strEtCardNumber, Integer.parseInt (strSpinMonth), Integer.parseInt (strSpinYear), strEtCvvCode);
            if (!card.validateNumber ()) {
                etCardNumber.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.valid_card_number), etCardNumber, this);
            }
            else if(!cardBrand.equalsIgnoreCase ("Visa")&&!cardBrand.equalsIgnoreCase ("Mastercard")&& !cardBrand.equalsIgnoreCase ("American Express") && !cardBrand.equalsIgnoreCase ("Valid")){
                AlertDialogUtility.showSnakeBar (getString (R.string.not_supported_card), spinMonth, this);
            }else if (!card.validateExpiryDate ()) {
                tvMonth.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.valid_expirydate), spinMonth, this);
            } else if (!card.validateCVC ()) {
                etCvvCode.requestFocus ();
                AlertDialogUtility.showSnakeBar (getString (R.string.valid_cvv), etCvvCode, this);
            } else {
                switch (viewId) {
                    case R.id.addCardBtn:
                        if (amount != null) {
                            callDonateUs ();
                        } else if (payNowWithouAddCard != null) {
                            payNowDirectly ();
                        } else if(paySubsWithouAddCard>0){
                            LogM.LogE("you have to pay this amount==>"+paySubsWithouAddCard);
                            payNowDirectly ();

                        }
                        else if(paySubsSlectCard>0){
                            callPaySubscription(paySubsSlectCard);
                        }
                        else {
                            callAddCardAPI ();
                        }

                        break;

//                    case R.id.payNowBtn:
//                        callDonateUs ();
//                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void payNowDirectly() {
        callAddCardAPI ();

    }


    private void callAddCardAPI() {

        if (ConnectivityDetector.isConnectingToInternet (context)) {

            try {
                strExpiry = strSpinMonth + "/" + strSpinYear;
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put (WebField.SET_UP_CARD.PARAM_USER_ID, SessionManager.getUserId (SetUpCardActivity.this));
                jsonObject.put (WebField.SET_UP_CARD.PARAM_CARD_ID, cardId);
                jsonObject.put (WebField.SET_UP_CARD.PARAM_CARD_NAME, strEtNameOnCard);
                jsonObject.put (WebField.SET_UP_CARD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken (SetUpCardActivity.this));
                jsonObject.put (WebField.SET_UP_CARD.PARAM_CARD_NO, strEtCardNumber);
                jsonObject.put (WebField.SET_UP_CARD.PARAM_EXPIRY, strExpiry);
                jsonObject.put (WebField.SET_UP_CARD.PARAM_CVV, strEtCvvCode);

                LogM.LogE ("Request : Card SetUp : " + jsonObject.toString ());

                new GetJsonWithAndroidNetworkingLib (context, jsonObject, WebField.BASE_URL + WebField.SET_UP_CARD.MODE, 1, new OnUpdateListener () {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {


                        if (isSuccess) {
                            LogM.LogE ("Response : Card SetUp : " + jsonObject.toString ());
                            try {
                                cardId = jsonObject.getInt ("cardId");
                            } catch (JSONException e) {
                                e.printStackTrace ();
                            }

                            if (payNowWithouAddCard != null) {
                                amount = payNowWithouAddCard;
                                callDonateUs ();
                            }
                            else if(paySubsWithouAddCard>0 && subscriptionId>0){
                                callPaySubscription(paySubsWithouAddCard);
                            }else {
                                setResult (RESULT_OK);
                                finish ();
                            }


                        }

                    }
                }).execute ();
            } catch (Exception e) {
                e.printStackTrace ();
            }

        } else {
            AlertDialogUtility.showAlert (SetUpCardActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    private void callPaySubscription(double subAmount) {

        if (ConnectivityDetector.isConnectingToInternet (context)) {

            try {
                strEtCvvCode = etCvvCode.getText ().toString ().trim ();
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put (WebField.PARAM_USER_ID, SessionManager.getUserId (SetUpCardActivity.this));
                jsonObject.put (WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken (SetUpCardActivity.this));
                jsonObject.put (WebField.DONATE_US.PARAM_CARD_ID, cardId);
                jsonObject.put (WebField.DONATE_US.PARAM_CVV, strEtCvvCode);
                jsonObject.put (WebField.DONATE_US.PARAM_AMOUNT, subAmount);
                jsonObject.put(WebField.PURCHASE_SUBSCRIPTION.SUBSCRIPTION_ID,subscriptionId);


                LogM.LogE ("Request : subscription : " + jsonObject.toString ());

                new GetJsonWithAndroidNetworkingLib (context, jsonObject, WebField.BASE_URL + WebField.PURCHASE_SUBSCRIPTION.MODE, 1, new OnUpdateListener () {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {

                        if (isSuccess) {
                            LogM.LogE ("Response :subscription : " + jsonObject.toString ());

                            try {
                                final String msg = jsonObject.get (GlobalData.MESSAGE).toString ();
                                LogM.LogV (msg);

                                startActivity (new Intent (context, SubscriptionMessageActivity.class));

//                                        .setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                LogM.LogV ("passing response to final screen " + msg);
                                setResult (RESULT_CANCELED);
                                finish ();

                            } catch (JSONException e) {
                                e.printStackTrace ();
                            }
                        }

                    }
                }).execute ();
            } catch (Exception e) {
                e.printStackTrace ();
            }

        } else {
            AlertDialogUtility.showAlert (SetUpCardActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    private void callDeleteCardAPI() {

        if (ConnectivityDetector.isConnectingToInternet (context)) {
            try {
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put (WebField.DELETE_CARD.PARAM_USER_ID, SessionManager.getUserId (SetUpCardActivity.this));
                jsonObject.put (WebField.DELETE_CARD.PARAM_ACCESSTOKEN, SessionManager.getAccessToken (SetUpCardActivity.this));
                jsonObject.put (WebField.DELETE_CARD.PARAM_CARD_ID, cardListBean.getCardId ());

                new GetJsonWithAndroidNetworkingLib (context, jsonObject, WebField.BASE_URL + WebField.DELETE_CARD.MODE, 1, new OnUpdateListener () {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        if (isSuccess) {

                            setResult (RESULT_OK);
                            finish ();
                        }

                    }
                }).execute ();
            } catch (Exception e) {
                e.printStackTrace ();
            }

        } else {
            AlertDialogUtility.showAlert (SetUpCardActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }


    }

    private void callDonateUs() {

        if (ConnectivityDetector.isConnectingToInternet (context)) {

            try {
                strEtCvvCode = etCvvCode.getText ().toString ().trim ();
                JSONObject jsonObject = new JSONObject ();
                jsonObject.put (WebField.DONATE_US.PARAM_USER_ID, SessionManager.getUserId (SetUpCardActivity.this));
                jsonObject.put (WebField.DONATE_US.PARAM_ACCESSTOKEN, SessionManager.getAccessToken (SetUpCardActivity.this));
                jsonObject.put (WebField.DONATE_US.PARAM_CARD_ID, cardId);
                jsonObject.put (WebField.DONATE_US.PARAM_CVV, strEtCvvCode);
                jsonObject.put (WebField.DONATE_US.PARAM_AMOUNT, amount);


                LogM.LogE ("Request : Pay Now : " + jsonObject.toString ());

                new GetJsonWithAndroidNetworkingLib (context, jsonObject, WebField.BASE_URL + WebField.DONATE_US.MODE, 1, new OnUpdateListener () {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {

                        if (isSuccess) {
                            LogM.LogE ("Response : Pay Now : " + jsonObject.toString ());

                            try {
                                final String msg = jsonObject.get (GlobalData.MESSAGE).toString ();
                                LogM.LogV (msg);

                                startActivity (new Intent (context, DonationSuccessMessageActivity.class)
                                        .putExtra (GlobalData.DONATION, amount)
                                        .putExtra (GlobalData.MESSAGE, msg));
//                                        .setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                LogM.LogV ("passing response to final screen " + msg);
                                setResult (RESULT_CANCELED);
                                finish ();

                            } catch (JSONException e) {
                                e.printStackTrace ();
                            }
                        }

                    }
                }).execute ();
            } catch (Exception e) {
                e.printStackTrace ();
            }

        } else {
            AlertDialogUtility.showAlert (SetUpCardActivity.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    public void callStripe(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        card = new Card (
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );

        card.validateNumber ();
        card.validateExpMonth ();
        card.validateExpiryDate ();
        card.validateCVC ();

    }
}
