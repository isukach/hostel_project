package war.webapp.action;

import java.io.Serializable;
import java.util.List;

import war.webapp.model.User;

public class UserList extends BasePage implements Serializable {
    private static final long serialVersionUID = 972359310602744018L;

    public UserList() {
        setSortColumn("username");
    }

    public List<User> getUsers() {
        return (List<User>) sort(userManager.getUsers());
    }
}
