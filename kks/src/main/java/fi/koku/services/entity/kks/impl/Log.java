package fi.koku.services.entity.kks.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogServiceFactory;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.services.utility.log.v1.ServiceFault;

public class Log {

  public Log() {
  }

  final public static String CLIENT_ID = "kks";
  final public static String READ = "read";
  final public static String CREATE = "create";
  final public static String DELETE = "delete";
  final public static String UPDATE = "update";
  final public static String QUERY = "query";

  final public static String KKS = "KKS";
  final public static String ENDPOINT = "http://localhost:8180/lok-service-0.0.1-SNAPSHOT/LogServiceEndpointBean?wsdl";
  // final public static String ENDPOINT = "http://localhost:8180";
  final public static String LOG_SERVICE_USER_ID = "marko";
  final public static String LOG_SERVICE_PASSWORD = "marko";

  public void logRead(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.READ, dataType, customer, audit, message);
  }

  public void logCreate(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.CREATE, dataType, customer, audit, message);
  }

  public void logUpdate(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.UPDATE, dataType, customer, audit, message);
  }

  public void logDelete(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.DELETE, dataType, customer, audit, message);
  }

  public void logQuery(String customer, String dataType, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      String message) {
    log(Log.QUERY, dataType, customer, audit, message);
  }

  private AuditInfoType getLogAuditInfo(String user) {
    AuditInfoType a = new AuditInfoType();
    a.setComponent(Log.KKS);
    a.setUserId(user);
    return a;
  }

  private LogServicePortType getLogService() {
    LogServiceFactory log = new LogServiceFactory(Log.LOG_SERVICE_USER_ID, Log.LOG_SERVICE_PASSWORD, Log.ENDPOINT);
    return log.getLogService();
  }

  private void log(String operation, String dataType, String customer,
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
