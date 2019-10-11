package com.example.mareu.Model;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Reunion
{

    /**
     * A list for the participants's emails
     */
    private List<String> mEmails;

    /**
     * A list for the Rooms
     */
    private String mRooms;

    /**
     * To describe the meeting's subject
     */
    private String mSubject;

    /**
     * To put the time
     */
    private Calendar mTime;

    public Reunion(List<String> mEmails, String mRooms, String mSubject, Calendar mTime) {
        this.mEmails = mEmails;
        this.mRooms = mRooms;
        this.mSubject = mSubject;
        this.mTime = mTime;
    }

    //////////////////// GETTERS ////////////////////

    public List<String> getmEmails() {
        return mEmails;
    }

    public String getmRooms() {
        return mRooms;
    }

    public String getmSubject() {
        return mSubject;
    }

    public Calendar getmTime() {
        return mTime;
    }


    //////////////////// SETTERS ////////////////////

    public void setmEmails(List<String> mEmails) {
        this.mEmails = mEmails;
    }

    public void setmRooms(String mRooms) {
        this.mRooms = mRooms;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public void setmTime(Calendar mTime) {
        this.mTime = mTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return Objects.equals(hashCode(), reunion.hashCode());
    }
}
