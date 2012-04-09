package war.webapp.action;

import org.junit.Test;
import war.webapp.service.DayDutyManagerStub;
import war.webapp.util.MonthHelper;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;

public class DutyListTest {
    @Test
    public void defaultMonthShouldBeCurrent() {
        DutyList dl = new DutyList();
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        assertEquals(currentMonth, dl.getMonth().intValue());
    }

    @Test
    public void defaultYearShouldBeCurrent() {
        DutyList dl = new DutyList();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        assertEquals(currentYear, dl.getYear().intValue());
    }

    @Test
    public void dutyListForAnyMonthMustHasSizeAsDaysInMonth() {
        DutyList dl = new DutyList();
        dl.setDayDutyManager(new DayDutyManagerStub());
        dl.setFloor("");
        for (int month = 0; month < 12; month++) {
            dl.setMonth(month);
            assertEquals(MonthHelper.getDaysNumInMonth(month), dl.getDutyList().size());
        }
    }


}
