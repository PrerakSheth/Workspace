package com.patchpets.model.responseModel;

public class SetUpBusinessProfileResponse {

//    {
//        "message": "we set up your profile successfully.",
//        "status": 1,
//        "userData": {
//            "userId": 90,
//            "isProfileSetup": 1,
//            "businessName": "Test business",
//            "profilePic": "http:\/\/dev2.ifuturz.com\/Laravel\/printr\/uploads\/userimage\/1546088018-userimage.jpg",
//            "contactNo": "9780465312",
//            "aboutBusiness": "About me",
//            "category": "Service",
//            "instaLink": "www.instagram.com\/test"
//        }
//    }

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

        private int userId;
        private int isProfileSetup;
        private String businessName;
        private String profilePic;
        private String contactNo;
        private String aboutBusiness;
        private String category;
        private String instaLink;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getIsProfileSetup() {
            return isProfileSetup;
        }

        public void setIsProfileSetup(int isProfileSetup) {
            this.isProfileSetup = isProfileSetup;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getAboutBusiness() {
            return aboutBusiness;
        }

        public void setAboutBusiness(String aboutBusiness) {
            this.aboutBusiness = aboutBusiness;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getInstaLink() {
            return instaLink;
        }

        public void setInstaLink(String instaLink) {
            this.instaLink = instaLink;
        }
    }
}
