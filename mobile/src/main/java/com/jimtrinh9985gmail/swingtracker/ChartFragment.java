package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import wearprefs.WearPrefs;

/**
 * Created by Kimo on 5/20/2016.
 */
public class ChartFragment extends Fragment {

    public final String LOG_TAG = ChartFragment.class.getSimpleName();

    private static int forehand, backhand, overhead;
    private BarChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chart_fragment, container, false);

        SharedPreferences forehands = this.getActivity().getSharedPreferences
                ("com.swingtracker.FOREHAND", Context.MODE_PRIVATE);
        forehand = forehands.getInt("com.swingtracker.FOREHAND", 0);

        SharedPreferences backhands = this.getActivity().getSharedPreferences
                ("com.swingtracker.BACKHAND", Context.MODE_PRIVATE);
        backhand = backhands.getInt("com.swingtracker.BACKHAND", 0);

        SharedPreferences overheads = this.getActivity().getSharedPreferences
                ("com.swingtracker.OVERHEAD", Context.MODE_PRIVATE);
        overhead = overheads.getInt("com.swingtracker.OVERHEAD", 0);

        Log.d(LOG_TAG, "Frag - Shared Preference Forehand: " + forehand);
        Log.d(LOG_TAG, "Frag - Shared Preference Backhand: " + backhand);
        Log.d(LOG_TAG, "Frag - Shared Preference Overhead: " + overhead);

        chart = (BarChart) view.findViewById(R.id.chart);

        // Chart Styling for BarChart //
        chart.setDescription("Swing Tracker");
        chart.setNoDataTextDescription("No Data");
        chart.setDescriptionColor(Color.BLACK);

        // X-Axis //
        XAxis xAxis = chart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setSpaceBetweenLabels(1);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Forehand");
        labels.add("Backhand");
        labels.add("Overhead");

        // Y-Axis //
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.RED);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setLabelCount(3, true);
        chart.getAxisRight().setEnabled(false);

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(forehand, 0));
        yValues.add(new BarEntry(backhand, 1));
        yValues.add(new BarEntry(overhead, 2));

        BarDataSet barDataSet = new BarDataSet(yValues, "# of Swings");
        barDataSet.setValueTextSize(12);

        // Set Data and Redraw //
        BarData data = new BarData(labels, barDataSet);
        data.setValueFormatter(new MyValueFormatter());
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();

        return view;
    }

}