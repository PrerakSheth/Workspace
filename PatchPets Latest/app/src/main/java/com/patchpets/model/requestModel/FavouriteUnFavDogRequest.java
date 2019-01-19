package com.patchpets.model.requestModel;

public class FavouriteUnFavDogRequest {

    private int userId;
    private String accessToken;
    private int dogId;
    private int favouriteOrUnfavourite;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getFavouriteOrUnfavourite() {
        return favouriteOrUnfavourite;
    }

    public void setFavouriteOrUnfavourite(int favouriteOrUnfavourite) {
        this.favouriteOrUnfavourite = favouriteOrUnfavourite;
    }
}
