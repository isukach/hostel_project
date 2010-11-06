package war.webapp.dao;

import java.util.Date;
import java.util.List;

import org.appfuse.dao.GenericDao;

import war.webapp.model.DayDuty;

/**
 * DutyDay Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:tokefa@tut.by">kefa</a>
 */
public interface DayDutyDao extends GenericDao<DayDuty, Long> {

    /**
     * Gets duty day information based on day and floor.
     * @param date duty day
     * @param floor floor
     * @return dutyDay populated dutyDay object
     */
    public DayDuty loadDayDutyByDateAndFloor(Date date, Integer floor);

    public List<DayDuty> loadAllDayDutyByDateAndFloor(Integer month, Integer floor);

    public DayDuty saveDayDuty(DayDuty dayDuty);

    public void deleteDayDuty(DayDuty dayDuty);

}
