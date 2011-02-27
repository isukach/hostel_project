package war.webapp.dao;

import java.util.List;

import war.webapp.model.Role;

/**
 * Lookup Data Access Object (GenericDao) interface. This is used to lookup
 * values in the database (i.e. for drop-downs).
 * 
 */
public interface LookupDao {
    // ~ Methods
    // ================================================================

    /**
     * Returns all Roles ordered by name
     * 
     * @return populated list of roles
     */
    List<Role> getRoles();
}
