package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Nurse {
    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("hospital")
    private String hospital;
    @SerializedName("type")
    private String type;
    @SerializedName("address")
    private String address;
    @SerializedName("day")
    private String day;
    @SerializedName("time")
    private String time;
    public Nurse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
