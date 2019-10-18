package com.example.mareu.Services;

import com.example.mareu.Model.Reunion;

import java.util.List;

/**
 * Reunion Api
 */
public interface ReunionApiService
{

    /**
     * Get all the meeting
     * @return {@link List}
     */
    List<Reunion> getReunions();

    /**
     * Delete a meeting
     * @param reunion
     */
    void deleteReunion(Reunion reunion);


    /**
     * Add a metting
     * @param reunion
     */
    void addReunion(Reunion reunion);

    /**
     * Give a list of String for the rooms
     * @param reunion
     * @return
     */
    List<String> generateNameRooms(Reunion reunion);


}
