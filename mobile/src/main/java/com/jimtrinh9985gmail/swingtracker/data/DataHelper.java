package com.jimtrinh9985gmail.swingtracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jimtrinh9985gmail.swingtracker.data.DataContract.WorkoutEntry;

/**
 * Created by Kimo on 7/5/2016.
 */
public class DataHelper extends SQLiteOpenHelper {

    public final String LOG_TAG = DataHelper.class.getSimpleName();

    // Database Info //
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myWorkoutDatabase.db";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_WORKOUT_TABLE = "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " (" +
                WorkoutEntry.KEY_ID + " INTEGER PRIMARY KEY," +
                WorkoutEntry.KEY_FOREHAND + " INTEGER, " +
                WorkoutEntry.KEY_BACKHAND + " INTEGER, " +
                WorkoutEntry.KEY_OVERHEAD + " INTEGER, " +
                WorkoutEntry.KEY_DATE + " TEXT " +
                " );";

        db.execSQL(CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME);
        onCreate(db);
    }
}
