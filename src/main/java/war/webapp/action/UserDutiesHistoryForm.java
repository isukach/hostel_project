package war.webapp.action;

import war.webapp.model.DayDuty;
import war.webapp.model.Duty;
import war.webapp.model.User;
import war.webapp.service.DayDutyManager;
import war.webapp.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDutiesHistoryForm extends BasePage {
    public static final int FIRST_SHIFT = 1;
    public static final int SECOND_SHIFT = 2;

    private String userId;
    private DayDutyManager dayDutyManager;
    private int dutiesCount;

    public List<Duty> getDutiesForUser() {
        User user = getUser();
        List<DayDuty> dayDutiesForUser = dayDutyManager.loadDutiesByUser(user);
        setDutiesCount(dayDutiesForUser.size());
        return makeListOfDuties(user, dayDutiesForUser);
    }

    private User getUser() {
        User user;
        if (userId == null) {
            user = userManager.getUserByUsername(getRequest().getRemoteUser());
        } else {
            user = userManager.getUser(userId);
        }
        return user;
    }

    private List<Duty> makeListOfDuties(User user, List<DayDuty> dayDuties) {
        List<Duty> duties = new ArrayList<Duty>();
        for (DayDuty dayDuty : dayDuties) {
            if (dayDuty.getFirstUser() != null && dayDuty.getFirstUser().equals(user)) {
                addNewDutyToList(duties, dayDuty, FIRST_SHIFT);
            }
            if (dayDuty.getSecondUser() != null && dayDuty.getSecondUser().equals(user)) {
                addNewDutyToList(duties, dayDuty, SECOND_SHIFT);
            }
        }
        return duties;
    }

    private void addNewDutyToList(List<Duty> duties, DayDuty dayDuty, int shift) {
        Duty duty = new Duty();

        String date = DateUtil.convertDateToString(dayDuty.getDate().getTime());
        duty.setDate(date);
        if (shift == FIRST_SHIFT) {
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDayDutyManager(DayDutyManager dayDutyManager) {
        this.dayDutyManager = dayDutyManager;
    }

    public int getDutiesCount() {
        return getDutiesForUser().size();
    }

    public void setDutiesCount(int dutiesCount) {
        //stub
    }
}
