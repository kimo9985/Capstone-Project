package com.jimtrinh9985gmail.swingtracker;

import android.app.Application;

import com.google.android.gms.analytics.Tracker;

/**
 * Created by Kimo on 6/20/2016.
 */
public class Analytics extends Application {

    public final String LOG_TAG = Analytics.class.getSimpleName();

    private static Analytics mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

    public static synchronized Analytics getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }
}
