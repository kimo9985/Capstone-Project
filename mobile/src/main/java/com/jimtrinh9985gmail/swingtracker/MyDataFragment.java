package com.jimtrinh9985gmail.swingtracker;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.database.Cursor;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jimtrinh9985gmail.swingtracker.data.DataContract;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyDataFragment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public final String LOG_TAG = MyDataFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private AdView mAdView;

    private static final int WORKOUT_LOADER_ID = 0;

    private static final String[] WORKOUT_COLUMNS = {
            DataContract.WorkoutEntry.KEY_ID,
            DataContract.WorkoutEntry.KEY_DATE,
            DataContract.WorkoutEntry.KEY_FOREHAND,
            DataContract.WorkoutEntry.KEY_BACKHAND,
            DataContract.WorkoutEntry.KEY_OVERHEAD
    };

    // These indices are tied to WORKOUT_COLUMNS //
    static final int COL_KEY_ID = 0;
    static final int COL_KEY_DATE = 1;
    static final int COL_KEY_FOREHAND = 2;
    static final int COL_KEY_BACKHAND = 3;
    static final int COL_KEY_OVERHEAD = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_fragment);

        // Google Ad Service //
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getSupportLoaderManager().initLoader(WORKOUT_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = DataContract.WorkoutEntry.KEY_DATE + " ASC";
        Uri workoutUri = DataContract.WorkoutEntry.CONTENT_URI;

        return new CursorLoader(this,
                workoutUri,
                WORKOUT_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        MyAdapter adapter = new MyAdapter(data);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerView.setAdapter(null);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Cursor mCursor;

        public MyAdapter(Cursor cursor) {
            mCursor = cursor;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.data_model, parent, false);
            final MyViewHolder mVHolder = new MyViewHolder(view);
            return mVHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            mCursor.moveToPosition(position);
            holder.mDate.setText(mCursor.getString(1));
            holder.mForehand.setText(mCursor.getString(2));
            holder.mBackhand.setText(mCursor.getString(3));
            holder.mOverhead.setText(mCursor.getString(4));
        }

        @Override
        public int getItemCount() {
            return mCursor.getCount();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener,
            View.OnCreateContextMenuListener {

        public final TextView mDate;
        public final TextView mForehand;
        public final TextView mBackhand;
        public final TextView mOverhead;

        public MyViewHolder(final View view) {
            super(view);
            mDate = (TextView) view.findViewById(R.id.dm_save_date);
            mForehand = (TextView) view.findViewById(R.id.dm_forehand_count);
            mBackhand = (TextView) view.findViewById(R.id.dm_backhand_count);
            mOverhead = (TextView) view.findViewById(R.id.dm_overhead_count);
            view.setOnLongClickListener(this);
            registerForContextMenu(view);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            onContextItemSelected();
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {

            final int MENU_DELETE_ITEM = 1;

            menu.setHeaderTitle("RecyclerView Item");
            menu.add(Menu.NONE, MENU_DELETE_ITEM, Menu.NONE, "Delete Item");
        }

        public boolean onContextItemSelected () {
            String where = DataContract.WorkoutEntry.KEY_DATE;
            String date = (String) ((TextView) mDate).getText();
            String[] arg = new String[]{date};
            ContentResolver contentResolver = getContentResolver();
            contentResolver.delete(DataContract.WorkoutEntry.CONTENT_URI, where + " =? ", arg);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_profile:
                Intent intent1 = new Intent(this, ProfileFragment.class);
                startActivity(intent1);
                return true;
            case R.id.menu_data:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}