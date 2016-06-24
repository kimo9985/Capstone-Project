package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jimtrinh9985gmail.swingtracker.ProfileFragment;
import com.jimtrinh9985gmail.swingtracker.R;

import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyData extends AppCompatActivity {

    public final String LOG_TAG = MyData.class.getSimpleName();

    private ListView listView;
    List<WorkoutDataModel> workoutDataModels;
    MyDatabaseAdapter myDatabaseAdapter;
    MyDatabase database;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_fragment);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        database = new MyDatabase(this);
        workoutDataModels = database.getAllItems();
        myDatabaseAdapter = new MyDatabaseAdapter(this, R.layout.data_model, workoutDataModels);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myDatabaseAdapter);

    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
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
                return true;
            case R.id.menu_profile:
                Intent intent = new Intent(this, ProfileFragment.class);
                startActivity(intent);
                return true;
            case R.id.menu_data:
                Intent intent1 = new Intent(this, MyData.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
