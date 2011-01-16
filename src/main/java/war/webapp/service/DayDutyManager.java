package war.webapp.service;

import java.util.Date;
import java.util.List;

import war.webapp.model.DayDuty;


public interface DayDutyManager extends GenericManager<DayDuty, Long> {
    
    public DayDuty loadDayDutyByDateAndFloor(Date date, Integer floor);

    public List<DayDuty> loadAllDayDutyByDateAndFloor(Integer month, Integer floor);

    public DayDuty saveDayDuty(DayDuty dayDuty);

    public void deleteDayDuty(DayDuty dayDuty);
}
