package com.example.crop_management_system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class DataAPI {
    static final String TAG = "DataAPI";

    /*------------------------Weather Temperature------------------------------------------*/
    public static List<WeatherTemperatureEntry> getTemperatures(String RestURL) throws Exception {
        String response = WebAPI.getWeatherTemperature(RestURL);

        if (response.isEmpty())
            return new ArrayList<WeatherTemperatureEntry>();

        List<WeatherTemperatureEntry> users = new Gson().fromJson(response,
                new TypeToken<List<WeatherTemperatureEntry>>() {}.getType());

        return users;
    }

    public static WeatherTemperature getWeatherTemperature(String RestURL) throws Exception {
        WeatherTemperature weather_temp = new WeatherTemperature();
        String response = WebAPI.getWeatherTemperature(RestURL);

        if (response.length() > 1) {
            Gson gson = new Gson();
            weather_temp = gson.fromJson(response, WeatherTemperature.class);
        }

        return weather_temp;
    }

    public static String setWeatherTempLimit(String RestURL, Float weatherTempValue1, Float weatherTempValue2)
            throws Exception {
        String response = WebAPI.setWeatherTempLimit(RestURL, weatherTempValue1, weatherTempValue2);
        return response;
    }
    /*--------------------------------------------------------------------------------------*/

   /*------------------------------Soil Temperature--------------------------------------------*/
    public static List<SoilTemperatureEntry> getSoilTemperatures(String RestURL) throws Exception {
        String response = WebAPI.getSoilTemperature(RestURL);

        if (response.isEmpty())
            return new ArrayList<SoilTemperatureEntry>();

        List<SoilTemperatureEntry> users = new Gson().fromJson(response,
                new TypeToken<List<SoilTemperatureEntry>>() {}.getType());

        return users;
    }

    public static SoilTemperature getSoilTemperature(String RestURL) throws Exception {
        SoilTemperature soil_temp = new SoilTemperature();
        String response = WebAPI.getSoilTemperature(RestURL);

        if (response.length() > 1) {
            Gson gson = new Gson();
            soil_temp = gson.fromJson(response, SoilTemperature.class);
        }

        return soil_temp;
    }

    public static String setSoilTempLimit(String RestURL, Float soilTempValue1, Float soilTempValue2)
            throws Exception {
        String response = WebAPI.setSoilTempLimit(RestURL, soilTempValue1, soilTempValue2);
        return response;
    }
    /*--------------------------------------------------------------------------------------*/

    /*---------------------------------Soil Humidity----------------------------------------*/
    public static List<SoilHumidityEntry> getSoilHumidities(String RestURL) throws Exception {
        String response = WebAPI.getSoilHumidity(RestURL);

        if (response.isEmpty())
            return new ArrayList<SoilHumidityEntry>();

        List<SoilHumidityEntry> users = new Gson().fromJson(response,
                new TypeToken<List<SoilHumidityEntry>>() {}.getType());

        return users;
    }

    public static SoilHumidity getSoilHumidity(String RestURL) throws Exception {
        SoilHumidity soil_hum = new SoilHumidity();
        String response = WebAPI.getSoilHumidity(RestURL);

        if (response.length() > 1) {
            Gson gson = new Gson();
            soil_hum = gson.fromJson(response, SoilHumidity.class);
        }

        return soil_hum;
    }

    public static String setSoilHumLimit(String RestURL, int soilHumValue1, int soilHumValue2)
            throws Exception {
        String response = WebAPI.setSoilHumLimit(RestURL, soilHumValue1, soilHumValue2);
        return response;
    }
    /*-------------------------------------------------------------------------------------------*/

    /*------------------------------Soil pH------------------------------------------------------*/
    public static List<SoilPhEntry> getSoilPhs(String RestURL) throws Exception { //Vietoj public static List<SoilTemperatureEntry> getSoilTemperatures(String RestURL) throws Exception {
        String response = WebAPI.getSoilPh(RestURL); //Vietoj String response = WebAPI.getSoilTemperature(RestURL)

        if (response.isEmpty())
            return new ArrayList<SoilPhEntry>(); //Vietoj  return new ArrayList<SoilTemperatureEntry>();

        List<SoilPhEntry> users = new Gson().fromJson(response, //Vietoj List<SoilTemperatureEntry> users = new Gson().fromJson(response,
                new TypeToken<List<SoilPhEntry>>() {}.getType()); //Vietoj new TypeToken<List<SoilTemperatureEntry>>() {}.getType());

        return users;
    }

    public static SoilPh getSoilPh(String RestURL) throws Exception { //Vietoj  public static SoilTemperature getSoilTemperature(String RestURL) throws Exception {
        SoilPh soil_ph = new SoilPh(); //Vietoj SoilTemperature soil_temp = new SoilTemperature();
        String response = WebAPI.getSoilPh(RestURL); //Vietoj String response = WebAPI.getSoilTemperature(RestURL);

        if (response.length() > 1) {
            Gson gson = new Gson();
            soil_ph = gson.fromJson(response, SoilPh.class); //Vietoj soil_temp = gson.fromJson(response, SoilTemperature.class);
        }

        return soil_ph; //Vietoj return soil_temp;
    }

    public static String setSoilPhLimit(String RestURL, Float soilPhValue1, Float soilPhValue2) //Vietoj public static String setSoilTempLimit(String RestURL, Float soilTempValue1, Float soilTempValue2)
            throws Exception {
        String response = WebAPI.setSoilPhLimit(RestURL, soilPhValue1, soilPhValue2); //Vietoj  String response = WebAPI.setSoilTempLimit(RestURL, soilTempValue1, soilTempValue2);
        return response;
    }

    /*-------------------------------------------------------------------------------------------*/
}



