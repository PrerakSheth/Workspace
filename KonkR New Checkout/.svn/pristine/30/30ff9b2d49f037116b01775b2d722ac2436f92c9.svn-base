package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Android on 6/30/2018.
 */

public class MyMeals {


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
    private List<MealsBreakfastBean> mealsBreakfast;
    private List<MealsLunchBean> mealsLunch;
    private List<MealsSnacksBean> mealsSnacks;
    private List<MealsDinnerBean> mealsDinner;
    private List<List<OtherMealDataBean>> otherMealData;

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

    public List<MealsBreakfastBean> getMealsBreakfast() {
        return mealsBreakfast;
    }

    public void setMealsBreakfast(List<MealsBreakfastBean> mealsBreakfast) {
        this.mealsBreakfast = mealsBreakfast;
    }

    public List<MealsLunchBean> getMealsLunch() {
        return mealsLunch;
    }

    public void setMealsLunch(List<MealsLunchBean> mealsLunch) {
        this.mealsLunch = mealsLunch;
    }

    public List<MealsSnacksBean> getMealsSnacks() {
        return mealsSnacks;
    }

    public void setMealsSnacks(List<MealsSnacksBean> mealsSnacks) {
        this.mealsSnacks = mealsSnacks;
    }

    public List<MealsDinnerBean> getMealsDinner() {
        return mealsDinner;
    }

    public void setMealsDinner(List<MealsDinnerBean> mealsDinner) {
        this.mealsDinner = mealsDinner;
    }

    public List<List<OtherMealDataBean>> getOtherMealData() {
        return otherMealData;
    }

    public void setOtherMealData(List<List<OtherMealDataBean>> otherMealData) {
        this.otherMealData = otherMealData;
    }

    public static class MealsBreakfastBean implements Parcelable{
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

        protected MealsBreakfastBean(Parcel in) {
            mealId = in.readInt();
            mealPhoto = in.readString();
            mealType = in.readInt();
            foodName = in.readString();
            mealName = in.readString();
            unit = in.readString();
            quantity = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mealId);
            dest.writeString(mealPhoto);
            dest.writeInt(mealType);
            dest.writeString(foodName);
            dest.writeString(mealName);
            dest.writeString(unit);
            dest.writeString(quantity);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MealsBreakfastBean> CREATOR = new Creator<MealsBreakfastBean>() {
            @Override
            public MealsBreakfastBean createFromParcel(Parcel in) {
                return new MealsBreakfastBean(in);
            }

            @Override
            public MealsBreakfastBean[] newArray(int size) {
                return new MealsBreakfastBean[size];
            }
        };

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
    }

    public static class MealsLunchBean implements Parcelable {
        /**
         * mealId : 14
         * mealPhoto :
         * mealType : 2
         * foodName : Whey HD
         * mealName :
         * unit : scoop
         * quantity : 64
         */

        private int mealId;
        private String mealPhoto;
        private int mealType;
        private String foodName;
        private String mealName;
        private String unit;
        private String quantity;

        protected MealsLunchBean(Parcel in) {
            mealId = in.readInt();
            mealPhoto = in.readString();
            mealType = in.readInt();
            foodName = in.readString();
            mealName = in.readString();
            unit = in.readString();
            quantity = in.readString();
        }

        public static final Creator<MealsLunchBean> CREATOR = new Creator<MealsLunchBean>() {
            @Override
            public MealsLunchBean createFromParcel(Parcel in) {
                return new MealsLunchBean(in);
            }

            @Override
            public MealsLunchBean[] newArray(int size) {
                return new MealsLunchBean[size];
            }
        };

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
            dest.writeInt(mealId);
            dest.writeString(mealPhoto);
            dest.writeInt(mealType);
            dest.writeString(foodName);
            dest.writeString(mealName);
            dest.writeString(unit);
            dest.writeString(quantity);
        }
    }

    public static class MealsSnacksBean implements Parcelable{
        /**
         * mealId : 5
         * mealPhoto :
         * mealType : 3
         * foodName : Butterscotch Squares with
         * mealName :
         * unit : g
         * quantity : 646
         */

        private int mealId;
        private String mealPhoto;
        private int mealType;
        private String foodName;
        private String mealName;
        private String unit;
        private String quantity;

        protected MealsSnacksBean(Parcel in) {
            mealId = in.readInt();
            mealPhoto = in.readString();
            mealType = in.readInt();
            foodName = in.readString();
            mealName = in.readString();
            unit = in.readString();
            quantity = in.readString();
        }

        public static final Creator<MealsSnacksBean> CREATOR = new Creator<MealsSnacksBean>() {
            @Override
            public MealsSnacksBean createFromParcel(Parcel in) {
                return new MealsSnacksBean(in);
            }

            @Override
            public MealsSnacksBean[] newArray(int size) {
                return new MealsSnacksBean[size];
            }
        };

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
            dest.writeInt(mealId);
            dest.writeString(mealPhoto);
            dest.writeInt(mealType);
            dest.writeString(foodName);
            dest.writeString(mealName);
            dest.writeString(unit);
            dest.writeString(quantity);
        }
    }

    public static class MealsDinnerBean implements Parcelable{
        /**
         * mealId : 6
         * mealPhoto :
         * mealType : 4
         * foodName : Super Dark Chocolate, Coc
         * mealName :
         * unit : bar
         * quantity : 64
         */

        private int mealId;
        private String mealPhoto;
        private int mealType;
        private String foodName;
        private String mealName;
        private String unit;
        private String quantity;

        protected MealsDinnerBean(Parcel in) {
            mealId = in.readInt();
            mealPhoto = in.readString();
            mealType = in.readInt();
            foodName = in.readString();
            mealName = in.readString();
            unit = in.readString();
            quantity = in.readString();
        }

        public static final Creator<MealsDinnerBean> CREATOR = new Creator<MealsDinnerBean>() {
            @Override
            public MealsDinnerBean createFromParcel(Parcel in) {
                return new MealsDinnerBean(in);
            }

            @Override
            public MealsDinnerBean[] newArray(int size) {
                return new MealsDinnerBean[size];
            }
        };

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
            dest.writeInt(mealId);
            dest.writeString(mealPhoto);
            dest.writeInt(mealType);
            dest.writeString(foodName);
            dest.writeString(mealName);
            dest.writeString(unit);
            dest.writeString(quantity);
        }
    }

    public static class OtherMealDataBean {
        /**
         * mealId : 11
         * mealPhoto :
         * mealType : 5
         * foodName : Appetite Control Cracker,
         * mealName : tttt
         * unit : cracker
         * quantity : 99
         */

        private int mealId;
        private String mealPhoto;
        private int mealType;
        private String foodName;
        private String mealName;
        private String unit;
        private String quantity;

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
    }
}
