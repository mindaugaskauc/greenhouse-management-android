package com.example.crop_management_system;

public class WeatherTemperature {

    public String weather_tmp;

    public WeatherTemperature() {
    }

    public WeatherTemperature(String weatherTemperature) { weather_tmp = weatherTemperature;} //vietoj fieldTemperature

    public String toString() {
        return weather_tmp + " ";
    }
}
