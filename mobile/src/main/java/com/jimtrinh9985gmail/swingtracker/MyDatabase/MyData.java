package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jimtrinh9985gmail.swingtracker.ChartFragment;
import com.jimtrinh9985gmail.swingtracker.MainActivity;
import com.jimtrinh9985gmail.swingtracker.ProfileFragment;
import com.jimtrinh9985gmail.swingtracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyData extends AppCompatActivity {

    public final String LOG_TAG = MyData.class.getSimpleName();

    private ListView listView;
    TextView forehand, backhand, overhead, date;
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

    private class MyDatabaseAdapter extends ArrayAdapter<WorkoutDataModel> {
        Context context;
        List<WorkoutDataModel> workoutDataModel = new ArrayList<WorkoutDataModel>();
        int layoutResourceId;

        public MyDatabaseAdapter(Context context, int layoutResourceId, List<WorkoutDataModel> object) {
            super(context, layoutResourceId, object);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.workoutDataModel = object;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CheckBox checkBox = null;
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.data_model, parent, false);
                forehand = (TextView) convertView.findViewById(R.id.dm_forehand_count);
                backhand = (TextView) convertView.findViewById(R.id.dm_backhand_count);
                overhead = (TextView) convertView.findViewById(R.id.dm_overhead_count);
                date = (TextView) convertView.findViewById(R.id.dm_save_date);
                checkBox = (CheckBox) convertView.findViewById(R.id.deleteBox);
                convertView.setTag(checkBox);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox check = (CheckBox) v;
                        WorkoutDataModel updateData = (WorkoutDataModel) check.getTag();
                        updateData.setMyStatus(check.isChecked() ? 1 : 0);
                        database.updateCheckBox(updateData);
                    }
                });
            } else {
                checkBox = (CheckBox) convertView.getTag();
            }
            WorkoutDataModel current = workoutDataModel.get(position);
            forehand.setText(String.valueOf(current.getMyForehand()));
            backhand.setText(String.valueOf(current.getMyBackhand()));
            overhead.setText(String.valueOf(current.getMyOverhead()));
            date.setText(String.valueOf(current.getMyDate()));
            checkBox.setChecked(current.getMyStatus() == 1);
            checkBox.setTag(current);

            return convertView;
        }
    }

    public void deleteItems(View v) {
        database.deleteChecked();
        myDatabaseAdapter.notifyDataSetChanged();
        recreate();
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
