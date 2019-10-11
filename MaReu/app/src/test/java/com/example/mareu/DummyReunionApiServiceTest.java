package com.example.mareu;

import com.example.mareu.Model.Di.DI;
import com.example.mareu.Model.Services.ReunionApiService;
import com.example.mareu.Model.Reunion;

//import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.mareu.Model.Services.DummyReunionApiService.LIST_REUNIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class DummyReunionApiServiceTest {

    private ReunionApiService apiService;
    private List<String> mailsPourTests;
    private Calendar calendarPourTests;
    List<Reunion> reunions = new ArrayList<>();

    @Before
    public void setup()
    {
        apiService = DI.getNewInstanceApiService();

        mailsPourTests = Arrays.asList("test1@gmail.com", "test2@gmail.com");
        calendarPourTests = Calendar.getInstance();
        calendarPourTests.set(2019,10,10,11,33);

        reunions.clear();
        LIST_REUNIONS.clear();

        reunions.add(new Reunion(mailsPourTests, "test1", "test1", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, "test2", "test2", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, "test3", "test3", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, "test4", "test4", calendarPourTests));

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
        apiService.deleteReunion(reunion);
        assertFalse(apiService.getReunions().contains(reunion));
    }

    @Test
    public void addReunion()
    {
        Reunion reunion = new Reunion(mailsPourTests, "test5", "test5", calendarPourTests);
        apiService.addReunion(reunion);
        assertTrue(apiService.getReunions().contains(reunion));
    }
}