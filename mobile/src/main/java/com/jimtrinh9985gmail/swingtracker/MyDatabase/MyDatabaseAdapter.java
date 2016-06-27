package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.jimtrinh9985gmail.swingtracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyDatabaseAdapter extends ArrayAdapter<WorkoutDataModel> {

    public final String LOG_TAG = MyDatabaseAdapter.class.getSimpleName();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.data_model, parent, false);

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.deleteBox);
        WorkoutDataModel current = workoutDataModel.get(position);
        checkBox.setChecked(current.getMyStatus() == 1);

        return convertView;
    }
}