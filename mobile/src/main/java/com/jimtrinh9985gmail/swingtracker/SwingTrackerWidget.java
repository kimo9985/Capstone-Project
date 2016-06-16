package com.jimtrinh9985gmail.swingtracker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by Kimo on 6/5/2016.
 */
public class SwingTrackerWidget extends AppWidgetProvider {

    public final String LOG_TAG = SwingTrackerWidget.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {

            int widgetId = appWidgetIds[i];

            String forehand = ChartFragment.SP_KEY_FOREHAND;
            String backhand = ChartFragment.SP_KEY_BACKHAND;
            String overhead = ChartFragment.SP_KEY_OVERHEAD;

            Log.d(LOG_TAG, "Widget forehand: " + forehand);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.forehand_count, forehand);
            remoteViews.setTextViewText(R.id.backhand_count, backhand);
            remoteViews.setTextViewText(R.id.overhead_count, overhead);

            Intent intent = new Intent(context, SwingTrackerWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }


    }
}
