package com.jimtrinh9985gmail.swingtracker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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
public class SettingsFragment extends Fragment {

    public final String LOG_TAG = SettingsFragment.class.getSimpleName();

    public static final String SP_KEY_FOREHAND = "com.swingtracker.FOREHAND";
    public static final String SP_KEY_BACKHAND = "com.swingtracker.BACKHAND";
    public static final String SP_KEY_OVERHEAD = "com.swingtracker.OVERHEAD";

    Button resetButton, sendDataButton;
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

        sendDataButton = (Button) view.findViewById(R.id.send_data_button);
        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSwingData();
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

    private void sendSwingData() {

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.d(LOG_TAG, "onConnected: " + bundle);
                        // Now you can use the Data Layer API
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d(LOG_TAG, "onConnectionSuspended: " + i);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(LOG_TAG, "onConnectionFailed: " + connectionResult);
                    }
                })
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();

        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/swing-data");

        putDataMapRequest.getDataMap().putInt(SP_KEY_FOREHAND,
                Utilities.getPrefForehand(getActivity()));
        putDataMapRequest.getDataMap().putInt(SP_KEY_BACKHAND,
                Utilities.getPrefBackhand(getActivity()));
        putDataMapRequest.getDataMap().putInt(SP_KEY_OVERHEAD,
                Utilities.getPrefOverhead(getActivity()));

        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                        if (dataItemResult.getStatus().isSuccess()) {
                            Log.d(LOG_TAG, "Swing Data sent successfully!");
                        } else {
                            Log.d(LOG_TAG, "Swing Data failed to send!");
                        }
                    }
                });
    }

    private void showGrip() {
        if (gr) {
            grip.setChecked(true);
        } else {
            grip.setChecked(false);
        }
    }
}
