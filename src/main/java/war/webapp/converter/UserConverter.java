package war.webapp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import war.webapp.model.User;
import war.webapp.service.UserManager;
import war.webapp.service.impl.UserManagerImpl;

public class UserConverter implements Converter{

    private UserManager userManager = new UserManagerImpl();

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
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
