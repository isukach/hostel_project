package war.webapp.model;

import org.appfuse.model.User;

public class EmptyUser extends User {
	
	public EmptyUser() {
		super();
		setFirstName("");
		setLastName("");
		setUsername("");
	}
	
	public boolean isEmptyUser() {
		return true;
	}

}
