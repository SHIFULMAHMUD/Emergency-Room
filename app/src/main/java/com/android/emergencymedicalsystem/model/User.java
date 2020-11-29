package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("date")
    private String date;
    @SerializedName("password")
    private String password;
    @SerializedName("division")
    private String division;
    @SerializedName("area")
    private String area;
    @SerializedName("blood")
    private String blood;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
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

    public String getBlood() {
        return blood;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}