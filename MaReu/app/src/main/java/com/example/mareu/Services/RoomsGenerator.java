<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Services/RoomsGenerator.java
<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Controler/Services/RoomsGenerator.java
package com.example.mareu.Controler.Services;
=======
package com.example.mareu.Services;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Services/RoomsGenerator.java
=======
package com.example.mareu.Services;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Model/Services/RoomsGenerator.java

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
