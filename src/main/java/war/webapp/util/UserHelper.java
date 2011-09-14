package war.webapp.util;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

import javax.faces.model.SelectItem;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserHelper {

    public boolean ifCurrentUserHasRole(String roleName) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAskedRole = false;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals(roleName)) {
                hasAskedRole = true;
                break;
            }
        }
        return hasAskedRole;
    }


}
