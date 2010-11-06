package war.webapp.service;

import java.util.Date;
import java.util.List;

import org.appfuse.service.GenericManager;

import war.webapp.model.DayDuty;

/**
 * Business Service Interface to handle communication between web and persistence layer.
 * 
 * @author <a href="mailto:tokefa@tut.by">kefa</a>
 */
public interface DayDutyManager extends GenericManager<DayDuty, Long> {
    /**
     * Gets duty day information based on day and floor.
     * 
     * @param date
     * @param floor day
     * @return dutyDay populated dutyDay object
     */
    public DayDuty loadDayDutyByDateAndFloor(Date date, Integer floor);

    public List<DayDuty> loadAllDayDutyByDateAndFloor(Integer month, Integer floor);

    public DayDuty saveDayDuty(DayDuty dayDuty);

    public void deleteDayDuty(DayDuty dayDuty);
}
