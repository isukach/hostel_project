package war.webapp.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * This class is used to represent an address with address, city, province and
 * postal-code information.
 * 
 */
@Embeddable
@Searchable(root = false)
public class Address extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3617859655330969141L;
    private Integer hostelFloor;
    private String hostelRoom;

    @Column(name = "hostel_room", nullable = false)
    @SearchableProperty
    public String getHostelRoom() {
        return hostelRoom;
    }

    @Column(name = "hostel_floor", nullable = false)
    @SearchableProperty
    public Integer getHostelFloor() {
        return hostelFloor;
    }

    public void setHostelFloor(Integer hostelFloor) {
        this.hostelFloor = hostelFloor;
    }

    public void setHostelRoom(String hostelRoom) {
        this.hostelRoom = hostelRoom;
    }

    /**
     * Overridden equals method for object comparison. Compares based on
     * hashCode.
     * 
     * @param o Object to compare
     * @return true/false based on hashCode
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }

        final Address address1 = (Address) o;

        return this.hashCode() == address1.hashCode();
    }

    /**
     * Overridden hashCode method - compares on address, city, province, country
     * and postal code.
     * 
     * @return hashCode
     */
    public int hashCode() {
        int result;
        result = (hostelRoom != null ? hostelRoom.hashCode() : 0);
        result = 29 * result + (hostelFloor != null ? hostelFloor.hashCode() : 0);
        result = 29 * result + (hostelRoom != null ? hostelRoom.hashCode() : 0);
        return result;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     * 
     * @return a String representation of this class.
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("hostel_floor", this.hostelFloor)
                .append("hostel_room", this.hostelRoom).toString();
    }
}
