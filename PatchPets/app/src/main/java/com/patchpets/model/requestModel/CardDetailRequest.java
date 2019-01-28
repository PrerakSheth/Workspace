package com.patchpets.model.requestModel;

public class CardDetailRequest {

    /**
     * userId : 280
     * cardId : 1
     * cardName : abc
     * accessToken : sdv46bfb
     * cardNo : 8976345213406345
     * expiry : 04-2018
     * cvv : 456
     */

    private int userId;
    private int cardId;
    private String cardName;
    private String accessToken;
    private String cardNo;
    private String expiry;
    private int cvv;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
}
