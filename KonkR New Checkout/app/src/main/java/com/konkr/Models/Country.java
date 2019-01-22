package com.konkr.Models;

import java.util.ArrayList;

/**
 * Created by Android on 5/19/2018.
 */

public class Country {

    /**
     * message : Country list have been fetched successfully.
     * status : 1
     * countryList : [{"countryId":1,"countryName":"Afghanistan"}]
     */

    private String message;
    private int status;
    private ArrayList<CountryListBean> countryList;

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

    public ArrayList<CountryListBean> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<CountryListBean> countryList) {
        this.countryList = countryList;
    }

    public static class CountryListBean {
        /**
         * countryId : 1
         * countryName : Afghanistan
         */

        private int countryId;
        private String countryName;

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String toString() {
            return countryName;
        }
    }
}
