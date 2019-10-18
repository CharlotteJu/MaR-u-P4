package com.example.mareu;

import com.example.mareu.Di.DI;
import com.example.mareu.Model.Room;
import com.example.mareu.Services.ReunionApiService;

import com.example.mareu.Model.Reunion;
import com.example.mareu.Services.RoomsGenerator;

//import org.hamcrest.collection.IsIterableContainingInAnyOrder;
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
    private List<String> mailsPourTests;
    private Calendar calendarPourTests;
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
            reunions.add(new Reunion());
            LIST_REUNIONS.add(reunions.get(i));
        }
    }

    @Test
    public void getReunionsWithSuccess()
    {
        List<Reunion> finalListReunions = apiService.getReunions();

        //assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(finalListReunions.toArray()));
        assertEquals(reunions.size(), finalListReunions.size());

    }

    @Test
    public void deleteReunionWithSuccess()
    {
        Reunion reunion = apiService.getReunions().get(0);
        Room room = reunion.getmRoom();
        apiService.deleteReunion(reunion);
        assertFalse(apiService.getReunions().contains(reunion));
        //assertFalse(room.getmPlanningThisRoom().contains(reunion));
    }

    @Test
    public void addReunionWithSuccess()
    {
        Reunion reunion = new Reunion();
        Room room = reunion.getmRoom();
        apiService.addReunion(reunion);
        assertTrue(apiService.getReunions().contains(reunion));
        //assertTrue(room.getmPlanningThisRoom().contains(reunion));
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
}