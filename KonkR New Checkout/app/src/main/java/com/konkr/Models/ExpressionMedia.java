package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ExpressionMedia implements Parcelable {


    /**
     * message : Like found successfully.
     * status : 1
     * Goals : [{"userId":1,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png","firstName":"Kim","lastName":"Test","badge":"1"}]
     * Inspiring : [{"userId":1,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png","firstName":"Kim","lastName":"Test","badge":"1"}]
     * Admiring : [{"userId":1,"profilePic":"http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png","firstName":"Kim","lastName":"Test","badge":"1"}]
     */

    private String message;
    private int status;
    private List<GoalsBean> Goals;
    private List<InspiringBean> Inspiring;
    private List<AdmiringBean> Admiring;

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

    public List<GoalsBean> getGoals() {
        return Goals;
    }

    public void setGoals(List<GoalsBean> Goals) {
        this.Goals = Goals;
    }

    public List<InspiringBean> getInspiring() {
        return Inspiring;
    }

    public void setInspiring(List<InspiringBean> Inspiring) {
        this.Inspiring = Inspiring;
    }

    public List<AdmiringBean> getAdmiring() {
        return Admiring;
    }

    public void setAdmiring(List<AdmiringBean> Admiring) {
        this.Admiring = Admiring;
    }

    public static class GoalsBean implements Parcelable {
        /**
         * userId : 1
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png
         * firstName : Kim
         * lastName : Test
         * badge : 1
         */

        private int userId;
        private String profilePic;
        private String firstName;
        private String lastName;
        private String badge;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
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

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.profilePic);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.badge);
        }

        public GoalsBean() {
        }

        protected GoalsBean(Parcel in) {
            this.userId = in.readInt();
            this.profilePic = in.readString();
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.badge = in.readString();
        }

        public static final Parcelable.Creator<GoalsBean> CREATOR = new Parcelable.Creator<GoalsBean>() {
            @Override
            public GoalsBean createFromParcel(Parcel source) {
                return new GoalsBean(source);
            }

            @Override
            public GoalsBean[] newArray(int size) {
                return new GoalsBean[size];
            }
        };
    }

    public static class InspiringBean implements Parcelable {
        /**
         * userId : 1
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png
         * firstName : Kim
         * lastName : Test
         * badge : 1
         */

        private int userId;
        private String profilePic;
        private String firstName;
        private String lastName;
        private String badge;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
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

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.profilePic);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.badge);
        }

        public InspiringBean() {
        }

        protected InspiringBean(Parcel in) {
            this.userId = in.readInt();
            this.profilePic = in.readString();
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.badge = in.readString();
        }

        public static final Parcelable.Creator<InspiringBean> CREATOR = new Parcelable.Creator<InspiringBean>() {
            @Override
            public InspiringBean createFromParcel(Parcel source) {
                return new InspiringBean(source);
            }

            @Override
            public InspiringBean[] newArray(int size) {
                return new InspiringBean[size];
            }
        };
    }

    public static class AdmiringBean implements Parcelable {
        /**
         * userId : 1
         * profilePic : http://dev2.ifuturz.com/core/konkr/assets/upload/profile/1_thumb.png
         * firstName : Kim
         * lastName : Test
         * badge : 1
         */

        private int userId;
        private String profilePic;
        private String firstName;
        private String lastName;
        private String badge;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
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

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.profilePic);
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.badge);
        }

        public AdmiringBean() {
        }

        protected AdmiringBean(Parcel in) {
            this.userId = in.readInt();
            this.profilePic = in.readString();
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.badge = in.readString();
        }

        public static final Creator<AdmiringBean> CREATOR = new Creator<AdmiringBean>() {
            @Override
            public AdmiringBean createFromParcel(Parcel source) {
                return new AdmiringBean(source);
            }

            @Override
            public AdmiringBean[] newArray(int size) {
                return new AdmiringBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.status);
        dest.writeTypedList(this.Goals);
        dest.writeTypedList(this.Inspiring);
        dest.writeList(this.Admiring);
    }

    public ExpressionMedia() {
    }

    protected ExpressionMedia(Parcel in) {
        this.message = in.readString();
        this.status = in.readInt();
        this.Goals = in.createTypedArrayList(GoalsBean.CREATOR);
        this.Inspiring = in.createTypedArrayList(InspiringBean.CREATOR);
        this.Admiring = new ArrayList<AdmiringBean>();
        in.readList(this.Admiring, AdmiringBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ExpressionMedia> CREATOR = new Parcelable.Creator<ExpressionMedia>() {
        @Override
        public ExpressionMedia createFromParcel(Parcel source) {
            return new ExpressionMedia(source);
        }

        @Override
        public ExpressionMedia[] newArray(int size) {
            return new ExpressionMedia[size];
        }
    };
}
