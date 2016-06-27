package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {

    public final String LOG_TAG = MyDatabase.class.getSimpleName();

    private Context mContext;

    // Database Info //
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myWorkoutDatabase";
    public static final String DATABASE_TABLE = "swingData";

    // Database Table Columns //
    private static final String KEY_ID = "_id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_FOREHAND = "forehand";
    private static final String KEY_BACKHAND = "backhand";
    private static final String KEY_OVERHEAD = "overhead";
    private static final String KEY_DATE = "date";

    private static final String CREATE_WORKOUT_TABLE = "CREATE TABLE " + DATABASE_TABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_STATUS + " INTEGER,"
            + KEY_FOREHAND + " INTEGER,"
            + KEY_BACKHAND + " INTEGER,"
            + KEY_OVERHEAD + " INTEGER,"
            + KEY_DATE + " TEXT)";

    private static final String DROP_WORKOUT_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_WORKOUT_TABLE);
        } catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_WORKOUT_TABLE);
            onCreate(db);
        } catch (SQLException e) {
        }
    }

    // Insert Data //
    public long insertData (int status, int forehand, int backhand, int overhead, String date) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATUS, status);
        contentValues.put(KEY_FOREHAND, forehand);
        contentValues.put(KEY_BACKHAND, backhand);
        contentValues.put(KEY_OVERHEAD, overhead);
        contentValues.put(KEY_DATE, date);
        database.insert(DATABASE_TABLE, null, contentValues);
        database.close();
        return 0;
    }

    // Get Data //
    public List<WorkoutDataModel> getAllItems() {
        List<WorkoutDataModel> workoutDataModels = new ArrayList<WorkoutDataModel>();

        // Query All //
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Loop through all rows and add to ListView //
        if (cursor.moveToFirst()) {
            do {
                WorkoutDataModel workoutDataModel = new WorkoutDataModel();
                workoutDataModel.set_id(cursor.getInt(0));
                workoutDataModel.setMyStatus(cursor.getInt(1));
                workoutDataModel.setMyForehand(cursor.getInt(2));
                workoutDataModel.setMyBackhand(cursor.getInt(3));
                workoutDataModel.setMyOverhead(cursor.getInt(4));
                workoutDataModel.setMyDate(cursor.getString(5));

                workoutDataModels.add(workoutDataModel);
            } while (cursor.moveToNext());
        }
        return workoutDataModels;
    }

    // Update Checkbox Status //
    public void updateCheckBox(WorkoutDataModel workoutDataModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATUS, workoutDataModel.getMyStatus());
        contentValues.put(KEY_FOREHAND, workoutDataModel.getMyForehand());
        contentValues.put(KEY_BACKHAND, workoutDataModel.getMyBackhand());
        contentValues.put(KEY_OVERHEAD, workoutDataModel.getMyOverhead());
        contentValues.put(KEY_DATE, workoutDataModel.getMyDate());
        database.update(DATABASE_TABLE, contentValues, KEY_ID
                + " = ?", new String[]{String.valueOf(workoutDataModel.get_id())});
    }

    // Delete Checkbox //
    public void deleteChecked() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DATABASE_TABLE, KEY_STATUS + "=" + "1", null);
        database.close();
    }
}
