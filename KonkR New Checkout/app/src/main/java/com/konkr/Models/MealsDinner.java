
package com.konkr.Models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MealsDinner {

    @SerializedName("foodName")
    private String mFoodName;
    @SerializedName("mealId")
    private Long mMealId;
    @SerializedName("mealName")
    private String mMealName;
    @SerializedName("mealPhoto")
    private String mMealPhoto;
    @SerializedName("mealType")
    private Long mMealType;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("unit")
    private String mUnit;

    public String getFoodName() {
        return mFoodName;
    }

    public void setFoodName(String foodName) {
        mFoodName = foodName;
    }

    public Long getMealId() {
        return mMealId;
    }

    public void setMealId(Long mealId) {
        mMealId = mealId;
    }

    public String getMealName() {
        return mMealName;
    }

    public void setMealName(String mealName) {
        mMealName = mealName;
    }

    public String getMealPhoto() {
        return mMealPhoto;
    }

    public void setMealPhoto(String mealPhoto) {
        mMealPhoto = mealPhoto;
    }

    public Long getMealType() {
        return mMealType;
    }

    public void setMealType(Long mealType) {
        mMealType = mealType;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

}
