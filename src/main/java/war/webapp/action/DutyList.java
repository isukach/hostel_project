package war.webapp.action;

import org.appfuse.dao.UserDao;
import org.appfuse.dao.hibernate.UserDaoHibernate;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.appfuse.service.impl.UserManagerImpl;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;
import war.webapp.model.DayDuty;
import war.webapp.model.DutyMonth;
import war.webapp.model.UserDuty;
import war.webapp.model.UserLocation;
import war.webapp.service.DayDutyManager;
import war.webapp.service.MonthManager;
import war.webapp.service.UserLocationManager;
import war.webapp.util.GeneratorToExcel;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Semaphore;

public class DutyList extends BasePage implements Serializable {
    private static final long serialVersionUID = 911159310602744018L;

    public static final int MIN_FLOOR = 2;
    public static final int MAX_FLOOR = 12;

    private DayDutyManager dayDutyManager;
    private UserLocationManager userLocationManager;
    private MonthManager monthManager;
    private UserManager userManager;
    private User user;
    private Integer month;
    private String monthString;
    private Integer floor;
    private boolean firstBoot = true;
    private HtmlPanelGrid updateForm;
    private String selectedUser;
    private UserDuty selectedUserDuty;

    public boolean isUpdateFormVisible() {
        return upDateFormVisible;
    }

    public void setUpdateFormVisible(boolean updateFormVisible) {
        upDateFormVisible = updateFormVisible;
    }

    private boolean upDateFormVisible = false;

