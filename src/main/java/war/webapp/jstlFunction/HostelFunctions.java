package war.webapp.jstlFunction;

import war.webapp.util.MonthHelper;
import java.util.ResourceBundle;

public final class HostelFunctions{

     public static String getShortMonthString(int monthIndex, ResourceBundle bundle){
         return MonthHelper.getShortMonthString(monthIndex, bundle);
     }
}
