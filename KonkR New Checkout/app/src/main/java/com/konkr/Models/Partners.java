package com.konkr.Models;

import java.util.List;

/**
 * Created by Android on 6/13/2018.
 */

public class Partners {


    /**
     * message : Partner list found successfully.
     * status : 1
     * partnerList : [{"clientName":"lorem ipsum","logo":"http://dev2.ifuturz.com/core/konkr/assets/upload/partner/20180602021647.png","email":"lorem ipsum","phone":"918541245784","companyName":"lorem "},{"clientName":"test","logo":"http://dev2.ifuturz.com/core/konkr/assets/upload/partner/20180602021632.png","email":"test","phone":"954512457845","companyName":"lorem "},{"clientName":"dsds","logo":"http://dev2.ifuturz.com/core/konkr/assets/upload/partner/20180602020437.png","email":"dsds","phone":"918541245784","companyName":"qwwsw"}]
     */

    private String message;
    private int status;
    private List <PartnerListBean> partnerList;

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

    public List <PartnerListBean> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List <PartnerListBean> partnerList) {
        this.partnerList = partnerList;
    }

    public static class PartnerListBean {
        /**
         * clientName : lorem ipsum
         * logo : http://dev2.ifuturz.com/core/konkr/assets/upload/partner/20180602021647.png
         * email : lorem ipsum
         * phone : 918541245784
         * companyName : lorem
         */

        private String clientName;
        private String logo;
        private String email;
        private String phone;
        private String companyName;

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
    }
}
