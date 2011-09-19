<<<<<<< Updated upstream
package war.webapp.service.impl;

import org.springframework.stereotype.Service;
import war.webapp.model.DutyRemark;
import war.webapp.service.RemarkManager;

@Service("remarkManager")
public class RemarkManagerImpl extends GenericManagerImpl<DutyRemark, Long> implements RemarkManager {

    public void saveDutyRemark(DutyRemark dutyRemark) {

    }
}
=======
package war.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import war.webapp.dao.RemarkDao;
import war.webapp.model.DutyRemark;
import war.webapp.service.RemarkManager;

@Service("remarkManager")
public class RemarkManagerImpl extends GenericManagerImpl<DutyRemark, Long> implements RemarkManager {
    RemarkDao remarkDao;

    @Autowired
    public RemarkManagerImpl(RemarkDao remarkDao) {
        super(remarkDao);
        this.remarkDao = remarkDao;
    }

    public DutyRemark saveDutyRemark(DutyRemark dutyRemark) {
        remarkDao.saveDutyRemark(dutyRemark);
        return dutyRemark;
    }
}

>>>>>>> Stashed changes
