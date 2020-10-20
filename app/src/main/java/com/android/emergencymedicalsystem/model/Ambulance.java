package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Ambulance {


    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("password")
    private String password;
    @SerializedName("division")
    private String division;
    @SerializedName("area")
    private String area;
    @SerializedName("type")
    private String type;
    @SerializedName("fair")
    private String fair;
    @SerializedName("v_reg_no")
    private String v_reg_no;

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

    public String getPassword() {
        return password;
    }

    public String getDivision() {
        return division;
    }

    public String getArea() {
        return area;
    }

    public String getType() {
        return type;
    }

    public String getFair() {
        return fair;
    }

    public String getV_reg_no() {
        return v_reg_no;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }


}