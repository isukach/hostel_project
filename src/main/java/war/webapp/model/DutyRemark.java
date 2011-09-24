package war.webapp.model;


import javax.persistence.*;

@Entity
@Table(name = "duty_remark")
public class DutyRemark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "day_duty", nullable = false)
    private DayDuty dayDuty;

    @Column(nullable = false)
    private int shift;

    @Column(nullable = false)
    private String remark;

    @Column(nullable = true)
    private String punishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }
}
