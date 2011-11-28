package war.webapp.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import war.webapp.service.builder.BaseDutyListTemplateBuilder;
import war.webapp.service.impl.HttpDutyListLoadService;

import java.io.IOException;

import static war.webapp.Constants.HTTP_DOWNLOADER;
/**
 *  DutyListLoadService response for loading dutyList data
 *  by some protocol(i.e. http)
 *  or save pass it to specified place(db for example or rmi loading)
 *
 * @author <a href="mailto:andrei.misan@gmail.com">Andrei Misan</a>
 * 
 */
public abstract class DutyListLoadService {
    private static final transient Log log = LogFactory.getLog(DutyListLoadService.class);
    
    public static DutyListLoadService getService(int pServiceType){
        switch(pServiceType){
            case(HTTP_DOWNLOADER):{
                return new HttpDutyListLoadService();
            }
            default:{
                //no service 
                log.error("Such service haven't been specified or not exist.");
                return null; 
            }
        }
    }
    public abstract String generateDataDescription(Object...params);
    public abstract void download(Object...params)  throws IOException;
    public abstract BaseDutyListTemplateBuilder getDutyListTemplateBuilder();
}
