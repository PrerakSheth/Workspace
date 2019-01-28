package com.patchpets.model.responseModel;

public class AddDogToProfileResponse {

    /**
     * message : You have added this dog details successfully.
     * status : 1
     * userData : {"userId":78,"dogDetails":{"dogId":2}}
     */

    private String message;
    private int status;
    private UserDataBean userData;

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

    public UserDataBean getUserData() {
        return userData;
    }

    public void setUserData(UserDataBean userData) {
        this.userData = userData;
    }

    public static class UserDataBean {
        /**
         * userId : 78
         * dogDetails : {"dogId":2}
         */

        private int userId;
        private DogDetailsBean dogDetails;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public DogDetailsBean getDogDetails() {
            return dogDetails;
        }

        public void setDogDetails(DogDetailsBean dogDetails) {
            this.dogDetails = dogDetails;
        }

        public static class DogDetailsBean {
            /**
             * dogId : 2
             */

            private int dogId;

            public int getDogId() {
                return dogId;
            }

            public void setDogId(int dogId) {
                this.dogId = dogId;
            }
        }
    }
}
