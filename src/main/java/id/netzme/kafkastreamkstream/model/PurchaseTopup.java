package id.netzme.kafkastreamkstream.model;

import id.netzme.kafkastreamkstream.util.CommonHelper;

public class PurchaseTopup {
    private String userID;
    private String billID;
    private Integer billAmount;
    private Integer respCode;
    private String respDesc;
    private String rrn;

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    private String refNumber;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public void setBillAmount(Integer billAmount) {
        this.billAmount = billAmount;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public String getUserID() {
        return userID;
    }

    public String getBillID() {
        return billID;
    }

    public Integer getBillAmount() {
        return billAmount;
    }

    public String getRrn() {
        return rrn;
    }

    public String toString() {
        return CommonHelper.getJsonPurchaseTopup(this);
    }

    private PurchaseTopup(Builder builder){
        userID = builder.userID;
        billID = builder.billID;
        respCode = builder.respCode;
        respDesc = builder.respDesc;
        billAmount = builder.billAmount;
        rrn = builder.rrn;
        refNumber = builder.refNumber;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(PurchaseTopup req) {
        Builder builder = new Builder();
        builder.userID = req.userID;
        builder.billID = req.billID;
        builder.respCode = req.respCode;
        builder.respDesc = req.respDesc;
        builder.billAmount = req.billAmount;
        builder.rrn = req.rrn;
        builder.refNumber = req.refNumber;
        return builder;
    }

    public static final class Builder{
        private String userID;
        private String billID;
        private Integer respCode;
        private String respDesc;
        private Integer billAmount;
        private String rrn;
        private String refNumber;

        public Builder userID(String var){
           userID = var;
           return this;
        }

        public Builder billID(String var){
            billID = var;
            return this;
        }

        public Builder billAmount(Integer var){
            billAmount = var;
            return this;
        }

        public Builder rrn(String var){
            rrn = var;
            return this;
        }

        public Builder respCode(Integer var){
            respCode = var;
            return this;
        }

        public Builder respDesc(String var){
            respDesc = var;
            return this;
        }

        public Builder refNumber(String var){
            refNumber = var;
            return this;
        }

        private Builder() {
        }

        public PurchaseTopup build() {
            return new PurchaseTopup(this);
        }
    }
}
