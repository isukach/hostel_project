package war.webapp.service;

import java.util.Calendar;
import java.util.List;

import war.webapp.model.DayDuty;

public interface DayDutyManager extends GenericManager<DayDuty, Long> {

    public DayDuty loadDayDutyByDateAndFloor(Calendar date, Integer floor);

    public List<DayDuty> loadAllDayDutyByDateAndFloor(Integer month, Integer floor);

    public DayDuty saveDayDuty(DayDuty dayDuty);

    public void deleteDayDuty(DayDuty dayDuty);
    
    public void deleteFirstDutyUser(DayDuty dayDuty);
    
    public void deleteSecondDutyUser(DayDuty dayDuty);
}
