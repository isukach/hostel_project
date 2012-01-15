package war.webapp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import war.webapp.Constants;

import javax.faces.model.SelectItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 */
public final class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";
    private  List<SelectItem> daysOfMonth;
    private  List<SelectItem> months;
    private  List<SelectItem> years;

    public List<SelectItem> getDaysOfMonth() {
        if (daysOfMonth != null) {
            return daysOfMonth;
        }
        daysOfMonth = new ArrayList<SelectItem>();
        for (int i = 1; i <= 31; ++i) {
            daysOfMonth.add(new SelectItem(i));
        }
        return daysOfMonth;
    }

    public List<SelectItem> getMonths() {
        if (months != null) {
            return months;
        }
        months = new ArrayList<SelectItem>();
        for (int i = 1; i <= 12; ++i) {
            months.add(new SelectItem(i));
        }
        return months;
    }

    public List<SelectItem> getYears() {
        if (years != null) {
            return years;
        }
        years = new ArrayList<SelectItem>();
        for (int i = 1980; i <= 2009; ++i) {
            years.add(new SelectItem(i));
        }
        return years;
    }

    
    public static int getDayOfWeekForFirstSeptember(int year) {
        switch (year) {
        case 2010:
            return 3;
        case 2011:
            return 4;
        case 2012:
            return 1; //saturday
        case 2013:
            return 1; //sunday
        case 2014:
            return 1;
        }
        Calendar c = getDate(year, Calendar.SEPTEMBER, 1);
        if (c.get(Calendar.DAY_OF_WEEK) == 1 || c.get(Calendar.DAY_OF_WEEK) == 7) {
            return 1;
        }
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }
    
    private static Calendar getDate(int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return c;
    }
    

    /**
     * Return default datePattern (MM/dd/yyyy)
     * 
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "dd/MM/yyyy";
        }

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to mm/dd/yyyy.
     * 
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     * 
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException when String doesn't match the expected format
     * @see java.text.SimpleDateFormat
     */
    public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     * a
     * 
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     * 
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     * 
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     * 
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     * 
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }

    /**
     * Returns Calendar instance that points to 31 August of current study year.
     * Eg: if today is 15 March 2012 than method will return 31 August 2011
     *
     * @return 31 August of current study year
     */
    public static Calendar getFirstDayOfStudyYear() {
        Calendar firstDayOfStudyYear = Calendar.getInstance();
        int curMonth = firstDayOfStudyYear.get(Calendar.MONTH);
        if (curMonth < Calendar.JULY) {
            int currentYear = firstDayOfStudyYear.get(Calendar.YEAR);
            firstDayOfStudyYear.set(Calendar.YEAR, currentYear - 1);
            --currentYear;
        }
        firstDayOfStudyYear.set(Calendar.MONTH, Calendar.AUGUST);
        firstDayOfStudyYear.set(Calendar.DAY_OF_MONTH, 31);
        return firstDayOfStudyYear;
    }
    
    public static Calendar getDateByYearAndMonthNum(Integer year, Integer month) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        return date;
    }
}
