package com.konkr.Models;

import java.util.List;

public class TagUsers {

    /**
     * message : User found successfully.
     * status : 1
     * userData : [{"name":"Kim Test","profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png"},{"name":"Steve Test","profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/2_thumb.png"}]
     */

    private String message;
    private int status;
    private List<UserDataBean> userData;

    public TagUsers() {
    }

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

    public List<UserDataBean> getUserData() {
        return userData;
    }

    public void setUserData(List<UserDataBean> userData) {
        this.userData = userData;
    }

    public static class UserDataBean {
        /**
         * name : Kim Test
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png
         */

        private String name;
        private String profilePic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        @Override
        public String toString() {
            return name + '\n';
        }
    }
}
