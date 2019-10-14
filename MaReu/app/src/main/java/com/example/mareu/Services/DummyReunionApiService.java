<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Controler/Services/DummyReunionApiService.java
<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream:MaReu/app/src/main/java/com/example/mareu/Controler/Services/DummyReunionApiService.java
>>>>>>> Stashed changes
package com.example.mareu.Controler.Services;
=======
package com.example.mareu.Services;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Services/DummyReunionApiService.java
<<<<<<< Updated upstream
=======
=======
package com.example.mareu.Services;
>>>>>>> Stashed changes:MaReu/app/src/main/java/com/example/mareu/Services/DummyReunionApiService.java
>>>>>>> Stashed changes

import com.example.mareu.Model.Reunion;

import java.util.ArrayList;
import java.util.List;

public class DummyReunionApiService implements ReunionApiService {

    public static List<Reunion> LIST_REUNIONS;

    public DummyReunionApiService()
    {
        if (this.LIST_REUNIONS == null)
        {
            this.LIST_REUNIONS = new ArrayList<>();
        }
    }

    @Override
    public List<Reunion> getReunions()
    {
        return  this.LIST_REUNIONS;
    }

    @Override
    public void deleteReunion(Reunion reunion)
    {
        if(LIST_REUNIONS.contains(reunion))
        {
            LIST_REUNIONS.remove(reunion);
        }
    }

    @Override
    public void addReunion(Reunion reunion)
    {
        if(!LIST_REUNIONS.contains(reunion))
        {
            LIST_REUNIONS.add(reunion);
        }
    }
}
