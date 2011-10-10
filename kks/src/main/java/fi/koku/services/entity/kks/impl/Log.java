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
import fi.koku.settings.KoKuPropertiesUtil;

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
  public static final String ENDPOINT = KoKuPropertiesUtil.get("lok.service.endpointaddress");
  public static final String LOG_SERVICE_USER_ID = "marko";
  public static final String LOG_SERVICE_PASSWORD = "marko";

  public static void logRead(String customer, String dataType, String userId, String message) {
    log(Log.READ, dataType, customer, userId, message);
  }

  public static void logCreate(String customer, String dataType, String userId, String message) {
    log(Log.CREATE, dataType, customer, userId, message);
  }

  public static void logUpdate(String customer, String dataType, String userId, String message) {
    log(Log.UPDATE, dataType, customer, userId, message);
  }

  public static void logDelete(String customer, String dataType, String userId, String message) {
    log(Log.DELETE, dataType, customer, userId, message);
  }

  public static void logQuery(String customer, String dataType, String userId, String message) {
    log(Log.QUERY, dataType, customer, userId, message);
  }

  public static void logValueUpdate(String collection, String collectionType, String customer, String user,
      KksEntry entry, KksValue oldVal, KksValue newVal) {
    if (oldVal.getValue().equals(newVal.getValue())) {

      StringBuilder sb = new StringBuilder();
      sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
          .append(entry.getEntryClassId().toString()).append(") value changed from ").append(oldVal.getValue())
          .append(" to ").append(newVal.getValue());

      Log.logUpdate(customer, collectionType, user, sb.toString());
    }
  }

  public static void logValueAddition(String collection, String collectionType, String customer, String user,
      KksEntry entry, KksValue newVal) {

    StringBuilder sb = new StringBuilder();
    sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
        .append(entry.getEntryClassId().toString()).append(") new value ").append(newVal.getValue());

    Log.logCreate(customer, collectionType, user, sb.toString());
  }

  public static void logValueDeletion(String collection, String collectionType, String customer, String user,
      KksEntry entry, KksValue remVal) {
    StringBuilder sb = new StringBuilder();
    sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
        .append(entry.getEntryClassId().toString()).append(") removed value ").append(remVal.getValue());

    Log.logDelete(customer, collectionType, user, sb.toString());
  }

  private static AuditInfoType getLogAuditInfo(String user) {
    AuditInfoType a = new AuditInfoType();
    a.setComponent(Log.KKS);
    a.setUserId(user);
    return a;
  }

  private static LogServicePortType getLogService() {
    String a = KoKuPropertiesUtil.get("lok.service.endpointaddress");
    LogServiceFactory log = new LogServiceFactory(Log.LOG_SERVICE_USER_ID, Log.LOG_SERVICE_PASSWORD, Log.ENDPOINT);
    return log.getLogService();
  }

  private static void log(String operation, String dataType, String customer, String userId, String message) {

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
      l.setUserPic(userId);

      log.opLog(l, getLogAuditInfo(userId));
    } catch (ServiceFault e) {

      LOG.error("Failed to log operation " + operation + " for data type " + dataType, e);
    }
  }
}
