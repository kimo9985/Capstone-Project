package com.jimtrinh9985gmail.swingtracker.myDatabase;

/**
 * Created by Kimo on 6/21/2016.
 */
public class WorkoutDataModel {

    private int _id, myForehand, myBackhand, myOverhead;
    private String myDate;

    public WorkoutDataModel() {
    }

    public WorkoutDataModel(int myForehand, int myBackhand, int myOverhead, String myDate) {
        this.myForehand = myForehand;
        this.myBackhand = myBackhand;
        this.myOverhead = myOverhead;
        this.myDate = myDate;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
