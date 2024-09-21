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

public class hum extends AppCompatActivity {

    private static final String TAG = "hum";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hum);

        Button setSoilHumLimit = findViewById(R.id.button_set_hum);


        EditText coldText = findViewById(R.id.hum_lowLimitNumberDecimal);
        EditText hotText = findViewById(R.id.hum_highLimitNumberDecimal);

        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                getSoilHumidity();
                handler.postDelayed(this, delay);
            }
        }, delay);

        setSoilHumLimit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rawValue1 = coldText.getText().toString();
                String rawValue2 = hotText.getText().toString();
                try {
                    int value1 = Integer.parseInt(rawValue1);
                    int value2 = Integer.parseInt(rawValue2);
                    if (sanityCheck(value1) || sanityCheck(value2) || value1 > value2) {
                        Toast.makeText(getApplicationContext(), "Netinkamas limitas",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    setSoilHumLimit(value1, value2);
                } catch (Exception e) {
                    Log.e("exception", e.getMessage());
                    return;
                }
            }
        });

    }

    static boolean sanityCheck(float value) {
        if (value > 5000 || value < -10)
            return true;
        return false;
    }

    private void showSoilHumidity(SoilHumidity soilHum) {
        TextView txtw = (TextView) findViewById(R.id.s_hum);
        String soilHumidity = soilHum.soil_hum != null ? soilHum.soil_hum : "-";
        txtw.setText(soilHumidity);
    }

    private void getSoilHumidity() {
        new hum.getSoilHumidityTask().execute(Tools.getSoilhRestURL, null, null);
    }

    private void setSoilHumLimit(int value1, int value2) {
        int soil_hum;

        if (value1 > value2) {
            soil_hum = value2;
            value2 = value1;
            value1 = soil_hum;
        }
        new hum.setSoilHumLimitTask().execute(Tools.postSoilhRestURL, String.valueOf(value1),
                String.valueOf(value2));
    }

    private class getSoilHumidityTask extends AsyncTask<String, Void, SoilHumidity> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected SoilHumidity doInBackground(String... str_param) {
            String RestURL = str_param[0];

            SoilHumidity soilHum = new SoilHumidity();
            try {
                soilHum = DataAPI.getSoilHumidity(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return soilHum;
        }

        protected void onPostExecute(SoilHumidity result) {
            showSoilHumidity(result);
        }
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