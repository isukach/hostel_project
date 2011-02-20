package war.webapp.model;

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
