package com.example.crop_management_system;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class w_temp extends AppCompatActivity {

    private static final String TAG = "w_temp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_temp);

        Button setFieldTempLimit = findViewById(R.id.button_set_w_temp);


        EditText coldText = findViewById(R.id.w_temp_coldLimitNumberDecimal);
        EditText hotText = findViewById(R.id.w_temp_heatLimitNumberDecimal);

        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                getWeatherTemperature();
                handler.postDelayed(this, delay);
            }
        }, delay);

        setFieldTempLimit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rawValue1 = coldText.getText().toString();
                String rawValue2 = hotText.getText().toString();
                try {
                    float value1 = Float.parseFloat(rawValue1);
                    float value2 = Float.parseFloat(rawValue2);
                    if (sanityCheck(value1) || sanityCheck(value2) || value1 > value2) {
                        Toast.makeText(getApplicationContext(), "Netinkamas limitas",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    setWeatherTempLimit(value1, value2);
                } catch (Exception e) {
                    Log.e("exception", e.getMessage());
                    return;
                }
            }
        });

    }

    static boolean sanityCheck(float value) {
        if (value > 32 || value < -1)
            return true;
        return false;
    }

    private void showWeatherTemperature(WeatherTemperature weatherTemp) {
        TextView txtw = (TextView) findViewById(R.id.weather_temp);
        String weatherTemperature = weatherTemp.weather_tmp != null ? weatherTemp.weather_tmp : "-";
        txtw.setText(weatherTemperature);
    }

    private void getWeatherTemperature() {
        new w_temp.getWeatherTemperatureTask().execute(Tools.getWeathertRestURL, null, null);
    }

    private void setWeatherTempLimit(float value1, float value2) {
        float weather_tmp;

        if (value1 > value2) {
            weather_tmp = value2;
            value2 = value1;
            value1 = weather_tmp;
        }
        new w_temp.setWeatherTempLimitTask().execute(Tools.postWeathertRestURL, String.valueOf(value1),
                String.valueOf(value2));
    }

    private class getWeatherTemperatureTask extends AsyncTask<String, Void, WeatherTemperature> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected WeatherTemperature doInBackground(String... str_param) {
            String RestURL = str_param[0];

            WeatherTemperature weatherTemp = new WeatherTemperature();
            try {
                weatherTemp = DataAPI.getWeatherTemperature(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return weatherTemp;
        }

        protected void onPostExecute(WeatherTemperature result) {
            showWeatherTemperature(result);
        }
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
}