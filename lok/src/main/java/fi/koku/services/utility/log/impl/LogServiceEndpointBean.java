/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

import static fi.koku.services.utility.log.impl.LogServiceErrorCode.LOG_ERROR_INVALID_ARCHIVE_DATE;
import static fi.koku.services.utility.log.impl.LogServiceErrorCode.LOG_ERROR_INVALID_QUERY_CRITERIA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.authorizationinfo.util.AuthUtils;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoService;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoServiceFactory;
import fi.koku.services.utility.log.v1.ArchivalResultsType;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.services.utility.log.v1.VoidType;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * KoKu log service implementation class.
 * 
 * @author aspluma
 * @author makinsu
 */
@Stateless
@Interceptors({ LogServiceFaultInterceptor.class })
@WebService(wsdlLocation = "META-INF/wsdl/logService.wsdl", endpointInterface = "fi.koku.services.utility.log.v1.LogServicePortType", targetNamespace = "http://services.koku.fi/utility/log/v1", portName = "logService-soap11-port", serviceName = "logService")
@RolesAllowed("koku-role")
public class LogServiceEndpointBean implements LogServicePortType {
  private static final Logger logger = LoggerFactory.getLogger(LogServiceEndpointBean.class);
  private SimpleDateFormat df = new SimpleDateFormat(LogConstants.DATE_FORMAT);

  @EJB
  private LogService logService;

  private LogConverter logConverter;

  private LogUtils lu = new LogUtils();

  private AuthorizationInfoService authInfoService;

  public LogServiceEndpointBean() {
    logConverter = new LogConverter();
    String uid = KoKuPropertiesUtil.get("lok.authorizationinfo.service.user.id");
    String pwd = KoKuPropertiesUtil.get("lok.authorizationinfo.service.password");
    String ep = KoKuPropertiesUtil.get("authorizationinfo.service.endpointaddress");
    AuthorizationInfoServiceFactory f = new AuthorizationInfoServiceFactory(uid, pwd, ep);
    authInfoService = f.getAuthorizationInfoService();
  }

  /**
   * Implements the use case LOK-1 (Tallenna lokitieto).
   * 
   * @return
   */
  @Override
  public VoidType opLog(LogEntriesType logEntriesType, AuditInfoType auditInfoType) { // throws
                                                                                      // KoKuFaultException
                                                                                      // {
    logger.info("opLog");
    List<LogEntryType> list = logEntriesType.getLogEntry();

    Iterator i = list.iterator();
    while (i.hasNext()) {
      LogEntryType logEntryType = (LogEntryType) i.next();
      if (logEntryType != null) {

        // call to the actual writing

        // Use ClientSystemId to tell in which log table we write!
        if (LogConstants.LOG_WRITER_LOG.equalsIgnoreCase(logEntryType.getClientSystemId())) {
          if (lu.logInputOk(logEntryType, LogConstants.LOG_ADMIN)) {
            logService.writeAdmin(logConverter.fromWsTypeToAdmin(logEntryType));
          }
        } else {
          if (lu.logInputOk(logEntryType, LogConstants.LOG_NORMAL)) {
            logService.write(logConverter.fromWsType(logEntryType));
          }
        }
      }
    }
    return new VoidType();
  }

