package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Kimo on 5/15/2016.
 */
public class Utilities {

    public final String LOG_TAG = Utilities.class.getSimpleName();

    public static final String SP_KEY_FOREHAND = "forehandCount";
    public static final String SP_KEY_BACKHAND = "backhandCount";
    public static final String SP_KEY_OVERHEAD = "overheadCount";
    public static final String SP_KEY_GRIP = "grip";


    public static void savePrefForehand(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("forehandCount", Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_FOREHAND).apply();
        } else {
            prefs.edit().putInt(SP_KEY_FOREHAND, value).apply();
        }
    }

    public static int getPrefForehand(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("forehandCount", Context.MODE_PRIVATE);
        return prefs.getInt(SP_KEY_FOREHAND, 0);
    }

    public static void savePrefBackhand(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("backhandCount", Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_BACKHAND).apply();
        } else {
            prefs.edit().putInt(SP_KEY_BACKHAND, value).apply();
        }
    }

    public static int getPrefBackhand(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("backhandCount", Context.MODE_PRIVATE);
        return prefs.getInt(SP_KEY_BACKHAND, 0);
    }

    public static void savePrefOverhead(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("overheadCount", Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_OVERHEAD).apply();
        } else {
            prefs.edit().putInt(SP_KEY_OVERHEAD, value).apply();
        }
    }

    public static int getPrefOverhead(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("overheadCount", Context.MODE_PRIVATE);
        return prefs.getInt(SP_KEY_OVERHEAD, 0);
    }

    public static void savePrefGrip(Context context, boolean grip) {
        SharedPreferences prefs = context.getSharedPreferences("grip", Context.MODE_PRIVATE);
    }
}
