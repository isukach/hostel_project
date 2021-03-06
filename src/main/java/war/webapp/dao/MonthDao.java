package war.webapp.dao;

import war.webapp.model.DutyMonth;

public interface MonthDao extends GenericDao<DutyMonth, Long> {
    public DutyMonth loadMonth(Integer year, Integer month, String floor);

    public DutyMonth saveMonth(DutyMonth dutyMonth);
}
