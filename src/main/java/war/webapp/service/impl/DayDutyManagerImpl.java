package war.webapp.service.impl;

import java.util.Date;
import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import war.webapp.dao.DayDutyDao;
import war.webapp.model.DayDuty;
import war.webapp.service.DayDutyManager;

public class DayDutyManagerImpl extends GenericManagerImpl<DayDuty, Long> implements DayDutyManager {
    DayDutyDao dayDutyDao;
    
    public DayDutyManagerImpl() {
    }

    @Autowired
    public DayDutyManagerImpl(DayDutyDao dayDutyDao) {
        super(dayDutyDao);
        this.dayDutyDao = dayDutyDao;
    }

    public DayDuty loadDayDutyByDateAndFloor(Date date, Integer floor) {
        return dayDutyDao.loadDayDutyByDateAndFloor(date, floor);
    }

    public List<DayDuty> loadAllDayDutyByDateAndFloor(Integer month, Integer floor) {
        return dayDutyDao.loadAllDayDutyByDateAndFloor(month, floor);
    }

    public DayDuty saveDayDuty(DayDuty dayDuty) {
        return dayDutyDao.saveDayDuty(dayDuty);
    }

    public void deleteDayDuty(DayDuty dayDuty) {
        dayDutyDao.deleteDayDuty(dayDuty);
    }

    public DayDutyDao getDayDutyDao() {
        return dayDutyDao;
    }

    public void setDayDutyDao(DayDutyDao dayDutyDao) {
        this.dayDutyDao = dayDutyDao;
    }
}
