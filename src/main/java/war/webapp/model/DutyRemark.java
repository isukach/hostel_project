package war.webapp.model;


import javax.persistence.*;

@Entity
@Table(name = "duty_remark")
public class DutyRemark {

    @ManyToOne
    @JoinColumn(name = "day_duty", nullable = false)
    private DayDuty dayDuty;

    @Column(nullable = false)
    private int shift;

    @Column(nullable = false)
    private String remark;

    @Id
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DayDuty getDayDuty() {
        return dayDuty;
    }

    public void setDayDuty(DayDuty dayDuty) {
        this.dayDuty = dayDuty;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
