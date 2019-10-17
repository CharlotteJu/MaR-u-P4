package com.example.mareu;

import com.example.mareu.Di.DI;
import com.example.mareu.Model.Room;
import com.example.mareu.Services.ReunionApiService;

import com.example.mareu.Model.Reunion;

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

        mailsPourTests = Arrays.asList("test1@gmail.com", "test2@gmail.com");
        calendarPourTests = Calendar.getInstance();
        calendarPourTests.set(2019,10,10,11,33);

        reunions.clear();
        LIST_REUNIONS.clear();

        roomForTest = new Room("Salle 1", R.drawable.room_1);

        reunions.add(new Reunion(mailsPourTests, roomForTest, "test1", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, roomForTest, "test2", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, roomForTest, "test3", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, roomForTest, "test4", calendarPourTests));

        for (int i = 0; i < reunions.size(); i ++)
        {
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
        assertFalse(room.getmPlanningThisRoom().contains(reunion));
    }

    @Test
    public void addReunion()
    {
        Reunion reunion = new Reunion(mailsPourTests, roomForTest, "test5", calendarPourTests);
        Room room = reunion.getmRoom();
        apiService.addReunion(reunion);
        assertTrue(apiService.getReunions().contains(reunion));
        assertTrue(room.getmPlanningThisRoom().contains(reunion));
    }
}