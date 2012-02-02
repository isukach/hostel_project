package war.webapp.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.compass.annotations.Searchable;

@Entity
@Table(name = "deleted_user")
@Searchable
public class DeletedUser extends User {
    
    private static final long serialVersionUID = 8570092016510574352L;
    
    @Column(name = "delete_date", nullable = false)
    private Calendar deleteDate;

    public Calendar getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Calendar deleteDate) {
        this.deleteDate = deleteDate;
    }

}
