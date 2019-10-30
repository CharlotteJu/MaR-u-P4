package com.example.mareu;

import com.example.mareu.Model.Reunion;
import com.example.mareu.Services.RoomsGenerator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ReunionTest {

    private boolean isComplete;
    Reunion reunion = new Reunion();

    @Test
    public void completeReunionWithSuccess()
    {
        reunion.setmSubject("Test");
        reunion.setmDate("12/12/2019");
        reunion.setmTime("11h00");

        isComplete = reunion.completeReunion();
        assertFalse(isComplete);

        reunion.setmRoom(RoomsGenerator.getListRooms()[0]);

        List<String> mailsForTest = Arrays.asList("mail@test.fr");

        reunion.setmEmails(mailsForTest);

        isComplete = reunion.completeReunion();
        assertTrue(isComplete);
    }
}




