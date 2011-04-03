import junit.framework.TestCase;
import org.junit.Test;
import war.webapp.action.DutyList;
import war.webapp.model.DayDuty;
import war.webapp.service.DayDutyManager;
import war.webapp.util.MonthHelper;

import java.util.Calendar;
import java.util.LinkedList;

import static org.easymock.EasyMock.*;

public class DutyListTest extends TestCase {
    private DutyList dutyList;
    private DayDutyManager dayDutyManagerMock;
    private String floor = "8";

    @Override
    protected void setUp() throws Exception {
        dutyList = new DutyList();
        dayDutyManagerMock = createMock(DayDutyManager.class);
        dutyList.setDayDutyManager(dayDutyManagerMock);
        dutyList.setFloor(floor);
        prepareDayDutyManagerMock();
    }

    @Test
    public void testDutyListSize() {
        for (int i = 0; i < 12; ++i) {
            dutyList.setMonth(i);
            assertEquals(MonthHelper.getDaysNumInMonth(i), dutyList.getDutyList().size());
        }
    }

    @Test
    public void testDutyListData() {
        int initialMonth = 3;
        dutyList.setMonth(initialMonth);
        DayDuty someDayDuty = new DayDuty();
        someDayDuty.setDate(Calendar.getInstance());
        dutyList.getDutyList().set(0, someDayDuty);
        for (int i = 0; i < 12; ++i) {
            if (i != initialMonth) {
                dutyList.setMonth(i);
                assertFalse("dutyList contains DayDuty for another month! (initial month = "
                        + initialMonth + ", month under test = " + i, dutyList.getDutyList().contains(someDayDuty));
            }
        }
    }

    private void prepareDayDutyManagerMock() {
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(0, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(1, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(2, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(3, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(4, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(5, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(6, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(7, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(8, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(9, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(10, floor)).andReturn(new LinkedList<DayDuty>());
        expect(dayDutyManagerMock.loadAllDayDutyByMonthAndFloor(11, floor)).andReturn(new LinkedList<DayDuty>());
        replay(dayDutyManagerMock);
    }
}