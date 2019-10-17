package com.example.mareu.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Room {

    private String mName;
    private int mRes;
    private Calendar mCalendar;
    private List<Reunion> mPlanningThisRoom;

    public Room(String mName, int mRes) {
        this.mName = mName;
        this.mRes = mRes;

        if (mPlanningThisRoom == null)
        {
            mPlanningThisRoom = new ArrayList<>();
        }

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

    public List<Reunion> getmPlanningThisRoom() {
        return mPlanningThisRoom;
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

    public void setmPlanningThisRoom(List<Reunion> mPlanningThisRoom) {
        this.mPlanningThisRoom = mPlanningThisRoom;
    }
}


