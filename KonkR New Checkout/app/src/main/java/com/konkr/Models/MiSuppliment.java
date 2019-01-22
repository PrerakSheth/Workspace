package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Android on 6/30/2018.
 */

public class MiSuppliment {


    /**
     * message : supplements found successfully.
     * status : 1
     * supplements : [{"suppId":5,"suppName":"Supp1","suppDetails":"description","suppPhoto":"http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/5.png"}]
     */

    private String message;
    private int status;
    private List <SupplementsBean> supplements;

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

    public List <SupplementsBean> getSupplements() {
        return supplements;
    }

    public void setSupplements(List <SupplementsBean> supplements) {
        this.supplements = supplements;
    }

    public static class SupplementsBean implements Parcelable {

        /**
         * suppId : 11
         * homeFeedId : 69
         * suppName : Protein Crunch with Pumpkin Seeds
         * suppDetails : jsj
         * suppPhoto : http://dev2.ifuturz.com/core/konkr/assets/upload/supplement/11.png
         * expressionCount : 0
         * commentCount : 0
         * is_admiring : 0
         * is_inspiring : 0
         * is_goals : 0
         */

        private int suppId;
        private String homeFeedId;
        private String suppName;
        private String suppDetails;
        private String suppPhoto;
        private int expressionCount;
        private int commentCount;
        private int is_admiring;
        private int is_inspiring;
        private int is_goals;

        protected SupplementsBean(Parcel in) {
            suppId = in.readInt();
            homeFeedId = in.readString();
            suppName = in.readString();
            suppDetails = in.readString();
            suppPhoto = in.readString();
            expressionCount = in.readInt();
            commentCount = in.readInt();
            is_admiring = in.readInt();
            is_inspiring = in.readInt();
            is_goals = in.readInt();
        }

        public static final Creator<SupplementsBean> CREATOR = new Creator<SupplementsBean>() {
            @Override
            public SupplementsBean createFromParcel(Parcel in) {
                return new SupplementsBean(in);
            }

            @Override
            public SupplementsBean[] newArray(int size) {
                return new SupplementsBean[size];
            }
        };

        public int getSuppId() {
            return suppId;
        }

        public void setSuppId(int suppId) {
            this.suppId = suppId;
        }

        public String getHomeFeedId() {
            return homeFeedId;
        }

        public void setHomeFeedId(String homeFeedId) {
            this.homeFeedId = homeFeedId;
        }

        public String getSuppName() {
            return suppName;
        }

        public void setSuppName(String suppName) {
            this.suppName = suppName;
        }

        public String getSuppDetails() {
            return suppDetails;
        }

        public void setSuppDetails(String suppDetails) {
            this.suppDetails = suppDetails;
        }

        public String getSuppPhoto() {
            return suppPhoto;
        }

        public void setSuppPhoto(String suppPhoto) {
            this.suppPhoto = suppPhoto;
        }

        public int getExpressionCount() {
            return expressionCount;
        }

        public void setExpressionCount(int expressionCount) {
            this.expressionCount = expressionCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getIs_admiring() {
            return is_admiring;
        }

        public void setIs_admiring(int is_admiring) {
            this.is_admiring = is_admiring;
        }

        public int getIs_inspiring() {
            return is_inspiring;
        }

        public void setIs_inspiring(int is_inspiring) {
            this.is_inspiring = is_inspiring;
        }

        public int getIs_goals() {
            return is_goals;
        }

        public void setIs_goals(int is_goals) {
            this.is_goals = is_goals;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(suppId);
            dest.writeString(homeFeedId);
            dest.writeString(suppName);
            dest.writeString(suppDetails);
            dest.writeString(suppPhoto);
            dest.writeInt(expressionCount);
            dest.writeInt(commentCount);
            dest.writeInt(is_admiring);
            dest.writeInt(is_inspiring);
            dest.writeInt(is_goals);
        }
    }
}
