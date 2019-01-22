package com.konkr.Models;

import java.util.List;

/**
 * Created by Android on 7/7/2018.
 */

public class SearchMeal {


    /**
     * message : Meal search list have been fetched successfully.
     * status : 1
     * mealSearchNewData : [{"item_name":"Milk Bar Pie Mix","serving_uom":"of dry mix","resource_id":"mdEfgA9w"},{"item_name":"Milk, Reduced Fat, 2% Milkfat","serving_uom":"carton","resource_id":"lNEue0p4"}]
     */

    private String message;
    private int status;
    private List <MealSearchNewDataBean> mealSearchNewData;

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

    public List <MealSearchNewDataBean> getMealSearchNewData() {
        return mealSearchNewData;
    }

    public void setMealSearchNewData(List <MealSearchNewDataBean> mealSearchNewData) {
        this.mealSearchNewData = mealSearchNewData;
    }

    public static class MealSearchNewDataBean {
        /**
         * item_name : Milk Bar Pie Mix
         * serving_uom : of dry mix
         * resource_id : mdEfgA9w
         */

        private String item_name;
        private String serving_uom;
        private String resource_id;

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getServing_uom() {
            return serving_uom;
        }

        public void setServing_uom(String serving_uom) {
            this.serving_uom = serving_uom;
        }

        public String getResource_id() {
            return resource_id;
        }

        public void setResource_id(String resource_id) {
            this.resource_id = resource_id;
        }


        @Override
        public String toString() {
            return  item_name;
        }
    }
}
