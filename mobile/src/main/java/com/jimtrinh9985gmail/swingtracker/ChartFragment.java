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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;

import wearprefs.WearPrefs;

/**
 * Created by Kimo on 5/20/2016.
 */
public class ChartFragment extends Fragment {

    public final String LOG_TAG = ChartFragment.class.getSimpleName();

    private static int forehand, backhand, overhead;
    //private SharedPreferences preferences;

    private PieChart chart;

    private int[] yData = { forehand, backhand, overhead };
    private String[] xData = { "ForeHand", "BackHand", "OverHead" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        chart = (PieChart) view.findViewById(R.id.chart);
        chart.setDescription("Swing Tracker");

        // Donut Chart //
        chart.setDrawHoleEnabled(true);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setHoleColor(Color.BLACK);

        // Enable rotation //
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);

        // Set a chart value selected listener //
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // Add Data //
        addData();

        // Customize Legends //
        Legend legend = chart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

        chart.invalidate();

        return view;
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // Create pie chart set //
        final PieDataSet dataSet = new PieDataSet(yVals1, "Type of Swing");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // Add colors //

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        // Instantiate pie data object now //
        final PieData data = new PieData(xVals, dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        chart.setData(data);
        Log.d(LOG_TAG, "addData: " + data);
        Log.d(LOG_TAG, "addData: " + yData);
        // Undo all highlights //
        chart.highlightValues(null);

        // Update pie chart //
        chart.invalidate();
        chart.refreshDrawableState();

        Log.d(LOG_TAG, "Invalidate Chart v2!!!");
    }
}
