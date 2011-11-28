package war.webapp.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.servlet.ServletContext;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents the basic "user" object in Hostel Duty that allows for
 * authentication and user management. It implements Acegi Security's
 * UserDetails interface.
 */
@Entity
@Table(name = "app_user")
@Searchable
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends BaseObject implements Serializable, UserDetails {
    private static final long serialVersionUID = 3832626162173359411L;

    private Long id;
    private String username; // required
    private String password; // required
    private String firstName; // required
    private String lastName; // required
    private String middleName; // required
    private Address address = new Address();
    private String universityGroup;
    private Integer version;
    private String email;
    private String phoneNumber;
    private Calendar dateOfBirth;
    private Set<Role> roles = new HashSet<Role>();
    private boolean enabled = true;
    private boolean accountExpired = false;
    private boolean accountLocked;
    private boolean credentialsExpired = false;
    private String imagePath;


    /*new fields*/
    private String department;
    private boolean isFreePayStudy;

    private boolean profileImageExist;
    /*end new fields*/

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {
    }

    /**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 50, unique = true)
    @SearchableProperty
    public String getUsername() {
        return username;
    }

    @Column(name = "email", length = 50, unique = false)
    public String getEmail() {
        return email;
    }

    @Column(name = "phone_number", length = 50, unique = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "first_name", nullable = false, length = 50)
    @SearchableProperty
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", nullable = false, length = 50)
    @SearchableProperty
    public String getLastName() {
        return lastName;
    }

    @Column(name = "middle_name", nullable = false, length = 50)
    @SearchableProperty
    public String getMiddleName() {
        return middleName;
    }

    @Transient
    public int getBirthdayDayOfMonth() {
        if (dateOfBirth == null) {
            return 11;
        }
        return dateOfBirth.get(Calendar.DAY_OF_MONTH);
    }

    @Transient
    public int getBirthdayMonth() {
        if (dateOfBirth == null) {
            return 7;
        }
        return dateOfBirth.get(Calendar.MONTH);
    }

    @Transient
    public int getBirthdayYear() {
        if (dateOfBirth == null) {
            return 1989;
        }
        return dateOfBirth.get(Calendar.YEAR);
    }
    
    @Transient
    public String getBirthDayString() {
        StringBuilder birthday = new StringBuilder();
        birthday.append(getBirthdayDayOfMonth());
        birthday.append(".");
        birthday.append(getBirthdayMonth());
        birthday.append(".");
        birthday.append(getBirthdayYear());
        return birthday.toString();
    }

    /**
     * Returns the full name.
     *
     * @return firstName + ' ' + lastName
     */
    @Transient
    public String getShortName() {
        StringBuilder result = new StringBuilder();
        result.append(lastName);
        if (firstName != null && firstName.length() > 0) {
            result.append(" ").append(firstName.substring(0, 1).toUpperCase()).append(".");
            if (middleName != null && middleName.length() > 0) {
                result.append(" ").append(middleName.substring(0, 1).toUpperCase()).append(".");
            }
        }
        return result.toString();
    }
    
    @Transient
    public String getImagePath() {
        String emptyPath = "/resources";
        return emptyPath +"/"+ getUsername()+ "/" + getFirstName()+"_ava";
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Embedded
    @SearchableComponent
    public Address getAddress() {
        return address;
    }

    @Column(name = "university_group")
    public String getUniversityGroup() {
        return universityGroup;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     *
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getName(), role.getName()));
            }
        }

        return userRoles;
    }

    /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public GrantedAuthority[] getAuthorities() {
        return roles.toArray(new GrantedAuthority[0]);
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    @Column(name = "account_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Column(name = "account_expired", nullable = false)
    public boolean isAccountExpired() {
        return accountExpired;
    }
    
    @Column(name = "department", nullable = true)
    public String getDepartment() {
        return department;
    }

    @Column(name = "study_pay", columnDefinition = "BIT(1) DEFAULT 1")
    public boolean isFreePayStudy() {
        return isFreePayStudy;
    }

    /**
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Column(name = "account_locked", nullable = false)
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Column(name = "credentials_expired", nullable = false)
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Transient
    public boolean isEmptyUser() {
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUniversityGroup(String universityGroup) {
        this.universityGroup = universityGroup;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBirthdayDayOfMonth(int day) {
        dateOfBirth.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setBirthdayMonth(int month) {
        dateOfBirth.set(Calendar.MONTH, month);
    }

    public void setBirthdayYear(int year) {
        dateOfBirth.set(Calendar.YEAR, year);
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setFreePayStudy(boolean freePayStudy) {
        isFreePayStudy = freePayStudy;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        final User user = (User) o;

        return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (username != null ? username.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("username", this.username)
                .append("enabled", this.enabled).append("accountExpired", this.accountExpired)
                .append("credentialsExpired", this.credentialsExpired).append("accountLocked", this.accountLocked);

        GrantedAuthority[] auths = this.getAuthorities();
        if (auths != null) {
            sb.append("Granted Authorities: ");

            for (int i = 0; i < auths.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(auths[i].toString());
            }
        } else {
            sb.append("No Granted Authorities");
        }
        return sb.toString();
    }
    
    @Transient
    public String getFullName()
    {
        return  this.getLastName() + " " + this.getFirstName()
        + " " + this.getMiddleName();
    }
    
    
    @Transient
    public boolean isProfileImageExist(){
        String filename = getImagePath();
        ServletContext sc = (ServletContext)  FacesContext.getCurrentInstance().getExternalContext().getContext();
        File file = new File(sc.getRealPath("") + filename);
        return file.exists();
    }

    public void setProfileImageExist(boolean profileImageExist) {
        this.profileImageExist = profileImageExist;
    }
}

