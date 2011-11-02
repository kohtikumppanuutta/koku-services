/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

import static fi.koku.services.utility.log.impl.LogServiceErrorCode.LOG_ERROR_INVALID_ARCHIVE_DATE;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.log.v1.ArchivalResultsType;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;

/**
 * Log service implementation class
 * 
 * @author makinsu
 *
 */
@Stateless
public class LogServiceBean implements LogService {

  @EJB
  private LogDAO logDAO;
  
  private SimpleDateFormat df;
  
  private LogUtils logUtils;
  
  private static final Logger logger = LoggerFactory.getLogger(LogServiceBean.class);
  
  public LogServiceBean() {
    logUtils = new LogUtils();
    df = new SimpleDateFormat(LogConstants.DATE_FORMAT);
  }

  @Override
  public List<LogEntry> queryNormalLog(LogQueryCriteria criteria) {
    return (List<LogEntry>) logDAO.queryLog(criteria);
  }

  @Override
  public List<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria) {
   return (List<AdminLogEntry>) logDAO.queryAdminLog(criteria);
  }
  
  @Override
  public void writeAdminLogEntry(AdminLogEntry entry) {
    logDAO.writeAdminLog(entry);
  }

  @Override
  public void writeNormalLogEntry(LogEntry entry) {
    logDAO.writeLog(entry);
  }
  
  @Override
  public void writeAdminLogQueryEvent(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) {
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

    logDAO.writeAdminLog(adminLogEntry);    
  }

  @Override
  public void writeNormalLogQueryEvent(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) {
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

    logDAO.writeLog(logEntry);
  }

  @Override
  public ArchivalResultsType archiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType) {
    int entryCount = 0;

    if (archivalParameters.getEndDate() == null) {
      logger.error("archival end date not found!");
    } else if (!logUtils.isBeforeToday(CalendarUtil.getDate(archivalParameters.getEndDate()))) {
      logger.error("Archive end date is not before today as it should.");
      throw new KoKuFaultException(LOG_ERROR_INVALID_ARCHIVE_DATE.getValue(),
          LOG_ERROR_INVALID_ARCHIVE_DATE.getDescription());
    } else {

      try {
        Date endDate = CalendarUtil.getDate(archivalParameters.getEndDate());

        // first, find out what is the earliest entry to be archived so that we
        // can write
        // about this in the archive log
        Date movedDate = logUtils.moveOneDay(endDate);
        // this has to be called before archiving
        Date earliest = logDAO.getEarliest(movedDate);

        // call to the actual archiving
        entryCount = logDAO.archiveLog(endDate);

        if (entryCount < 1) {
          // do not throw a KoKuFaultException
          logger.info("Nothing to archive before date " + endDate);
        } else {
          // write to admin log about the archive only if there was
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
          writeAdminLogEntry(adminLogEntry);
        }
      } catch (Exception e) {
        // We need to indicate this error scenario in the ui
        logger.error("Error in archiving: " + e.getMessage());
        LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_ARCHIVE_LOG_NOT_AVAILABLE;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }
    ArchivalResultsType count = new ArchivalResultsType();
    count.setLogEntryCount(entryCount);

    return count;
  }

}
