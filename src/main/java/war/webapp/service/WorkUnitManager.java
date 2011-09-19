package war.webapp.service;

import java.util.Calendar;
import java.util.List;

import war.webapp.model.State;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;

public interface WorkUnitManager extends GenericManager<WorkUnit, Long> {
    
    public WorkUnit loadWorkUnitById(Long id);

    public WorkUnit loadWorkUnitByDate(Calendar date);

    public List<WorkUnit> loadAllWorkUnitsByEmployer(User employer);

    public List<WorkUnit> loadAllWorkUnitsByEmployee(User employee);

    public List<WorkUnit> loadAllWorkUnitsByState(State state);

    public List<WorkUnit> loadAllWorkUnitsByEmployeeAndState(User employee, State state);
    
    public List<WorkUnit> loadAllWorkUnitsByEmployerAndState(User employer, State state);
    
    public WorkUnit saveWorkUnit(WorkUnit workUnit);
    
    public void deleteWorkUnit(WorkUnit workUnit);
}
