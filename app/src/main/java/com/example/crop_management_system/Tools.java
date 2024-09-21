package com.example.crop_management_system;

public class Tools {
    //Authentication
    public static String postRestRegisterURL = "http://10.0.2.5:3000/registerm";
    public static String postRestLoginURL = "http://10.0.2.5:3000/loginm";
    public static String postRestSendPasswordLinkUrl = "http://10.0.2.5:3000/sendPasswordLinkm";

    //Weather temperature
    public static String getWeathertRestAllURL = "http://10.0.2.5:3000/weathert/all";
    public static String getWeathertRestURL = "http://10.0.2.5:3000/weathert";
    public static String postWeathertRestURL = "http://10.0.2.5:3000/weathert";

    //Soil temperature
    public static String getSoiltRestAllURL = "http://10.0.2.5:3000/soilt/all";
    public static String getSoiltRestURL = "http://10.0.2.5:3000/soilt";
    public static String postSoiltRestURL = "http://10.0.2.5:3000/soilt";

    //Soil humidity
    public static String getSoilhRestAllURL = "http://10.0.2.5:3000/soilh/all";
    public static String getSoilhRestURL = "http://10.0.2.5:3000/soilh";
    public static String postSoilhRestURL = "http://10.0.2.5:3000/soilh";

    // Soil pH
    public static String getSoilphRestAllURL = "http://10.0.2.5:3000/soilph/all";
    public static String getSoilphRestURL = "http://10.0.2.5:3000/soilph";
    public static String postSoilphRestURL = "http://10.0.2.5:3000/soilph";

    //Watering
    public static String postWateringOnURL = "http://10.0.2.5:3000/wateringOnM";
    public static String postWateringOffURL = "http://10.0.2.5:3000/wateringOffM";
    //Window control
    public static String postWindowOnURL = "http://10.0.2.5:3000/windowOnM";
    public static String postWindowOffURL = "http://10.0.2.5:3000/windowOffM";
}
