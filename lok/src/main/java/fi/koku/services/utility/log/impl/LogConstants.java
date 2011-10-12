package fi.koku.services.utility.log.impl;

public class LogConstants {
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String LOG_NORMAL = "loki"; 
  public static final String LOG_ADMIN = "seurantaloki"; 

  public static final int LOG_ERROR_PARSING = 2000;
  public static final int LOG_ERROR_QUERY = 2100;
  public static final int LOG_NOTHING_TO_ARCHIVE = 2200;
  public static final int LOG_ERROR_TIMESTAMP_MISSING = 2300;
  public static final int LOG_ERROR_USERPIC_MISSING = 2310;
  
  public static final String LOG_WRITER_LOG = "log";
  public static final String COMPONENT_LOK = "lok";
  
  public static final String OPERATION_VIEW = "view log";
  
  private LogConstants(){
  }
  
}
