package com.example.mareu.Services;

import com.example.mareu.Model.Reunion;
import com.example.mareu.Model.Room;

import java.util.ArrayList;
import java.util.List;

public abstract class ReunionsGenerator
{
    private static List<Reunion> mReunions;

    public static List<Reunion> getmReunions() {

        List<String> aMails = new ArrayList<>();
        Room[] mRooms = RoomsGenerator.getListRooms();
        ReunionApiService mApiService= new DummyReunionApiService();

        if (mReunions == null)
        {
            aMails.add("mail1@test.fr");
            aMails.add("mail2@test.fr");
            aMails.add("mail3@test.fr");
            aMails.add("mail4@test.fr");

            Reunion reunionTest1 = new Reunion(aMails, mRooms[0], "Réunion Test 1", "10:00", "22/10/2020");
            Reunion reunionTest2 = new Reunion(aMails, mRooms[1], "Réunion Test 2", "15:00", "22/10/2020");
            Reunion reunionTest3 = new Reunion(aMails, mRooms[2], "Réunion Test 3", "14:00", "12/12/2020");

            mApiService.addReunion(reunionTest1);
            mApiService.addReunion(reunionTest2);
            mApiService.addReunion(reunionTest3);
        }

        mReunions = mApiService.getReunions();

        return mReunions;
    }
}
