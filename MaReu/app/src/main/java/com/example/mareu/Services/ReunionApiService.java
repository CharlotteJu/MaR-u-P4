<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Services/ReunionApiService.java
<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Controler/Services/ReunionApiService.java
package com.example.mareu.Controler.Services;
=======
package com.example.mareu.Services;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Services/ReunionApiService.java
=======
package com.example.mareu.Services;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Model/Services/ReunionApiService.java

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




}
