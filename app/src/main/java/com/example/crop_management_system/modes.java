package com.example.crop_management_system;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class modes extends AppCompatActivity {

    private static final String TAG = "f_temp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modes);

        Button Wheat = findViewById(R.id.Kviečiai);
        Button Rye = findViewById(R.id.Rugiai);
        Button Barley = findViewById(R.id.Mieziai);
        Button Corn = findViewById(R.id.Kukuruzai);

            EditText coldTempText = findViewById(R.id.f_temp_coldLimitNumberDecimalM);
            EditText hotTempText = findViewById(R.id.f_temp_heatLimitNumberDecimalM);
            EditText coldHumText = findViewById(R.id.hum_coldLimitNumberDecimalM);
            EditText hotHumText = findViewById(R.id.hum_heatLimitNumberDecimalM);


            Wheat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String rawTempValue1 = "20";
                    String rawTempValue2 = "30";
                    String rawHumValue1 = "400";
                    String rawHumValue2 = "800";

                    coldTempText.setText(rawTempValue1);
                    hotTempText.setText(rawTempValue2);
                    coldHumText.setText(rawHumValue1);
                    hotHumText.setText(rawHumValue2);
                }
            });

        Rye.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rawTempValue1 = "20";
                String rawTempValue2 = "30";
                String rawHumValue1 = "800";
                String rawHumValue2 = "1000";

                coldTempText.setText(rawTempValue1);
                hotTempText.setText(rawTempValue2);
                coldHumText.setText(rawHumValue1);
                hotHumText.setText(rawHumValue2);

            }
        });

        Barley.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rawTempValue1 = "20";
                String rawTempValue2 = "30";
                String rawHumValue1 = "600";
                String rawHumValue2 = "800";

                coldTempText.setText(rawTempValue1);
                hotTempText.setText(rawTempValue2);
                coldHumText.setText(rawHumValue1);
                hotHumText.setText(rawHumValue2);
            }
        });

        Corn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rawTempValue1 = "20";
                String rawTempValue2 = "30";
                String rawHumValue1 = "400";
                String rawHumValue2 = "700";

                coldTempText.setText(rawTempValue1);
                hotTempText.setText(rawTempValue2);
                coldHumText.setText(rawHumValue1);
                hotHumText.setText(rawHumValue2);

            }
        });
        }

        static boolean sanityCheck(float value) {
            if (value > 85 || value < -10)
                return true;
            return false;
        }

        private void setWeatherTempLimit(float tempValue1, float tempValue2) {
            float weather_tmp;

            if (tempValue1 > tempValue2) {
                weather_tmp = tempValue2;
                tempValue2 = tempValue1;
                tempValue1 = weather_tmp;
            }
            new modes.setWeatherTempLimitTask().execute(Tools.postWeathertRestURL, String.valueOf(tempValue1),
                    String.valueOf(tempValue2));
        }

        private class setWeatherTempLimitTask extends AsyncTask<String, Void, WeatherTemperature> {
            @Override
            protected WeatherTemperature doInBackground(String... strings) {
                String RestURL = strings[0];
                Float tempValue1 = Float.parseFloat(strings[1]);
                Float tempValue2 = Float.parseFloat(strings[2]);

                try {
                    DataAPI.setWeatherTempLimit(RestURL, tempValue1, tempValue2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
    private void setSoilHumLimit(int humValue1, int humValue2) {
        int hum_tmp;

        if (humValue1 > humValue2) {
            hum_tmp = humValue2;
            humValue2 = humValue1;
            humValue1 = hum_tmp;
        }
        new modes.setSoilHumLimitTask().execute(Tools.postSoilhRestURL, String.valueOf(humValue1),
                String.valueOf(humValue2));
    }

    private class setSoilHumLimitTask extends AsyncTask<String, Void, SoilHumidity> {
        @Override
        protected SoilHumidity doInBackground(String... strings) {
            String RestURL = strings[0];
            int humValue1 = Integer.parseInt(strings[1]);
            int humValue2 = Integer.parseInt(strings[2]);

            try {
                DataAPI.setSoilHumLimit(RestURL, humValue1, humValue2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}