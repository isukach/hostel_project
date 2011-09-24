package war.webapp.model;

import java.util.Calendar;

public class Duty {
    private Calendar date;
    private String time;
    private String remarks;
    private String punishment;

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        if (remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = remarks;
        }
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        if (punishment == null) {
            this.punishment = "";
        } else {
            this.punishment = punishment;
        }
    }
}
