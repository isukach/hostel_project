package war.webapp.action;

import war.webapp.listener.UserCounterListener;
import war.webapp.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ActiveUserList extends BasePage implements Serializable {
    private static final long serialVersionUID = -2725378005612769815L;

    public ActiveUserList() {
        setSortColumn("username");
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        Set<User> users = (Set<User>) getServletContext().getAttribute(UserCounterListener.USERS_KEY);
        if (users != null) {
            return (List<User>) sort(new ArrayList<User>(users));
        } else {
            return new ArrayList<User>();
        }
    }
}
