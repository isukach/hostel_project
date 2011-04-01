package war.webapp.service;

import war.webapp.model.DutyMonth;

public interface MonthManager extends GenericManager<DutyMonth, Long> {
    public DutyMonth loadMonth(Integer year, Integer month, String floor);

    public DutyMonth saveMonth(DutyMonth dutyMonth);
}
