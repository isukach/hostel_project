package war.webapp.service;

import war.webapp.model.DayDuty;
import war.webapp.model.User;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DayDutyManagerStub implements DayDutyManager {
    public DayDuty loadDayDutyByDateAndFloor(Calendar date, String floor) {
        return null;
    }

    public List<DayDuty> loadAllDayDutyByDateAndFloor(Integer year, Integer month, String floor) {
        return Collections.emptyList();
    }

    public DayDuty saveDayDuty(DayDuty dayDuty) {
        return null;
    }

    public void deleteDayDuty(DayDuty dayDuty) {
    }

    public void deleteFirstDutyUser(DayDuty dayDuty) {
    }

    public void deleteSecondDutyUser(DayDuty dayDuty) {
    }

    public List<DayDuty> loadDutiesByUser(User user) {
        return null;
    }

    public List<DayDuty> getAll() {
        return null;
    }

    public DayDuty get(Long id) {
        return null;
    }

    public boolean exists(Long id) {
        return false;
    }

    public DayDuty save(DayDuty object) {
        return null;
    }

    public void remove(Long id) {
    }
}
