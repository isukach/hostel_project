package war.webapp.action;

import war.webapp.model.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserList extends BasePage implements Serializable {
    private static final long serialVersionUID = 972359310602744018L;

    public UserList() {
        setSortColumn("username");
    }

    public List<User> getUsersByRoom() {
        return sortByRoom(userManager.getUsers());
    }

    public List<User> getUsersByFloorheadFloor() {
        List<User> floorUsers = userManager.getUsersByFloor(getCurrentUserFloor());
        return excludeCurrentUserAndSort(floorUsers);
    }

    public List<User> getMovedOutUsersByFloorOfCurrentUser() {
        List<User> movedOutUsers = userManager.getMovedOutUsersByFloor(getCurrentUserFloor());
        return excludeCurrentUserAndSort(movedOutUsers);
    }

    private List<User> excludeCurrentUserAndSort(List<User> users) {
        users.remove(getCurrentUser());
        return sortByRoom(users);
    }

    public List<User> getAllUsers() {
        return userManager.getAll();
    }

    private List<User> sortByRoom(List<User> users) {
        Collections.sort(users, new Comparator<User>() {
            public int compare(User u1, User u2) {
                return u1.getAddress().getHostelRoom().compareToIgnoreCase(u2.getAddress().getHostelRoom());
            }
        });
        return users;
    }

    public String getCurrentUserFloor() {
        return getFloorOf(getCurrentUser());
    }
}