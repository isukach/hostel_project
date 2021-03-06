package war.webapp.service;

import org.springframework.security.userdetails.UsernameNotFoundException;
import war.webapp.dao.UserDao;
import war.webapp.model.User;

import java.util.List;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface UserManager extends GenericManager<User, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on
     * an interface.
     * 
     * @param userDao the UserDao implementation to use
     */
    void setUserDao(UserDao userDao);

    /**
     * Retrieves a user by userId. An exception is thrown if user not found
     * 
     * @param userId the identifier for the user
     * @return User
     */
    User getUser(String userId);

    /**
     * Finds a user by their username.
     * 
     * @param username the user's username used to login
     * @return User a populated user object
     * @throws org.springframework.security.userdetails.UsernameNotFoundException
     *             exception thrown when user not found
     */
    User getUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves a list of all users.
     * 
     * @return List
     */
    List<User> getUsers();

    /**
     * Saves a user's information.
     * 
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    User saveUser(User user) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     * 
     * @param userId the user's id
     */
    void removeUser(String userId);

    /**
     * Returns user of given floor
     * 
     * @param floor number of floor
     */
    List<User> getUsersByFloor(String floor);
    
    /**
     * Returns moved out user of given floor
     * 
     * @param floor number of floor
     */
    List<User> getMovedOutUsersByFloor(String floor);

    /**
     * Returns user of given room and full name
     * 
     * @param room room number
     * @param fullName full name of user
     */
    User getUserByRoomAndFullName(String room, String fullName);
    
    /**
     * Returns users of given room
     * 
     * @param room room number
     */
    List<User> getUsersByRoom(String room);
    
    /**
     * Returns number of users on floorhead floor
     * 
     * @param floor floor number
     * @return amount of users
     */
    int getNumberOfFloorUsers(String floor);

    List<User> getAllFloorheads();

}
