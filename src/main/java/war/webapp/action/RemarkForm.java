package war.webapp.action;


import war.webapp.model.DayDuty;
import war.webapp.service.DayDutyManager;

import javax.faces.event.ActionEvent;

public class RemarkForm extends BasePage {
    private String remark;
    private Long dayDutyId;
    private int shift;
    private DayDutyManager dayDutyManager;


    public void addRemarkToUser(ActionEvent e) {
        DayDuty dayDuty = dayDutyManager.get(dayDutyId);
        if (shift == 1) {
            if (dayDuty.getFirstUserRemarks() != null) {
                dayDuty.setFirstUserRemarks(dayDuty.getFirstUserRemarks() + "; " + remark);
            } else {
                dayDuty.setFirstUserRemarks(remark);
            }
        } else {
            if (dayDuty.getSecondUserRemarks() != null) {
                dayDuty.setSecondUserRemarks(dayDuty.getSecondUserRemarks() + "; " + remark);
            } else {
                dayDuty.setSecondUserRemarks(remark);
            }
        }
        dayDutyManager.saveDayDuty(dayDuty);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDayDutyId() {
        return dayDutyId;
    }

    public void setDayDutyId(Long dayDutyId) {
        this.dayDutyId = dayDutyId;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public void setDayDutyManager(DayDutyManager dayDutyManager) {
        this.dayDutyManager = dayDutyManager;
    }
}
