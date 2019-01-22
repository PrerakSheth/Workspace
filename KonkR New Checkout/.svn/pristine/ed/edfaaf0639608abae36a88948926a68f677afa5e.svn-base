package com.konkr.Models;

import java.util.List;

/**
 * Created by Android on 7/2/2018.
 */

public class SearchSuppliment {

    /**
     * message : Supplement search list have been fetched successfully.
     * status : 1
     * supplimentSearchData : [{"suppName":"Greek Yogurt, Strawberry or Vanilla","brand_name":"Greek Yogurt"},{"suppName":"Greek Yogurt, Berry & Lemon","brand_name":"Chobani Greek Yogurt"}]
     */

    private String message;
    private int status;
    private List<SupplimentSearchDataBean> supplimentSearchData;

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

    public List<SupplimentSearchDataBean> getSupplimentSearchData() {
        return supplimentSearchData;
    }

    public void setSupplimentSearchData(List<SupplimentSearchDataBean> supplimentSearchData) {
        this.supplimentSearchData = supplimentSearchData;
    }

    public static class SupplimentSearchDataBean {
        /**
         * suppName : Greek Yogurt, Strawberry or Vanilla
         * brand_name : Greek Yogurt
         */

        private String suppName;
        private String brand_name;
        private String resourceId;

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getSuppName() {
            return suppName;
        }

        public void setSuppName(String suppName) {
            this.suppName = suppName;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

//        @Override
//        public String toString() {
//            return suppName +","+brand_name;
//        }

        @Override
        public String toString() {
            return suppName;
        }
    }
}
