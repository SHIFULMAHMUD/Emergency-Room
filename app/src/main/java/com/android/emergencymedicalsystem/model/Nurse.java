package com.android.emergencymedicalsystem.model;

import com.google.gson.annotations.SerializedName;

public class Nurse {
    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("hospital")
    private String hospital;
    public Nurse() {
    }

    public Nurse(String name, String cell, String hospital) {
        this.name = name;
        this.cell = cell;
        this.hospital = hospital;
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
}
