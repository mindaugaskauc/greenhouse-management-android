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


public class s_temp extends AppCompatActivity {
    private static final String TAG = "s_temp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_temp);

        Button setSoilTempLimit = findViewById(R.id.button_set_s_temp);


        EditText coldText = findViewById(R.id.s_temp_coldLimitNumberDecimal);
        EditText hotText = findViewById(R.id.s_temp_heatLimitNumberDecimal);

        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                getSoilTemperature();
                handler.postDelayed(this, delay);
            }
        }, delay);

        setSoilTempLimit.setOnClickListener(new View.OnClickListener() {
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

                    setSoilTempLimit(value1, value2);
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

    private void showSoilTemperature(SoilTemperature soilTemp) {
        TextView txtw = (TextView) findViewById(R.id.s_temp);
        String temperature = soilTemp.soil_tmp != null ? soilTemp.soil_tmp : "-";
        txtw.setText(temperature);
    }

    private void getSoilTemperature() {
        new getSoilTemperatureTask().execute(Tools.getSoiltRestURL, null, null);
    }

    private void setSoilTempLimit(float value1, float value2) {
        float soil_tmp;

        if (value1 > value2) {
            soil_tmp = value2;
            value2 = value1;
            value1 = soil_tmp;
        }
        new setSoilTempLimitTask().execute(Tools.postSoiltRestURL, String.valueOf(value1),
                String.valueOf(value2));
    }

    private class getSoilTemperatureTask extends AsyncTask<String, Void, SoilTemperature> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected SoilTemperature doInBackground(String... str_param) {
            String RestURL = str_param[0];

            SoilTemperature soilTemp = new SoilTemperature();
            try {
                soilTemp = DataAPI.getSoilTemperature(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return soilTemp;
        }

        protected void onPostExecute(SoilTemperature result) {
            showSoilTemperature(result);
        }
    }

    private class setSoilTempLimitTask extends AsyncTask<String, Void, SoilTemperature> {
        @Override
        protected SoilTemperature doInBackground(String... strings) {
            String RestURL = strings[0];
            Float tempValue1 = Float.parseFloat(strings[1]);
            Float tempValue2 = Float.parseFloat(strings[2]);

            try {
                DataAPI.setSoilTempLimit(RestURL, tempValue1, tempValue2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}