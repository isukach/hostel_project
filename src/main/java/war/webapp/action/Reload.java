package war.webapp.action;

import java.io.IOException;
import java.io.Serializable;

import war.webapp.listener.StartupListener;

/**
 * JSF Page class to handle reloading options in servlet context.
 * 
 * @author Matt Raible
 */
public class Reload extends BasePage implements Serializable {
    private static final long serialVersionUID = 2466200890766730676L;

    public String execute() throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("Entering 'execute' method");
        }

        StartupListener.setupContext(getServletContext());
        addMessage("reload.succeeded");

        return "mainMenu";
    }

}
