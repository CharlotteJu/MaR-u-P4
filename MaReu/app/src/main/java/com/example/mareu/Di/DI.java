
package com.example.mareu.Di;

import com.example.mareu.Services.*;

import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;


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
