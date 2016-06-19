package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


/**
 * Created by Kimo on 5/15/2016.
 */
public class SettingsFragment extends Fragment {

    public final String LOG_TAG = SettingsFragment.class.getSimpleName();

    static TextView profileName, profileHeight, profileWeight, profileRacket;
    Button resetButton;
    Switch grip;
    private boolean gr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);

        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileHeight = (TextView) view.findViewById(R.id.profile_height);
        profileWeight = (TextView) view.findViewById(R.id.profile_weight);
        profileRacket = (TextView) view.findViewById(R.id.profile_racket);

        profileName.setText(Utilities.getProfileName(getActivity()));
        profileHeight.setText(Utilities.getProfileHeight(getActivity()));
        profileWeight.setText(Utilities.getProfileWeight(getActivity()));
        profileRacket.setText(Utilities.getProfileRacket(getActivity()));

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

    public static void setProfileName(String text) {
        profileName.setText(text);
    }

    public static void setProfileHeight(String text) {
        profileHeight.setText(text);
    }

    public static void setProfileWeight(String text) {
        profileWeight.setText(text);
    }

    public static void setProfileRacket(String text) {
        profileRacket.setText(text);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showGrip() {
        if (gr) {
            grip.setChecked(true);
        } else {
            grip.setChecked(false);
        }
    }
}
