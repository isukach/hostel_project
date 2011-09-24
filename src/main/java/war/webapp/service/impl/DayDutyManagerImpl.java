package war.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import war.webapp.dao.DayDutyDao;
import war.webapp.model.DayDuty;
import war.webapp.service.DayDutyManager;

import java.util.Calendar;
import java.util.List;

public class DayDutyManagerImpl extends GenericManagerImpl<DayDuty, Long> implements DayDutyManager {
    DayDutyDao dayDutyDao;

    public DayDutyManagerImpl() {
    }

    @Autowired
    public DayDutyManagerImpl(DayDutyDao dayDutyDao) {
        super(dayDutyDao);
        this.dayDutyDao = dayDutyDao;
    }

    public DayDuty loadDayDutyByDateAndFloor(Calendar date, String floor) {
        return dayDutyDao.loadDayDutyByDateAndFloor(date, floor);
    }

    public List<DayDuty> loadAllDayDutyByMonthAndFloor(Integer month, String floor) {
        return dayDutyDao.loadAllDayDutyByMonthAndFloor(month, floor);
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

    public void deleteFirstDutyUser(DayDuty dayDuty) {
        dayDutyDao.deleteFirstDutyUser(dayDuty);
    }

    public void deleteSecondDutyUser(DayDuty dayDuty) {
        dayDutyDao.deleteSecondDutyUser(dayDuty);        
    }

    public List<DayDuty> loadDutiesByUserId(Long userId) {
        return dayDutyDao.loadDutiesByUserId(userId);
    }
}
