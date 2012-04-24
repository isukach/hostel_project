package war.webapp.action;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContext;
import org.springframework.web.HttpSessionRequiredException;

import war.webapp.Constants;
import war.webapp.model.Address;
import war.webapp.model.LabelValue;
import war.webapp.model.Role;
import war.webapp.model.User;
import war.webapp.model.WorkUnit;
import war.webapp.service.FloorManager;
import war.webapp.service.RoleManager;
import war.webapp.service.UserExistsException;
import war.webapp.service.WorkUnitManager;
import war.webapp.util.ConvertUtil;
import war.webapp.util.FacesUtils;
import war.webapp.util.UserHelper;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.security.context.SecurityContextHolder.getContext;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author mraible
 */
public class UserForm extends BasePage implements Serializable {
    
    private static final long serialVersionUID = -1141119853856863204L;
    
    private RoleManager roleManager;
    private FloorManager floorManager;
    private WorkUnitManager workUnitManager;
    
    private String id;
    private User user;
    private Map<String, String> availableRoles;
    private String[] userRoles;
    private String action;
    
    private static String ACTION_EDIT = "edit";
    private static String ACTION_ADD = "add";
    private static String ACTION_VIEW = "view";
    private static String DEFAULT_PASSWORD = "pass";

    private Map<String, Boolean> payModeToValue;
    
    public UserForm() {
        HttpServletRequest request = getRequest();
        String action = request.getParameter("action");
        setAction(action);
    }

    //Should be removed
    {
        payModeToValue = new LinkedHashMap<String, Boolean>();
        payModeToValue.put(getBundle().getString("user.studyPaymentFree"), true);
        payModeToValue.put(getBundle().getString("user.studyPaymentNonFree"), false);
    }

    public Map<String, Boolean> getPayModeToValue() {
        return payModeToValue;
    }

    public void setPayModeToValue(Map<String, Boolean> payModeToValue) {
        this.payModeToValue = payModeToValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        if (user == null) {
            loadUser();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String add() {
        setAction(ACTION_ADD);
        return "editProfile";
    }

    public String cancel() {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'cancel' method");
        }

        /*if (!"list".equals(getParameter("from"))) {
            return "mainMenu";
        } else {
            return "cancel";
        }*/
        return "viewProfile";
    }

    public String edit() {
        setAction(ACTION_EDIT);
        return "editProfile";
    }

    public String viewProfile() {
        setAction(ACTION_VIEW);
        return "viewProfile";
    }

    private void loadUser() {
        HttpServletRequest request = getRequest();
        String userId = request.getParameter("userId");
        String action = getAction();
        if (userId != null) {
            id = userId;
        }
        
        // if a user's id is passed in
        if (id != null) {
            // lookup the user using that id
            setUser(userManager.getUser(id));
        } else if (!ACTION_ADD.equals(action)){
            setUser(userManager.getUserByUsername(request.getRemoteUser()));
        } else {
            setUser(new User());
            getUser().setAddress(new Address());
            getUser().setVersion(null);
            getUser().setEnabled(true);
            getUser().setDateOfBirth(Calendar.getInstance());
        }

        if (getUser() != null && getUser().getUsername() != null && isRememberMe()) {
            // if user logged in with remember me, display a warning that
            // they can't change passwords
            log.debug("checking for remember me login...");
            log.trace("User '" + getUser().getUsername() + "' logged in with cookie");
            addMessage("userProfile.cookieLogin");
        }
    }

    public String resetPassword() {
        if (getUser().getId() != null) {
            setUser(userManager.getUser(getUser().getId().toString()));
            getUser().setPassword(DEFAULT_PASSWORD);
            try {
                userManager.saveUser(getUser());
                addMessage("user.password.reset", new Object[] {DEFAULT_PASSWORD});
            } catch (UserExistsException ex) {
                ex.printStackTrace();
            }
        }
        return "viewProfile";
    }

    /**
     * Convenience method for view templates to check if the user is logged in
     * with RememberMe (cookies).
     *
     * @return true/false - false if user interactively logged in.
     */
    public boolean isRememberMe() {
        if (getUser() != null && getUser().getId() == null)
            return false; // check for add()

        AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
        SecurityContext ctx = getContext();

        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            return resolver.isRememberMe(auth);
        }
        return false;
    }

