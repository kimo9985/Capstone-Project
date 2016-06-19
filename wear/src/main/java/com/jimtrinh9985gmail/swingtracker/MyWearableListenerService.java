package com.jimtrinh9985gmail.swingtracker;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kimo on 6/10/2016.
 */
public class MyWearableListenerService extends WearableListenerService {

    public final String LOG_TAG = MyWearableListenerService.class.getSimpleName();

    public static final String SP_KEY_NAME = "com.swingtracker.NAME";
    public static final String SP_KEY_HEIGHT = "com.swingtracker.HEIGHT";
    public static final String SP_KEY_WEIGHT = "com.swingtracker.WEIGHT";
    public static final String SP_KEY_RACKET = "com.swingtracker.RACKET";

    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

        Log.d(LOG_TAG, "MyWearableService onDataChanged!");

        if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
            ConnectionResult connectionResult = mGoogleApiClient
                    .blockingConnect(30, TimeUnit.SECONDS);
            if (!connectionResult.isSuccess()) {
                Log.e(LOG_TAG, "DataLayerListenerService failed to connect to GoogleApiClient, "
                        + "error code: " + connectionResult.getErrorCode());
                return;
            }
        }

        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();

                Log.d(LOG_TAG, "MyWearableService path:" + path);

                if (path.equals("/swing-data")) {

                }
            }
        }

    }
}
