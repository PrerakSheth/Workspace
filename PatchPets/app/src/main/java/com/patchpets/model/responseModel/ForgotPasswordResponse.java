package com.patchpets.model.responseModel;

public class ForgotPasswordResponse {

//    {
//        "userDetails": "test123@gmail.com",
//        "status": 1,
//        "message": "We have sent an email on your email-id. Kindly check your inbox to get the password."
//    }

    private String userDetails;
    private int status;
    private String message;

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
