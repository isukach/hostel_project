package war.webapp.action;

import org.appfuse.model.User;

import java.io.Serializable;
import java.util.List;

public class UserList extends BasePage implements Serializable {
    private static final long serialVersionUID = 972359310602744018L;

    public UserList() {
        setSortColumn("username");
    }

    public List<User> getUsers() {
        return (List<User>) sort(userManager.getUsers());
    }
}
