package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimtrinh9985gmail.swingtracker.R;

import java.util.List;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyDatabaseAdapter extends RecyclerView.Adapter<MyDatabaseAdapter.MyViewHolder> {

    public final String LOG_TAG = MyDatabaseAdapter.class.getSimpleName();

    private List<WorkoutDataModel> workoutDataModels;

    public MyDatabaseAdapter(List<WorkoutDataModel> workoutDataModels) {
        this.workoutDataModels = workoutDataModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_model, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WorkoutDataModel current = workoutDataModels.get(position);
        holder.forehand.setText(current.getMyForehand());
        holder.backhand.setText(current.getMyBackhand());
        holder.overhead.setText(current.getMyOverhead());
        holder.date.setText(current.getMyDate());
    }

    @Override
    public int getItemCount() {
        return workoutDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView forehand, backhand, overhead, date;

        public MyViewHolder(View itemView) {
            super(itemView);
            forehand = (TextView) itemView.findViewById(R.id.forehand_count);
            backhand = (TextView) itemView.findViewById(R.id.backhand_count);
            overhead = (TextView) itemView.findViewById(R.id.overhead_count);
            date = (TextView) itemView.findViewById(R.id.save_date);
        }
    }
 }
