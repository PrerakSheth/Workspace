package com.konkr.Models;
import java.util.List;
import com.google.gson.annotations.Expose;

public class PremiumMembership {

    @Expose
    private String message;
    @Expose
    private Long status;
    @Expose
    private List<SubscriptionDatum> subscriptionData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<SubscriptionDatum> getSubscriptionData() {
        return subscriptionData;
    }

    public void setSubscriptionData(List<SubscriptionDatum> subscriptionData) {
        this.subscriptionData = subscriptionData;
    }

    @SuppressWarnings("unused")
    public static class SubscriptionDatum {

        @Expose
        private double amount;
        @Expose
        private Long duration;
        @Expose
        private int subscriptionId;
        @Expose
        private String subscriptionName;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public int getSubscriptionId() {
            return subscriptionId;
        }

        public void setSubscriptionId(int subscriptionId) {
            this.subscriptionId = subscriptionId;
        }

        public String getSubscriptionName() {
            return subscriptionName;
        }

        public void setSubscriptionName(String subscriptionName) {
            this.subscriptionName = subscriptionName;
        }

    }
}
