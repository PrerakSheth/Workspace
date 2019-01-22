package com.konkr.Models;

import java.util.List;

public class MeidaPhotoComment {


    /**
     * message : Comments found successfully.
     * status : 1
     * comments : [{"userId":1,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png","firstName":"Kim","lastName":"Test","badge":"1","comment":"testetstetstets tetst"}]
     */

    private String message;
    private int status;
    private List<CommentsBean> comments;

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

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * userId : 1
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png
         * firstName : Kim
         * lastName : Test
         * badge : 1
         * comment : testetstetstets tetst
         */

        private int userId;
        private String profilePic;
        private String firstName;
        private String lastName;
        private String badge;
        private String comment;

        public CommentsBean(int userId, String profilePic, String firstName, String lastName, String badge, String comment) {
            this.userId = userId;
            this.profilePic = profilePic;
            this.firstName = firstName;
            this.lastName = lastName;
            this.badge = badge;
            this.comment = comment;
        }

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
