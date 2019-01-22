package com.konkr.Models;

import java.util.List;

/**
 * Created by Android on 5/19/2018.
 */

public class AddExtraPhoto {


    /**
     * message : Photo uploaded successfully.
     * status : 1
     * imageArray : [{"image":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/4/4_10.png","mediaId":35,"commentCount":0,"expressionCount":0,"is_goals":0,"is_inspiring":0,"is_admiring":0}]
     * photoPosition : 10
     */

    private String message;
    private int status;
    private int photoPosition;
    private List<ImageArrayBean> imageArray;

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

    public int getPhotoPosition() {
        return photoPosition;
    }

    public void setPhotoPosition(int photoPosition) {
        this.photoPosition = photoPosition;
    }

    public List<ImageArrayBean> getImageArray() {
        return imageArray;
    }

    public void setImageArray(List<ImageArrayBean> imageArray) {
        this.imageArray = imageArray;
    }

    public static class ImageArrayBean {
        /**
         * image : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/4/4_10.png
         * mediaId : 35
         * commentCount : 0
         * expressionCount : 0
         * is_goals : 0
         * is_inspiring : 0
         * is_admiring : 0
         */

        private String image;
        private int mediaId;
        private int commentCount;
        private int expressionCount;
        private int is_goals;
        private int is_inspiring;
        private int is_admiring;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getMediaId() {
            return mediaId;
        }

        public void setMediaId(int mediaId) {
            this.mediaId = mediaId;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getExpressionCount() {
            return expressionCount;
        }

        public void setExpressionCount(int expressionCount) {
            this.expressionCount = expressionCount;
        }

        public int getIs_goals() {
            return is_goals;
        }

        public void setIs_goals(int is_goals) {
            this.is_goals = is_goals;
        }

        public int getIs_inspiring() {
            return is_inspiring;
        }

        public void setIs_inspiring(int is_inspiring) {
            this.is_inspiring = is_inspiring;
        }

        public int getIs_admiring() {
            return is_admiring;
        }

        public void setIs_admiring(int is_admiring) {
            this.is_admiring = is_admiring;
        }
    }
}
