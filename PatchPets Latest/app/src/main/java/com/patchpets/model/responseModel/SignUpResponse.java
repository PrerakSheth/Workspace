package com.patchpets.model.responseModel;

public class SignUpResponse {

    /*{
        "userDetails": {
        "userId": 25,
                "accessToken": "",
                "loginType": 0,
                "deviceType": 2,
                "userType": 1,
                "email": "test123@gmail.com",
                "qrcodeURL": "http://dev2.ifuturz.com/Laravel/printr/uploads/qrcodes/.png"
    },
        "status": 1,
            "is_login": 0,
            "message": "You have been registered successfully."
    }*/

    private UserDetailsBean userDetails;
    private int status;
    private int is_login;
    private String message;

    public UserDetailsBean getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsBean userDetails) {
        this.userDetails = userDetails;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_login() {
        return is_login;
    }

    public void setIs_login(int is_login) {
        this.is_login = is_login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class UserDetailsBean {

        private int userId;
        private String accessToken;
        private int loginType;
        private int deviceType;
        private int userType;
        private String email;
        private String qrcodeURL;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getLoginType() {
            return loginType;
        }

        public void setLoginType(int loginType) {
            this.loginType = loginType;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQrcodeURL() {
            return qrcodeURL;
        }

        public void setQrcodeURL(String qrcodeURL) {
            this.qrcodeURL = qrcodeURL;
        }
    }
}
