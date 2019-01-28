package com.patchpets.model.responseModel;

import java.util.List;

public class SetUpBusinessProfileResponse {

//    {
//        "message": "we set up your profile successfully.",
//        "userData": {
//            "businessName": "Business user",
//            "isProfileSetup": 1,
//            "category": "Grooming",
//            "services": [
//                "test",
//                "tredfg",
//                "vbnmk",
//                "cvbbn"
//		    ],
//            "aboutBusiness": "Dhdhf fgf  cfhgv vhh",
//            "userId": "159",
//            "profilePic": "http:\/\/dev2.ifuturz.com\/Laravel\/printr\/uploads\/userimage\/1547108845-userimage.jpg",
//            "instaLink": "www.instagram.com\/test",
//            "contactNo": "9856321470"
//        },
//        "status": 1
//    }

    private String message;
    private UserDataBean userData;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDataBean getUserData() {
        return userData;
    }

    public void setUserData(UserDataBean userData) {
        this.userData = userData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class UserDataBean {

        private String businessName;
        private int isProfileSetup;
        private String category;
        private String aboutBusiness;
        private String userId;
        private String profilePic;
        private String instaLink;
        private String contactNo;
        private List<String> services;

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public int getIsProfileSetup() {
            return isProfileSetup;
        }

        public void setIsProfileSetup(int isProfileSetup) {
            this.isProfileSetup = isProfileSetup;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAboutBusiness() {
            return aboutBusiness;
        }

        public void setAboutBusiness(String aboutBusiness) {
            this.aboutBusiness = aboutBusiness;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getInstaLink() {
            return instaLink;
        }

        public void setInstaLink(String instaLink) {
            this.instaLink = instaLink;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public List<String> getServices() {
            return services;
        }

        public void setServices(List<String> services) {
            this.services = services;
        }
    }
}
