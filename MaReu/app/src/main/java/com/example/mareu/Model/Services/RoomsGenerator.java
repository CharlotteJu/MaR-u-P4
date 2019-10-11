package com.example.mareu.Model.Services;

public abstract class RoomsGenerator {

    private static String[] LIST_ROOMS = {
            "Salle A",
            "Salle B",
            "Salle C",
            "Salle D",
            "Salle E",
            "Salle F",
            "Salle G",
            "Salle H",
            "Salle I",
            "Salle J",
            };

    public static String[] generateRooms()
    {
        return LIST_ROOMS;
    }
}
