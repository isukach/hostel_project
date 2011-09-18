package war.webapp.action;


import war.webapp.model.DutyRemark;

import javax.faces.event.ActionEvent;

public class RemarkPage extends BasePage {
    private String remark;
    private Long dayDutyId;
    private int shift;

    public void addRemarkToUser(ActionEvent e) {
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
}
