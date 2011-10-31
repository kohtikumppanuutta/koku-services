/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.ServiceFault;

/**
 * Helper class for audit logs
 * 
 * @author Ixonos / tuomape
 * 
 */
public final class Log {

  public Log() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(Log.class);
  public static final String CLIENT_ID = "kks";
  public static final String READ = "read";
  public static final String CREATE = "create";
  public static final String DELETE = "delete";
  public static final String UPDATE = "update";
  public static final String QUERY = "query";
  public static final String KKS = "KKS";

  public void logRead(String customer, String dataType, String userId, String message) {
    log(Log.READ, dataType, customer, userId, message);
  }

  public void logCreate(String customer, String dataType, String userId, String message) {
    log(Log.CREATE, dataType, customer, userId, message);
  }

  public void logUpdate(String customer, String dataType, String userId, String message) {
    log(Log.UPDATE, dataType, customer, userId, message);

  }

  public void logDelete(String customer, String dataType, String userId, String message) {
    log(Log.DELETE, dataType, customer, userId, message);
  }

  public void logQuery(String customer, String dataType, String userId, String message) {
    log(Log.QUERY, dataType, customer, userId, message);
  }

  public void logValueUpdate(String collection, String collectionType, String customer, String user, KksEntry entry,
      KksValue oldVal, KksValue newVal, LogEntriesType logEntries) {
    if (oldVal == null || !oldVal.getValue().equals(newVal.getValue())) {

      StringBuilder sb = new StringBuilder();
      sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
          .append(entry.getEntryClassId().toString()).append(") value changed from [")
          .append(oldVal == null ? "" : oldVal.getValue()).append("] to [").append(newVal.getValue() + "]");

      addLogEntry(UPDATE, collectionType, customer, user, sb.toString(), logEntries);
    }
  }

  public void logValueUpdate(String collection, String collectionType, String customer, String user, KksEntry entry,
      KksValue oldVal, KksValue newVal) {
    if (oldVal == null || !oldVal.getValue().equals(newVal.getValue())) {

      StringBuilder sb = new StringBuilder();
      sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
          .append(entry.getEntryClassId().toString()).append(") value changed from [")
          .append(oldVal == null ? "" : oldVal.getValue()).append("] to [").append(newVal.getValue() + "]");

      logUpdate(customer, collectionType, user, sb.toString());
    }
  }

  public void logEntries(LogEntriesType entries, String userId) {
    try {

      for (LogEntryType lt : entries.getLogEntry()) {
        System.out.println("LOG " + lt.getCustomerPic() + " MESSAGE " + lt.getMessage());
      }

      KksServiceContainer.getService().log().opLog(entries, getLogAuditInfo(userId));
    } catch (ServiceFault e) {

      LOG.error("Failed to log entries set", e);
    }
  }

  public void logValueAddition(String collection, String collectionType, String customer, String user, KksEntry entry,
      KksValue newVal) {

    StringBuilder sb = new StringBuilder();
    sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
        .append(entry.getEntryClassId().toString()).append(") new value ").append(newVal.getValue());

    logCreate(customer, collectionType, user, sb.toString());
  }

  public void logValueDeletion(String collection, String collectionType, String customer, String user, KksEntry entry,
      KksValue remVal) {
    StringBuilder sb = new StringBuilder();
    sb.append(collection).append(" entry ").append(entry.getId().toString()).append(" (class id=")
        .append(entry.getEntryClassId().toString()).append(") removed value ").append(remVal.getValue());

    logDelete(customer, collectionType, user, sb.toString());
  }

  private AuditInfoType getLogAuditInfo(String user) {
    AuditInfoType a = new AuditInfoType();
    a.setComponent(Log.KKS);
    a.setUserId(user);
    return a;
  }

  private void log(String operation, String dataType, String customer, String userId, String message) {

    try {

      LogEntryType l = new LogEntryType();
      l.setClientSystemId(Log.CLIENT_ID);
      l.setCustomerPic(customer);
      l.setDataItemId(Log.CLIENT_ID);
      l.setDataItemType(dataType);
      l.setMessage(message);
      l.setOperation(operation);
      l.setTimestamp(CalendarUtil.getXmlDateTime(new Date()));
      l.setUserPic(userId);

      LogEntriesType entries = new LogEntriesType();
      entries.getLogEntry().add(l);
      KksServiceContainer.getService().log().opLog(entries, getLogAuditInfo(userId));
    } catch (ServiceFault e) {
      LOG.error("Failed to log operation " + operation + " for data type " + dataType, e);
    }
  }

  private void addLogEntry(String operation, String dataType, String customer, String userId, String message,
      LogEntriesType entries) {

    LogEntryType l = new LogEntryType();
    l.setClientSystemId(Log.CLIENT_ID);
    l.setCustomerPic(customer);
    l.setDataItemId(Log.CLIENT_ID);
    l.setDataItemType(dataType);
    l.setMessage(message);
    l.setOperation(operation);
    l.setTimestamp(CalendarUtil.getXmlDateTime(new Date()));
    l.setUserPic(userId);
    entries.getLogEntry().add(l);
  }
}
