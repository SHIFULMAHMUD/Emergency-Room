package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Sample {
    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("address")
    private String address;
    @SerializedName("blood")
    private String blood;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private String age;
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

    public String getAddress() {
        return address;
    }

    public String getBlood() {
        return blood;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }
}
