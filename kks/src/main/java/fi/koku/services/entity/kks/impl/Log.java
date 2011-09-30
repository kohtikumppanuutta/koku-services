package fi.koku.services.entity.kks.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogServiceFactory;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.services.utility.log.v1.ServiceFault;

public class Log {

  private Log() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(Log.class);

  public static final String CLIENT_ID = "kks";
  public static final String READ = "read";
  public static final String CREATE = "create";
  public static final String DELETE = "delete";
  public static final String UPDATE = "update";
  public static final String QUERY = "query";

  public static final String KKS = "KKS";
  public static final String ENDPOINT = "http://localhost:8180/lok-service-0.0.1-SNAPSHOT/LogServiceEndpointBean?wsdl";
  // final public static String ENDPOINT = "http://localhost:8180";
  public static final String LOG_SERVICE_USER_ID = "marko";
  public static final String LOG_SERVICE_PASSWORD = "marko";

  public static void logRead(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.READ, dataType, customer, audit, message);
  }

  public static void logCreate(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.CREATE, dataType, customer, audit, message);
  }

  public static void logUpdate(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.UPDATE, dataType, customer, audit, message);
  }

  public static void logDelete(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.DELETE, dataType, customer, audit, message);
  }

  public static void logQuery(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.QUERY, dataType, customer, audit, message);
  }

  private static AuditInfoType getLogAuditInfo(String user) {
    AuditInfoType a = new AuditInfoType();
    a.setComponent(Log.KKS);
    a.setUserId(user);
    return a;
  }

  private static LogServicePortType getLogService() {
    LogServiceFactory log = new LogServiceFactory(Log.LOG_SERVICE_USER_ID, Log.LOG_SERVICE_PASSWORD, Log.ENDPOINT);
    return log.getLogService();
  }

  private static void log(String operation, String dataType, String customer,
      fi.koku.services.entity.kks.v1.AuditInfoType audit, String message) {

    try {
      LogServicePortType log = getLogService();
      LogEntryType l = new LogEntryType();
      l.setClientSystemId(Log.CLIENT_ID);
      l.setCustomerPic(customer);
      l.setDataItemId(Log.CLIENT_ID);
      l.setDataItemType(dataType);
      l.setMessage(message);
      l.setOperation(operation);
      Calendar c = new GregorianCalendar();
      c.setTime(new Date());
      l.setTimestamp(c);
      l.setUserPic(audit.getUserId());

      log.opLog(l, getLogAuditInfo(audit.getUserId()));
    } catch (ServiceFault e) {

      LOG.error("Failed to log operation " + operation + " for data type " + dataType, e);
    }
  }
}
