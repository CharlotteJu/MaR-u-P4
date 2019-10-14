<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Di/DI.java
<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Controler/Di/DI.java
package com.example.mareu.Controler.Di;

import com.example.mareu.Controler.Services.DummyReunionApiService;
import com.example.mareu.Controler.Services.ReunionApiService;
=======
package com.example.mareu.Di;

import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Di/DI.java
=======
package com.example.mareu.Di;

import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Model/Di/DI.java

/**
 * Dependency injector to get instance of services
 */
public class DI
{
    private static ReunionApiService service = new DummyReunionApiService();

    /**
     * Get an instance on @{@link ReunionApiService}
     * @return
     */
    public static ReunionApiService getNeighbourApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link ReunionApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static ReunionApiService getNewInstanceApiService() {
        return new DummyReunionApiService();
    }


}
