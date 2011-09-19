package war.webapp.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import war.webapp.dao.WorkUnitDao;
import war.webapp.model.State;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;
import war.webapp.service.WorkUnitManager;

public class WorkUnitManagerImpl extends GenericManagerImpl<WorkUnit, Long> implements WorkUnitManager{
    
    WorkUnitDao workUnitDao;
    
    public WorkUnitManagerImpl() {
        
    }
    
    @Autowired
    public WorkUnitManagerImpl(WorkUnitDao workUnitDao) {
        super(workUnitDao);
        this.workUnitDao = workUnitDao;
    }
    
    public WorkUnit loadWorkUnitById(Long id) {
        return workUnitDao.loadWorkUnitById(id);
    }
    

    public WorkUnit loadWorkUnitByDate(Calendar date) {
        return workUnitDao.loadWorkUnitByDate(date);
    }
    

    public List<WorkUnit> loadAllWorkUnitsByEmployer(User employer) {
        return workUnitDao.loadAllWorkUnitsByEmployer(employer);
    }
    

    public List<WorkUnit> loadAllWorkUnitsByEmployee(User employee) {
        return workUnitDao.loadAllWorkUnitsByEmployee(employee);
    }
    

    public List<WorkUnit> loadAllWorkUnitsByState(State state) {
        return workUnitDao.loadAllWorkUnitsByState(state);
    }
    

    public List<WorkUnit> loadAllWorkUnitsByEmployeeAndState(User employee, State state) {
        return workUnitDao.loadAllWorkUnitsByEmployeeAndState(employee, state);
    }
    
    public List<WorkUnit> loadAllWorkUnitsByEmployerAndState(User employer, State state){
        return workUnitDao.loadAllWorkUnitsByEmployerAndState(employer, state);
    }
    
    public WorkUnit saveWorkUnit(WorkUnit workUnit) {
        return workUnitDao.saveWorkUnit(workUnit);

    }
    
    public void deleteWorkUnit(WorkUnit workUnit) {
        workUnitDao.deleteWorkUnit(workUnit);
    }

    public WorkUnitDao getWorkUnitDao() {
        return workUnitDao;
    }

    public void setWorkUnitDao(WorkUnitDao workUnitDao) {
        this.workUnitDao = workUnitDao;
    }

    
}
