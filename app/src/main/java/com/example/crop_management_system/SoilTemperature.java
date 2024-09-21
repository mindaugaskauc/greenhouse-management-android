package com.example.crop_management_system;

public class SoilTemperature {

    public String soil_tmp;

    public SoilTemperature() {
    }

    public SoilTemperature(String soilTemperature) {
        soil_tmp = soilTemperature;
    }

    public String toString() {
        return soil_tmp + " ";
    }
}
