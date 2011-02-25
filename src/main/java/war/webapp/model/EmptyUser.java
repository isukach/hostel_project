package war.webapp.model;

public class EmptyUser extends User {
    private static final long serialVersionUID = -424748657288071329L;

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
