package war.webapp.model;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "work_unit")
public class WorkUnit extends BaseObject {
    
    private Long id;
    private Calendar date;
    private int hoursAmount;
    private String workDescription;
    private User employer;
    private User employee;
    private State state;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    
    @Column(name = "date")
    public Calendar getDate() {
        return date;
    }

    @Column(name = "hours_amount")
    public int getHoursAmount() {
        return hoursAmount;
    }
    
    @Column(name = "description")
    public String getWorkDescription() {
        return workDescription;
    }
    
    @ManyToOne
    @JoinColumn(name = "employer")
    public User getEmployer() {
        return employer;
    }
    
    @ManyToOne
    @JoinColumn(name = "employee")
    public User getEmployee() {
        return employee;
    }
    
    @Enumerated
    @Column(name = "state")
    public State getState() {
        return state;
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
        if (!(object instanceof WorkUnit)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        WorkUnit workUnit = (WorkUnit)object;
        return new EqualsBuilder().append(this.id, workUnit.id).isEquals();
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
    
    @Transient
    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    @Transient
    public int getDayOfWeek() {
        return date.get(Calendar.DATE);
    }
    
    @Transient
    public int getDayOfMonth() {
        return date.get(Calendar.MONTH);
    }
    
    @Transient
    public Date getFullDate() {
        return new Date( getYear() -1900 , getDayOfMonth(), this.getDayOfWeek());
    }
    
    @Transient
    public String getIdAsString() {
        return "wuid_" + id.toString();
    }

}
