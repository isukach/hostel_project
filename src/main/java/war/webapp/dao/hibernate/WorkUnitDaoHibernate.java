package war.webapp.dao.hibernate;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import war.webapp.dao.WorkUnitDao;
import war.webapp.model.State;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;

@Repository
public class WorkUnitDaoHibernate extends GenericDaoHibernate<WorkUnit, Long> implements WorkUnitDao
{   
    
    public WorkUnitDaoHibernate() {
        super(WorkUnit.class);
    }

    public WorkUnitDaoHibernate(Class<WorkUnit> persistentClass) {
        super(persistentClass);
    }
    
    @SuppressWarnings("rawtypes")
    public WorkUnit loadWorkUnitById(Long id) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where id=?",id);
        if (workUnits == null || workUnits.isEmpty()) {
            return null;
        } else {
            return (WorkUnit) workUnits.get(0);
        }
    }
    
    @SuppressWarnings("rawtypes")
    public WorkUnit loadWorkUnitByDate(Calendar date) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where date=?",date);
        if (workUnits == null || workUnits.isEmpty()) {
            return null;
        } else {
            return (WorkUnit) workUnits.get(0);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployer(User employer) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where employer=?", employer);
        if (workUnits == null || workUnits.isEmpty()) {
            return new LinkedList<WorkUnit>();
        }
        List<WorkUnit> allWorkUnits = (List<WorkUnit>) workUnits;
        return allWorkUnits;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployee(User employee) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where employee=?", employee);
        if (workUnits == null || workUnits.isEmpty()) {
            return new LinkedList<WorkUnit>();
        }
        List<WorkUnit> allWorkUnits = (List<WorkUnit>) workUnits;
        return allWorkUnits;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByState(State state) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where state=?", state);
        if (workUnits == null || workUnits.isEmpty()) {
            return new LinkedList<WorkUnit>();
        }
        List<WorkUnit> allWorkUnits = (List<WorkUnit>) workUnits;
        return allWorkUnits;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployeeAndState(User employee, State state) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where employee=? and state=?", 
                new Object[] { employee, state });
        if (workUnits == null || workUnits.isEmpty()) {
            return new LinkedList<WorkUnit>();
        }
        List<WorkUnit> allWorkUnits = (List<WorkUnit>) workUnits;
        return allWorkUnits;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<WorkUnit> loadAllWorkUnitsByEmployerAndState(User employer, State state) {
        List workUnits = getHibernateTemplate().find("from WorkUnit where employer=? and state=?", 
                new Object[] { employer, state });
        if (workUnits == null || workUnits.isEmpty()) {
            return new LinkedList<WorkUnit>();
        }
        List<WorkUnit> allWorkUnits = (List<WorkUnit>) workUnits;
        return allWorkUnits;
    }
    
    public WorkUnit saveWorkUnit(WorkUnit workUnit) {
        if (log.isDebugEnabled()) {
            log.debug("user's id: " + workUnit.getId());
        }
        getHibernateTemplate().saveOrUpdate(workUnit);
        getHibernateTemplate().flush();
        return workUnit;
    }
    
    public void deleteWorkUnit(WorkUnit workUnit) {
        remove(workUnit.getId());
    }
}
