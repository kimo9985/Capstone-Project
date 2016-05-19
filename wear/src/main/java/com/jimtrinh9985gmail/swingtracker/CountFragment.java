package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Kimo on 5/15/2016.
 */
public class CountFragment extends Fragment {

    public final String LOG_TAG = CountFragment.class.getSimpleName();

    // Swings //
    private TextView forehandText, backhandText, overheadText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_layout, container, false);

        forehandText = (TextView) view.findViewById(R.id.forehand_count);
        backhandText = (TextView) view.findViewById(R.id.backhand_count);
        overheadText = (TextView) view.findViewById(R.id.overhead_count);

        setForehandCounter(Utilities.getPrefForehand(getActivity()));
        setBackhandCounter(Utilities.getPrefBackhand(getActivity()));
        setOverheadCounter(Utilities.getPrefOverhead(getActivity()));

        return view;
    }

    public void setForehandCounter(String text) {
        forehandText.setText(text);
    }

    public void setForehandCounter(int i) {
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
}
