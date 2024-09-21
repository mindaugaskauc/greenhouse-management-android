package com.example.crop_management_system;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class stat extends AppCompatActivity {

    LineChart lineChart;
    private float xInterval = 5f;
    private float lastX = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        //getTemperature();
        getSoilHumidity();
    }

    private void CreateChart(List<SoilHumidityEntry> result) {
        lineChart = findViewById(R.id.lineChart);

        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(result), null);

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        lineChart.getDescription().setEnabled(false);
        lineChart.setNoDataText("Data not Available");
        lineChart.setVisibleXRangeMaximum(5);
        lineChart.setDragEnabled(true);
        lineChart.invalidate();

        LineData lineData = new LineData(iLineDataSets);

        lineChart.setData(lineData);
    }

    private ArrayList<Entry> lineChartDataSet(List<SoilHumidityEntry> result) {
        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        for (int x = 0; x < result.size(); x++) {
            SoilHumidityEntry hum = result.get(x);
            dataSet.add(new Entry(x, hum.getValue()));
        }
        return dataSet;
    }

    private void getSoilHumidity() {
        new getSoilHumidityTask().execute(Tools.getSoilhRestAllURL, null, null);
    }

    private class getSoilHumidityTask extends AsyncTask<String, Void, List<SoilHumidityEntry>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<SoilHumidityEntry> doInBackground(String... str_param) {
            String RestURL = str_param[0];

            List<SoilHumidityEntry> hum = null;
            try {
                hum = DataAPI.getSoilHumidities(RestURL);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return hum;
        }

        protected void onPostExecute(List<SoilHumidityEntry> result) {
            CreateChart(result);
        }
    }
}