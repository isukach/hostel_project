package war.webapp.dao.hibernate;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import war.webapp.dao.MonthDao;
import war.webapp.model.DutyMonth;

import java.util.List;

@Repository
public class MonthDaoHibernate extends GenericDaoHibernate<DutyMonth, Long> implements MonthDao {

    public MonthDaoHibernate() {
        super(DutyMonth.class);
    }

    public DutyMonth loadMonth(Integer year, Integer month, Integer floor) {
        List months = getHibernateTemplate().find("from DutyMonth where year=? and month=? and floor=?",
                new Object[]{year, month, floor});
        if (months == null || months.isEmpty()) {
            return null;
        }
        return (DutyMonth) months.get(0);
    }

    public DutyMonth saveMonth(DutyMonth dutyMonth) {
        if (log.isDebugEnabled()) {
            log.debug("month id = " + dutyMonth.getId());
        }
        getHibernateTemplate().saveOrUpdate(dutyMonth);
        getHibernateTemplate().flush();
        return dutyMonth;
    }
}
