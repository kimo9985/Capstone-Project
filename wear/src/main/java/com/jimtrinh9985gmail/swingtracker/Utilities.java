package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kimo on 5/15/2016.
 */
public class Utilities {

    private static final String SP_KEY_FOREHAND = "forehandCount";
    private static final String SP_KEY_BACKHAND = "backhandCount";
    private static final String SP_KEY_OVERHEAD = "overheadCount";
    private static final String SP_KEY_GRIP = "grip";

    public static void savePrefForehand(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("forehandCount", Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_FOREHAND).apply();
        } else {
            prefs.edit().putInt(SP_KEY_FOREHAND, value).apply();
        }
    }

    public static void savePrefBackhand(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("backhandCount", Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_BACKHAND).apply();
        } else {
            prefs.edit().putInt(SP_KEY_BACKHAND, value).apply();
        }
    }

    public static void savePrefOverhead(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("overheadCount", Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_OVERHEAD).apply();
        } else {
            prefs.edit().putInt(SP_KEY_OVERHEAD, value).apply();
        }
    }

    public static void savePrefGrip(Context context, boolean grip) {
        SharedPreferences prefs = context.getSharedPreferences("grip", Context.MODE_PRIVATE);
    }


}
