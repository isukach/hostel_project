package war.webapp.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import war.webapp.model.User;

public class EmployeeValidator implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value instanceof User) {
            ((UIInput)component).setValid(true);
        }
    }

}
