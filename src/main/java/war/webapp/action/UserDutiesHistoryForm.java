package war.webapp.action;

import war.webapp.model.DayDuty;
import war.webapp.model.Duty;
import war.webapp.service.DayDutyManager;

import java.util.ArrayList;
import java.util.List;

public class UserDutiesHistoryForm extends BasePage {
    private Long userId;
    private DayDutyManager dayDutyManager;

    public List<Duty> getDutiesForUser() {
        if (userId == null) {
            userId = userManager.getUserByUsername(getRequest().getRemoteUser()).getId();
        }

        List<DayDuty> dayDuties = dayDutyManager.loadDutiesByUserId(userId);
        List<Duty> duties = new ArrayList<Duty>();
        for (DayDuty dayDuty : dayDuties) {
            if (dayDuty.getFirstUser().getId().equals(userId)) {
                addNewDutyToList(duties, dayDuty, 1);
            }
            if (dayDuty.getSecondUser().getId().equals(userId)) {
                addNewDutyToList(duties, dayDuty, 2);
            }
        }
        return duties;
    }

    private void addNewDutyToList(List<Duty> duties, DayDuty dayDuty, int shift) {
        Duty duty = new Duty();

        duty.setDate(dayDuty.getDate());
        if (shift == 1) {
            duty.setTime("8:00-16:00");
            duty.setRemarks(dayDuty.getFirstUserRemarks());
            duty.setPunishment(dayDuty.getFirstUserPunishment());
        } else {
            duty.setTime("16:00-24:00");
            duty.setRemarks(dayDuty.getSecondUserRemarks());
            duty.setPunishment(dayDuty.getSecondUserPunishment());
        }

        duties.add(duty);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDayDutyManager(DayDutyManager dayDutyManager) {
        this.dayDutyManager = dayDutyManager;
    }
}
