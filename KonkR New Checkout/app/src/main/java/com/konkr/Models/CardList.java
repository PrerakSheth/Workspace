package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Android on 6/6/2018.
 */

public class CardList {


    /**
     * message : Card list found successfully.
     * status : 1
     * cardList : [{"cardNumber":8976345213406345,"expiry":"04-2018","cvv":456}]
     */

    private String message;
    private int status;
    private List <CardListBean> cardList;

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

    public List <CardListBean> getCardList() {
        return cardList;
    }

    public void setCardList(List <CardListBean> cardList) {
        this.cardList = cardList;
    }

    public static class CardListBean implements Parcelable{
        /**
         * cardNumber : 8976345213406345
         * expiry : 04-2018
         * cvv : 456
         */

        private String cardNumber;
        private String expiry;
        private int cvv;
        private int cardId;
        private String cardName;

        protected CardListBean(Parcel in) {
            cardNumber = in.readString ();
            expiry = in.readString ();
            cvv = in.readInt ();
            cardName=in.readString ();
            cardId=in.readInt ();
        }

        public static final Creator <CardListBean> CREATOR = new Creator <CardListBean> () {
            @Override
            public CardListBean createFromParcel(Parcel in) {
                return new CardListBean (in);
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

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
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
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString (cardNumber);
            parcel.writeString (expiry);
            parcel.writeInt (cvv);
            parcel.writeString (cardName);
            parcel.writeInt (cardId);

        }
    }
}
