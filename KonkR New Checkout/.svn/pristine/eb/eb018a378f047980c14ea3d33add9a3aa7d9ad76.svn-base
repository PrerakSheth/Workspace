package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Android on 8/21/2018.
 */

public class Advertisement {


    /**
     * message : Advertise list found successfully.
     * status : 1
     * advertiseList : [{"clientName":"new","logo":"http://dev2.ifuturz.com/core/konkr/assets/upload/ads/20180821015321.png","email":"new@email.coms","phone":"677539012","companyName":"myCompany","campaignName":"myCampaign"},{"clientName":"test","logo":"http://dev2.ifuturz.com/core/konkr/assets/upload/ads/20180821125536.png","email":"fdsfd@ff.com","phone":"798541201","companyName":"myCompany","campaignName":"myCampaign"},{"clientName":"fff","logo":"http://dev2.ifuturz.com/core/konkr/assets/upload/ads/20180720052916.png","email":"111@y.com","phone":"789456123111","companyName":"sss","campaignName":"ssss"}]
     */

    private String message;
    private int status;
    private List<AdvertiseListBean> advertiseList;

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

    public List<AdvertiseListBean> getAdvertiseList() {
        return advertiseList;
    }

    public void setAdvertiseList(List<AdvertiseListBean> advertiseList) {
        this.advertiseList = advertiseList;
    }

    public static class AdvertiseListBean implements Parcelable{
        /**
         * clientName : new
         * logo : http://dev2.ifuturz.com/core/konkr/assets/upload/ads/20180821015321.png
         * email : new@email.coms
         * phone : 677539012
         * companyName : myCompany
         * campaignName : myCampaign
         */

        private String clientName;
        private String logo;
        private String email;
        private String phone;
        private String companyName;
        private String campaignName;

        protected AdvertiseListBean(Parcel in) {
            clientName = in.readString();
            logo = in.readString();
            email = in.readString();
            phone = in.readString();
            companyName = in.readString();
            campaignName = in.readString();
        }

        public static final Creator<AdvertiseListBean> CREATOR = new Creator<AdvertiseListBean>() {
            @Override
            public AdvertiseListBean createFromParcel(Parcel in) {
                return new AdvertiseListBean(in);
            }

            @Override
            public AdvertiseListBean[] newArray(int size) {
                return new AdvertiseListBean[size];
            }
        };

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCampaignName() {
            return campaignName;
        }

        public void setCampaignName(String campaignName) {
            this.campaignName = campaignName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(clientName);
            parcel.writeString(logo);
            parcel.writeString(email);
            parcel.writeString(phone);
            parcel.writeString(companyName);
            parcel.writeString(campaignName);
        }
    }
}