    public DutyList() {
        user = (User) ((SecurityContext) getSession()
                .getAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY))
                .getAuthentication().getPrincipal();
        setSortColumn("dayOfWeek");
        setMonth(Calendar.getInstance().get(Calendar.MONTH));
    }

    public List<DayDuty> getDutyList() {
        if (getFloor() == null) {
            setFloor(userLocationManager.getByUser(user).getFloor());
        }
        List<DayDuty> d = dayDutyManager.loadAllDayDutyByDateAndFloor(month, floor);
        for (DayDuty duty : d) {
            if (duty.getFirstEmpty()) {
                duty.setFirstUser(getEmptyUser());
                duty.setFirstUserLocation(getEmptyLocation());
            }
            if (duty.getSecondEmpty()) {
                duty.setSecondUser(getEmptyUser());
                duty.setSecondUserLocation(getEmptyLocation());
            }
        }
        List<DayDuty> result = getEmptyDutyList();
        if (d != null) {
            for (DayDuty dayDuty : d) {
                result.set(dayDuty.getDate().getDate() - 1, dayDuty);
            }
        }
        return result;
    }

    public void showUpdateForm(ActionEvent e) throws Exception {
        upDateFormVisible = true;

        int index = Integer.valueOf(e.getComponent().getClientId(getFacesContext()).split(":")[2]);
        String shift = ((HtmlCommandLink)e.getComponent()).getId();

        Date date = getDate(e);
        DayDuty selectedDayDuty = getDayDutyManager().loadDayDutyByDateAndFloor(date, floor);        
        if (selectedDayDuty == null) {
            selectedDayDuty = new DayDuty();
            selectedDayDuty.setDate(date);
            selectedDayDuty.setFloor(floor);
        }

        if (shift.equals("first"))
            selectedUserDuty = new UserDuty(1, selectedDayDuty);
        else
            selectedUserDuty = new UserDuty(2, selectedDayDuty);
    }

    public void saveSelectedUser() {
        upDateFormVisible = false;

        String selectedUserName = selectedUser.split(" ")[0];
        User user = userManager.getUserByUsername(selectedUserName);
        DayDuty selectedDayDuty = selectedUserDuty.getDayDuty();
        UserLocation ul = userLocationManager.getByUser(user);
        if (selectedUserDuty.getShift() == 1) {
            selectedDayDuty.setFirstUser(user);
            selectedDayDuty.setFirstUserLocation(ul);
        } else {
            selectedDayDuty.setSecondUser(user);
            selectedDayDuty.setSecondUserLocation(ul);
        }            
        dayDutyManager.saveDayDuty(selectedDayDuty);
    }

    private User getEmptyUser() {
    	//her
        User user = new User();
        user.setFirstName("");
        user.setLastName("");
        return user;
    }

    private UserLocation getEmptyLocation() {
        UserLocation ul = new UserLocation();
        ul.setRoom("");
        ul.setUniversityGroup("");
        return ul;
    }

    public List<SelectItem> getUsersByStarostaFloor() {
        user = (User) ((SecurityContext) getSession()
                .getAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY))
                .getAuthentication().getPrincipal();
        UserLocation starostaLocation = userLocationManager.getByUser(user);

        List<SelectItem> users = new ArrayList<SelectItem>();
        users.add(new SelectItem("Select user"));

        int starostaFloor = userLocationManager.getByUser(user).getFloor();
        if (starostaFloor == floor) {
            List<UserLocation> userLocations = userLocationManager.getByFloor(starostaFloor);
            userLocations.remove(starostaLocation);

            for (UserLocation userLocation: userLocations) {
                users.add(new SelectItem(userLocation.getUser().getUsername() + " "+
                                         userLocation.getUser().getFirstName() + " "+
                                         userLocation.getUser().getLastName()));
            }
        }
        return users;       
    }

    public boolean isStarostaFloor() {
        if (user == null)
            user = (User) ((SecurityContext) getSession()
                    .getAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY))
                    .getAuthentication().getPrincipal();
        UserLocation starostaLocation = userLocationManager.getByUser(user);
        if (starostaLocation.getFloor().equals(Integer.valueOf(floor)))
            return true;
        else
            return false;
    }

    public List<UserDuty> getUserDuties() throws Exception {
        List<UserDuty> userDuties = new ArrayList<UserDuty>();
        for (DayDuty dayDuty : getDutyList()) {
            if (dayDuty.getFirstUser() != null && dayDuty.getFirstUser().equals(user)) {
                userDuties.add(new UserDuty(1, dayDuty));
            }
            if (dayDuty.getSecondUser() != null && dayDuty.getSecondUser().equals(user)) {
                userDuties.add(new UserDuty(2, dayDuty));
            }
        }
        return userDuties;
    }


    public void writeFirstOnDuty(ActionEvent e) {
        if (!onOwnFloor() || !isMonthAvailable()) {
            return;
        }
        Date date = getDate(e);
        DayDuty dayDuty = getDayDutyManager().loadDayDutyByDateAndFloor(date, floor);
        //TODO ����������� ����������
        if (dayDuty == null) {
            dayDuty = new DayDuty();
            dayDuty.setDate(date);
            dayDuty.setFloor(floor);
        }
        if (dayDuty.getFirstUser() != null) {
            return;
        }
        dayDuty.setFirstUser(user);
        dayDuty.setFirstUserLocation(userLocationManager.getByUser(user));
        getDayDutyManager().saveDayDuty(dayDuty);
        return;
    }

    public void writeSecondOnDuty(ActionEvent e) {
        if (!onOwnFloor() || !isMonthAvailable()) {
            return;
        }
        Date date = getDate(e);
        DayDuty dayDuty = getDayDutyManager().loadDayDutyByDateAndFloor(date, floor);
        if (dayDuty == null) {
            dayDuty = new DayDuty();
            dayDuty.setDate(date);
            dayDuty.setFloor(floor);
        }
        if (dayDuty.getSecondUser() != null) {
            return;
        }
        dayDuty.setSecondUser(user);
        dayDuty.setSecondUserLocation(userLocationManager.getByUser(user));
        getDayDutyManager().saveDayDuty(dayDuty);
        return;
    }

    public void deleteDuty(ActionEvent e) throws Exception {
        int index = Integer.valueOf(e.getComponent().getClientId(getFacesContext()).split(":")[2]);
        try {
            UserDuty userDuty = getUserDuties().get(index);
            if (userDuty.getShift() == 1) {
                userDuty.getDayDuty().setFirstUser(null);
                userDuty.getDayDuty().setFirstUserLocation(null);
            }
            if (userDuty.getShift() == 2) {
                userDuty.getDayDuty().setSecondUser(null);
                userDuty.getDayDuty().setSecondUserLocation(null);
            }
            //dayDutyManager.saveDayDuty(userDuty.getDayDuty());
            dayDutyManager.deleteDayDuty(userDuty.getDayDuty());
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    private Date getDate(ActionEvent e) {
        String id = e.getComponent().getClientId(getFacesContext());
        int day = Integer.parseInt(id.split(":")[2]) + 1;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        return date.getTime();
    }

    private Date getDate(ValueChangeEvent e) {
        String id = e.getComponent().getClientId(getFacesContext());
        int day = Integer.parseInt(id.split(":")[2]) + 1;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        return date.getTime();
    }

    private List<DayDuty> getEmptyDutyList() {
        List<DayDuty> result = new ArrayList<DayDuty>();
        for (int i = 1; i <= getDaysNum(month + 1); ++i) {
            Date date = new Date();
            date.setMonth(month);
            date.setDate(i);
            DayDuty dayDuty = new DayDuty();
            dayDuty.setDate(date);

            User user = new User();
            user.setFirstName("");
            user.setLastName("");
            dayDuty.setFirstUser(user);
            dayDuty.setSecondUser(user);

            UserLocation ul = new UserLocation();
            ul.setRoom("");
            ul.setUniversityGroup("");
            dayDuty.setFirstUserLocation(ul);
            dayDuty.setSecondUserLocation(ul);

            result.add(dayDuty);
        }
        return result;
    }

    public List<SelectItem> getMonthItems() {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        String[] months = getMonths();
        for (int i = 0; i < months.length; ++i) {
            items.add(new SelectItem(months[i]));
        }
        return items;
    }

    public void monthSelectionChanged(ValueChangeEvent e) {
        String newValue = (String) e.getNewValue();
        setMonth(getMonth(newValue));
    }

    public List<SelectItem> getFloors() {
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; ++i) {
            items.add(new SelectItem(i));
        }
        return items;
    }

    public void floorChanged(ValueChangeEvent e) {
        setFloor((Integer) e.getNewValue());
    }

    public void print(ActionEvent e) {
        Locale localeRU = new Locale("ru");
        String filename = "c:/My-tmp.xls";
        String floor = "9";
        String month = "ноябрь";

        String starosta = "длинныйолень";
        String vosptka = "Длинныйолень2";
        Semaphore sem = new Semaphore(1, true);

        GeneratorToExcel generator = new GeneratorToExcel(localeRU, getDutyList(), filename, floor, month);
        try {
            sem.acquire();
            generator.generate(month, floor, starosta, vosptka);
            generator.download(filename,new HttpServletResponseWrapper(getResponse()), getSession());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            sem.release();
        }
    }

    public void closeOpen(ActionEvent e) {
        //TODO
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        DutyMonth dutyMonth = monthManager.loadMonth(year, month, floor);
        if (dutyMonth == null) {
            dutyMonth = new DutyMonth();
            dutyMonth.setYear(year);
            dutyMonth.setMonth(month);
            dutyMonth.setFloor(floor);
            dutyMonth.setAvailable(false);
        } else {
            dutyMonth.setAvailable(!dutyMonth.getAvailable());
        }
        monthManager.saveMonth(dutyMonth);
    }

    public boolean isMonthAvailable() {
        //TODO
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        DutyMonth dutyMonth = monthManager.loadMonth(year, month, floor);
        if (dutyMonth == null) {
            return false;
        }
        return dutyMonth.getAvailable();
    }

    private String[] getMonths() {
        return new String[]{
                getBundle().getString("month.jan"),
                getBundle().getString("month.feb"),
                getBundle().getString("month.mar"),
                getBundle().getString("month.apr"),
                getBundle().getString("month.may"),
                getBundle().getString("month.jun"),
                getBundle().getString("month.jul"),
                getBundle().getString("month.aug"),
                getBundle().getString("month.sep"),
                getBundle().getString("month.oct"),
                getBundle().getString("month.nov"),
                getBundle().getString("month.dec"),
        };
    }

    private int getMonth(String monthString) {
        if (monthString.equals(getBundle().getString("month.jan"))) {
            return 0;
        }
        if (monthString.equals(getBundle().getString("month.feb"))) {
            return 1;
        }
        if (monthString.equals(getBundle().getString("month.mar"))) {
            return 2;
        }
        if (monthString.equals(getBundle().getString("month.apr"))) {
            return 3;
        }
        if (monthString.equals(getBundle().getString("month.may"))) {
            return 4;
        }
        if (monthString.equals(getBundle().getString("month.jun"))) {
            return 5;
        }
        if (monthString.equals(getBundle().getString("month.jul"))) {
            return 6;
        }
        if (monthString.equals(getBundle().getString("month.aug"))) {
            return 7;
        }
        if (monthString.equals(getBundle().getString("month.sep"))) {
            return 8;
        }
        if (monthString.equals(getBundle().getString("month.oct"))) {
            return 9;
        }
        if (monthString.equals(getBundle().getString("month.nov"))) {
            return 10;
        }
        if (monthString.equals(getBundle().getString("month.dec"))) {
            return 11;
        }
        return 0;
    }

    private String getMonthString(int month) {
        if (month == 0) {
            return getBundle().getString("month.jan");
        }
        if (month == 1) {
            return getBundle().getString("month.feb");
        }
        if (month == 2) {
            return getBundle().getString("month.mar");
        }
        if (month == 3) {
            return getBundle().getString("month.apr");
        }
        if (month == 4) {
            return getBundle().getString("month.may");
        }
        if (month == 5) {
            return getBundle().getString("month.jun");
        }
        if (month == 6) {
            return getBundle().getString("month.jul");
        }
        if (month == 7) {
            return getBundle().getString("month.aug");
        }
        if (month == 8) {
            return getBundle().getString("month.sep");
        }
        if (month == 9) {
            return getBundle().getString("month.oct");
        }
        if (month == 10) {
            return getBundle().getString("month.nov");
        }
        if (month == 11) {
            return getBundle().getString("month.dec");
        }
        return null;
    }

    public int getDaysNum(int month) {
        switch (month) {
            case 1:
                return 31;
            case 2:
                if (Calendar.getInstance().get(Calendar.YEAR) % 4 == 0) {
                    return 29;
                }
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 0;
    }

    public DayDutyManager getDayDutyManager() {
        return dayDutyManager;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
        monthString = getMonthString(month);
    }

    public Integer getFloor() {
        if (firstBoot) {
            setFloor(userLocationManager.getByUser(user).getFloor());
            firstBoot = false;
        }
        return floor;
    }

    public boolean onOwnFloor() {
        return userLocationManager.getByUser(user).getFloor().equals(getFloor());
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setDayDutyManager(DayDutyManager dayDutyManager) {
        this.dayDutyManager = dayDutyManager;
    }

    public UserLocationManager getUserLocationManager() {
        return userLocationManager;
    }

    public void setUserLocationManager(UserLocationManager userLocationManager) {
        this.userLocationManager = userLocationManager;
    }

    public MonthManager getMonthManager() {
        return monthManager;
    }

    public void setMonthManager(MonthManager monthManager) {
        this.monthManager = monthManager;
    }

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }

    public HtmlPanelGrid getUpdateForm() {
        return updateForm;
    }

    public void setUpdateForm(HtmlPanelGrid updateForm) {
        this.updateForm = updateForm;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}