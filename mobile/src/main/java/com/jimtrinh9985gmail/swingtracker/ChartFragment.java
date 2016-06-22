package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by Kimo on 5/20/2016.
 */
public class ChartFragment extends Fragment {

    public final String LOG_TAG = ChartFragment.class.getSimpleName();

    public static final String SP_KEY_FOREHAND = "com.swingtracker.FOREHAND";
    public static final String SP_KEY_BACKHAND = "com.swingtracker.BACKHAND";
    public static final String SP_KEY_OVERHEAD = "com.swingtracker.OVERHEAD";

    public static int cForehand, cBackhand, cOverhead;
    private BarChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chart_fragment, container, false);

        SharedPreferences forehands = this.getActivity().getSharedPreferences
                ("com.swingtracker.FOREHAND", Context.MODE_PRIVATE);
        cForehand = forehands.getInt("com.swingtracker.FOREHAND", 0);

        SharedPreferences backhands = this.getActivity().getSharedPreferences
                ("com.swingtracker.BACKHAND", Context.MODE_PRIVATE);
        cBackhand = backhands.getInt("com.swingtracker.BACKHAND", 0);

        SharedPreferences overheads = this.getActivity().getSharedPreferences
                ("com.swingtracker.OVERHEAD", Context.MODE_PRIVATE);
        cOverhead = overheads.getInt("com.swingtracker.OVERHEAD", 0);

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
        yValues.add(new BarEntry(cForehand, 0));
        yValues.add(new BarEntry(cBackhand, 1));
        yValues.add(new BarEntry(cOverhead, 2));

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
