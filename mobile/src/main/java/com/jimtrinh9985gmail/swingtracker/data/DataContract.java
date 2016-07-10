package com.jimtrinh9985gmail.swingtracker.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;

/**
 * Created by Kimo on 7/5/2016.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.jimtrinh9985gmail.swingtracker";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/swingData");

    public static final String PATH_WORKOUT = "swingData";

    /* Inner class that defines the table contents of the location table */
    public static final class WorkoutEntry implements BaseColumns {

        // Database Table Name/Columns //
        public static final String TABLE_NAME = "swing_Data_table";

        public static final String KEY_ID = "_id";
        public static final String KEY_FOREHAND = "forehand";
        public static final String KEY_BACKHAND = "backhand";
        public static final String KEY_OVERHEAD = "overhead";
        public static final String KEY_DATE = "date";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKOUT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORKOUT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORKOUT;

        public static Uri buildWorkoutUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
