package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kimo on 6/5/2016.
 */
public class SwingDBHelper extends SQLiteOpenHelper {

    // Database info //
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "swingTracker";
    private static final String DATABASE_TABLE = "swings";

    // Database Table Columns //
    private static final String KEY_ID = "_id";
    private static final String KEY_DATE = "save_date";
    private static final String KEY_FOREHAND = "save_forehand";
    private static final String KEY_BACKHAND = "save_backhand";
    private static final String KEY_OVERHEAD = "save_overhead";

    private static final String CREATE_TRACKER_TABLE = "CREATE TABLE " + DATABASE_TABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_DATE + " TEXT, "
            + KEY_FOREHAND + " INTEGER, "
            + KEY_BACKHAND + " INTEGER, "
            + KEY_OVERHEAD + " INTEGER)";
    private static final String DROP_TRACKER_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;

    public SwingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TRACKER_TABLE);
        } catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TRACKER_TABLE);
            onCreate(db);
        } catch (SQLException e) {
        }
    }



}
