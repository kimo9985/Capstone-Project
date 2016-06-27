package com.jimtrinh9985gmail.swingtracker.myDatabase;

import android.util.Log;

/**
 * Created by Kimo on 6/21/2016.
 */
public class WorkoutDataModel {

    public final String LOG_TAG = WorkoutDataModel.class.getSimpleName();
    private int _id, myForehand, myBackhand, myOverhead, myStatus;
    private String myDate;

    public WorkoutDataModel() {
        this.myForehand = 0;
        this.myBackhand = 0;
        this.myOverhead = 0;
        this.myStatus = 0;
        this.myDate = null;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(int myStatus) {
        this.myStatus = myStatus;
        Log.d(LOG_TAG, "Datamodel setMyStatus: " + myStatus);
    }

    public int getMyForehand() {
        return myForehand;
    }

    public void setMyForehand(int myForehand) {
        this.myForehand = myForehand;
    }

    public int getMyBackhand() {
        return myBackhand;
    }

    public void setMyBackhand(int myBackhand) {
        this.myBackhand = myBackhand;
    }

    public int getMyOverhead() {
        return myOverhead;
    }

    public void setMyOverhead(int myOverhead) {
        this.myOverhead = myOverhead;
    }

    public String getMyDate() {
        return myDate;
    }

    public void setMyDate(String myDate) {
        this.myDate = myDate;
    }
}
