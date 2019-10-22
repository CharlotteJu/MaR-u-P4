package com.example.mareu;

import com.example.mareu.Di.DI;
import com.example.mareu.Model.Room;
import com.example.mareu.Services.ReunionApiService;

import com.example.mareu.Model.Reunion;
import com.example.mareu.Services.RoomsGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import static com.example.mareu.Services.DummyReunionApiService.LIST_REUNIONS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class DummyReunionApiServiceTest {

    private ReunionApiService apiService;
    private Room roomForTest;
    private List<Reunion> reunions = new ArrayList<>();

    @Before
    public void setup()
    {
        apiService = DI.getNewInstanceApiService();

        reunions.clear();
        LIST_REUNIONS.clear();

        roomForTest = RoomsGenerator.getListRooms()[0];


        for (int i = 0; i < 4; i ++)
        {
            Reunion r= new Reunion();
            r.setmRoom(roomForTest);
            r.setmDate("15/12/2019");
            reunions.add(r);
            LIST_REUNIONS.add(r);
        }
    }

    @Test
    public void getReunionsWithSuccess()
    {
        List<Reunion> finalListReunions = apiService.getReunions();

        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(finalListReunions.toArray()));
        //assertEquals(reunions.size(), finalListReunions.size());

    }

    @Test
    public void deleteReunionWithSuccess()
    {
        Reunion reunion = apiService.getReunions().get(0);
        Room room = reunion.getmRoom();
        apiService.deleteReunion(reunion);
        assertFalse(apiService.getReunions().contains(reunion));
        assertFalse(room.getmPlanningThisRoom().contains(reunion));
    }

    @Test
    public void addReunionWithSuccess()
    {
        Reunion reunion = new Reunion();
        reunion.setmRoom(roomForTest);
        Room room = reunion.getmRoom();
        apiService.addReunion(reunion);
        assertTrue(apiService.getReunions().contains(reunion));
        assertTrue(room.getmPlanningThisRoom().contains(reunion));
    }

    @Test
    public void generateNameRoomsWithSuccess()
    {
        Reunion reunion = new Reunion();
        reunion.setmRoom(roomForTest);
        reunion.setmTime("10:00");
        reunion.setmDate("18/10/2019");

        apiService.addReunion(reunion);

        List<String> listForTest = apiService.generateNameRooms(reunion);

        assertEquals(listForTest.size(), RoomsGenerator.getListRooms().length -1);
        assertFalse(listForTest.contains(reunion.getmRoom()));
    }

    @Test
    public void filterDateWithSuccess()
    {
        String date = "01/01/2020";
        Reunion reunion = new Reunion();
        reunion.setmDate(date);
        reunion.setmRoom(roomForTest);

        apiService.addReunion(reunion);

        List<Reunion> compare = apiService.filterDate(date);
        assertEquals(compare.size(), 1);


    }

    @Test
    public void filterRoomWithSuccess()
    {
        Room roomAd = new Room("RoomX", 0);

        Reunion reunion = new Reunion();
        reunion.setmRoom(roomAd);

        apiService.addReunion(reunion);

        assertEquals(apiService.getReunions().size(),5);

        List<Reunion> compare = apiService.filterRoom(roomAd);
        assertTrue(compare.contains(reunion));
        assertEquals(compare.size(), 1);
    }


}