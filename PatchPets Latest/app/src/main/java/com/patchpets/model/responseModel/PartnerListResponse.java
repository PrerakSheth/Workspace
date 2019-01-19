package com.patchpets.model.responseModel;

import java.util.List;

public class PartnerListResponse {

    /**
     * message : Partner list found successfully.
     * status : 1
     * partnerList : [{"clientName":"lorem ipsum","logo":"URL","email":"lorem ipsum","phone":"918541245784","companyName":"lorem "}]
     */

    private String message;
    private int status;
    private List<PartnerListBean> partnerList;

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

    public List<PartnerListBean> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<PartnerListBean> partnerList) {
        this.partnerList = partnerList;
    }

    public static class PartnerListBean {
        /**
         * clientName : lorem ipsum
         * logo : URL
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
