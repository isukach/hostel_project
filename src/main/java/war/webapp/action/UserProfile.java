package war.webapp.action;

import war.webapp.model.User;
import war.webapp.service.UserManager;

import java.io.Serializable;

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
    private UserManager userManager;

    public String getId() {
        return id;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public User getUser() {
         if (user == null) {
            setUser((User) getContext().getAuthentication().getPrincipal());
        }
        return user;
    }


    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
