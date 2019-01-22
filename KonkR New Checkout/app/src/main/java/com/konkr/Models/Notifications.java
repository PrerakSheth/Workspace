package com.konkr.Models;

/**
 * Created by Android on 6/19/2018.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notifications {
//    private int userPhoto;
////    private String notificationMessage;
////    private String notificationTime;
////
////    public Notifications(int userPhoto, String notificationMessage, String notificationTime) {
////        this.userPhoto = userPhoto;
////        this.notificationMessage = notificationMessage;
////        this.notificationTime = notificationTime;
////    }
////
////    public int getUserPhoto() {
////        return userPhoto;
////    }
////
////    public void setUserPhoto(int userPhoto) {
////        this.userPhoto = userPhoto;
////    }
////
////    public String getNotificationMessage() {
////        return notificationMessage;
////    }
////
////    public void setNotificationMessage(String notificationMessage) {
////        this.notificationMessage = notificationMessage;
////    }
////
////    public String getNotificationTime() {
////        return notificationTime;
////    }
////
////    public void setNotificationTime(String notificationTime) {
////        this.notificationTime = notificationTime;
////    }

    @SerializedName("notificationList")
    @Expose
    private List<NotificationList> notificationList = null;

    public List<NotificationList> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationList> notificationList) {
        this.notificationList = notificationList;
    }

    public class NotificationList {

        @SerializedName("profilePic")
        @Expose
        private String profilePic;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("senderId")
        @Expose
        private String senderId;
        @SerializedName("notificationUnixTime")
        @Expose
        private String notificationUnixTime;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("receiverId")
        @Expose
        private String receiverId;
        @SerializedName("notificationType")
        @Expose
        private String notificationType;

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getNotificationUnixTime() {
            return notificationUnixTime;
        }

        public void setNotificationUnixTime(String notificationUnixTime) {
            this.notificationUnixTime = notificationUnixTime;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

    }
}