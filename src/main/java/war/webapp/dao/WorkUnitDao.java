package war.webapp.dao;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import war.webapp.model.State;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;

public interface WorkUnitDao extends GenericDao<WorkUnit, Long> {
    
    @SuppressWarnings("rawtypes")
    public WorkUnit loadWorkUnitById(Long id);
    
    @SuppressWarnings("rawtypes")
    public WorkUnit loadWorkUnitByDate(Calendar date);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployer(User employer);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployee(User employee);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByState(State state);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployeeAndState(User employee, State state);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployerAndState(User employer, State state);
    
    public WorkUnit saveWorkUnit(WorkUnit workUnit);
    
    public void deleteWorkUnit(WorkUnit workUnit);
}
