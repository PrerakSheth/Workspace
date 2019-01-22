package com.patchpets.model.responseModel;

public class SetUpDogOwnerProfileResponse {

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

        private String firstName;
        private int isProfileSetup;
        private String lastName;
        private String profilePic;
        private int userId;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public int getIsProfileSetup() {
            return isProfileSetup;
        }

        public void setIsProfileSetup(int isProfileSetup) {
            this.isProfileSetup = isProfileSetup;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

//    {
//        "message": "we set up your profile successfully.",
//            "status": 1,
//            "userData": {
//                 "firstName": "Test",
//                "isProfileSetup": 1,
//                "lastName": "2",
//                "profilePic": "http://dev2.ifuturz.com/Laravel/printr/uploads/userimage/1545641469-userimage.jpeg",
//                "userId": 31
//    }
//    }
}
