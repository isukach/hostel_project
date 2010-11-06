package war.webapp.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "duty_month")
public class DutyMonth extends BaseObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer month;
    private Integer year;
    private Integer floor;
    private Boolean available;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "month")
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "floor")
    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Column(name = "available")
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("floor", floor)
                .append("year", year)
                .append("floor", floor)
                .append("available", available).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DutyMonth)) {
            return false;
        }
        DutyMonth dutyMonth = (DutyMonth) o;
        return dutyMonth.year.equals(year) && dutyMonth.month.equals(this.month) && dutyMonth.floor.equals(floor);
    }

    @Override
    public int hashCode() {
        return month != null && floor != null && year != null ? month.hashCode() - year.hashCode()
                + floor.hashCode() : 0;
    }
}
