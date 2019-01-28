package com.patchpets.model.requestModel;

import android.os.Parcel;
import android.os.Parcelable;

public class SignUpRequest implements Parcelable {

    private String email;
    private int deviceType;
    private int userType;
    private String password;
    private String deviceToken;
    private double latitude;
    private double longitude;
    private String location;

    public SignUpRequest() {
    }

    protected SignUpRequest(Parcel in) {
        email = in.readString();
        deviceType = in.readInt();
        userType = in.readInt();
        password = in.readString();
        deviceToken = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        location = in.readString();
    }

    public static final Creator<SignUpRequest> CREATOR = new Creator<SignUpRequest>() {
        @Override
        public SignUpRequest createFromParcel(Parcel in) {
            return new SignUpRequest(in);
        }

        @Override
        public SignUpRequest[] newArray(int size) {
            return new SignUpRequest[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeInt(deviceType);
        parcel.writeInt(userType);
        parcel.writeString(password);
        parcel.writeString(deviceToken);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(location);
    }
}
