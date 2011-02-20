package war.webapp.model;

public class EmptyUserLocation extends UserLocation {
	
	public EmptyUserLocation() {
		super();
		setUniversityGroup("");
		setRoom("");
	}
	
	public boolean isEmptyUserLocation() {
		return true;
	}

}
