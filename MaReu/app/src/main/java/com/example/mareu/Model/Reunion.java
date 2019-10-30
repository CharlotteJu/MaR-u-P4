package com.example.mareu.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Reunion
{

    /**
     * A list for the participants's emails
     */
    private List<String> mEmails;
    /**
     * The ref to for the Rooms
     */
    private Room mRoom;
    /**
     * To describe the meeting's subject
     */
    private String mSubject;
    /**
     * To put the time
     */
    private String mTime;

    /**
     * To put the date
     */
    private String mDate;

    public Reunion () {}

    public Reunion(List<String> aEmails, Room aRoom, String aSubject, String aTime, String aDate) {
        this.mEmails = aEmails;
        this.mRoom = aRoom;
        this.mSubject = aSubject;
        this.mTime = aTime;
        this.mDate = aDate;
    }

    //////////////////// GETTERS ////////////////////

    public List<String> getmEmails() {
        return mEmails;
    }

    public Room getmRoom() {
        return mRoom;
    }

    public String getmSubject() {
        return mSubject;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmDate() {
        return mDate;
    }

    //////////////////// SETTERS ////////////////////

    public void setmEmails(List<String> mEmails) {
        this.mEmails = mEmails;
    }

    public void setmRoom(Room mRoom) {
        this.mRoom = mRoom;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return Objects.equals(hashCode(), reunion.hashCode());
    }

    public boolean completeReunion()
    {
        if (mEmails != null && mRoom != null && mSubject !=null && mDate!= null && mTime!= null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
