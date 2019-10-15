package com.example.mareu.Model;

import java.util.Date;

public class Room {

    /**
     * Room's name
     * Used also for take the good drawable
     */
    private String mName;

    /**
     * Room's schedule
     */
    private Date mSchedule;


    public Room(String mName,  Date mSchedule) {
        this.mName = mName;
        this.mSchedule = mSchedule;
    }

    //////////////////// GETTERS ////////////////////


    public String getmName() {
        return mName;
    }

    public Date getmSchedule() {
        return mSchedule;
    }

    //////////////////// SETTERS ////////////////////


    public void setmName(String mName) {
        this.mName = mName;
    }



    public void setmSchedule(Date mSchedule) {
        this.mSchedule = mSchedule;
    }
}
