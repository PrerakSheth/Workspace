package com.patchpets.model.requestModel;

public class ShareYourExperienceRequest {

    /**
     * userId : 2
     * accessToken : KZ0Qaapqcb48jAyBNq72ZIPlA
     * title : Awesome app
     * experienceDetails : Good experience
     * suggestionsForFuture : Nothing for now
     * deviceType : 1
     * mobileName : Motoz
     * appVersion : 9.9
     * email : test@test.com
     */

    private int userId;
    private String accessToken;
    private String title;
    private String experienceDetails;
    private String suggestionsForFuture;
    private int deviceType;
    private String mobileName;
    private String appVersion;
    private String email;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExperienceDetails() {
        return experienceDetails;
    }

    public void setExperienceDetails(String experienceDetails) {
        this.experienceDetails = experienceDetails;
    }

    public String getSuggestionsForFuture() {
        return suggestionsForFuture;
    }

    public void setSuggestionsForFuture(String suggestionsForFuture) {
        this.suggestionsForFuture = suggestionsForFuture;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
