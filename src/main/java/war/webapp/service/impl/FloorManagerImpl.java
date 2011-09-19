package war.webapp.service.impl;

import war.webapp.service.FloorManager;

import java.util.ArrayList;
import java.util.List;

public class FloorManagerImpl implements FloorManager{
    
    private List<String> floorsNames;
    private List<String> availableRooms;
    
    public FloorManagerImpl() {
        floorsNames = new ArrayList<String>();
        availableRooms = new ArrayList<String>();
    }
    
    public void setFloorsNames(List<String> floorsNames) {
        this.floorsNames = floorsNames;
    }
    
    public List<String> getFloorsNames() {
        return floorsNames;
    }

    public List<String> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(List<String> availableRooms) {
        this.availableRooms = availableRooms;
    }
    
}
