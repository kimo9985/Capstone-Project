package com.jimtrinh9985gmail.swingtracker;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimtrinh9985gmail.swingtracker.data.DataContract;

/**
 * Created by Kimo on 6/21/2016.
 */
public class MyDatabaseAdapter extends RecyclerView.Adapter<MyDatabaseAdapter.MyDatabaseAdapterViewHolder> {

    public final String LOG_TAG = MyDatabaseAdapter.class.getSimpleName();

    private Cursor mCursor;

    public class MyDatabaseAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mDate;
        public final TextView mForehand;
        public final TextView mBackhand;
        public final TextView mOverhead;

        public MyDatabaseAdapterViewHolder(View view) {
            super(view);
            mDate = (TextView) view.findViewById(R.id.dm_save_date);
            mForehand = (TextView) view.findViewById(R.id.dm_forehand_count);
            mBackhand = (TextView) view.findViewById(R.id.dm_backhand_count);
            mOverhead = (TextView) view.findViewById(R.id.dm_overhead_count);
            //view.setOnClickListener(this);
        }

    }

    @Override
    public MyDatabaseAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_model, parent, false);
        view.setFocusable(true);
        return new MyDatabaseAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyDatabaseAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int idx_date = mCursor.getColumnIndex(DataContract.WorkoutEntry.KEY_DATE);
        int idx_forehand = mCursor.getColumnIndex(DataContract.WorkoutEntry.KEY_FOREHAND);
        int idx_backhand = mCursor.getColumnIndex(DataContract.WorkoutEntry.KEY_BACKHAND);
        int idx_overhead = mCursor.getColumnIndex(DataContract.WorkoutEntry.KEY_OVERHEAD);

        String date = mCursor.getString(idx_date);
        String forehand = mCursor.getString(idx_forehand);
        String backhand = mCursor.getString(idx_backhand);
        String overhead = mCursor.getString(idx_overhead);

        holder.mDate.setText(date);
        holder.mForehand.setText(forehand);
        holder.mBackhand.setText(backhand);
        holder.mOverhead.setText(overhead);
    }

    @Override
    public int getItemCount() {
        if ( null == mCursor )return 0;
        return mCursor.getCount();
    }
}