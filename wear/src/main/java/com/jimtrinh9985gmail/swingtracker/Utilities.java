package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Kimo on 5/15/2016.
 */
public class Utilities {

    public final String LOG_TAG = Utilities.class.getSimpleName();

    public static final String SP_KEY_FOREHAND = "com.swingtracker.FOREHAND";
    public static final String SP_KEY_BACKHAND = "com.swingtracker.BACKHAND";
    public static final String SP_KEY_OVERHEAD = "com.swingtracker.OVERHEAD";
    public static final String SP_KEY_GRIP = "com.swingtracker.GRIP";

    public static final String PROFILE_NAME_KEY = "com.swingtracker.NAME";
    public static final String PROFILE_HEIGHT_KEY = "com.swingtracker.HEIGHT";
    public static final String PROFILE_WEIGHT_KEY = "com.swingtracker.WEIGHT";
    public static final String PROFILE_RACKET_KEY = "com.swingtracker.RACKET";

    public static void savePrefForehand(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.FOREHAND",
                Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_FOREHAND).apply();
        } else {
            prefs.edit().putInt(SP_KEY_FOREHAND, value).apply();
        }
    }

    public static int getPrefForehand(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.FOREHAND",
                Context.MODE_PRIVATE);
        return prefs.getInt(SP_KEY_FOREHAND, 0);
    }

    public static void savePrefBackhand(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.BACKHAND",
                Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_BACKHAND).apply();
        } else {
            prefs.edit().putInt(SP_KEY_BACKHAND, value).apply();
        }
    }

    public static int getPrefBackhand(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.BACKHAND",
                Context.MODE_PRIVATE);
        return prefs.getInt(SP_KEY_BACKHAND, 0);
    }

    public static void savePrefOverhead(Context context, int value) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.OVERHEAD",
                Context.MODE_PRIVATE);
        if (value < 0) {
            prefs.edit().remove(SP_KEY_OVERHEAD).apply();
        } else {
            prefs.edit().putInt(SP_KEY_OVERHEAD, value).apply();
        }
    }

    public static int getPrefOverhead(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.OVERHEAD",
                Context.MODE_PRIVATE);
        return prefs.getInt(SP_KEY_OVERHEAD, 0);
    }

    public static void savePrefGrip(Context context, boolean grip) {
            SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.GRIP",
                    Context.MODE_PRIVATE);
            prefs.edit().putBoolean(SP_KEY_GRIP, grip).apply();
    }

    public static boolean getPrefGrip(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.GRIP",
                Context.MODE_PRIVATE);
        return prefs.getBoolean(SP_KEY_GRIP, false);
    }

    public static void saveProfileName(Context context, String profileName) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.NAME",
                Context.MODE_PRIVATE);
        prefs.edit().putString(PROFILE_NAME_KEY, profileName).apply();
    }

    public static String getProfileName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.NAME",
                Context.MODE_PRIVATE);
        return prefs.getString(PROFILE_NAME_KEY, "Profile");
    }

    public static void saveProfileHeight(Context context, String profileHeight) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.HEIGHT",
                Context.MODE_PRIVATE);
        prefs.edit().putString(PROFILE_HEIGHT_KEY, profileHeight).apply();
    }

    public static String getProfileHeight(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.HEIGHT",
                Context.MODE_PRIVATE);
        return prefs.getString(PROFILE_HEIGHT_KEY, "Height: ");
    }

    public static void saveProfileWeight(Context context, String profileWeight) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.WEIGHT",
                Context.MODE_PRIVATE);
        prefs.edit().putString(PROFILE_WEIGHT_KEY, profileWeight).apply();
    }

    public static String getProfileWeight(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.WEIGHT",
                Context.MODE_PRIVATE);
        return prefs.getString(PROFILE_WEIGHT_KEY, "Weight: ");
    }

    public static void saveProfileRacket(Context context, String profileRacket) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.RACKET",
                Context.MODE_PRIVATE);
        prefs.edit().putString(PROFILE_RACKET_KEY, profileRacket).apply();
    }

    public static String getProfileRacket(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.RACKET",
                Context.MODE_PRIVATE);
        return prefs.getString(PROFILE_RACKET_KEY, "-");
    }
}
