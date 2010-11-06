package war.webapp.converter;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.appfuse.service.impl.UserManagerImpl;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Created by IntelliJ IDEA.
 * User: Андрей
 * Date: 29.9.2010
 * Time: 19.20.53
 * To change this template use File | Settings | File Templates.
 */
public class UserConverter implements Converter{

    private UserManager userManager = new UserManagerImpl();

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        Object ob = value;
        User user = (User)value;
        StringBuilder userInfo = new StringBuilder();
        userInfo.append(user.getUsername() + " ").append(user.getFirstName() + " ").append(user.getLastName());
        return userInfo.toString();
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        User user = userManager.getUserByUsername(value.split(" ")[0]);
        return user;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
