package war.webapp.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import war.webapp.dao.DeletedUserDao;
import war.webapp.model.DeletedUser;
import war.webapp.service.DeletedUserManager;

@Service("deleteUserManager")
public class DeleteUserManagerImpl extends GenericManagerImpl<DeletedUser, Long> implements DeletedUserManager {
    private DeletedUserDao deletedUserDao;

    public DeletedUserDao getDeletedUserDao() {
        return deletedUserDao;
    }

    @Autowired
    public void setDeletedUserDao(DeletedUserDao deletedUserDao) {
        this.deletedUserDao = deletedUserDao;
    }
    
    public DeletedUser saveDeletedUser(DeletedUser user) {
        return deletedUserDao.saveDeletedUser(user);
    }
    
}
