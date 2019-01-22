package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityCardDetailsBinding;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.CardDetailRequest;
import com.patchpets.model.requestModel.DeleteCardRequest;
import com.patchpets.model.responseModel.ApiResponse;
import com.patchpets.model.responseModel.CardDetailResponse;
import com.patchpets.model.responseModel.CardListResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.utils.KeyboardUtility;
import com.patchpets.utils.MonthYearPicker;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;
import com.stripe.android.model.Card;

public class CardDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCardDetailsBinding binding;
    private HeaderBar headerBar;
    private EditText etNameOnCard, etCardNumber, etCvvCode;
    private Spinner spinMonth, spinYear;
    private TextView tvAdd, tvYear, tvMonth;
    private ImageView ivCard;
    private MonthYearPicker myp;
    private ProgressDialog pDialog;
    private APIRequest apiRequest;
    private View snackBar;

    private String strEtCardNumber, strEtCvvCode, strEtNameOnCard;
    private String strSpinMonth, strSpinYear, strExpiry;
    private CardListResponse.CardListBean cardListBean;
    private Card card;
    private String cardBrand;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_details);
        snackBar = findViewById(android.R.id.content);
        user = SessionManager.getInstance().getUser(CardDetailsActivity.this);

        bindView();
        setListener();
        setHeaderBar();
    }

    private void bindView() {
        headerBar = binding.headerBar;
        etNameOnCard = binding.etNameOnCard;
        etCardNumber = binding.etCardNo;
        etCvvCode = binding.etCvvCode;
        spinMonth = binding.spinMonth;
        spinYear = binding.spinYear;
        tvMonth = binding.tvMonth;
        tvYear = binding.tvYear;
        tvAdd = binding.tvAdd;
        ivCard = binding.ivCard;
        etCardNumber.addTextChangedListener(mTextWatcher);
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.ADD)) {
            tvAdd.setText(getResources().getString(R.string.add));
        }
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.EDIT)) {
            tvAdd.setText(getResources().getString(R.string.edit_this_card));
            cardListBean = getIntent().getParcelableExtra(Constants.EDIT_CARD);
            setData(cardListBean);
        }
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvYear.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.ADD)) {
            headerBar.ibRight.setVisibility(View.GONE);
            headerBar.ibRight.setVisibility(View.GONE);
            headerBar.tvTitle.setText(R.string.add_card);
        }
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.EDIT)) {
            headerBar.ibRight.setVisibility(View.VISIBLE);
            headerBar.ibRight.setImageResource(R.drawable.delete);
            headerBar.tvTitle.setText(R.string.card_detail);
        }
    }

    private void setData(CardListResponse.CardListBean card) {
        try {
            etNameOnCard.setText(card.getCardName());
            etCardNumber.setText(card.getCardNumber());
            etCvvCode.setText(String.valueOf(card.getCvv()));
            String[] expiry = card.getExpiry().split("-");
            tvMonth.setText(expiry[0]);
            tvYear.setText(expiry[1]);
            tvMonth.setTextColor(getResources().getColor(R.color.text_color));
            tvYear.setTextColor(getResources().getColor(R.color.text_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
        private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
        private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
        private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
        private static final char DIVIDER = ' ';

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
            boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
            for (int i = 0; i < s.length(); i++) { // check that every element is right
                if (i > 0 && (i + 1) % dividerModulo == 0) {
                    isCorrect &= divider == s.charAt(i);
                } else {
                    isCorrect &= Character.isDigit(s.charAt(i));
                }
            }
            return isCorrect;
        }

        private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
            final StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length; i++) {
                if (digits[i] != 0) {
                    formatted.append(digits[i]);
                    if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                        formatted.append(divider);
                    }
                }
            }
            return formatted.toString();
        }

        private char[] getDigitArray(final Editable s, final int size) {
            char[] digits = new char[size];
            int index = 0;
            for (int i = 0; i < s.length() && index < size; i++) {
                char current = s.charAt(i);
                if (Character.isDigit(current)) {
                    digits[index] = current;
                    index++;
                }
            }
            return digits;
        }
    };

    public void onBackPressed(boolean isAddEdit) {
        if (isAddEdit) {
            setResult(RESULT_OK, new Intent());
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibRight:
                showDeleteCard();
                break;

            case R.id.ibLeft:
                onBackPressed(false);
                break;

            case R.id.tvAdd:
//                Intent intent = new Intent(CardDetailsActivity.this, CardListActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                if (isValid()) {
                    callAddEditCardDetails();
                }
                break;

            case R.id.tvMonth:
                clickOnMonthOrYear();
                break;

            case R.id.tvYear:
                clickOnMonthOrYear();
                break;
        }
    }

    private void showDeleteCard() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(CardDetailsActivity.this);
            builder.setMessage(getResources().getString(R.string.are_you_sure_dlt_card));
            builder.setCancelable(true);

            builder.setPositiveButton(getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            callCardListAPI();
                        }
                    });

            builder.setNegativeButton(getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callCardListAPI() {
        if (Helper.isCheckInternet(CardDetailsActivity.this)) {
            pDialog = new ProgressDialog(CardDetailsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.deleteCardAPI(deleteCardRequest(), responseCallbackDeleteCard);
        }
    }

    private DeleteCardRequest deleteCardRequest() {
        DeleteCardRequest deleteCardRequest = new DeleteCardRequest();
        deleteCardRequest.setUserId(user.getUserId());
        deleteCardRequest.setAccessToken(user.getAccessToken());
        deleteCardRequest.setCardId(cardListBean.getCardId());
        return deleteCardRequest;
    }

    ResponseCallback responseCallbackDeleteCard = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                ApiResponse response = (ApiResponse) object;
                if (response.getStatus() == 1) {
                    onBackPressed(true);
                } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                    AlertDialogUtility.showAlert(CardDetailsActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(CardDetailsActivity.this, SignInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
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

    private boolean isValid() {
        try {
            strEtNameOnCard = etNameOnCard.getText().toString().trim();
            strEtCardNumber = etCardNumber.getText().toString().trim();
            strEtCardNumber = strEtCardNumber.replaceAll("\\s+", "");
            strSpinMonth = tvMonth.getText().toString().trim();
            strSpinYear = tvYear.getText().toString().trim();
            strEtCvvCode = etCvvCode.getText().toString().trim();

            if (strEtNameOnCard.length() == 0) {
                AlertDialogUtility.showSnakeBar(getString(R.string.name_on_card_filed), etNameOnCard, this);
                etNameOnCard.requestFocus();
                return false;
            }

            if (strEtCardNumber.length() == 0) {
                AlertDialogUtility.showSnakeBar(getString(R.string.card_number_null), etCardNumber, this);
                etCardNumber.requestFocus();
                return false;
            }
            if (strEtCardNumber.length() < Constants.MIN_CARD_NUMBER) {
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_card_number), etCardNumber, this);
                etCardNumber.requestFocus();
                return false;
            }

            if (strSpinMonth.equalsIgnoreCase(getResources().getString(R.string.month)) || strSpinYear.equalsIgnoreCase(getResources().getString(R.string.year))) {
                tvMonth.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.empty_expirydate), spinMonth, this);
                return false;
            }

            if (strEtCvvCode.length() < Constants.MIN_CVV_NUMBER) {
                etCvvCode.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.cvv_null), etCvvCode, this);
                return false;
            }

            card = new Card(strEtCardNumber, Integer.parseInt(strSpinMonth), Integer.parseInt(strSpinYear), etCvvCode.getText().toString().trim());
            cardBrand = card.getBrand();

            if (!card.validateNumber()) {
                etCardNumber.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_card_number), etCardNumber, this);
                return false;
            }
            if (!cardBrand.equalsIgnoreCase("Visa") && !cardBrand.equalsIgnoreCase("Mastercard") && !cardBrand.equalsIgnoreCase("American Express") && !cardBrand.equalsIgnoreCase("Valid")) {
                AlertDialogUtility.showSnakeBar(getString(R.string.not_supported_card), spinMonth, this);
                etCardNumber.requestFocus();
                return false;
            }
            if (!card.validateExpiryDate()) {
                tvMonth.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_expirydate), spinMonth, this);
                return false;
            }
            if (!card.validateCVC()) {
                etCvvCode.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_cvv), etCvvCode, this);
                etCvvCode.requestFocus();
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void callAddEditCardDetails() {
        if (Helper.isCheckInternet(CardDetailsActivity.this)) {
            pDialog = new ProgressDialog(CardDetailsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.cardDetailAPI(cardDetailRequest(), responseCallback);
        }
    }

    private CardDetailRequest cardDetailRequest() {
        CardDetailRequest cardDetailRequest = new CardDetailRequest();
        cardDetailRequest.setUserId(user.getUserId());
        cardDetailRequest.setAccessToken(user.getAccessToken());
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.ADD)) {
//            cardDetailRequest.setCardId(0);
        }
        if (getIntent().getStringExtra(Constants.FROM).equalsIgnoreCase(Constants.EDIT)) {
            cardDetailRequest.setCardId(cardListBean.getCardId());
        }
        cardDetailRequest.setCardName(strEtNameOnCard);
        cardDetailRequest.setCardNo(strEtCardNumber);
        cardDetailRequest.setCvv(Integer.parseInt(strEtCvvCode));
        strExpiry = strSpinMonth + "-" + strSpinYear;
        cardDetailRequest.setExpiry(strExpiry);
        return cardDetailRequest;
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                CardDetailResponse response = (CardDetailResponse) object;
                if (response.getStatus() == 1) {
                    onBackPressed(true);
                } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                    AlertDialogUtility.showAlert(CardDetailsActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(CardDetailsActivity.this, SignInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, CardDetailsActivity.this);
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

    private void clickOnMonthOrYear() {
        try {
            if (tvMonth.getText().toString().trim().contains(getResources().getString(R.string.month)) && tvYear.getText().toString().trim().contains(getResources().getString(R.string.year))) {
                myp = new MonthYearPicker(CardDetailsActivity.this);
                myp.build(new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvMonth.setText(myp.getSelectedMonthName());
                        tvYear.setText(myp.getSelectedYear() + "");
                        tvMonth.setTextColor(getResources().getColor(R.color.text_color));
                        tvYear.setTextColor(getResources().getColor(R.color.text_color));
                    }
                }, null);
                myp.show();
            } else {
                myp = new MonthYearPicker(this);
                int month = Integer.parseInt(tvMonth.getText().toString().trim()) - 1;
                int year = Integer.parseInt(tvYear.getText().toString().trim());
                myp.build(month, year, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvMonth.setText(myp.getSelectedMonthName());
                        tvYear.setText(myp.getSelectedYear() + "");
                    }
                }, null);
                myp.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validation(int viewId) {
        try {
            if (etCardNumber.getText().toString().trim().contains("\u2022")) {
                strEtCardNumber = cardListBean.getCardNumber();
                cardBrand = "Valid";
            } else {
                strEtCardNumber = etCardNumber.getText().toString().trim();
                strEtCardNumber = strEtCardNumber.replaceAll("\\s+", "");
                card = new Card(strEtCardNumber, Integer.parseInt(strSpinMonth), Integer.parseInt(strSpinYear), etCvvCode.getText().toString().trim());
                cardBrand = card.getBrand();
            }

            strEtCvvCode = etCvvCode.getText().toString().trim();
            strSpinMonth = tvMonth.getText().toString().trim();
            strSpinYear = tvYear.getText().toString().trim();
            strEtNameOnCard = etNameOnCard.getText().toString().trim();

            if (strEtNameOnCard.length() == 0) {
                etNameOnCard.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.name_on_card_filed), etCardNumber, this);
            } else if (strEtCardNumber.length() == 0) {
                etCardNumber.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.card_number_null), etCardNumber, this);
            } else if (strEtCardNumber.length() < 12) {
                etCardNumber.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_card_number), etCardNumber, this);
            } else if (strSpinMonth.equalsIgnoreCase(getResources().getString(R.string.month)) || strSpinYear.equalsIgnoreCase(getResources().getString(R.string.year))) {
                tvMonth.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.empty_expirydate), spinMonth, this);
            } else if (strEtCvvCode.length() < 3) {
                etCvvCode.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_cvv), etCvvCode, this);
            } else {
                KeyboardUtility.hideKeyboard(CardDetailsActivity.this, etCardNumber);
                updateDetail(viewId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDetail(final int viewId) {
        try {
//            LogM.LogV("Card Details: " + strEtCardNumber + " && " + Integer.parseInt(strSpinMonth) + " && " + Integer.parseInt(strSpinYear) + " && " + strEtCvvCode);
            callStripe(strEtCardNumber, Integer.parseInt(strSpinMonth), Integer.parseInt(strSpinYear), strEtCvvCode);
            if (!card.validateNumber()) {
                etCardNumber.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_card_number), etCardNumber, this);
            } else if (!cardBrand.equalsIgnoreCase("Visa") && !cardBrand.equalsIgnoreCase("Mastercard") && !cardBrand.equalsIgnoreCase("American Express") && !cardBrand.equalsIgnoreCase("Valid")) {
                AlertDialogUtility.showSnakeBar(getString(R.string.not_supported_card), spinMonth, this);
            } else if (!card.validateExpiryDate()) {
                tvMonth.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_expirydate), spinMonth, this);
            } else if (!card.validateCVC()) {
                etCvvCode.requestFocus();
                AlertDialogUtility.showSnakeBar(getString(R.string.valid_cvv), etCvvCode, this);
            } else {
                switch (viewId) {
                    case R.id.tvAdd:
                        callAddEditCardDetails();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callStripe(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        card = new Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );

        card.validateNumber();
        card.validateExpMonth();
        card.validateExpiryDate();
        card.validateCVC();
    }
}
