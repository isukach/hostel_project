package war.webapp.action;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;

import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;

import war.webapp.model.DayDuty;
import war.webapp.model.DutyMonth;
import war.webapp.model.EmptyUser;
import war.webapp.model.Role;
import war.webapp.model.User;
import war.webapp.model.UserDuty;
import war.webapp.service.DayDutyManager;
import war.webapp.service.MonthManager;
import war.webapp.service.UserManager;
import war.webapp.util.GeneratorToExcel;
import war.webapp.util.MonthHelper;

public class DutyList extends BasePage implements Serializable {
	private static final long serialVersionUID = 911159310602744018L;

	public static final int MIN_FLOOR = 2;
	public static final int MAX_FLOOR = 12;
	
	public static final String ROLE_STAROSTA = "ROLE_STAROSTA";
	public static final String FIRST_SHIFT = "firstShift";
	public static final String SECOND_SHIFT = "secondShift";
	public static final String FIRST_SHIFT_USER = "firstShiftUser";
	public static final String SECOND_SHIFT_USER = "secondShiftUser";
	public static final String SELECT_USER_STRING = "Select User";

	private DayDutyManager dayDutyManager;
	private MonthManager monthManager;
	private UserManager userManager;
	
	private User user;
	private Integer month;
	private String monthString;
	private Integer floor;
	private boolean firstBoot = true;
	
	private String selectedUser;	
	private List<SelectItem> floorUsersList;
	
	private List<DayDuty> dutyList;

	public DutyList() {
		user = (User) ((SecurityContext) getSession()
				.getAttribute(
						HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY))
				.getAuthentication().getPrincipal();
		setSortColumn("dayOfWeek");
		setMonth(Calendar.getInstance().get(Calendar.MONTH));		
	}

	public List<DayDuty> getDutyList() {
		if (getFloor() == null) {
			setFloor(user.getAddress().getHostelFloor());
		}
		if (dutyList == null) {
			List<DayDuty> d = dayDutyManager.loadAllDayDutyByDateAndFloor(month, floor);
			for (DayDuty duty : d) {
				if (duty.isFirstEmpty()) {
					duty.setFirstUser(getEmptyUser());
				}
				if (duty.isSecondEmpty()) {
					duty.setSecondUser(getEmptyUser());
				}
			}
			List<DayDuty> result = getEmptyDutyList();
			if (d != null) {
				for (DayDuty dayDuty : d) {
					result.set(dayDuty.getDate().getDate() - 1, dayDuty);
				}
			}
			dutyList = result;
		}
		return dutyList;
	}

	private User getEmptyUser() {
		return new EmptyUser();
	}

	public List<SelectItem> getUsersByStarostaFloor() {		
		if (isOnOwnFloor() && isUserStarosta() && floorUsersList == null) {
			floorUsersList = new ArrayList<SelectItem>();
			floorUsersList.add(new SelectItem(SELECT_USER_STRING));
			
			List<User> floorUsers = userManager.getUsersByFloor(floor);
			floorUsers.remove(user);
			for (User floorUser : floorUsers) {
				floorUsersList.add(new SelectItem(floorUser.getUsername()
								   + " " + user.getFirstName() + " "
						           + user.getLastName()));
			}
		}
		return floorUsersList;
	}
	
	public void deleteUser(ActionEvent e) {
		int index = getTableRowNumber(e);
		User emptyUser = getEmptyUser();
		DayDuty dayDuty = dutyList.get(index);
		if (e.getComponent().getId().equals(FIRST_SHIFT_USER)) {
			dayDuty.setFirstUser(emptyUser);
		} else if (e.getComponent().getId().equals(SECOND_SHIFT_USER)) {
			dayDuty.setSecondUser(emptyUser);
		}
		return;
	}

	public void floorUserChanged(ValueChangeEvent e) {		
		String newValue = (String)e.getNewValue();
		
		if (newValue.equals(SELECT_USER_STRING))
			return;
		
		String userName = newValue.split(" ")[0];
		User userToWriteOnDuty = userManager.getUserByUsername(userName);
		
		Date date = getDate(e);
		DayDuty dayDuty = dayDutyManager.loadDayDutyByDateAndFloor(date, floor);
		if (dayDuty == null) {
			dayDuty = new DayDuty();
			dayDuty.setDate(date);
			dayDuty.setFloor(floor);
		}
		
		String shift = e.getComponent().getId();
		if (shift.equals(FIRST_SHIFT)) {
			dayDuty.setFirstUser(userToWriteOnDuty);
		} else if (shift.equals(SECOND_SHIFT)){
			dayDuty.setSecondUser(userToWriteOnDuty);
		}
		
		dayDutyManager.saveDayDuty(dayDuty);
		dutyList = null;
		
	}

	public boolean isUserStarosta() {
		return user.getRoles().contains(new Role(ROLE_STAROSTA));
	}
	
	public int getTableRowNumber(FacesEvent e) {
		return Integer.valueOf(e.getComponent()
				.getClientId(getFacesContext()).split(":")[2]);
	}

	public List<UserDuty> getUserDuties() throws Exception {
		List<UserDuty> userDuties = new ArrayList<UserDuty>();
		for (DayDuty dayDuty : getDutyList()) {
			if (dayDuty.getFirstUser() != null
					&& dayDuty.getFirstUser().equals(user)) {
				userDuties.add(new UserDuty(1, dayDuty));
			}
			if (dayDuty.getSecondUser() != null
					&& dayDuty.getSecondUser().equals(user)) {
				userDuties.add(new UserDuty(2, dayDuty));
			}
		}
		return userDuties;
	}

