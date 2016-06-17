package com.jimtrinh9985gmail.swingtracker;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Created by Kimo on 6/5/2016.
 */
public class SwingTrackerWidget extends AppWidgetProvider {

    public final String LOG_TAG = SwingTrackerWidget.class.getSimpleName();

    String forehand = String.valueOf(ChartFragment.cForehand);
    String backhand = String.valueOf(ChartFragment.cBackhand);
    String overhead = String.valueOf(ChartFragment.cOverhead);

    Calendar calendar = Calendar.getInstance();
    String mDate = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" +
            calendar.get(Calendar.DATE);

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {

            int widgetId = appWidgetIds[i];

            Log.d(LOG_TAG, "Forehand: " + forehand);
            Log.d(LOG_TAG, "Backhand: " + backhand);
            Log.d(LOG_TAG, "Overhead: " + overhead);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.date, mDate);
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