  /**
   * Implements the use cases LOK-3 (Etsi lokitieto) and LOK-4 (Tarkista lokin
   * käsittelyloki).
   */
  @Override
  public LogEntriesType opQueryLog(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) { 
                                                                                                    
    logger.info("opQueryLog");
    LogEntriesType logEntriesType = new LogEntriesType();

    if (LogConstants.LOG_NORMAL.equals(criteriaType.getLogType())) {
      AuthUtils.requirePermission("AdminSystemLogFile", auditInfoType.getUserId(),
          authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

      List<LogEntry> entries;

      try {
        // call to the log database
        entries = logService.query(logConverter.fromWsType(criteriaType));

        if (entries == null) {
          logger.info("No entries found in log table!");
        } else {
          Iterator<LogEntry> i = entries.iterator();
          while (i.hasNext()) {
            LogEntry entry = (LogEntry) i.next();
            if (entry != null) {
              // convert log entry to Web Service type and add it to the
              // collection
              logEntriesType.getLogEntry().add(logConverter.toWsType(entry));
            }
          }
        }
      } catch (ParseException e) {
        logger.error(e.getMessage());
        LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_PARSING;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }

   // log the query to Admin log
      AdminLogEntry adminLogEntry = new AdminLogEntry();
      adminLogEntry.setTimestamp(Calendar.getInstance().getTime());
      adminLogEntry.setUserPic(auditInfoType.getUserId());
      adminLogEntry.setCustomerPic(criteriaType.getCustomerPic());
      adminLogEntry.setOperation(LogConstants.OPERATION_VIEW);

      // set end date back to 1 day earlier so that the search criteria given by
      // the user is written to admin log
      Calendar end = criteriaType.getEndTime().toGregorianCalendar();

      end.set(Calendar.DATE, end.get(Calendar.DATE) - 1);
      criteriaType.setEndTime(CalendarUtil.getXmlDateTime(end.getTime()));
      // LOK-3: "tapahtumatietona hakuehdot"
      adminLogEntry.setMessage(criteriaType.getCustomerPic() + " " + criteriaType.getDataItemType() + " "
          + df.format(CalendarUtil.getDate(criteriaType.getStartTime())) + " - "
          + df.format(CalendarUtil.getDate(criteriaType.getEndTime())));

      //call the lok service
      logService.writeAdmin(adminLogEntry);

    } else if (LogConstants.LOG_ADMIN.equals(criteriaType.getLogType())) {
      AuthUtils.requirePermission("ViewAdminLogFile", auditInfoType.getUserId(),
          authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

      List<AdminLogEntry> entries;

      try {
        // call to the log database
        entries = logService.queryAdmin(logConverter.fromWsType(criteriaType));

        if (entries == null) {
          logger.info("No entries found in log_admin table!");
        } else {
          Iterator<AdminLogEntry> j = entries.iterator();

          while (j.hasNext()) {
            // convert log entry to AdminLog Web Service type and
            // add it to the collection
            AdminLogEntry entry = (AdminLogEntry) j.next();
            if (entry != null) {
              logEntriesType.getLogEntry().add(logConverter.toWsFromAdminType(entry));
            }
          }
        }
      } catch (ParseException e) {
        logger.error("parseException: " + e.getMessage());
        LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_PARSING;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      // end of the actual query

      // log the query to normal log
      LogEntry logEntry = new LogEntry();
      logEntry.setUserPic(auditInfoType.getUserId());

      // set end date back to 1 day earlier so that the real query end date
      // given by the user is written to log
      Calendar end = criteriaType.getEndTime().toGregorianCalendar();
      end.set(Calendar.DATE, end.get(Calendar.DATE) - 1);

      // LOK-4: "Tapahtumatietona hakuehdot"
      logEntry.setMessage("start: " + df.format(CalendarUtil.getDate(criteriaType.getStartTime())) + ", end: "
          + df.format(end.getTime()));
      logEntry.setTimestamp(Calendar.getInstance().getTime());
      logEntry.setOperation("search");
      logEntry.setClientSystemId("adminlog");
      logEntry.setDataItemType("log");
      logEntry.setDataItemId("adminlogid");

      // call to lok service
      logService.write(logEntry);
    } else {
      throw new KoKuFaultException(LOG_ERROR_INVALID_QUERY_CRITERIA.getValue(),
          LOG_ERROR_INVALID_QUERY_CRITERIA.getDescription());
    }

    return logEntriesType;
  }

  /**
   * Implements the use case LOK-2 (Arkistoi lokitietoa).
   */
  @Override
  public ArchivalResultsType opArchiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType) {
    logger.info("opArchiveLog");
    AuthUtils.requirePermission("AdminSystemLogFile", auditInfoType.getUserId(),
        authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

    int entryCount = 0;

    if (archivalParameters.getEndDate() == null) {
      logger.error("archival end date not found!");
    } else if (!lu.isBeforeToday(CalendarUtil.getDate(archivalParameters.getEndDate()))) {
      logger.error("Archive end date is not before today as it should.");
      throw new KoKuFaultException(LOG_ERROR_INVALID_ARCHIVE_DATE.getValue(),
          LOG_ERROR_INVALID_ARCHIVE_DATE.getDescription());
    } else {

      try {
        Date endDate = CalendarUtil.getDate(archivalParameters.getEndDate());

        // first, find out what is the earliest entry to be archived so that we
        // can write
        // about this in the archive log
        Date movedDate = lu.moveOneDay(endDate);
        // this has to be called before archiving
        Date earliest = logService.getEarliest(movedDate);

        // call to the actual archiving
        entryCount = logService.archive(endDate);

        if (entryCount < 1) {
          // do not throw a KoKuFaultException
          logger.info("Nothing to archive before date " + endDate);
        } else { // write to admin log about the archive only if there was
                 // something to archive

          logger.info("Log was archived. Now try to write in admin log.");
          // move the archive date one day ahead so that everything from the end
          // date will be taken account

          // log this query to admin log
          AdminLogEntry adminLogEntry = new AdminLogEntry();
          adminLogEntry.setTimestamp(Calendar.getInstance().getTime());
          adminLogEntry.setUserPic(auditInfoType.getUserId());
          adminLogEntry.setOperation("archive");

          if (earliest == null) {
            logger.error("Could not get the date of the earliest entry that was archived.");
            adminLogEntry.setMessage("archive log from ? to "
                + df.format(CalendarUtil.getDate(archivalParameters.getEndDate())));
          } else {
            adminLogEntry.setMessage("archive log from " + df.format(earliest) + " to " + df.format(endDate));
          }
          logService.writeAdmin(adminLogEntry);
        }

      } catch (KoKuFaultException f) {
        throw f;
      } catch (Exception e) {
        logger.error("Error in archiving: " + e.getMessage());
        LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_ARCHIVE_LOG_NOT_AVAILABLE;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }

    }
    ArchivalResultsType count = new ArchivalResultsType();
    count.setLogEntryCount(entryCount);

    return count;
  }

  /**
   * Convert from internal object representation (LogEntry) to webservice type
   * (LogEntryType).
   * 
   * @author makinsu
   */
  private static class LogConverter {

    public LogConverter() {
    }

    /**
     * Method converts the query criteria from WS type to service type.
     * 
     * @param type
     * @return
     */
    public LogQueryCriteria fromWsType(LogQueryCriteriaType type) {
      // criteria parameters have been null-checked on the portlet side
      LogQueryCriteria criteria = new LogQueryCriteria(type.getLogType(), type.getCustomerPic(),
          type.getDataItemType(), CalendarUtil.getDate(type.getStartTime()), CalendarUtil.getDate(type.getEndTime()));

      return criteria;
    }

    /**
     * Helper method that converts the Admin log entry to Web Service type
     * 
     * @param entry
     * @return
     * @throws ParseException
     */
    public LogEntryType toWsFromAdminType(AdminLogEntry entry) throws ParseException {

      LogEntryType entryType = new LogEntryType();
      entryType.setCustomerPic(entry.getCustomerPic());
      entryType.setUserPic(entry.getUserPic());
      entryType.setOperation(entry.getOperation());
      entryType.setMessage(entry.getMessage());
      entryType.setTimestamp(CalendarUtil.getXmlDateTime(entry.getTimestamp()));
      return entryType;
    }

    /**
     * Helper method that converts the Log entry to Web Service type
     * 
     * @param entry
     * @return
     * @throws ParseException
     */
    public LogEntryType toWsType(LogEntry entry) throws ParseException {
      LogEntryType et = new LogEntryType();

      et.setClientSystemId(entry.getClientSystemId());
      et.setCustomerPic(entry.getCustomerPic());
      et.setDataItemId(entry.getDataItemId());
      et.setDataItemType(entry.getDataItemType());
      et.setMessage(entry.getMessage());
      et.setOperation(entry.getOperation());
      et.setTimestamp(CalendarUtil.getXmlDateTime(entry.getTimestamp()));
      et.setUserPic(entry.getUserPic());

      return et;
    }

    /**
     * Convert from webservice type (LogEntryType) to internal object
     * representation (LogEntry).
     * 
     * @author makinsu
     * @param logt
     * @return
     */
    public LogEntry fromWsType(LogEntryType logt) {
      LogEntry entry = new LogEntry();

      entry.setCustomerPic(logt.getCustomerPic());
      entry.setClientSystemId(logt.getClientSystemId());
      entry.setDataItemType(logt.getDataItemType());
      entry.setDataItemId(logt.getDataItemId());
      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      entry.setTimestamp(CalendarUtil.getDate(logt.getTimestamp()));
      entry.setUserPic(logt.getUserPic());

      return entry;
    }

    /**
     * Converts the LogEntryType ws type to AdminLogEntry type
     * 
     * @param logt
     * @return
     */
    public AdminLogEntry fromWsTypeToAdmin(LogEntryType logt) {
      AdminLogEntry entry = new AdminLogEntry();

      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      entry.setTimestamp(CalendarUtil.getDate(logt.getTimestamp()));
      entry.setUserPic(logt.getUserPic());
      entry.setCustomerPic(logt.getCustomerPic());

      return entry;
    }

  }

}
