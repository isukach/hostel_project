package war.webapp.dao.hibernate;

import org.springframework.stereotype.Repository;

import war.webapp.dao.DeletedUserDao;
import war.webapp.model.DeletedUser;

@Repository("deletedUserDao")
public class DeletedUserDaoHibernate extends GenericDaoHibernate<DeletedUser, Long> implements DeletedUserDao {
    
    public DeletedUserDaoHibernate() {
        super(DeletedUser.class);
    }
    
    public DeletedUser saveDeletedUser(DeletedUser user) {
        getHibernateTemplate().saveOrUpdate(user);
        getHibernateTemplate().flush();
        
        return user;
    }

}