    public String save() throws IOException {
        
        setUserRoles(getRoles());
        generateFloor();
        generateUsername();
        generatePassword();

        if (!validateEmail()) {
            addError("errors.email", new Object[]{getUser().getEmail()});
            return "editProfile";
        }
//        user.getRoles().clear();
        for (int i = 0; (userRoles != null) && (i < userRoles.length); i++) {
            String roleName = userRoles[i];
            getUser().addRole(roleManager.getRole(roleName));
        }

        Integer originalVersion = getUser().getVersion();

        try {
            setUser(userManager.saveUser(getUser()));
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor
            // userManagerSecurity
            log.warn(ade.getMessage());
            getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (UserExistsException e) {
            addError("errors.existing.user", new Object[]{getUser().getUsername()});

            // reset the version # to what was passed in
            getUser().setVersion(originalVersion);
            return "editProfile";
        }

        /*if ("list".equals(getParameter("from"))) {
            addMessage("user.added", getUser().getShortName());
            return "list"; // return to list screen
        } else {
            addMessage("user.saved");
            return "viewProfile"; // return to profile page
        }*/
        if (ACTION_ADD.equals(getAction())) {
            addMessage("user.added", getUser().getShortName());
            return "list"; // return to list screen
        } else if (ACTION_EDIT.equals(getAction())) {
            addMessage("user.saved");
            return "viewProfile"; // return to profile page
        }
        return "editProfile";
    }

    private String[] getRoles() {
        if (getRequest().getParameterValues("userForm:userRoles") == null) {
            return new String[]{Constants.USER_ROLE};
        }
        return getRequest().getParameterValues("userForm:userRoles");
    }

    private void generateFloor() {
        String floor = getFloorOf(getUser());
        if (floor == null) {
            floor = getFloorOf(getCurrentUser());
        }
        getUser().getAddress().setHostelFloor(floor);
    }

    private void generateUsername() {
        if (StringUtils.isEmpty(getUser().getUsername())) {
            StringBuilder username = new StringBuilder();
            username.append(getUser().getAddress().getHostelRoom()).append(getUser().getLastName())
                    .append(getUser().getFirstName().charAt(0)).append(getUser().getMiddleName().charAt(0));
            getUser().setUsername(username.toString());
        }
    }

    private void generatePassword() {
        String pass = getUser().getPassword();
        String sessionUserPass = (getCurrentUser()).getPassword();
        if (pass == null) {
            if (sessionUserPass == null) {
                getUser().setPassword("pass");
            } else {
                getUser().setPassword(sessionUserPass);
            }
        }
    }

    private boolean validateEmail() {
        if (StringUtils.isEmpty(getUser().getEmail())) {
            return true;
        }
        Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(getUser().getEmail());
        return m.matches();
    }


    public String delete() {
        userManager.removeUser(getUser().getId().toString());
        addMessage("user.deleted", getUser().getShortName());

        return "list";
    }

    /**
     * Convenience method to determine if the user came from the list screen
     *
     * @return String
     */
    public String getFrom() {
        if ((id != null) || (getParameter("editUser:add") != null) || ("list".equals(getParameter("from")))) {
            return "list";
        }

        return "";
    }

    // Form Controls ==========================================================
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, String> getAvailableRoles() {
        if (availableRoles == null) {
            List<LabelValue> roles = (List<LabelValue>) getServletContext().getAttribute(Constants.AVAILABLE_ROLES);

            availableRoles = ConvertUtil.convertListToMap(roles);
            if (!isCurrentUserAdmin()) {
                availableRoles.remove(Constants.ADMIN_ROLE);
                availableRoles.remove(Constants.STAROSTA_ROLE);
            }
        }

        return availableRoles;
    }

    public boolean isInputFieldShouldBeDisabled() {
        boolean isInputToDisable = true;
        if (isCurrentUserAdmin() || isCurrentUserStarosta()) {
            isInputToDisable = false;
        }
        return isInputToDisable;
    }

    public boolean isCurrentUserAdmin() {
        UserHelper userHelperBean = (UserHelper) FacesUtils.getManagedBean("userHelper");
        return userHelperBean.ifCurrentUserHasRole(Constants.ADMIN_ROLE);
    }
    
    public boolean isCurrentUserStarosta() {
        UserHelper userHelperBean = (UserHelper) FacesUtils.getManagedBean("userHelper");
        return userHelperBean.ifCurrentUserHasRole(Constants.STAROSTA_ROLE);
    }

    public boolean isCurrentUserJustUser() {
        UserHelper userHelperBean = (UserHelper) FacesUtils.getManagedBean("userHelper");
        return userHelperBean.ifCurrentUserHasRole(Constants.USER_ROLE);
    }

    public List<WorkUnit> getAllWorkUnits() {
        setSortColumn("date");
        setAscending(false);
        return workUnitManager.loadAllWorkUnitsByEmployee(getUser());
    }
    
    public int getAllWorksOurs(){
        List<WorkUnit> units = workUnitManager.loadAllWorkUnitsByEmployee(getUser());
        int sum = 0;
        for(WorkUnit u: units){
            sum+=u.getHoursAmount();
        }
        return sum;
    }

    public List<SelectItem> getFloors() {
        List<String> names = floorManager.getFloorsNames();

        List<SelectItem> items = new ArrayList<SelectItem>();
        for (String name : names) {
            items.add(new SelectItem(name));
        }
        return items;
    }

    public String[] getUserRoles() {
        userRoles = new String[getUser().getRoles().size()];

        int i = 0;

        if (userRoles.length > 0) {
            for (Role role : getUser().getRoles()) {
                userRoles[i] = role.getName();
                i++;
            }
        }

        return userRoles;
    }

    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    public FloorManager getFloorManager() {
        return floorManager;
    }

    public void setFloorManager(FloorManager floorManager) {
        this.floorManager = floorManager;
    }

    public WorkUnitManager getWorkUnitManager() {
        return workUnitManager;
    }

    public void setWorkUnitManager(WorkUnitManager workUnitManager) {
        this.workUnitManager = workUnitManager;
    }

    public void setRememberMe(boolean param){
        //stub
    }
}
