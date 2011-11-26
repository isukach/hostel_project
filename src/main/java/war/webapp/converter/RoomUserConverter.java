package war.webapp.converter;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import war.webapp.model.EmptyUser;
import war.webapp.model.User;
import war.webapp.service.FloorManager;
import war.webapp.service.UserManager;

public class RoomUserConverter implements Converter {
    
    public static String EMPTY_USER_ID = "-1";

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        User user = new EmptyUser();
        if (value != null && !value.isEmpty() && !value.equals(EMPTY_USER_ID)) {
            ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();
            UserManager userManager = (UserManager)WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean("userManager");
            user = userManager.get(Long.parseLong(value));
        }
        return user;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (value == null) {
            return null;
        }
        if (value instanceof EmptyUser) {
            return EMPTY_USER_ID;
        }
        return ((User)value).getId().toString();
    }

}
