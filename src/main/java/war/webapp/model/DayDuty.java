package war.webapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.appfuse.model.User;

/**
 * @author <a href="mailto:tokefa@tut.by">kefa</a>
 */
@Entity
@Table(name = "day_duty")
public class DayDuty extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1842676162177859911L;

	private Long id;
	private Date date;
	private User firstUser;
	private UserLocation firstUserLocation;
	private User secondUser;
	private UserLocation secondUserLocation;
	private Integer floor;

	public DayDuty() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}

	@ManyToOne
	@JoinColumn(name = "first_user")
	public User getFirstUser() {
		return firstUser;
	}

	@ManyToOne
	@JoinColumn(name = "first_user_location")
	public UserLocation getFirstUserLocation() {
		return firstUserLocation;
	}

	@ManyToOne
	@JoinColumn(name = "second_user")
	public User getSecondUser() {
		return secondUser;
	}

	@ManyToOne
	@JoinColumn(name = "second_user_location")
	public UserLocation getSecondUserLocation() {
		return secondUserLocation;
	}

	@Column
	public Integer getFloor() {
		return floor;
	}

	@Transient
	public Integer getStudyWeek() {
		int[] daysInMonth = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 };
		int totalDaysFromFirstSeptember = -1;
		for (int i = 8; i < date.getMonth(); ++i) {
			totalDaysFromFirstSeptember += daysInMonth[i];
		}
		if (date.getMonth() >= 0 && date.getMonth() < 8) {
			totalDaysFromFirstSeptember += 122;
			for (int i = 0; i < date.getMonth(); ++i) {
				totalDaysFromFirstSeptember += daysInMonth[i];
			}
		}
		totalDaysFromFirstSeptember += date.getDate();
		return (totalDaysFromFirstSeptember / 7) % 4 + 1;
	}

	@Transient
	public Boolean getFirstEmpty() {
		return firstUserLocation == null || firstUserLocation.getUser() == null;
	}

	@Transient
	public Boolean getSecondEmpty() {
		return secondUserLocation == null
				|| secondUserLocation.getUser() == null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setFirstUser(User firstUser) {
		this.firstUser = firstUser;
	}

	public void setFirstUserLocation(UserLocation firstUserLocation) {
		this.firstUserLocation = firstUserLocation;
	}

	public void setSecondUser(User secondUser) {
		this.secondUser = secondUser;
	}

	public void setSecondUserLocation(UserLocation secondUserLocation) {
		this.secondUserLocation = secondUserLocation;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DayDuty)) {
			return false;
		}

		final DayDuty dutyUser = (DayDuty) o;

		return date.equals(dutyUser.getDate())
				&& firstUser.equals(dutyUser.getFirstUser())
				&& secondUser.equals(dutyUser.getSecondUser());
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (date != null ? date.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("date", date.toString())
				.append("first_shift", firstUser)
				.append("second_shift", secondUser).toString();
	}

}
