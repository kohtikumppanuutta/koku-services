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

/**
 * LogDAOBean.
 * 
 * @author makinsu
 * 
 */
@Stateless
public class LogDAOBean implements LogDAO {
  private static final Logger logger = LoggerFactory.getLogger(LogDAOBean.class);

  private LogUtils lu = new LogUtils();

  @PersistenceContext
  private EntityManager em;

  public LogDAOBean() {
  }

  @Override
  public int archiveLog(Date date){

    // set the end time 1 day later so that everything added on the last day
    // will be archived. (Date has already been null-checked!)
    Date movedDate = lu.moveOneDay(date);

    // check if there is anything to archive
    Query selectQuery = em.createQuery("SELECT COUNT(l) FROM LogEntry l WHERE l.timestamp < :date");
    // set the moved end date
    selectQuery.setParameter("date", movedDate);
    Long logEntryCount = (Long) selectQuery.getSingleResult();

    logger.debug("archive list size: " + logEntryCount);

    // there is nothing to archive, the portlet user will see an error message
    if (logEntryCount == 0) {
      return 0;
    } else {
   
      logger.info("insert log entries to archive log (enddate=" + date+")");
      Query archiveQuery = em
          .createNativeQuery("INSERT INTO "
              + "log_archive (data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message) "
              + "SELECT data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message "
              + "FROM log WHERE timestamp < :date");
      // set the moved end date
      archiveQuery.setParameter("date", movedDate);
      
      // execute the query
      int updateCount = archiveQuery.executeUpdate();

      logger.info("Archived " + updateCount + " lines of log to log_archive table");//from "+startDate+" to " + date);

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
    logger.debug("got earliest date: "+earliestDate);
   
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

  @Override
  public void writeAdminLog(AdminLogEntry entry) {
   logger.debug("write to Admin Log");
    em.persist(entry);
  }

  /**
   * Makes a query to the "normal" log and returns a list of LogEntries for
   * showing to the user in the portlet. (LOK-3)
   */
  @Override
  public List<LogEntry> queryLog(LogQueryCriteria criteria) {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();

    String entity = "";
    // choose the table here

    // All four query parameters are mandatory: starttime, endime,
    // customerpic, dataitemtype. The fields of criteria are null-checked on the
    // portlet side but let's check them here again
    if (criteria.getStartTime() == null || criteria.getEndTime() == null || criteria.getCustomerPic() == null
        || criteria.getDataItemType() == null) {
      throw new RuntimeException("Criteria is not valid");
    } else {

      logger.debug("queryLog-metodi: " + criteria.getLogType() + ", " + criteria.getCustomerPic() + ", "
          + criteria.getStartTime() + ", " + criteria.getEndTime());

      if (LogConstants.LOG_NORMAL.equalsIgnoreCase(criteria.getLogType())) { // tapahtumaloki
        entity = "LogEntry";
      } else {
        logger.error("Joku virhe"); // TODO: parempi virheenkäsittely
      }

      sb.append("SELECT e FROM " + entity + " e WHERE ");
      
      sb.append("e.timestamp >= :startTime");
      params.add(new Object[] { "startTime", criteria.getStartTime() });
      logger.debug("start: " + criteria.getStartTime().toString());

      sb.append(" AND ");

      // set the end time 1 day later so that everything added on the last day
      // will be archived
      criteria.setEndTime(lu.moveOneDay(criteria.getEndTime()));

      sb.append("e.timestamp <= :endTime");
      params.add(new Object[] { "endTime", criteria.getEndTime() });
      logger.debug("end: " + criteria.getEndTime().toString());

      sb.append(" AND ");

      sb.append("e.customerPic = :pic");
      params.add(new Object[] { "pic", criteria.getCustomerPic() });

      if (criteria.getDataItemType() != null && !criteria.getDataItemType().isEmpty()) {

        sb.append(" AND ");

        sb.append("e.dataItemType = :dataItemType");
        params.add(new Object[] { "dataItemType", criteria.getDataItemType() });
      }

      if (params.size() == 0) {
        throw new RuntimeException("missing criteria");
      }

      try {
        logger.debug("query: " + sb.toString());
        Query q = em.createQuery(sb.toString());

        // TODO: lisää info-lokitus!

        // build the query
        for (int i = 0; i < params.size(); i++) {
          q.setParameter((String) params.get(i)[0], params.get(i)[1]);
        }

        logger.debug("query: " + q.toString());

        // query the database
        if (LogConstants.LOG_NORMAL.equalsIgnoreCase(criteria.getLogType())) {
          return q.getResultList();
        }

      } catch (IllegalStateException e) {
        // TODO
      } catch (IllegalArgumentException ex) {
        // TODO
      }
    }
    return null; // if something went wrong
  }

  /**
   * Makes a query to the admin log and returns a list of AdminLogEntries for
   * showing to the super user in the portlet. (LOK-4)
   * 
   * 
   */
  @Override
  public Collection<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria) {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();
    String entity = "";

    if (criteria == null || criteria.getStartTime() == null || criteria.getEndTime() == null) {
      throw new RuntimeException("Criteria is not valid");
    } else {
      if (LogConstants.LOG_ADMIN.equalsIgnoreCase(criteria.getLogType())) { // seurantaloki
        entity = "AdminLogEntry";
      } else {
        logger.error("joku virhe"); // TODO: parempi virheenkäsittely
        throw new RuntimeException("virhe logtypessä");
      }

      // set the end time 1 day later so that everything added on the last day
      // will be found
      criteria.setEndTime(lu.moveOneDay(criteria.getEndTime()));

      sb.append("SELECT e FROM " + entity + " e WHERE ");

      // starttime and enddate are required and are null-checked in the portlet
      // let's check the criteria once again

      sb.append("e.timestamp >= :startTime");
      params.add(new Object[] { "startTime", criteria.getStartTime() });
      logger.debug("start: " + criteria.getStartTime().toString());

      sb.append(" AND ");

      sb.append("e.timestamp <= :endTime");
      params.add(new Object[] { "endTime", criteria.getEndTime() });

      logger.debug("end: " + criteria.getEndTime().toString());

      if (params.size() == 0) {
        throw new RuntimeException("missing criteria");
      }

      try {
        Query q = em.createQuery(sb.toString());

        // TODO: lisää info-lokitus!

        // build the query
        for (int i = 0; i < params.size(); i++) {
          q.setParameter((String) params.get(i)[0], params.get(i)[1]);
        }

        // query the database
        return q.getResultList();

      } catch (IllegalStateException e) {
        // TODO
      } catch (IllegalArgumentException ex) {
        // TODO
      } 
    }
    return null;

  }

}
