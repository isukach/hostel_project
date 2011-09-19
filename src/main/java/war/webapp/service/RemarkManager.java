package war.webapp.service;

import war.webapp.model.DutyRemark;

public interface RemarkManager extends GenericManager<DutyRemark, Long> {
    public DutyRemark saveDutyRemark(DutyRemark dutyRemark);
}