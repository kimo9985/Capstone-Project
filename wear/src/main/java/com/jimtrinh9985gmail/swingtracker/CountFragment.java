package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Kimo on 5/15/2016.
 */
public class CountFragment extends Fragment {

    public final String LOG_TAG = CountFragment.class.getSimpleName();

    // Swings //
    private TextView forehandText, backhandText, overheadText;

    // Battery //
    public TextView battery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_layout, container, false);

        forehandText = (TextView) view.findViewById(R.id.forehand_count);
        backhandText = (TextView) view.findViewById(R.id.backhand_count);
        overheadText = (TextView) view.findViewById(R.id.overhead_count);

        battery = (TextView) view.findViewById(R.id.battery_level);
        getActivity().registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        setForehandCounter(Utilities.getPrefForehand(getActivity()));
        setBackhandCounter(Utilities.getPrefBackhand(getActivity()));
        setOverheadCounter(Utilities.getPrefOverhead(getActivity()));

        return view;
    }

    // Battery Level //
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            battery.setText(String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) + "%"));
        }
    };

    public void setForehandCounter(String text) {
        forehandText.setText(text);
    }

    public void setForehandCounter(int i) {
        Log.d(LOG_TAG, "SetForehandCounter: " + i);
        setForehandCounter(i < 0 ? "0" : String.valueOf(i));
    }

    public void setBackhandCounter(String text) {
        backhandText.setText(text);
    }

    public void setBackhandCounter(int i) {
        setBackhandCounter(i < 0 ? "0" : String.valueOf(i));
    }

    public void setOverheadCounter(String text) {
        overheadText.setText(text);
    }

    public void setOverheadCounter(int i) {
        setOverheadCounter(i < 0 ? "0" : String.valueOf(i));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
