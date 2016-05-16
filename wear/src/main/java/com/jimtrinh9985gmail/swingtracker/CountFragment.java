package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kimo on 5/15/2016.
 */
public class CountFragment extends Fragment {

    // Swings //
    private TextView forehandText, backhandText, overheadText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_layout, container, false);

        forehandText = (TextView) view.findViewById(R.id.forehand_count);
        backhandText = (TextView) view.findViewById(R.id.backhand_count);
        overheadText = (TextView) view.findViewById(R.id.overhead_count);

        return view;
    }
}
