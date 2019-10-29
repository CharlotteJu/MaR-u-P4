package com.example.mareu.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Room {

    private String mName;
    private int mRes;

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

    //////////////////// SETTERS ////////////////////

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmRes(int mRes) {
        this.mRes = mRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getmName(), room.getmName());
        //return Objects.equals(hashCode(), room.hashCode());
    }
}


