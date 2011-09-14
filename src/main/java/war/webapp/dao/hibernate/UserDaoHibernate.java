package war.webapp.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import war.webapp.dao.UserDao;
import war.webapp.model.User;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler</a> Extended to
 *         implement Acegi UserDetailsService interface by David Carter
 *         david@carter.net Modified by <a href="mailto:bwnoll@gmail.com">Bryan
 *         Noll</a> to work with the new BaseDaoHibernate implementation that
 *         uses generics.
 */
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserDaoHibernate() {
        super(User.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return getHibernateTemplate().find("from User u order by upper(u.username)");
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) {
        if (log.isDebugEnabled())
            log.debug("user's id: " + user.getId());
        getHibernateTemplate().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in
        // UserManager
        getHibernateTemplate().flush();
        return user;
    }

    /**
     * Overridden simply to call the saveUser method. This is happenening
     * because saveUser flushes the session and saveObject of BaseDaoHibernate
     * does not.
     * 
     * @param user the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    public User save(User user) {
        return this.saveUser(user);
    }

    /**
     * {@inheritDoc}
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        @SuppressWarnings("rawtypes")
        List users = getHibernateTemplate().find("from User where username=?", username);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getUserPassword(String username) {
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject("select password from " + table.name() + " where username=?", String.class,
                username);

    }

    public String getUserPasswordById(Long id) {
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject("select password from " + table.name() + " where id=?", String.class, id);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<User> loadUsersByFloor(String floor) {
        List users = getHibernateTemplate().find("from User where address.hostelFloor=? and accountLocked=false", floor);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("users on '" + floor + "' floor not found...");
        } else {
            return (List<User>) users;
        }
    }

    public User loadUserByRoomAndFullName(String room, String fullName) {
        int n = fullName.indexOf(' ');
        String lastName = fullName.substring(0, n);
        String firstLetterOfFirstName = fullName.substring(n + 1, n + 2);
        String firstLetterOfSecondName = fullName.substring(n + 4, n + 5);

        List users = getHibernateTemplate().find("from User where id=468");
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + fullName + "' not found...");
        } else {
            return (User) users.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> loadUsersByRoom(String room) {        
        List<User> users = (List<User>)getHibernateTemplate().find("from User where address.hostelRoom=?", room);
        if (users == null || users.isEmpty()) {
            log.warn("no users was found for given room, maybe incorrect room number");
            return new ArrayList<User>();
        } else {
            return users;
        }
    }
}
