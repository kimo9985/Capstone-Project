package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jimtrinh9985gmail.swingtracker.ProfileFragment;
import com.jimtrinh9985gmail.swingtracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyData extends AppCompatActivity {

    public final String LOG_TAG = MyData.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<WorkoutDataModel> workoutDataModels = new ArrayList<>();
    MyDatabaseAdapter myDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_fragment);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        myDatabaseAdapter = new MyDatabaseAdapter(workoutDataModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myDatabaseAdapter);
    }

    public static List<WorkoutDataModel> getData() {
        List<WorkoutDataModel> workoutDataModels = new ArrayList<>();
        return workoutDataModels;
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
