package com.patchpets.model.responseModel;

public class CardDetailResponse {

    /**
     * message : We setup your card. Now you can do the payment using this card directly.
     * cardId : 84
     * status : 1
     */

    private String message;
    private int cardId;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
