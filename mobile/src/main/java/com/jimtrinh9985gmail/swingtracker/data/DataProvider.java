package com.jimtrinh9985gmail.swingtracker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Kimo on 7/5/2016.
 */
public class DataProvider extends ContentProvider {

    public final String LOG_TAG = DataProvider.class.getSimpleName();

    // URI Matcher used by this content provider //
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DataHelper mOpenHelper;

    static final int WORKOUT = 100;
    static final int WORKOUT_ID = 101;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_WORKOUT, WORKOUT);
        matcher.addURI(authority, DataContract.PATH_WORKOUT + "/#", WORKOUT_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case WORKOUT:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                       DataContract.WorkoutEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case WORKOUT:
                return DataContract.WorkoutEntry.CONTENT_TYPE;
            case WORKOUT_ID:
                return DataContract.WorkoutEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case WORKOUT: {
                long _id = database.insert(DataContract.WorkoutEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DataContract.WorkoutEntry.buildWorkoutUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case WORKOUT:
                rowsDeleted = database.delete(
                        DataContract.WorkoutEntry.TABLE_NAME,
                        selection, selectionArgs);
                Log.d(LOG_TAG, "DataProvider: " + selection + " " + selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case WORKOUT:
                rowsUpdated = database.update(DataContract.WorkoutEntry.TABLE_NAME,
                        values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
