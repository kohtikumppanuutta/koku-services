package fi.koku.services.utility.log.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fi.koku.services.utility.log.v1.ServiceFault;
import fi.koku.services.utility.log.v1.ServiceFaultDetailType;

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

  // TODO: Missä ServiceFault heitetään?
  // 1) arkistoloki ei vastaa
  // 2) arkistoloki ei kuittaa onnistunutta tietojen kopiointia
  @Override
  public int archiveLog(Date date) throws ServiceFault {

    // set the end time 1 day later so that everything added on the last day
    // will be archived
    Date movedDate = lu.moveOneDay(date);

    // check if there is anything to archive
    Query selectQuery = em.createQuery("SELECT COUNT(l) FROM LogEntry l WHERE l.timestamp < :date");
    // set the moved end date
    selectQuery.setParameter("date", movedDate);
    Long logEntryCount = (Long) selectQuery.getSingleResult();

    logger.debug("archive list size: " + logEntryCount);

    // there is nothing to archive
    if (logEntryCount == 0) {
      return 0;
    } else {

      logger.info("insert log entries to archive log (date=" + date);
      Query archiveQuery = em
          .createNativeQuery("INSERT INTO "
              + "log_archive (data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message) "
              + "SELECT data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message "
              + "FROM log WHERE timestamp < :date");
      // set the moved end date
      archiveQuery.setParameter("date", movedDate);
      int updateCount = archiveQuery.executeUpdate();

      logger.info("Archived " + updateCount + "lines of log to log_archive table up to " + date);

      // jos arkistointi onnistui, ajetaan tämä
      Query deleteQuery = em.createNativeQuery("DELETE FROM log WHERE timestamp < :date");
      // set the moved end date
      deleteQuery.setParameter("date", movedDate);
      int deletedRows = deleteQuery.executeUpdate();
      logger.info("Deleted " + deletedRows + " rows from log table");
      if (deletedRows != updateCount) {
        logger.error("poistettiin eri määrä rivejä kuin siirrettiin admin_logiin.");
      }
      return updateCount;
    }
  }

  // TODO: entä jos write menee pieleen? Mistä ServiceFault heitetään?
  /**
   * Write log (note: archive log is not written with this method!)
   */
  @Override
  public void writeLog(LogEntry entry) {
    em.persist(entry);
  }

  @Override
  public void writeAdminLog(AdminLogEntry entry) {
    logger.debug("writeAdminLog: " + entry.getTimestamp().toString());
    logger.debug("customerPic: " + entry.getCustomerPic());
    em.persist(entry);
  }

  /**
   * Makes a query to the "normal" log and returns a list of LogEntries for
   * showing to the user in the portlet. (LOK-3)
   * 
   * 
   * @throws ServiceFault
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

      // TODO: ei pakollinen, jos vetovalikossa myös tyhjä vaihtoehto???
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
   * @throws ServiceFault
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
        logger.debug("query: " + sb.toString());
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

  /*
  public List<LogEntry> adminListToLogList(List<AdminLogEntry> list) {
    List<LogEntry> entryList = new ArrayList(); // TODO: onko oikein?
    ListIterator<AdminLogEntry> i = list.listIterator();
    while (i.hasNext()) {
      AdminLogEntry aentry = (AdminLogEntry) i.next();
      LogEntry entry = new LogEntry();
      entry.setCustomerPic(aentry.getCustomerPic());
      entry.setOperation(aentry.getOperation());
      entry.setMessage(aentry.getMessage());
      entry.setTimestamp(aentry.getTimestamp());
      entry.setUserPic(aentry.getUserPic());

      entryList.add(entry);
    }

    return entryList;
  }
  */
}
