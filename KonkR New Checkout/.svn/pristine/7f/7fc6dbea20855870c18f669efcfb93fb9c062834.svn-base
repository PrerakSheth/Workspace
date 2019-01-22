package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MiTrainingCategory implements Parcelable {

    /**
     * message : Workout categories found successfully.
     * status : 1
     * workoutCategories : [{"workoutCategoryId":10,"workoutCategoryName":"Abs"},{"workoutCategoryId":8,"workoutCategoryName":"Arms"},{"workoutCategoryId":12,"workoutCategoryName":"Back"},{"workoutCategoryId":14,"workoutCategoryName":"Calves"},{"workoutCategoryId":11,"workoutCategoryName":"Chest"},{"workoutCategoryId":9,"workoutCategoryName":"Legs"},{"workoutCategoryId":13,"workoutCategoryName":"Shoulders"}]
     */

    private String message;
    private int status;
    private List<WorkoutCategoriesBean> workoutCategories;
    private int isSubscribed;

    public int getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(int isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    protected MiTrainingCategory(Parcel in) {
        message = in.readString();
        status = in.readInt();
    }

    public static final Creator<MiTrainingCategory> CREATOR = new Creator<MiTrainingCategory>() {
        @Override
        public MiTrainingCategory createFromParcel(Parcel in) {
            return new MiTrainingCategory(in);
        }

        @Override
        public MiTrainingCategory[] newArray(int size) {
            return new MiTrainingCategory[size];
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

    public List<WorkoutCategoriesBean> getWorkoutCategories() {
        return workoutCategories;
    }

    public void setWorkoutCategories(List<WorkoutCategoriesBean> workoutCategories) {
        this.workoutCategories = workoutCategories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeInt(status);
    }

    public static class WorkoutCategoriesBean implements Parcelable {
        /**
         * workoutCategoryId : 10
         * workoutCategoryName : Abs
         */

        private int workoutCategoryId;
        private String workoutCategoryName;

        protected WorkoutCategoriesBean(Parcel in) {
            workoutCategoryId = in.readInt();
            workoutCategoryName = in.readString();
        }

        public static final Creator<WorkoutCategoriesBean> CREATOR = new Creator<WorkoutCategoriesBean>() {
            @Override
            public WorkoutCategoriesBean createFromParcel(Parcel in) {
                return new WorkoutCategoriesBean(in);
            }

            @Override
            public WorkoutCategoriesBean[] newArray(int size) {
                return new WorkoutCategoriesBean[size];
            }
        };

        public int getWorkoutCategoryId() {
            return workoutCategoryId;
        }

        public void setWorkoutCategoryId(int workoutCategoryId) {
            this.workoutCategoryId = workoutCategoryId;
        }

        public String getWorkoutCategoryName() {
            return workoutCategoryName;
        }

        public void setWorkoutCategoryName(String workoutCategoryName) {
            this.workoutCategoryName = workoutCategoryName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(workoutCategoryId);
            dest.writeString(workoutCategoryName);
        }
    }
}
