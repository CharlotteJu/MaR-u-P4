package com.example.mareu.Services;


import com.example.mareu.Model.Room;
import com.example.mareu.R;

public abstract class RoomsGenerator {

    private static Room[] LIST_ROOMS = {
            new Room("Salle 1", R.drawable.room_1),
            new Room("Salle 2", R.drawable.room_2),
            new Room("Salle 3", R.drawable.room_3),
            new Room("Salle 4", R.drawable.room_4),
            new Room("Salle 5", R.drawable.room_5),
            new Room("Salle 6", R.drawable.room_6),
            new Room("Salle 7", R.drawable.room_7),
            new Room("Salle 8", R.drawable.room_8),
            new Room("Salle 9", R.drawable.room_9),
            new Room("Salle 10", R.drawable.room_10),
    };

    public static Room[] generateRooms()
    {
        return LIST_ROOMS;
    }

}
