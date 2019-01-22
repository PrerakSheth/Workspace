package com.patchpets.model.responseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CardListResponse {

//    {
//        "message": "Card list found successfully.",
//            "status": 1,
//            "cardList": [{
//                 "cardId": 13,
//                "cardNumber": "9086345213406345",
//                "cardName": "demo",
//                "expiry": "2018-04-01",
//                "cvv": 560
//            }]
//    }

    private String message;
    private int status;
    private List<CardListBean> cardList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CardListBean> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardListBean> cardList) {
        this.cardList = cardList;
    }

    public static class CardListBean implements Parcelable {

        private int cardId;
        private String cardNumber;
        private String cardName;
        private String expiry;
        private int cvv;

        protected CardListBean(Parcel in) {
            cardId = in.readInt();
            cardNumber = in.readString();
            cardName = in.readString();
            expiry = in.readString();
            cvv = in.readInt();
        }

        public static final Creator<CardListBean> CREATOR = new Creator<CardListBean>() {
            @Override
            public CardListBean createFromParcel(Parcel in) {
                return new CardListBean(in);
            }

            @Override
            public CardListBean[] newArray(int size) {
                return new CardListBean[size];
            }
        };

        public int getCardId() {
            return cardId;
        }

        public void setCardId(int cardId) {
            this.cardId = cardId;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public int getCvv() {
            return cvv;
        }

        public void setCvv(int cvv) {
            this.cvv = cvv;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(cardId);
            dest.writeString(cardNumber);
            dest.writeString(cardName);
            dest.writeString(expiry);
            dest.writeInt(cvv);
        }
    }
}
