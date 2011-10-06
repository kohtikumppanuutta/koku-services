package fi.koku.services.utility.log.impl;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
  private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

  
  /**
   * Helper method for parsing a Date to a Calendar
   * 
   * @param date
   * @return
   */
  public Calendar parseToCal(Date date) {
    logger.debug("parseToCal original date: "+date.toString());
    Calendar cal = null;

    if (date != null) { // if it's null, return a null value
      cal = Calendar.getInstance();
      cal.setTime(date);
    }
    logger.debug("parseToCal new date: "+cal.getTime().toString());
    return cal;
  }
  
  /**
   * Helper method that moves a given date one day ahead.
   * 
   * @param date
   * @return
   */
  public Date moveOneDay(Date date){
    // set the end time 1 day later so that everything added on the last day will be found
    Calendar endday = parseToCal(date);
    logger.debug("alkuperäinen endday: "+endday.getTime().toString());
    endday.set(Calendar.DATE, endday.get(Calendar.DATE) +1);
    logger.debug("uusi endday: "+endday.getTime().toString());
    return endday.getTime();
  }
  
}
