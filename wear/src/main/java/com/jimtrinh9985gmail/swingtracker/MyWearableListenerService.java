package com.jimtrinh9985gmail.swingtracker;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Kimo on 6/10/2016.
 */
public class MyWearableListenerService extends WearableListenerService {

    public final String LOG_TAG = MyWearableListenerService.class.getSimpleName();

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();

                Log.d(LOG_TAG, "MyWearableService path:" + path);


            }
        }

    }
}
