package war.webapp.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import war.webapp.dao.UserLocationDao;
import war.webapp.model.User;
import war.webapp.model.UserLocation;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and retrieve UserLocation
 * objects.
 * 
 * @author <a href="mailto:tokefa@tut.by">kefa</a>
 */
@Repository
public class UserLocationDaoHibernate extends GenericDaoHibernate<UserLocation, Long> implements
        UserLocationDao {

    public UserLocationDaoHibernate() {
        super(UserLocation.class);
    }

    @SuppressWarnings("unchecked")
    public List<UserLocation> getByFloor(Integer floor) {
        return getHibernateTemplate().find("from UserLocation where floor=?", floor);
    }

    @SuppressWarnings("rawtypes")
    public UserLocation getByUser(User user) {
        List userLocations = getHibernateTemplate().find("from UserLocation where user_id=?",
                user.getId());
        if (userLocations == null || userLocations.isEmpty()) {
            return null;
        }
        return (UserLocation) userLocations.get(0);
    }

}
