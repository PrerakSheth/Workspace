package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 6/30/2018.
 */

public class Meals {


    /**
     * message : Meals found successfully.
     * status : 1
     * mealsBreakfast : [{"mealId":3,"mealPhoto":"","mealType":1,"foodName":"Tandoori Chicken Samosa","mealName":"","unit":"pieces","quantity":"94"}]
     * mealsLunch : [{"mealId":14,"mealPhoto":"","mealType":2,"foodName":"Whey HD","mealName":"","unit":"scoop","quantity":"64"},{"mealId":13,"mealPhoto":"","mealType":2,"foodName":"Meat Curry Masala","mealName":"","unit":"g","quantity":"64"},{"mealId":12,"mealPhoto":"","mealType":2,"foodName":"Chocolate","mealName":"","unit":"bars","quantity":"65"}]
     * mealsSnacks : [{"mealId":5,"mealPhoto":"","mealType":3,"foodName":"Butterscotch Squares with","mealName":"","unit":"g","quantity":"646"}]
     * mealsDinner : [{"mealId":6,"mealPhoto":"","mealType":4,"foodName":"Super Dark Chocolate, Coc","mealName":"","unit":"bar","quantity":"64"}]
     * otherMealData : [[{"mealId":11,"mealPhoto":"","mealType":5,"foodName":"Appetite Control Cracker,","mealName":"tttt","unit":"cracker","quantity":"99"},{"mealId":10,"mealPhoto":"","mealType":5,"foodName":"GG Peppers","mealName":"jljj","unit":"Serving","quantity":"9"}],[{"mealId":9,"mealPhoto":"","mealType":5,"foodName":"Jasmine Rice","mealName":"riC","unit":"cup","quantity":"23"}],[{"mealId":2,"mealPhoto":"","mealType":5,"foodName":"Mexican Rice","mealName":"dings","unit":"cup","quantity":"23"},{"mealId":1,"mealPhoto":"","mealType":5,"foodName":"Rice, Fried Rice","mealName":"dings","unit":"Oz","quantity":"2"}]]
     */

    private String message;
    private int status;
    private ArrayList<Meal> mealsBreakfast;
    private ArrayList<Meal> mealsLunch;
    private ArrayList<Meal> mealsSnacks;
    private ArrayList<Meal> mealsDinner;
    private ArrayList<ArrayList<Meal>> otherMealData;

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

    public ArrayList<Meal> getMealsBreakfast() {
        return mealsBreakfast;
    }

    public void setMealsBreakfast(ArrayList<Meal> mealsBreakfast) {
        this.mealsBreakfast = mealsBreakfast;
    }

    public ArrayList<Meal> getMealsLunch() {
        return mealsLunch;
    }

    public void setMealsLunch(ArrayList<Meal> mealsLunch) {
        this.mealsLunch = mealsLunch;
    }

    public ArrayList<Meal> getMealsSnacks() {
        return mealsSnacks;
    }

    public void setMealsSnacks(ArrayList<Meal> mealsSnacks) {
        this.mealsSnacks = mealsSnacks;
    }

    public ArrayList<Meal> getMealsDinner() {
        return mealsDinner;
    }

    public void setMealsDinner(ArrayList<Meal> mealsDinner) {
        this.mealsDinner = mealsDinner;
    }

    public ArrayList<ArrayList<Meal>> getOtherMealData() {
        return otherMealData;
    }

    public void setOtherMealData(ArrayList<ArrayList<Meal>> otherMealData) {
        this.otherMealData = otherMealData;
    }


    public static class Meal implements Parcelable {
        /**
         * mealId : 3
         * mealPhoto :
         * mealType : 1
         * foodName : Tandoori Chicken Samosa
         * mealName :
         * unit : pieces
         * quantity : 94
         */

        private int mealId;
        private String mealPhoto;
        private int mealType;
        private String foodName;
        private String mealName;
        private String unit;
        private String quantity;
        private String homeFeedId;
        int is_inspiring;
        int is_goals;
        int is_admiring;
        int expressionCount;
        int commentCount;

        public String getHomeFeedId() {
            return homeFeedId;
        }

        public void setHomeFeedId(String homeFeedId) {
            this.homeFeedId = homeFeedId;
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

        public int getIs_admiring() {
            return is_admiring;
        }

        public void setIs_admiring(int is_admiring) {
            this.is_admiring = is_admiring;
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

        public static Creator<Meal> getCREATOR() {
            return CREATOR;
        }

        public Meal(int mealId, String mealPhoto, int mealType, String foodName, String mealName, String unit, String quantity, String homeFeedId, int is_inspiring, int is_goals, int is_admiring, int expressionCount, int commentCount) {
            this.mealId = mealId;
            this.mealPhoto = mealPhoto;
            this.mealType = mealType;
            this.foodName = foodName;
            this.mealName = mealName;
            this.unit = unit;
            this.quantity = quantity;
            this.homeFeedId = homeFeedId;
            this.is_inspiring = is_inspiring;
            this.is_goals = is_goals;
            this.is_admiring = is_admiring;
            this.expressionCount = expressionCount;
            this.commentCount = commentCount;
        }

//        public Meal(int mealId, String mealPhoto, int mealType, String foodName, String mealName, String unit, String quantity) {
//            this.mealId = mealId;
//            this.mealPhoto = mealPhoto;
//            this.mealType = mealType;
//            this.foodName = foodName;
//            this.mealName = mealName;
//            this.unit = unit;
//            this.quantity = quantity;
//
//        }

        public int getMealId() {
            return mealId;
        }

        public void setMealId(int mealId) {
            this.mealId = mealId;
        }

        public String getMealPhoto() {
            return mealPhoto;
        }

        public void setMealPhoto(String mealPhoto) {
            this.mealPhoto = mealPhoto;
        }

        public int getMealType() {
            return mealType;
        }

        public void setMealType(int mealType) {
            this.mealType = mealType;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getMealName() {
            return mealName;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mealId);
            dest.writeString(this.mealPhoto);
            dest.writeInt(this.mealType);
            dest.writeString(this.foodName);
            dest.writeString(this.mealName);
            dest.writeString(this.unit);
            dest.writeString(this.quantity);
            dest.writeString(this.homeFeedId);
            dest.writeInt(this.is_inspiring);
            dest.writeInt(this.is_goals);
            dest.writeInt(this.is_admiring);
            dest.writeInt(this.expressionCount);
            dest.writeInt(this.commentCount);
        }

        public Meal() {
        }

        protected Meal(Parcel in) {
            this.mealId = in.readInt();
            this.mealPhoto = in.readString();
            this.mealType = in.readInt();
            this.foodName = in.readString();
            this.mealName = in.readString();
            this.unit = in.readString();
            this.quantity = in.readString();
            this.homeFeedId = in.readString();
            this.is_inspiring = in.readInt();
            this.is_goals = in.readInt();
            this.is_admiring = in.readInt();
            this.expressionCount = in.readInt();
            this.commentCount = in.readInt();
        }

        public static final Parcelable.Creator<Meal> CREATOR = new Parcelable.Creator<Meal>() {
            @Override
            public Meal createFromParcel(Parcel source) {
                return new Meal(source);
            }

            @Override
            public Meal[] newArray(int size) {
                return new Meal[size];
            }
        };
    }


}
