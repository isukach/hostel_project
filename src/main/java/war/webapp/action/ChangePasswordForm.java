package war.webapp.action;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import war.webapp.model.User;
import war.webapp.service.UserExistsException;

import static org.springframework.security.context.SecurityContextHolder.getContext;

public class ChangePasswordForm extends BasePage {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public String changePassword() {
        User user = getCurrentUser();
        if (validateForm(user)) {
            try {
                user.setPassword(newPassword);
                userManager.saveUser(user);
                addMessage("user.password.changedSuccessfully");
            } catch (UserExistsException e) {
                log.error(e.getMessage());
            }
        }
        return "success";
    }

    private boolean validateForm(User user) {
        if (isRememberMe()) {
            addMessage("user.password.cantBeChanged");
            return false;
        } else if (user == null) {
            addError("errors.userCannotBeFound");
            return false;
        } else if (!validateOldPassword(user)) {
            addError("user.password.oldPasswordIncorrect");
            return false;
        } else if (!validateNewPasswordsForMatching()) {
            addError("user.password.missmatch");
            return false;
        }
        return true;
    }

    private boolean validateOldPassword(User user) {
        String hashForInputOldPassword = getPasswordEncoder().encodePassword(oldPassword, null);
        return (user.getPassword().equals(hashForInputOldPassword));
    }

    private boolean validateNewPasswordsForMatching() {
        return newPassword.equals(newPasswordConfirm);
    }

    private boolean isRememberMe() {
        AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
        SecurityContext ctx = getContext();

        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            return resolver.isRememberMe(auth);
        }
        return false;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public PasswordEncoder getPasswordEncoder() {
        return (PasswordEncoder) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("passwordEncoder");
    }
}
