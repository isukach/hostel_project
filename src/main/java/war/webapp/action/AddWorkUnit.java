package war.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.security.context.SecurityContextHolder;

import war.webapp.model.EmptyUser;
import war.webapp.model.State;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;
import war.webapp.service.UserManager;
import war.webapp.service.WorkUnitManager;

public class AddWorkUnit extends BasePage{
    
    private UserManager userManager;
    private WorkUnitManager workUnitManager;
    
    private Date date;
    private int hoursAmount;
    private String description;
    private String room;
    private User employee;
    private List<SelectItem> roomMates;
    private boolean isRoomChoosed = false;
    
    public String addWorkUnit() {
        Calendar lDate = Calendar.getInstance();
        lDate.setTime(date);
        
        WorkUnit workUnit = new WorkUnit();
        workUnit.setDate(lDate);
        workUnit.setHoursAmount(hoursAmount);
        workUnit.setWorkDescription(description);
        workUnit.setState(State.DONE);
        workUnit.setEmployee(employee);
        workUnit.setEmployer(getCurrentUser());
        
        workUnitManager.save(workUnit);     
        
        return null;
    }
    
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public UserManager getUserManager() {
        return userManager;
    }
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    public WorkUnitManager getWorkUnitManager() {
        return workUnitManager;
    }
    
    public void setWorkUnitManager(WorkUnitManager workUnitManager) {
        this.workUnitManager = workUnitManager;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public int getHoursAmount() {
        return hoursAmount;
    }
    
    public void setHoursAmount(int hoursAmount) {
        this.hoursAmount = hoursAmount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        List<User> lRoomMates = userManager.getUsersByRoom(room);
        List<SelectItem> usersList = new ArrayList<SelectItem>();
        if (lRoomMates.size() != 0) {
            for (User user: lRoomMates) {
                SelectItem item = new SelectItem(user, user.getShortFio());
                usersList.add(item);
            }
        } else {
            usersList.add(new SelectItem(new EmptyUser(), "Empty or wrong room"));
        }
        setRoomMates(usersList);
        this.room = room;
    }

    public List<SelectItem> getRoomMates() {
        if (room == null) {
            roomMates = new ArrayList<SelectItem>();
            roomMates.add(new SelectItem(new EmptyUser(), "Empty or wrong room"));
        }
        return roomMates;
    }

    public void setRoomMates(List<SelectItem> roomMates) {
        if (roomMates.size() == 1 && roomMates.get(0).getValue() instanceof EmptyUser) {
            roomMates = null;
            return;
        }
        this.roomMates = roomMates;
    }
    
    public boolean isRoomChoosed() {
        return isRoomChoosed;
    }
    
}
