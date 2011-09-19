package war.webapp.action;


import war.webapp.model.DayDuty;
import war.webapp.model.DutyRemark;
import war.webapp.service.DayDutyManager;
import war.webapp.service.RemarkManager;

import javax.faces.event.ActionEvent;

public class RemarkForm extends BasePage {
    private String remark;
    private Long dayDutyId;
    private int shift;
    private RemarkManager remarkManager;
    private DayDutyManager dayDutyManager;


    public void addRemarkToUser(ActionEvent e) {
        DutyRemark dutyRemark = new DutyRemark();
        DayDuty dayDuty = dayDutyManager.get(dayDutyId);
        dutyRemark.setDayDuty(dayDuty);
        dutyRemark.setRemark(remark);
        dutyRemark.setShift(shift);

        remarkManager.saveDutyRemark(dutyRemark);
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

    public RemarkManager getRemarkManager() {
        return remarkManager;
    }

    public void setRemarkManager(RemarkManager remarkManager) {
        this.remarkManager = remarkManager;
    }

    public DayDutyManager getDayDutyManager() {
        return dayDutyManager;
    }

    public void setDayDutyManager(DayDutyManager dayDutyManager) {
        this.dayDutyManager = dayDutyManager;
    }
}
