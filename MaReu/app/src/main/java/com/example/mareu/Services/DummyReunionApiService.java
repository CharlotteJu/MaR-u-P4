
package com.example.mareu.Services;

import com.example.mareu.Model.Reunion;
import com.example.mareu.Model.Room;

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
            Room room = reunion.getmRoom();
            //room.getmPlanningThisRoom().remove(reunion);
        }
    }

    @Override
    public void addReunion(Reunion reunion)
    {
        if(!LIST_REUNIONS.contains(reunion))
        {
            LIST_REUNIONS.add(reunion);
            Room room = reunion.getmRoom();
            //room.getmPlanningThisRoom().add(reunion);
        }
    }

    @Override
    public List<String> generateNameRooms(Reunion reunion) {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < RoomsGenerator.getListRooms().length; i ++)
        {
            list.add(RoomsGenerator.getListRooms()[i].getmName());
        }

        for(int i = 0; i < DummyReunionApiService.LIST_REUNIONS.size(); i ++) {

            if (DummyReunionApiService.LIST_REUNIONS.get(i).getmDate() == reunion.getmDate() && DummyReunionApiService.LIST_REUNIONS.get(i).getmTime() == reunion.getmTime()) {

                String delete = DummyReunionApiService.LIST_REUNIONS.get(i).getmRoom().getmName();

                if (list.contains(delete))
                {
                    list.remove(delete);
                }
            }
        }

            return list;
    }

}



