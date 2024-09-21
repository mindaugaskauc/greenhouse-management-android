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


public class s_ph extends AppCompatActivity {
    private static final String TAG = "s_pH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ph);

        Button setSoilPhLimit = findViewById(R.id.button_set_s_ph);


        EditText lowText = findViewById(R.id.s_ph_lowLimitNumberDecimal);
        EditText highText = findViewById(R.id.s_ph_highLimitNumberDecimal);

        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        handler.postDelayed(new Runnable() {
            public void run() {
                getSoilPh();
                handler.postDelayed(this, delay);
            }
        }, delay);

        setSoilPhLimit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rawValue1 = lowText.getText().toString();
                String rawValue2 = highText.getText().toString();
                try {
                    float value1 = Float.parseFloat(rawValue1);
                    float value2 = Float.parseFloat(rawValue2);
                    if (sanityCheck(value1) || sanityCheck(value2) || value1 > value2) {
                        Toast.makeText(getApplicationContext(), "Netinkamas limitas",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    setSoilPhLimit(value1, value2);
                } catch (Exception e) {
                    Log.e("exception", e.getMessage());
                    return;
                }
            }
        });

    }

    static boolean sanityCheck(float value) {
        if (value > 9 || value < 3)
            return true;
        return false;
    }

    private void showSoilPh(SoilPh soilP) { //Vietoj showSoilTemperature ir SoilTemperature soilTemp
        TextView txtw = (TextView) findViewById(R.id.s_ph); //vietoj TextView txtw = (TextView) findViewById(R.id.field_s_temp);
        String ph = soilP.soil_pH != null ? soilP.soil_pH : "-"; //vietoj String temperature, vietoj soilTemp ir soil_tmp
        txtw.setText(ph); //vietoj temperature
    }

    private void getSoilPh() { //Vietoje getSoilTemperature()
        new getSoilPhTask().execute(Tools.getSoilphRestURL, null, null); //Vietoj new getSoilTemperatureTask().execute(Tools.getSoiltRestURL, null, null);
    }

    private void setSoilPhLimit(float value1, float value2) { //Vietoj setSoilTempLimit
        float soil_ph; //vietoj soil_tmp

        if (value1 > value2) {
            soil_ph = value2; //vietoj soil_tmp
            value2 = value1;
            value1 = soil_ph; //vietoj soil_tmp
        }
        new setSoilPhLimitTask().execute(Tools.postSoilphRestURL, String.valueOf(value1), //Vietoj setSoilTempLimitTask() ir postSoiltRestURL
                String.valueOf(value2));
    }

    private class getSoilPhTask extends AsyncTask<String, Void, SoilPh> { //Vietoj getSoilTemperatureTask ir SoilTemperature
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected SoilPh doInBackground(String... str_param) { //Vietoj SoilTemperature
            String RestURL = str_param[0];

            SoilPh soilP = new SoilPh(); //Vietoj SoilTemperature soilTemp = new SoilTemperature()
            try {
                soilP = DataAPI.getSoilPh(RestURL); //vietoj soilTemp = DataAPI.getSoilTemperature(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return soilP; //Vietoj soilTemp
        }

        protected void onPostExecute(SoilPh result) { //vietoj SoilTemperature result
            showSoilPh(result); //Vietoj showSoilTemperature(result)
        }
    }

    private class setSoilPhLimitTask extends AsyncTask<String, Void, SoilPh> { //Vietoj  private class setSoilTempLimitTask extends AsyncTask<String, Void, SoilTemperature> {
        @Override
        protected SoilPh doInBackground(String... strings) { //Vietoj SoilTemperature
            String RestURL = strings[0];
            Float phValue1 = Float.parseFloat(strings[1]); //vietoj tempValue1
            Float phValue2 = Float.parseFloat(strings[2]); //vietoj tempValue2

            try {
                DataAPI.setSoilPhLimit(RestURL, phValue1, phValue2); //vietoj DataAPI.setSoilTempLimit(RestURL, tempValue1, tempValue2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}