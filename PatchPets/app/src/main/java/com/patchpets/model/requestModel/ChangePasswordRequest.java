package com.patchpets.model.requestModel;

public class ChangePasswordRequest {

    /**
     * userId : 280
     * accessToken : sdv46bfb
     * currentPass : abc@123
     * newPass : abc@123
     */

    private int userId;
    private String accessToken;
    private String currentPass;
    private String newPass;

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

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
