package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SuggestedTrainingGoals implements Parcelable {

    /**
     * message : Goals have been fetched successfully.
     * status : 1
     * SuggestedTrainingGoals : [{"goalId":132,"goalName":"aa"},{"goalId":125,"goalName":"jajmm"},{"goalId":120,"goalName":"asd asdf asdfg"},{"goalId":119,"goalName":"jaj"},{"goalId":110,"goalName":"hah"},{"goalId":108,"goalName":"nana"},{"goalId":103,"goalName":"Aaaaa"},{"goalId":99,"goalName":"Yack"},{"goalId":97,"goalName":"Komal"},{"goalId":91,"goalName":"Mosan"},{"goalId":81,"goalName":"Akashshah"},{"goalId":70,"goalName":"Shah"},{"goalId":69,"goalName":"Verma"},{"goalId":65,"goalName":"Acaafamlfmamcaafma"},{"goalId":62,"goalName":"Akash"},{"goalId":60,"goalName":"cscsacsa"},{"goalId":53,"goalName":"naja"},{"goalId":49,"goalName":"ban"},{"goalId":37,"goalName":"aaa"},{"goalId":33,"goalName":"Afat"},{"goalId":32,"goalName":"a"},{"goalId":19,"goalName":"Heal"},{"goalId":3,"goalName":"dads"}]
     */

    private String message;
    private int status;
    private List<SuggestedTrainingGoalsBean> SuggestedTrainingGoals;

    protected SuggestedTrainingGoals(Parcel in) {
        message = in.readString();
        status = in.readInt();
    }

    public static final Creator<SuggestedTrainingGoals> CREATOR = new Creator<SuggestedTrainingGoals>() {
        @Override
        public SuggestedTrainingGoals createFromParcel(Parcel in) {
            return new SuggestedTrainingGoals(in);
        }

        @Override
        public SuggestedTrainingGoals[] newArray(int size) {
            return new SuggestedTrainingGoals[size];
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

    public List<SuggestedTrainingGoalsBean> getSuggestedTrainingGoals() {
        return SuggestedTrainingGoals;
    }

    public void setSuggestedTrainingGoals(List<SuggestedTrainingGoalsBean> SuggestedTrainingGoals) {
        this.SuggestedTrainingGoals = SuggestedTrainingGoals;
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

    public static class SuggestedTrainingGoalsBean implements Parcelable {
        /**
         * goalId : 132
         * goalName : aa
         */

        private int goalId;
        private String goalName;

        protected SuggestedTrainingGoalsBean(Parcel in) {
            goalId = in.readInt();
            goalName = in.readString();
        }

        public static final Creator<SuggestedTrainingGoalsBean> CREATOR = new Creator<SuggestedTrainingGoalsBean>() {
            @Override
            public SuggestedTrainingGoalsBean createFromParcel(Parcel in) {
                return new SuggestedTrainingGoalsBean(in);
            }

            @Override
            public SuggestedTrainingGoalsBean[] newArray(int size) {
                return new SuggestedTrainingGoalsBean[size];
            }
        };

        public int getGoalId() {
            return goalId;
        }

        public void setGoalId(int goalId) {
            this.goalId = goalId;
        }

        public String getGoalName() {
            return goalName;
        }

        public void setGoalName(String goalName) {
            this.goalName = goalName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(goalId);
            dest.writeString(goalName);
        }
    }
}
