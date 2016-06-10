package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Kimo on 6/8/2016.
 */
public class UtilityProfile {

    public final String LOG_TAG = UtilityProfile.class.getSimpleName();

    public static final String SP_KEY_IMAGE = "com.swingtracker.IMAGE";
    public static final String SP_KEY_NAME = "com.swingtracker.NAME";
    public static final String SP_KEY_HEIGHT = "com.swingtracker.HEIGHT";
    public static final String SP_KEY_WEIGHT = "com.swingtracker.WEIGHT";
    public static final String SP_KEY_GRIP1 = "com.swingtracker.GRIP1";
    public static final String SP_KEY_RACKET = "com.swingtracker.RACKET";

    public static void saveImageUri(Context context, String imageUri) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.IMAGE",
                Context.MODE_PRIVATE);
        prefs.edit().putString(SP_KEY_IMAGE, imageUri).apply();
    }

    public static String getPrefImageUri(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.IMAGE",
                Context.MODE_PRIVATE);
        return prefs.getString(SP_KEY_IMAGE, "null");
    }

    public static void savePrefProfileName(Context context, String profileName) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.NAME",
                Context.MODE_PRIVATE);
        prefs.edit().putString(SP_KEY_NAME, profileName).apply();
    }

    public static String getPrefProfileName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.NAME",
                Context.MODE_PRIVATE);
        return prefs.getString(SP_KEY_NAME, "Name: ");
    }

    public static void savePrefProfileHeight(Context context, String profileHeight) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.HEIGHT",
                Context.MODE_PRIVATE);
        prefs.edit().putString(SP_KEY_HEIGHT, profileHeight).apply();
    }

    public static String getPrefProfileHeight(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.HEIGHT",
                Context.MODE_PRIVATE);
        return prefs.getString(SP_KEY_HEIGHT, "Height: ");
    }

    public static void savePrefProfileWeight(Context context, String profileWeight) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.WEIGHT",
                Context.MODE_PRIVATE);
        prefs.edit().putString(SP_KEY_WEIGHT, profileWeight).apply();
    }

    public static String getPrefProfileWeight(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.WEIGHT",
                Context.MODE_PRIVATE);
        return prefs.getString(SP_KEY_WEIGHT, "Weight: ");
    }

    public static void savePrefProfileGrip1(Context context, String profileGrip1) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.GRIP1",
                Context.MODE_PRIVATE);
        prefs.edit().putString(SP_KEY_GRIP1, profileGrip1).apply();
    }

    public static String getPrefProfileGrip1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.GRIP1",
                Context.MODE_PRIVATE);
        return prefs.getString(SP_KEY_GRIP1, "Grip: ");
    }

    public static void savePrefProfileRacket(Context context, String profileRacket) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.RACKET",
                Context.MODE_PRIVATE);
        prefs.edit().putString(SP_KEY_RACKET, profileRacket).apply();
    }

    public static String getPrefProfileRacket(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.swingtracker.RACKET",
                Context.MODE_PRIVATE);
        return prefs.getString(SP_KEY_RACKET, "Racket: ");
    }
}
