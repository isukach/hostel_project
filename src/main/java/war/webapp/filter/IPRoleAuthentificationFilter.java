package war.webapp.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IPRoleAuthentificationFilter extends OncePerRequestFilter {
    private final transient Log log = LogFactory.getLog(LocaleRequestWrapper.class);
    private String targetRole;
    private List<String> allowedIPAddresses;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && targetRole != null) {
            boolean shouldCheck = false;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(targetRole)) {
                    shouldCheck = true;
                    break;
                }
            }
            if (shouldCheck && allowedIPAddresses != null) {
                boolean shouldAllow = false;
                for (String ipAddress : allowedIPAddresses)
                    if (request.getRemoteAddr().equals(ipAddress)) {
                        shouldAllow = true;
                        break;
                    }
                if (!shouldAllow) {
                    throw new AccessDeniedException("Access has been denied for your IP address: " + request.getRemoteAddr());
                }
            }
        } else {
            log.warn("The IPRoleAuthentificationFilter should be placed after the user has been authentificated in filter chain");
        }
        filterChain.doFilter(request, response);
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public void setAllowedIPAddresses(List<String> allowedIPAddresses) {
        this.allowedIPAddresses = allowedIPAddresses;
    }
}
