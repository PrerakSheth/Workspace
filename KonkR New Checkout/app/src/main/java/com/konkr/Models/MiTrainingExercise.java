package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MiTrainingExercise implements Parcelable {

    /**
     * message : Excercises found successfully.
     * status : 1
     * excercises : [{"excerciseId":478,"excerciseName":"Arms"},{"excerciseId":135,"excerciseName":"Ausfallschritt Trizepsdrücken ü. Kopf, ß-Stange"},{"excerciseId":289,"excerciseName":"Axe Hold"},{"excerciseId":38,"excerciseName":"Bankdrücken Eng"},{"excerciseId":522,"excerciseName":"Barbell Squat"},{"excerciseId":344,"excerciseName":"Barbell Triceps Extension"},{"excerciseId":451,"excerciseName":"Barbell Wrist Curls"},{"excerciseId":88,"excerciseName":"Bench Press Narrow Grip"},{"excerciseId":482,"excerciseName":"Bicep Curls"},{"excerciseId":542,"excerciseName":"Biceps"},{"excerciseId":74,"excerciseName":"Biceps Curls With Barbell"},{"excerciseId":81,"excerciseName":"Biceps Curls With Dumbbell"},{"excerciseId":80,"excerciseName":"Biceps Curls With SZ-bar"},{"excerciseId":129,"excerciseName":"Biceps Curl With Cable"},{"excerciseId":561,"excerciseName":"Bigmarms"},{"excerciseId":3,"excerciseName":"Bizeps am Kabel"},{"excerciseId":231,"excerciseName":"Bizeps am Kabel, ß-Stange"},{"excerciseId":44,"excerciseName":"Bizeps Curls Mit ß-Stange "},{"excerciseId":26,"excerciseName":"Bizeps KH-Curls"},{"excerciseId":242,"excerciseName":"Bizeps KH-Curls Schrägbank"}]
     */

    private String message;
    private int status;
    private List<ExcercisesBean> excercises;

    protected MiTrainingExercise(Parcel in) {
        message = in.readString();
        status = in.readInt();
    }

    public static final Creator<MiTrainingExercise> CREATOR = new Creator<MiTrainingExercise>() {
        @Override
        public MiTrainingExercise createFromParcel(Parcel in) {
            return new MiTrainingExercise(in);
        }

        @Override
        public MiTrainingExercise[] newArray(int size) {
            return new MiTrainingExercise[size];
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

    public List<ExcercisesBean> getExcercises() {
        return excercises;
    }

    public void setExcercises(List<ExcercisesBean> excercises) {
        this.excercises = excercises;
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

    public static class ExcercisesBean implements Parcelable {
        /**
         * excerciseId : 478
         * excerciseName : Arms
         */

        private int excerciseId;
        private String excerciseName;

        protected ExcercisesBean(Parcel in) {
            excerciseId = in.readInt();
            excerciseName = in.readString();
        }

        public static final Creator<ExcercisesBean> CREATOR = new Creator<ExcercisesBean>() {
            @Override
            public ExcercisesBean createFromParcel(Parcel in) {
                return new ExcercisesBean(in);
            }

            @Override
            public ExcercisesBean[] newArray(int size) {
                return new ExcercisesBean[size];
            }
        };

        public int getExcerciseId() {
            return excerciseId;
        }

        public void setExcerciseId(int excerciseId) {
            this.excerciseId = excerciseId;
        }

        public String getExcerciseName() {
            return excerciseName;
        }

        public void setExcerciseName(String excerciseName) {
            this.excerciseName = excerciseName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(excerciseId);
            dest.writeString(excerciseName);
        }
    }
}
