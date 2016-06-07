package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


/**
 * Created by Kimo on 5/15/2016.
 */
public class SettingsFragment extends Fragment {

    public final String LOG_TAG = SettingsFragment.class.getSimpleName();

    Button resetButton;
    Switch grip;
    private boolean gr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);

        resetButton = (Button) view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).resetCounter();
            }
        });

        grip = (Switch) view.findViewById(R.id.switch1);
        showGrip();
        grip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utilities.savePrefGrip(getActivity(), true);
                    ((MainActivity) getActivity()).reinitializeGrip();
                } else {
                    Utilities.savePrefGrip(getActivity(), false);
                    ((MainActivity) getActivity()).reinitializeGrip();
                }
            }
        });
        return view;
    }

    private void showGrip() {
        if (gr) {
            grip.setChecked(true);
        } else {
            grip.setChecked(false);
        }
    }
}
