package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Hospital {
    @SerializedName("id")
    private String id;
    @SerializedName("website")
    private String website;
    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("address")
    private String address;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public String getName() {
        return name;
    }

    public String getCell() {
        return cell;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public String getWebsite() {
        return website;
    }
}
