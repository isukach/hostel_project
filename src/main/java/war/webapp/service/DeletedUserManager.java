package war.webapp.service;

import war.webapp.model.DeletedUser;

public interface DeletedUserManager extends GenericManager<DeletedUser, Long> {
    DeletedUser saveDeletedUser(DeletedUser user);
}
