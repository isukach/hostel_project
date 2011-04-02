package war.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import war.webapp.service.FloorManager;

public class FloorManagerImpl implements FloorManager{
    
    private List<String> floorsNames;
    
    public FloorManagerImpl() {
        floorsNames = new ArrayList<String>();
    }
    
    public void setFloorsNames(List<String> floorsNames) {
        this.floorsNames = floorsNames;
    }
    
    public List<String> getFloorsNames() {
        return floorsNames;
    }
    
}
