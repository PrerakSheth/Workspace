package com.patchpets.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DogDetails implements Parcelable {
    private int dogId;
    private String distance;
    private int isUserActive;
    private String location;
    private String dogName;
    private int dogBreed;
    private int dogAge;
    private int gender;
    private int isDesexed;
    private int isFavourite;
    private String dogSize;
    private String lastActiveTime;
    private String Vaccinations;
    private String dogDesc;
    private String dogInstaLink;
    private String dogProfilePic;
    private ArrayList<String> dogPics;

    public DogDetails() {
    }

    protected DogDetails(Parcel in) {
        dogId = in.readInt();
        distance = in.readString();
        isUserActive = in.readInt();
        location = in.readString();
        dogName = in.readString();
        dogBreed = in.readInt();
        dogAge = in.readInt();
        gender = in.readInt();
        isDesexed = in.readInt();
        isFavourite = in.readInt();
        dogSize = in.readString();
        lastActiveTime = in.readString();
        Vaccinations = in.readString();
        dogDesc = in.readString();
        dogInstaLink = in.readString();
        dogProfilePic = in.readString();
        dogPics = in.createStringArrayList();
    }

    public static final Creator<DogDetails> CREATOR = new Creator<DogDetails>() {
        @Override
        public DogDetails createFromParcel(Parcel in) {
            return new DogDetails(in);
        }

        @Override
        public DogDetails[] newArray(int size) {
            return new DogDetails[size];
        }
    };

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getIsUserActive() {
        return isUserActive;
    }

    public void setIsUserActive(int isUserActive) {
        this.isUserActive = isUserActive;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public int getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(int dogBreed) {
        this.dogBreed = dogBreed;
    }

    public int getDogAge() {
        return dogAge;
    }

    public void setDogAge(int dogAge) {
        this.dogAge = dogAge;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getIsDesexed() {
        return isDesexed;
    }

    public void setIsDesexed(int isDesexed) {
        this.isDesexed = isDesexed;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getDogSize() {
        return dogSize;
    }

    public void setDogSize(String dogSize) {
        this.dogSize = dogSize;
    }

    public String getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getVaccinations() {
        return Vaccinations;
    }

    public void setVaccinations(String vaccinations) {
        Vaccinations = vaccinations;
    }

    public String getDogDesc() {
        return dogDesc;
    }

    public void setDogDesc(String dogDesc) {
        this.dogDesc = dogDesc;
    }

    public String getDogInstaLink() {
        return dogInstaLink;
    }

    public void setDogInstaLink(String dogInstaLink) {
        this.dogInstaLink = dogInstaLink;
    }

    public String getDogProfilePic() {
        return dogProfilePic;
    }

    public void setDogProfilePic(String dogProfilePic) {
        this.dogProfilePic = dogProfilePic;
    }

    public ArrayList<String> getDogPics() {
        return dogPics;
    }

    public void setDogPics(ArrayList<String> dogPics) {
        this.dogPics = dogPics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dogId);
        dest.writeString(distance);
        dest.writeInt(isUserActive);
        dest.writeString(location);
        dest.writeString(dogName);
        dest.writeInt(dogBreed);
        dest.writeInt(dogAge);
        dest.writeInt(gender);
        dest.writeInt(isDesexed);
        dest.writeInt(isFavourite);
        dest.writeString(dogSize);
        dest.writeString(lastActiveTime);
        dest.writeString(Vaccinations);
        dest.writeString(dogDesc);
        dest.writeString(dogInstaLink);
        dest.writeString(dogProfilePic);
        dest.writeStringList(dogPics);
    }
}
