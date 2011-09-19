package war.webapp.service.impl;

import org.springframework.stereotype.Service;
import war.webapp.model.DutyRemark;
import war.webapp.service.RemarkManager;

@Service("remarkManager")
public class RemarkManagerImpl extends GenericManagerImpl<DutyRemark, Long> implements RemarkManager {

    public void saveDutyRemark(DutyRemark dutyRemark) {

    }
}
