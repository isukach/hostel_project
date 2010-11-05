package war.webapp.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;
import org.appfuse.model.User;

import war.webapp.model.UserLocation;

/**
 * UserLocation Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:tokefa@tut.by">kefa</a>
 */
public interface UserLocationDao extends GenericDao<UserLocation, Long> {

    /**
     * Gets all users based on floor.
     * @return userLocation populated UserLocation object
     */
    public List<UserLocation> getByFloor(Integer floor);

    /**
     * Gets UserLocation object based on user.
     * @return UserLocation populated UserLocation object
     */
    public UserLocation getByUser(User user);
}
