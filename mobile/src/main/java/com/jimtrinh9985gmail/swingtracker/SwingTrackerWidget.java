package com.jimtrinh9985gmail.swingtracker;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Created by Kimo on 6/5/2016.
 */
public class SwingTrackerWidget extends AppWidgetProvider {

    public final String LOG_TAG = SwingTrackerWidget.class.getSimpleName();

    private String forehand, backhand, overhead;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        SharedPreferences forehands = context.getSharedPreferences
                ("com.swingtracker.FOREHAND", Context.MODE_PRIVATE);
        forehand = String.valueOf(forehands.getInt("com.swingtracker.FOREHAND", 0));

        SharedPreferences backhands = context.getSharedPreferences
                ("com.swingtracker.BACKHAND", Context.MODE_PRIVATE);
        backhand = String.valueOf(backhands.getInt("com.swingtracker.BACKHAND", 0));

        SharedPreferences overheads = context.getSharedPreferences
                ("com.swingtracker.OVERHEAD", Context.MODE_PRIVATE);
        overhead = String.valueOf(overheads.getInt("com.swingtracker.OVERHEAD", 0));

        for (int i = 0; i < count; i++) {

            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.forehand_count, forehand);
            remoteViews.setTextViewText(R.id.backhand_count, backhand);
            remoteViews.setTextViewText(R.id.overhead_count, overhead);

            Intent intent = new Intent(context, SwingTrackerWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
