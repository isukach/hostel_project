package war.webapp.dao;

import org.appfuse.dao.GenericDao;
import war.webapp.model.DutyMonth;

public interface MonthDao extends GenericDao<DutyMonth, Long> {
    public DutyMonth loadMonth(Integer year, Integer month, Integer floor);

    public DutyMonth saveMonth(DutyMonth dutyMonth);
}
