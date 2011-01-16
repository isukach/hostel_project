package war.webapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class represents map between user and his hostel address (floor, room) and group
 * 
 */
@Entity
@Table(name = "user_location")
public class UserLocation extends BaseObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private User user;
    private Integer floor;
    private String room;
    private String universityGroup;

    public UserLocation() {
    }
    
    public UserLocation(final User user) {
        this.user = user;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Column(name = "floor")
    public Integer getFloor() {
        return floor;
    }

    @Column(name = "room")
    public String getRoom() {
        return room;
    }

    @Column(name = "univeristy_group")
    public String getUniversityGroup() {
        return universityGroup;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setUniversityGroup(String universityGroup) {
        this.universityGroup = universityGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserLocation)) {
            return false;
        }
        final UserLocation obj = (UserLocation) o;
        return obj.getUser().equals(getUser());
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("user",
                user.getLastName() + " " + user.getFirstName()).append("floor", getFloor()).append(
                "room", getRoom()).append("group", getUniversityGroup()).toString();
    }

}
