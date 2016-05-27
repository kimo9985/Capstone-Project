package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import wearprefs.WearPrefs;


/**
 * Created by Kimo on 5/15/2016.
 */
public class SettingsFragment extends Fragment {

    public final String LOG_TAG = SettingsFragment.class.getSimpleName();

    Button resetButton;
    Button saveButton;
    Switch grip;


    double forehand, backhand, overhead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);

        SharedPreferences forehands = getActivity().getSharedPreferences("com.swingtracker.FOREHAND",
                getActivity().MODE_PRIVATE);
        int forehand = forehands.getInt("com.swingtracker.FOREHAND", 0);

        SharedPreferences backhands = getActivity().getSharedPreferences("com.swingtracker.BACKHAND",
                getActivity().MODE_PRIVATE);
        int backhand = backhands.getInt("com.swingtracker.BACKHAND", 0);

        SharedPreferences overheads = getActivity().getSharedPreferences("com.swingtracker.OVERHEAD",
                getActivity().MODE_PRIVATE);
        int overhead = overheads.getInt("com.swingtracker.OVERHEAD", 0);

        Log.d(LOG_TAG, "Wearable Forehand: " + forehand);
        Log.d(LOG_TAG, "Wearable Backhand: " + backhand);
        Log.d(LOG_TAG, "Wearable Overhead: " + overhead);

        resetButton = (Button) view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).resetCounter();
            }
        });

        grip = (Switch) view.findViewById(R.id.switch1);
        grip.setChecked(true);

        return view;
    }
}
