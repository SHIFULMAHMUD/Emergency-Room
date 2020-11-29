package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Ambulance {
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("id")
    private String id;
    @SerializedName("time")
    private String time;
    @SerializedName("token")
    private String token;
    @SerializedName("passenger_cell")
    private String passenger_cell;
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
    @SerializedName("facility")
    private String facility;
    @SerializedName("image")
    private String image;
    @SerializedName("request")
    private String request;
    @SerializedName("status")
    private String status;
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

    public String getFacility() {
        return facility;
    }

    public String getImage() {
        return image;
    }

    public String getRequest() {
        return request;
    }

    public String getStatus() {
        return status;
    }

    public String getPassenger_cell() {
        return passenger_cell;
    }

    public String getTime() {
        return time;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}