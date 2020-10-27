package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("trxid")
    private String trxid;
    @SerializedName("address")
    private String address;
    @SerializedName("amount")
    private String amount;
    @SerializedName("comment")
    private String comment;
    @SerializedName("cardnumber")
    private String cardnumber;
    @SerializedName("cardcvc")
    private String cardcvc;
    @SerializedName("cardholdername")
    private String cardholdername;
    @SerializedName("cardexpirydate")
    private String cardexpirydate;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getName() {
        return name;
    }

    public String getCell() {
        return cell;
    }

    public String getTrxid() {
        return trxid;
    }

    public String getAddress() {
        return address;
    }

    public String getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public String getCardcvc() {
        return cardcvc;
    }

    public String getCardholdername() {
        return cardholdername;
    }

    public String getCardexpirydate() {
        return cardexpirydate;
    }
}
