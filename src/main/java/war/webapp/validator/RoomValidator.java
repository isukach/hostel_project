package war.webapp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.xml.bind.ValidationException;

import org.springframework.web.context.support.WebApplicationContextUtils;

import war.webapp.service.FloorManager;

public class RoomValidator implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();
        FloorManager floorManager = (FloorManager)WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean("floorManager");
        String room = (String)value;
        String floorNumber = room.substring(0,1);
        String roomNumberOnFloor = room.substring(1,3);
        if (!floorManager.getAvailableRooms().contains(roomNumberOnFloor) || !floorManager.getFloorsNames().contains(floorNumber)) {
            FacesMessage message = new FacesMessage("Room isn't exist, try again");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
