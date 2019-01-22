package com.konkr.Models;

import java.util.List;

/**
 * Created by Android on 5/22/2018.
 */

public class SearchUser  {

    /**
     * message : User search list have been fetched successfully.
     * status : 1
     * userSearchList : [{"userId":328,"firstName":"Afafa","lastName":"Faafaf","followUnfollow":0,"profilePic":""},{"userId":315,"firstName":"Ttewt","lastName":"Gfsgwg","followUnfollow":0,"profilePic":""},{"userId":300,"firstName":"Test","lastName":"One","followUnfollow":0,"profilePic":""},{"userId":295,"firstName":"Dfdfdff","lastName":"Dfdfdfdfdfdf","followUnfollow":0,"profilePic":""},{"userId":284,"firstName":"Akash","lastName":"Raghani","followUnfollow":0,"profilePic":""},{"userId":281,"firstName":"Ghhgh","lastName":"Hghghgh","followUnfollow":0,"profilePic":""},{"userId":279,"firstName":"Akash","lastName":"Raghani","followUnfollow":0,"profilePic":""},{"userId":277,"firstName":"Aad","lastName":"Adad","followUnfollow":0,"profilePic":""},{"userId":270,"firstName":"Yyy","lastName":"Uuu","followUnfollow":0,"profilePic":""},{"userId":267,"firstName":"Akash","lastName":"Raghani","followUnfollow":0,"profilePic":""},{"userId":190,"firstName":"Aaaa","lastName":"Aaa","followUnfollow":0,"profilePic":""},{"userId":181,"firstName":"Qweerty","lastName":"Tttt","followUnfollow":0,"profilePic":""},{"userId":99,"firstName":"jfjfj","lastName":"jfjfj","followUnfollow":0,"profilePic":""},{"userId":97,"firstName":"slash","lastName":"rag Hank","followUnfollow":0,"profilePic":""}]
     */

    private String message;
    private int status;
    private List<UserSearchListBean> userSearchList;

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

    public List<UserSearchListBean> getUserSearchList() {
        return userSearchList;
    }

    public void setUserSearchList(List<UserSearchListBean> userSearchList) {
        this.userSearchList = userSearchList;
    }

    public static class UserSearchListBean {
        /**
         * userId : 328
         * firstName : Afafa
         * lastName : Faafaf
         * followUnfollow : 0
         * profilePic :
         */

        private int userId;
        private String firstName;
        private String lastName;
        private int followUnfollow;
        private String profilePic;


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public int getFollowUnfollow() {
            return followUnfollow;
        }

        public void setFollowUnfollow(int followUnfollow) {
            this.followUnfollow = followUnfollow;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }
}
