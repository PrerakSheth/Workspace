package com.konkr.Models;

import java.util.ArrayList;

/**
 * Created by Android on 6/19/2018.
 */

public class Comments {

    /**
     * message : Comments found successfully.
     * expressionCount : 2
     * commentCount : 4
     * status : 1
     * CommentsOnThisFeed : [{"userId":71,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/71_thumb.png","firstName":"jordan","lastName":"Tommy","badge":"1","comment":"This is a test first comment"},{"userId":71,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/71_thumb.png","firstName":"jordan","lastName":"Tommy","badge":"1","comment":"This is a test second comment"},{"userId":356,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/356_thumb.png","firstName":"Sames","lastName":"Piza","badge":"1","comment":"This is a test comment"},{"userId":356,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/356_thumb.png","firstName":"Sames","lastName":"Piza","badge":"1","comment":"You gave your comment on this feed successfully."}]
     */

    private String message;
    private int expressionCount;
    private int commentCount;
    private int status;
    private ArrayList<CommentsOnThisFeedBean> CommentsOnThisFeed;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getExpressionCount() {
        return expressionCount;
    }

    public void setExpressionCount(int expressionCount) {
        this.expressionCount = expressionCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<CommentsOnThisFeedBean> getCommentsOnThisFeed() {
        return CommentsOnThisFeed;
    }

    public void setCommentsOnThisFeed(ArrayList<CommentsOnThisFeedBean> commentsOnThisFeed) {
        CommentsOnThisFeed = commentsOnThisFeed;
    }

    public static class CommentsOnThisFeedBean {
        /**
         * userId : 71
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/71_thumb.png
         * firstName : jordan
         * lastName : Tommy
         * badge : 1
         * comment : This is a test first comment
         */

        private int userId;
        private String profilePic;
        private String firstName;
        private String lastName;
        private String badge;
        private String comment;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
