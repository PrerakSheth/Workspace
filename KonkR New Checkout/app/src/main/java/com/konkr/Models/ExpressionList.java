package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ExpressionList implements Parcelable {

    @SerializedName("Admiring")
    private List<Admiring> admiring;
    @Expose
    private Long commentCount;
    @Expose
    private Long expressionCount;
    @SerializedName("Goals")
    private List<Goal> goals;
    @SerializedName("Inspiring")
    private List<Inspiring> inspiring;
    @Expose
    private String message;
    @Expose
    private Long status;

    public List<Admiring> getAdmiring() {
        return admiring;
    }

    public void setAdmiring(List<Admiring> admiring) {
        this.admiring = admiring;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getExpressionCount() {
        return expressionCount;
    }

    public void setExpressionCount(Long expressionCount) {
        this.expressionCount = expressionCount;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public List<Inspiring> getInspiring() {
        return inspiring;
    }

    public void setInspiring(List<Inspiring> inspiring) {
        this.inspiring = inspiring;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @SuppressWarnings("unused")
    public static class Admiring implements Parcelable {

        @Expose
        private int badge;
        @Expose
        private String firstName;
        @Expose
        private String lastName;
        @Expose
        private String profilePic;
        @Expose
        private Long userId;

        public int getBadge() {
            return badge;
        }

        public void setBadge(int badge) {
            this.badge = badge;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.badge);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.profilePic);
            dest.writeValue(this.userId);
        }

        public Admiring() {
        }

        protected Admiring(Parcel in) {
            this.badge = (int) in.readValue(Long.class.getClassLoader());
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.profilePic = in.readString();
            this.userId = (Long) in.readValue(Long.class.getClassLoader());
        }

        public static final Parcelable.Creator<Admiring> CREATOR = new Parcelable.Creator<Admiring>() {
            @Override
            public Admiring createFromParcel(Parcel source) {
                return new Admiring(source);
            }

            @Override
            public Admiring[] newArray(int size) {
                return new Admiring[size];
            }
        };
    }

    @SuppressWarnings("unused")
    public static class Goal implements Parcelable {

        @Expose
        private int badge;
        @Expose
        private String firstName;
        @Expose
        private String lastName;
        @Expose
        private String profilePic;
        @Expose
        private Long userId;

        public int getBadge() {
            return badge;
        }

        public void setBadge(int badge) {
            this.badge = badge;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.badge);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.profilePic);
            dest.writeValue(this.userId);
        }

        public Goal() {
        }

        protected Goal(Parcel in) {
            this.badge = (int) in.readValue(Long.class.getClassLoader());
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.profilePic = in.readString();
            this.userId = (Long) in.readValue(Long.class.getClassLoader());
        }

        public static final Parcelable.Creator<Goal> CREATOR = new Parcelable.Creator<Goal>() {
            @Override
            public Goal createFromParcel(Parcel source) {
                return new Goal(source);
            }

            @Override
            public Goal[] newArray(int size) {
                return new Goal[size];
            }
        };
    }

    @SuppressWarnings("unused")
    public static class Inspiring implements Parcelable {

        @Expose
        private int badge;
        @Expose
        private String firstName;
        @Expose
        private String lastName;
        @Expose
        private String profilePic;
        @Expose
        private Long userId;

        public int getBadge() {
            return badge;
        }

        public void setBadge(int badge) {
            this.badge = badge;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.badge);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.profilePic);
            dest.writeValue(this.userId);
        }

        public Inspiring() {
        }

        protected Inspiring(Parcel in) {
            this.badge = (int) in.readValue(Long.class.getClassLoader());
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.profilePic = in.readString();
            this.userId = (Long) in.readValue(Long.class.getClassLoader());
        }

        public static final Parcelable.Creator<Inspiring> CREATOR = new Parcelable.Creator<Inspiring>() {
            @Override
            public Inspiring createFromParcel(Parcel source) {
                return new Inspiring(source);
            }

            @Override
            public Inspiring[] newArray(int size) {
                return new Inspiring[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.admiring);
        dest.writeValue(this.commentCount);
        dest.writeValue(this.expressionCount);
        dest.writeTypedList(this.goals);
        dest.writeTypedList(this.inspiring);
        dest.writeString(this.message);
        dest.writeValue(this.status);
    }

    public ExpressionList() {
    }

    protected ExpressionList(Parcel in) {
        this.admiring = in.createTypedArrayList(Admiring.CREATOR);
        this.commentCount = (Long) in.readValue(Long.class.getClassLoader());
        this.expressionCount = (Long) in.readValue(Long.class.getClassLoader());
        this.goals = in.createTypedArrayList(Goal.CREATOR);
        this.inspiring = in.createTypedArrayList(Inspiring.CREATOR);
        this.message = in.readString();
        this.status = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExpressionList> CREATOR = new Parcelable.Creator<ExpressionList>() {
        @Override
        public ExpressionList createFromParcel(Parcel source) {
            return new ExpressionList(source);
        }

        @Override
        public ExpressionList[] newArray(int size) {
            return new ExpressionList[size];
        }
    };
}
