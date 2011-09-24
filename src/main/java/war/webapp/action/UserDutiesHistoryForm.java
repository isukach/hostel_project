package war.webapp.action;

import war.webapp.model.DayDuty;

import java.util.Collections;
import java.util.List;

public class UserDutiesHistoryForm extends BasePage {
    private String userId;

    public List<DayDuty> getDutiesHistory() {
        if (userId == null) {
            return Collections.emptyList();
        }
        return null;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
