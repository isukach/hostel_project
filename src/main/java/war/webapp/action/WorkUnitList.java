package war.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import war.webapp.model.DayDuty;
import war.webapp.model.State;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;
import war.webapp.service.WorkUnitManager;

public class WorkUnitList extends BasePage {
    
    private WorkUnitManager workUnitManager;
    
    public WorkUnitList() {
    }
    
    public List<WorkUnit> getAllWorkUnits() {
        return workUnitManager.getAll();
    }

    public WorkUnitManager getworkUnitManager() {
        return workUnitManager;
    }

    public void setworkUnitManager(WorkUnitManager workUnitManager) {
        this.workUnitManager = workUnitManager;
    }
    
    public void stateChanged(ValueChangeEvent e) {
        String newValue = (String) e.getNewValue();
        String componentId = e.getComponent().getId();
        String workUnitId = componentId;
        WorkUnit workUnit = workUnitManager.get(Long.parseLong(workUnitId));
        State newState = State.valueOf(newValue);
        workUnit.setState(newState);
        workUnitManager.save(workUnit);
    }    
    
    public List<SelectItem> getStatesList() {
        List<SelectItem> statesList = new ArrayList<SelectItem>();
        for (State state: State.values()) {
            statesList.add(new SelectItem(state.name(), state.toString()));
        }
        return statesList;
    }

}
