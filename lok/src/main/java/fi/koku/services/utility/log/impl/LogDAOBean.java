/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;

/**
 * Log related data access facilities.
 * 
 * @author makinsu
 * 
 */
@Stateless
public class LogDAOBean implements LogDAO {
  
  private static final Logger logger = LoggerFactory.getLogger(LogDAOBean.class);

  private LogUtils logUtils;

  @PersistenceContext
  private EntityManager em;

  public LogDAOBean() {
    logUtils = new LogUtils();
  }

  @Override
  public int archiveLog(Date date){
    // set the end time 1 day later so that everything added on the last day
    // will be archived. (Date has already been null-checked!)
    Date movedDate = logUtils.moveOneDay(date);

    // check if there is anything to archive
    Query selectQuery = em.createQuery("SELECT COUNT(l) FROM LogEntry l WHERE l.timestamp < :date");
    // set the moved end date
    selectQuery.setParameter("date", movedDate);
    Long logEntryCount = (Long) selectQuery.getSingleResult();

    logger.info("archive list size: " + logEntryCount);

    // there is nothing to archive, the portlet user will see an error message
    if (logEntryCount == 0) {
      return 0;
    } else { 
      logger.info("insert log entries to archive log (enddate=" + date+")");
      Query archiveQuery = em.createNativeQuery("INSERT INTO "
          + "log_archive (data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message) "
          + "SELECT data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message "
          + "FROM log WHERE timestamp < :date");
      // set the moved end date
      archiveQuery.setParameter("date", movedDate);

      // execute the query
      int updateCount = archiveQuery.executeUpdate();

      logger.info("Archived " + updateCount + " lines of log to log_archive table");

      // If there is a Runtime error in archiving, no entries will be deleted from the database.
      // The portlet user will see an error message.

      // If the archiving went ok, delete the entries
      if(updateCount > 0){
        Query deleteQuery = em.createNativeQuery("DELETE FROM log WHERE timestamp < :date");
        // set the moved end date
        deleteQuery.setParameter("date", movedDate);
        int deletedRows = deleteQuery.executeUpdate();
        logger.info("Deleted " + deletedRows + " rows from log table");
        if (deletedRows != updateCount) {
          logger.error("the number of entries moved to archive is not the same as the number of deleted entries!");
        }
      }
      return updateCount;
    }
  }

  /**
   * Find out the earliest entry to be archived
   */
  @Override
  public Date getEarliest(Date date){
    // get the timestamp of the earliest entry to be archived
    Query dateQuery = em.createNativeQuery("SELECT MIN(timestamp) FROM log WHERE timestamp <= :date");
    dateQuery.setParameter("date", date);
    Date earliestDate = (Date)dateQuery.getSingleResult();

    if(earliestDate == null){
      logger.error("Could not get the timestamp of the earliest entry to be archived.");
    }

    return earliestDate;
  }

  /**
   * Write log (note: archive log is not written with this method!)
   */
  @Override
  public void writeLog(LogEntry entry) {
    em.persist(entry);
  }

  /**
   * Write admin log.
   */
  @Override
  public void writeAdminLog(AdminLogEntry entry) {
    em.persist(entry);
  }

  /**
   * Makes a query to the "normal" log and returns a list of LogEntries for
   * showing to the user in the portlet. (LOK-3)
   */
  @Override
  public List<LogEntry> queryLog(LogQueryCriteria criteria) throws KoKuFaultException {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();

    String entity = "";

    // All four query parameters are mandatory: starttime, endime,
    // customerpic, dataitemtype. These fields are null-checked on the
    // portlet side but let's check them here again
    if (criteria.getStartTime() == null || criteria.getEndTime() == null || criteria.getCustomerPic() == null
        || criteria.getDataItemType() == null) {
      LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_INVALID_QUERY_CRITERIA;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }

    if (LogConstants.LOG_NORMAL.equalsIgnoreCase(criteria.getLogType())) { // tapahtumaloki
      entity = "LogEntry";
    } else {
      logger.error("Wrong logtype in log query");
      LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_INVALID_LOGTYPE;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }

    sb.append("SELECT e FROM " + entity + " e WHERE ");

    sb.append("e.timestamp >= :startTime");
    params.add(new Object[] { "startTime", criteria.getStartTime() });

    sb.append(" AND ");

    // set the end time 1 day later so that everything added on the last day
    // will be archived
    criteria.setEndTime(logUtils.moveOneDay(criteria.getEndTime()));

    sb.append("e.timestamp <= :endTime");
    params.add(new Object[] { "endTime", criteria.getEndTime() });

    sb.append(" AND ");

    sb.append("e.customerPic = :pic");
    params.add(new Object[] { "pic", criteria.getCustomerPic() });

    if (criteria.getDataItemType() != null && !criteria.getDataItemType().isEmpty()) {
      sb.append(" AND ");
      String concept = criteria.getDataItemType();
      // enable star queries (only star in the end can be used)
      if (concept.endsWith("*")){
        int length = concept.length();
        concept = concept.substring(0, length - 1) + "%";
        sb.append("e.dataItemType LIKE :dataItemType");
      } else{
        sb.append("e.dataItemType = :dataItemType"); 
      }
      
      params.add(new Object[] { "dataItemType", concept}); 
    }
   

    if (params.size() == 0) {
      throw new IllegalArgumentException("Missing criteria for log query.");
    }

    Query q = em.createQuery(sb.toString());
    // limit the number of results
    q.setMaxResults(LogConstants.MAX_QUERY_RESULTS);
    // build the query
    for (int i = 0; i < params.size(); i++) {
      q.setParameter((String) params.get(i)[0], params.get(i)[1]);
    }

    // query the database
    return q.getResultList();
  }

  /**
   * Makes a query to the admin log and returns a list of AdminLogEntries for
   * showing to the super user in the portlet. (LOK-4) 
   */
  @Override
  public Collection<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria) {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();
    String entity = "";

    // starttime and enddate are required and are null-checked in the portlet
    // let's check the criteria once again
    if (criteria == null || criteria.getStartTime() == null || criteria.getEndTime() == null) {
      logger.error("Admin log query criteria is null or invalid");
      LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_INVALID_QUERY_CRITERIA;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }

    if (LogConstants.LOG_ADMIN.equalsIgnoreCase(criteria.getLogType())) { // seurantaloki
      entity = "AdminLogEntry";
    } else {
      logger.error("Wrong logtype in admin log query");
      LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_INVALID_LOGTYPE;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }

    // set the end time 1 day later so that everything added on the last day
    // will be found
    criteria.setEndTime(logUtils.moveOneDay(criteria.getEndTime()));

    sb.append("SELECT e FROM " + entity + " e WHERE ");

    sb.append("e.timestamp >= :startTime");
    params.add(new Object[] { "startTime", criteria.getStartTime() });

    sb.append(" AND ");

    sb.append("e.timestamp <= :endTime");
    params.add(new Object[] { "endTime", criteria.getEndTime() });

    if (params.size() == 0) {
      throw new IllegalArgumentException("missing query criteria");
    }

    Query q = em.createQuery(sb.toString());
    // limit the number of results
    q.setMaxResults(LogConstants.MAX_QUERY_RESULTS);
    // set parameters
    for (int i = 0; i < params.size(); i++) {
      q.setParameter((String) params.get(i)[0], params.get(i)[1]);
    }

    // query the database
    return q.getResultList();
  }

}
