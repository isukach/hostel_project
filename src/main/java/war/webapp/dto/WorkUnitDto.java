package war.webapp.dto;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import war.webapp.model.State;
import war.webapp.model.User;

public class WorkUnitDto {
    private Long id;
    private Calendar date;
    private int hoursAmount;
    private String workDescription;
    private User employer;
    private User employee;
    private State state;
    private boolean selected;
    
    public Long getId() {
        return id;
    }
    
    public Calendar getDate() {
        return date;
    }

    public int getHoursAmount() {
        return hoursAmount;
    }
    
    public String getWorkDescription() {
        return workDescription;
    }
    
    public User getEmployer() {
        return employer;
    }
    
    public User getEmployee() {
        return employee;
    }
    
    public State getState() {
        return state;
    }
    
    public boolean getSelected() {
        return selected;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setHoursAmount(int hoursAmount) {
        this.hoursAmount = hoursAmount;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("date", date)
            .append("hoursAmount", hoursAmount)
            .append("description", workDescription)
            .append("employer", employer)
            .append("employee", employee)
            .append("state", state)
            .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkUnitDto)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        WorkUnitDto workUnitDto = (WorkUnitDto)object;
        return new EqualsBuilder().append(this.id, workUnitDto.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .append(date)
            .append(hoursAmount)
            .append(workDescription)
            .append(employer)
            .append(employee)
            .append(state)
            .toHashCode();
    }
    
    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    public int getDayOfWeek() {
        return date.get(Calendar.DATE);
    }
    
    public int getDayOfMonth() {
        return date.get(Calendar.MONTH);
    }
    
    public Date getFullDate() {
        return new Date( getYear() -1900 , getDayOfMonth(), this.getDayOfWeek());
    }
}
