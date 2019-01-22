package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MiTrainingAddWorkout implements Parcelable {

    /**
     * message : workout item added successfully.
     * status : 1
     * itemId : 80
     * workoutId : 0
     * mediaType : 3
     * ImageURL : http://dev2.ifuturz.com/core/konkr/assets/upload/workout/80.png
     * VideoURL : http://dev2.ifuturz.com/core/konkr/assets/upload/workout/16.mp4
     * videoThumbImage : http://dev2.ifuturz.com/core/konkr/assets/upload/workout/video_thumb/16.png
     */

    private String message;
    private int status;
    private int itemId;
    private int workoutId;
    private int mediaType;
    private String ImageURL;
    private String VideoURL;
    private String videoThumbImage;

    public MiTrainingAddWorkout() {
    }

    protected MiTrainingAddWorkout(Parcel in) {
        message = in.readString();
        status = in.readInt();
        itemId = in.readInt();
        workoutId = in.readInt();
        mediaType = in.readInt();
        ImageURL = in.readString();
        VideoURL = in.readString();
        videoThumbImage = in.readString();
    }

    public static final Creator<MiTrainingAddWorkout> CREATOR = new Creator<MiTrainingAddWorkout>() {
        @Override
        public MiTrainingAddWorkout createFromParcel(Parcel in) {
            return new MiTrainingAddWorkout(in);
        }

        @Override
        public MiTrainingAddWorkout[] newArray(int size) {
            return new MiTrainingAddWorkout[size];
        }
    };

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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String VideoURL) {
        this.VideoURL = VideoURL;
    }

    public String getVideoThumbImage() {
        return videoThumbImage;
    }

    public void setVideoThumbImage(String videoThumbImage) {
        this.videoThumbImage = videoThumbImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeInt(status);
        dest.writeInt(itemId);
        dest.writeInt(workoutId);
        dest.writeInt(mediaType);
        dest.writeString(ImageURL);
        dest.writeString(VideoURL);
        dest.writeString(videoThumbImage);
    }

//    {
//        "message": "workout item added successfully.",
//        "status": 1,
//        "itemId": 80,
//        "workoutId": 0,
//        "mediaType": 3,
//        "ImageURL": "http:\/\/dev2.ifuturz.com\/core\/konkr\/assets\/upload\/workout\/80.png",
//        "VideoURL": "http://dev2.ifuturz.com/core/konkr/assets/upload/workout/16.mp4",
//        "videoThumbImage": "http://dev2.ifuturz.com/core/konkr/assets/upload/workout/video_thumb/16.png"
//    }
}
