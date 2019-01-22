package com.konkr.Models;

/**
 * Created by Android on 6/18/2018.
 */

public class VerificationBadgeList {

    private int imageId;
    private  String title;
    private  String description;
    private boolean isChecked;

    public VerificationBadgeList(int imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    public VerificationBadgeList(int imageId, String title, String description, boolean isChecked) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
