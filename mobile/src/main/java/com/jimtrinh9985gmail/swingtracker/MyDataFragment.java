package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private static final int WORKOUT_LOADER_ID = 0;

    private static final String[] ENTRY_COLUMNS = {
            DataContract.WorkoutEntry.KEY_ID,
            DataContract.WorkoutEntry.KEY_DATE,
            DataContract.WorkoutEntry.KEY_FOREHAND,
            DataContract.WorkoutEntry.KEY_BACKHAND,
            DataContract.WorkoutEntry.KEY_OVERHEAD
    };

    private RecyclerView recyclerView;
    private MyDatabaseAdapter mMyDatabaseAdapter;
    TextView forehand, backhand, overhead, date;
    private AdView mAdView;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mMyDatabaseAdapter);

        //getLoaderManager().initLoader(WORKOUT_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


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
