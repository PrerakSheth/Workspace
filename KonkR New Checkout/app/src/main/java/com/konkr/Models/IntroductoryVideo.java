package com.konkr.Models;

public class IntroductoryVideo {

    /**
     * message : video uploaded successfully.
     * status : 1
     * userId : 8
     * VideoURL : http://192.168.1.245/core/konkr/assets/upload/profile/video/8.mp4
     * videoThumbImage : http://192.168.1.245/core/konkr/assets/upload/profile/video/video_thumb/8.png
     */

    private String message;
    private int status;
    private int userId;
    private String VideoURL;
    private String videoThumbImage;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
