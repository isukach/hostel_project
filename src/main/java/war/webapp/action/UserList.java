package war.webapp.action;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;

import war.webapp.model.User;
import war.webapp.util.FacesUtils;

public class UserList extends BasePage implements Serializable {
    private static final long serialVersionUID = 972359310602744018L;

    public UserList() {

        setSortColumn("username");
    }

    public List<User> getUsers() {
        return (List<User>) sort(userManager.getUsers());
    }

    public List<User> getUsersByStarostaFloor() {
        User user = (User) ((SecurityContext) FacesUtils.getSession().getAttribute(
                HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY)).getAuthentication().getPrincipal();
        int floor = user.getAddress().getHostelFloor();
        return (List<User>) sort(userManager.getUsersByFloor(floor));
    }

    public int getCurrentUserFloor() {
        User user = (User) ((SecurityContext) FacesUtils.getSession().getAttribute(
                HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY)).getAuthentication().getPrincipal();
        int floor = user.getAddress().getHostelFloor();
        return floor;
    }
}
