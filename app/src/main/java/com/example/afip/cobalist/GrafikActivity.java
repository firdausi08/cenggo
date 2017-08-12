package com.example.afip.cobalist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.example.afip.cobalist.model.Percobaan;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;

import java.util.ArrayList;
import java.util.List;

public class GrafikActivity extends AppCompatActivity {

  //  private LineChart chart;
    ArrayList<Percobaan> percobaanArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);
        percobaanArrayList = getIntent().getParcelableArrayListExtra("Percobaan");
        LineChart chart = (LineChart) findViewById(R.id.chart);
        LineChart chart2 = (LineChart) findViewById(R.id.chart2);



        List<Entry> entries = new ArrayList<Entry>();
        int i=1;

        for(Percobaan data : percobaanArrayList) {

            // turn your data into Entry objects
            entries.add(new Entry(i, (float) data.getIndeks_pencemaran()));
            i++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Indeks pencemaran"); // add entries to dataset
        dataSet.setColor(Color.RED);
     //   dataSet.setValueTextColor(...); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh



        List<Entry> entries2 = new ArrayList<Entry>();
        int j=1;

        for(Percobaan data2 : percobaanArrayList) {

            // turn your data into Entry objects
            entries2.add(new Entry(j, (float) data2.getProbabilitas()));
            j++;
        }
        LineDataSet dataSet2 = new LineDataSet(entries2, "Probabilitas"); // add entries to dataset
        dataSet2.setColor(Color.BLUE);
        //   dataSet.setValueTextColor(...); // styling, ...

        LineData lineData2 = new LineData(dataSet2);
        chart2.setData(lineData2);
        chart2.invalidate(); // refresh
    }



}
