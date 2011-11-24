package war.webapp.dao;

import war.webapp.model.DeletedUser;

public interface DeletedUserDao extends GenericDao<DeletedUser, Long> {
    public DeletedUser saveDeletedUser(DeletedUser object);
}
