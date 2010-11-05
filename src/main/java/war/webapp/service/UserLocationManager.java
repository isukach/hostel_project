package war.webapp.service;

import java.util.List;

import org.appfuse.model.User;
import org.appfuse.service.GenericManager;

import war.webapp.model.UserLocation;

/**
 * Business Service Interface to handle communication between web and persistence layer.
 * 
 * @author <a href="mailto:tokefa@tut.by">kefa</a>
 */
public interface UserLocationManager extends GenericManager<UserLocation, Long> {
    /**
     * Gets all users based on floor.
     * 
     * @return userLocation populated UserLocation object
     */
    public List<UserLocation> getByFloor(Integer floor);

    /**
     * Gets UserLocation object based on user.
     * 
     * @return UserLocation populated UserLocation object
     */
    public UserLocation getByUser(User user);
}
