package com.example.mareu.Model;

import java.util.Calendar;

public class Room {

    private String mName;
    private int mRes;
    private Calendar mCalendar;

    public Room(String mName, int mRes) {
        this.mName = mName;
        this.mRes = mRes;

    }

    //////////////////// GETTERS ////////////////////

    public String getmName() {
        return mName;
    }

    public int getmRes() {
        return mRes;
    }

    public Calendar getmCalendar() {
        return mCalendar;
    }

    //////////////////// SETTERS ////////////////////

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmRes(int mRes) {
        this.mRes = mRes;
    }

    public void setmCalendar(Calendar mCalendar) {
        this.mCalendar = mCalendar;
    }
}


