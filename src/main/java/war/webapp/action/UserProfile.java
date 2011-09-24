package war.webapp.action;

import war.webapp.dao.WorkUnitDao;
import war.webapp.dao.hibernate.WorkUnitDaoHibernate;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;
import war.webapp.service.UserManager;
import war.webapp.service.WorkUnitManager;

import java.io.Serializable;
import java.util.List;

import static org.springframework.security.context.SecurityContextHolder.getContext;

/**
 * Created by IntelliJ IDEA.
 * User: AndreyM
 * Date: 11.9.11
 * Time: 16.28
 */
public class UserProfile extends BasePage implements Serializable{
    private static final long serialVersionUID = -1141119853856834204L;
    private User user;
    private String id;
    private WorkUnitManager workUnitManager;

    public String getId() {
        return id;
    }

    public WorkUnitManager getWorkUnitManager() {
        return workUnitManager;
    }

    public void setWorkUnitManager(WorkUnitManager workUnitManager) {
        this.workUnitManager = workUnitManager;
    }

    public User getUser() {
         if (user == null) {
            setUser((User) getContext().getAuthentication().getPrincipal());
        }
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public List<WorkUnit> getAllWorkUnits() {
        setSortColumn("date");
        setAscending(false);
        return workUnitManager.loadAllWorkUnitsByEmployee(getUser());
    }
}
