package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

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

    //private static final String SP_KEY_FOREHAND = "forehandCount";
    //private static final String SP_KEY_BACKHAND = "backhandCount";
    //private static final String SP_KEY_OVERHEAD = "overheadCount";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);

        WearPrefs.init(getActivity(), Utilities.SP_KEY_FOREHAND);
        WearPrefs.init(getActivity(), Utilities.SP_KEY_BACKHAND);
        WearPrefs.init(getActivity(), Utilities.SP_KEY_OVERHEAD);

        SharedPreferences forehands = getActivity().getSharedPreferences("forehandCount",
                getActivity().MODE_PRIVATE);
        int forehand = forehands.getInt("forehandCount", -1);

        SharedPreferences backhands = getActivity().getSharedPreferences("backhandCount",
                getActivity().MODE_PRIVATE);
        int backhand = backhands.getInt("backhandCount", -1);

        SharedPreferences overheads = getActivity().getSharedPreferences("overheadCount",
                getActivity().MODE_PRIVATE);
        int overhead = overheads.getInt("backhandCount", -1);

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

        saveButton = (Button) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "EXECUTE GOOGLE API CLIENT!");
            }
        });

        grip = (Switch) view.findViewById(R.id.switch1);
        grip.setChecked(true);

        return view;
    }


    //    private void sendSwingData (double forehand, double backhand, double overhead) {
//
//        mGoogleApiClient = new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(Bundle connectionHint) {
//                        Log.d(LOG_TAG, "onConnected: " + connectionHint);
//                        // Now you can use the Data Layer API
//                    }
//                    @Override
//                    public void onConnectionSuspended(int cause) {
//                        Log.d(LOG_TAG, "onConnectionSuspended: " + cause);
//                    }
//                })
//                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(ConnectionResult connectionResult) {
//                        Log.d(LOG_TAG, "onConnectionFailed: " + connectionResult);
//                    }
//                })
//                // Request access only to the Mobile API //
//                .addApi()
//                .build();
//
//        mGoogleApiClient.connect();
//
//        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/swing-data");
//
//        putDataMapRequest.getDataMap().putString(SP_KEY_FOREHAND, String.valueOf(forehand));
//        putDataMapRequest.getDataMap().putString(SP_KEY_BACKHAND, String.valueOf(backhand));
//        putDataMapRequest.getDataMap().putString(SP_KEY_OVERHEAD, String.valueOf(overhead));
//
//        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
//
//        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest)
//                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
//                    @Override
//                    public void onResult(DataApi.DataItemResult dataItemResult) {
//                        if (dataItemResult.getStatus().isSuccess()) {
//                            Log.d(LOG_TAG, "Weather Data sent successfully!");
//                        } else {
//                            Log.d(LOG_TAG, "Weather Data failed to send!");
//                        }
//                    }
//                });
//    }
}
