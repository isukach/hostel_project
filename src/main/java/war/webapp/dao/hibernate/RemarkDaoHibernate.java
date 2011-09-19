package war.webapp.dao.hibernate;

import org.springframework.stereotype.Repository;
import war.webapp.dao.RemarkDao;
import war.webapp.model.DutyRemark;

@Repository
public class RemarkDaoHibernate extends GenericDaoHibernate<DutyRemark, Long> implements RemarkDao {
    public RemarkDaoHibernate() {
        super(DutyRemark.class);
    }

    public DutyRemark saveDutyRemark(DutyRemark dutyRemark) {
        getHibernateTemplate().saveOrUpdate(dutyRemark);
        getHibernateTemplate().flush();
        return dutyRemark;
    }
}