	public void writeFirstOnDuty(ActionEvent e) {
		if (!isOnOwnFloor() || !isMonthAvailable()) {
			return;
		}
		Date date = getDate(e);
		DayDuty dayDuty = getDayDutyManager().loadDayDutyByDateAndFloor(date,
				floor);
		
		if (dayDuty == null) {
			dayDuty = new DayDuty();
			dayDuty.setDate(date);
			dayDuty.setFloor(floor);
		}
		if (dayDuty.getFirstUser() != null) {
			return;
		}
		dayDuty.setFirstUser(user);
		getDayDutyManager().saveDayDuty(dayDuty);
		return;
	}

	public void writeSecondOnDuty(ActionEvent e) {
		if (!isOnOwnFloor() || !isMonthAvailable()) {
			return;
		}
		Date date = getDate(e);
		DayDuty dayDuty = getDayDutyManager().loadDayDutyByDateAndFloor(date,
				floor);
		if (dayDuty == null) {
			dayDuty = new DayDuty();
			dayDuty.setDate(date);
			dayDuty.setFloor(floor);
		}
		if (dayDuty.getSecondUser() != null) {
			return;
		}
		dayDuty.setSecondUser(user);
		getDayDutyManager().saveDayDuty(dayDuty);
		return;
	}

	public void deleteDuty(ActionEvent e) throws Exception {
		int index = getTableRowNumber(e);
		try {
			UserDuty userDuty = getUserDuties().get(index);
			if (userDuty.getShift() == 1) {
				userDuty.getDayDuty().setFirstUser(null);
			}
			if (userDuty.getShift() == 2) {
				userDuty.getDayDuty().setSecondUser(null);
			}
			
			dayDutyManager.deleteDayDuty(userDuty.getDayDuty());
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	private Date getDate(FacesEvent e) {
		String id = e.getComponent().getClientId(getFacesContext());
		int day = Integer.parseInt(id.split(":")[2]) + 1;
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DAY_OF_MONTH, day);
		return date.getTime();
	}

	private List<DayDuty> getEmptyDutyList() {
		List<DayDuty> result = new ArrayList<DayDuty>();
		for (int i = 1; i <= MonthHelper.getDaysNum(month + 1); ++i) {
			Date date = new Date();
			date.setMonth(month);
			date.setDate(i);
			DayDuty dayDuty = new DayDuty();
			dayDuty.setDate(date);

			User user = getEmptyUser();
			dayDuty.setFirstUser(user);
			dayDuty.setSecondUser(user);

			result.add(dayDuty);
		}
		return result;
	}

	public List<SelectItem> getMonthItems() {
		ArrayList<SelectItem> items = new ArrayList<SelectItem>();
		String[] months = MonthHelper.getMonths(getBundle());
		for (int i = 0; i < months.length; ++i) {
			items.add(new SelectItem(months[i]));
		}
		return items;
	}

	public void monthSelectionChanged(ValueChangeEvent e) {
		String newValue = (String) e.getNewValue();
		setMonth(MonthHelper.getMonth(newValue, getBundle()));
		dutyList = null;
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
		dutyList = null;
	}

	public void print(ActionEvent e) {
		Locale localeRU = new Locale("ru");
		String filename = "c:/My-tmp.xls";
		String floor = "9";
		String month = "ноябрь";

		String starosta = "длинныйолень";
		String vosptka = "Длинныйолень2";
		Semaphore sem = new Semaphore(1, true);

		GeneratorToExcel generator = new GeneratorToExcel(localeRU,
				getDutyList(), filename, floor, month);
		try {
			sem.acquire();
			generator.generate(month, floor, starosta, vosptka);
			generator.download(filename, getResponse(), getSession());
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace(); 
		} finally {
			sem.release();
		}
	}

    public void changeMonthAvailability(ActionEvent e) {
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        DutyMonth dutyMonth = monthManager.loadMonth(year, month, floor);
        if (dutyMonth == null) {
            dutyMonth = createDutyMonth();
            dutyMonth.setAvailable(false);
        } else {
            dutyMonth.setAvailable(!dutyMonth.getAvailable());
        }
        monthManager.saveMonth(dutyMonth);
    }

    private DutyMonth createDutyMonth() {
        DutyMonth dutyMonth = new DutyMonth();
        //TODO user should be able to choose the year
        dutyMonth.setYear(Calendar.getInstance().get(Calendar.YEAR));
        dutyMonth.setMonth(month);
        dutyMonth.setFloor(floor);
        return dutyMonth;
    }

	public boolean isMonthAvailable() {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		DutyMonth dutyMonth = monthManager.loadMonth(year, month, floor);
		if (dutyMonth == null) {
			return false;
		}
		return dutyMonth.getAvailable();
	}

	public DayDutyManager getDayDutyManager() {
		return dayDutyManager;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
		monthString = MonthHelper.getMonthString(month, getBundle());
	}

	public Integer getFloor() {
		if (firstBoot) {
			setFloor(user.getAddress().getHostelFloor());
			firstBoot = false;
		}
		return floor;
	}

	public boolean isOnOwnFloor() {
		return user.getAddress().getHostelFloor()
				.equals(getFloor());
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public void setDayDutyManager(DayDutyManager dayDutyManager) {
		this.dayDutyManager = dayDutyManager;
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

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}
	
}