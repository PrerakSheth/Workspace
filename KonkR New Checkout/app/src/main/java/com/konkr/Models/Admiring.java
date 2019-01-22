package com.konkr.Models;

/**
 * Created by Android on 6/20/2018.
 */

public class Admiring {
    private int userPhoto;
    private String userName;
    private int badge;

    public Admiring(int userPhoto, String userName, int badge) {
        this.userPhoto = userPhoto;
        this.userName = userName;
        this.badge = badge;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }
}
