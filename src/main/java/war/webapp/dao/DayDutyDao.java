package war.webapp.dao;

import war.webapp.model.DayDuty;
import war.webapp.model.User;

import java.util.Calendar;
import java.util.List;

public interface DayDutyDao extends GenericDao<DayDuty, Long> {

    public DayDuty loadDayDutyByDateAndFloor(Calendar date, String floor);

    public List<DayDuty> loadAllDayDutyByMonthAndFloor(Integer month, String floor);

    public DayDuty saveDayDuty(DayDuty dayDuty);

    public void deleteDayDuty(DayDuty dayDuty);

    public void deleteFirstDutyUser(DayDuty dayDuty);

    public void deleteSecondDutyUser(DayDuty dayDuty);
    
    public DayDuty loadSingleDayDutyByExample(DayDuty exampleDayDuty);

    public List<DayDuty> loadDutiesByUser(User user);

}
