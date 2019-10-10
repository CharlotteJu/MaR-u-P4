package com.example.mareu;

import com.example.mareu.Controler.Di.DI;
import com.example.mareu.Controler.Services.ReunionApiService;
import com.example.mareu.Model.Reunion;

//import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.mareu.Controler.Services.DummyReunionApiService.LIST_REUNIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class DummyReunionApiServiceTest {

    private ReunionApiService apiService;
    private List<String> mailsPourTests;
    private Calendar calendarPourTests;

    @Before
    public void setup()
    {
        apiService = DI.getNewInstanceApiService();
        mailsPourTests = Arrays.asList("test1@gmail.com", "test2@gmail.com");
        calendarPourTests = Calendar.getInstance();
        calendarPourTests.set(2019,10,10,11,33);
    }

    @Test
    public void getReunionsWithSuccess()
    {
        List<Reunion> reunions = new ArrayList<>();
        reunions.add(new Reunion(mailsPourTests, "test1", "test1", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, "test2", "test2", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, "test3", "test3", calendarPourTests));
        reunions.add(new Reunion(mailsPourTests, "test4", "test4", calendarPourTests));

        for (int i = 0; i < reunions.size(); i ++)
        {
            LIST_REUNIONS.add(reunions.get(i));
        }

        List<Reunion> finalListReunions = apiService.getReunions();

        //assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(finalListReunions.toArray()));
        assertEquals(reunions.size(), finalListReunions.size());

    }

    @Test
    public void deleteReunion() {
    }

    @Test
    public void addReunion() {
    }
}