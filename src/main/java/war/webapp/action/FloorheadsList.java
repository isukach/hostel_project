package war.webapp.action;

import war.webapp.model.User;

import java.io.Serializable;
import java.util.List;

public class FloorheadsList extends BasePage implements Serializable {
    private static final long serialVersionUID = 972359310602744018L;


    public List<User> getUsers() {
        return userManager.getAllFloorheads();
    }
}
