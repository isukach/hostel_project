package war.webapp.service.impl;

import java.util.List;

import org.appfuse.model.User;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import war.webapp.dao.UserLocationDao;
import war.webapp.model.UserLocation;
import war.webapp.service.UserLocationManager;

@Service("userLocationManager")
public class UserLocationManagerImpl extends GenericManagerImpl<UserLocation, Long> implements
        UserLocationManager {

    UserLocationDao userLocationDao;

    public UserLocationManagerImpl() {
    }

    @Autowired
    public UserLocationManagerImpl(UserLocationDao userLocationDao) {
        super(userLocationDao);
        this.userLocationDao = userLocationDao;
    }

    public List<UserLocation> getByFloor(Integer floor) {
        return userLocationDao.getByFloor(floor);
    }

    public UserLocation getByUser(User user) {
        return userLocationDao.getByUser(user);
    }
}
