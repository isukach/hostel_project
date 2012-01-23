package war.webapp.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import war.webapp.dao.UserDao;
import war.webapp.model.DeletedUser;
import war.webapp.model.User;
import war.webapp.service.DeletedUserManager;
import war.webapp.service.UserExistsException;
import war.webapp.service.UserManager;
import war.webapp.service.UserService;

/**
 * Implementation of UserManager interface.
 * 
 */
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "war.webapp.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private DeletedUserManager deletedUserManager;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }

    @Autowired
    public void setDeletedUserManager(DeletedUserManager deletedUserManager) {
        this.deletedUserManager = deletedUserManager;
    }

    /**
     * {@inheritDoc}
     */
    public User getUser(String userId) {
        return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }
    
    public DeletedUserManager getDeletedUserManager() {
        return deletedUserManager;
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws UserExistsException {

        if (user.getVersion() == null) {
            user.setId(null);
            // if new user, lowercase userId
            user.setPassword("pass");
            user.setUsername(user.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                String currentPassword = userDao.getUserPasswordById(user.getId());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (JpaSystemException e) { // needed for JPA
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * Create {@link DeletedUser} object from {@link User} object, remove user from db
     * and save deletedUser to db
     */
    public void removeUser(String userId) {
        log.debug("remove user: " + userId + " create deletedUser object from user and save");
        Long id = new Long(userId);
        
        //User user = get(id);
        //DeletedUser deletedUser = new DeletedUser();
        
        //copyUserDate(deletedUser, user);
       // deletedUser.setDeleteDate(Calendar.getInstance());        
        //getDeletedUserManager().saveDeletedUser(deletedUser);
        
        userDao.remove(id);
    }

    /**
     * Copy all properties from {@link User} object to {@link DeletedUser}
     * @param deletedUser {@link DeletedUser} object to copy to
     * @param user {@link User} object to copy from
     */
    /*private void copyUserDate(DeletedUser deletedUser, User user) {
        
        deletedUser.setUsername(user.getUsername());
        deletedUser.setPassword(user.getPassword());
        
        deletedUser.setFirstName(user.getFirstName());
        deletedUser.setLastName(user.getLastName());
        deletedUser.setMiddleName(user.getMiddleName());
        
        deletedUser.setDepartment(user.getDepartment());
        deletedUser.setAddress(user.getAddress());
        deletedUser.setEmail(user.getEmail());
        deletedUser.setPhoneNumber(user.getPhoneNumber());
        
        deletedUser.setUniversityGroup(user.getUniversityGroup());
        
        deletedUser.setBirthdayDayOfMonth(user.getBirthdayDayOfMonth());
        deletedUser.setBirthdayMonth(user.getBirthdayMonth());
        deletedUser.setBirthdayYear(user.getBirthdayYear());
        deletedUser.setDateOfBirth(user.getDateOfBirth());        
        
        deletedUser.setFreePayStudy(user.isFreePayStudy());
        deletedUser.setImagePath(user.getImagePath());

        deletedUser.setRoles(user.getRoles());
    }*/

    /**
     * {@inheritDoc}
     * 
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }

    public List<User> getUsersByFloor(String floor) {
        List<User> result = userDao.loadUsersByFloor(floor);
        return result;
    }
    
    public List<User> getMovedOutUsersByFloor(String floor) {
        List<User> result = userDao.loadMovedOutUsersByFloor(floor);
        return result;
    }

    public User getUserByRoomAndFullName(String room, String fullName) {
        return userDao.loadUserByRoomAndFullName(room, fullName);
    }

    public List<User> getUsersByRoom(String room) {
        return userDao.loadUsersByRoom(room);
    }

    public int getNumberOfFloorUsers(String floor) {
        return userDao.getNumberOfFloorUsers(floor);
    }
}
