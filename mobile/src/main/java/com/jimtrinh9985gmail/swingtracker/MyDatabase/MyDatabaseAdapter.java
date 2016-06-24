package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jimtrinh9985gmail.swingtracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyDatabaseAdapter extends ArrayAdapter<WorkoutDataModel> {

    public final String LOG_TAG = MyDatabaseAdapter.class.getSimpleName();
    private TextView forehand, backhand, overhead, date;
    Context context;
    int layoutResourceId;
    List<WorkoutDataModel> workoutDataModel = new ArrayList<WorkoutDataModel>();

    public MyDatabaseAdapter(Context context, int layoutResourceId, List<WorkoutDataModel> object) {
        super(context, layoutResourceId, object);
        this.layoutResourceId = layoutResourceId;
        this.workoutDataModel = object;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.data_model, parent, false);

            forehand = (TextView) convertView.findViewById(R.id.dm_forehand_count);
            backhand = (TextView) convertView.findViewById(R.id.dm_backhand_count);
            overhead = (TextView) convertView.findViewById(R.id.dm_overhead_count);
            date = (TextView) convertView.findViewById(R.id.dm_save_date);
        }
        WorkoutDataModel current = workoutDataModel.get(position);
        forehand.setText(String.valueOf(current.getMyForehand()));
        backhand.setText(String.valueOf(current.getMyBackhand()));
        overhead.setText(String.valueOf(current.getMyOverhead()));
        date.setText(String.valueOf(current.getMyDate()));

        return convertView;
    }
 }
