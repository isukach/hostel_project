package war.webapp.service;

import org.appfuse.service.GenericManager;
import war.webapp.model.DutyMonth;

public interface MonthManager extends GenericManager<DutyMonth, Long> {
    public DutyMonth loadMonth(Integer year, Integer month, Integer floor);

    public DutyMonth saveMonth(DutyMonth dutyMonth);
}
