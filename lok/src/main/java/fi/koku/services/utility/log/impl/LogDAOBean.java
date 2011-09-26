package fi.koku.services.utility.log.impl;

import java.util.ArrayList;
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

  @PersistenceContext
  private EntityManager em;

  public LogDAOBean() {
  }

  // TODO: Missä ServiceFault heitetään?
  // 1) arkistoloki ei vastaa
  // 2) arkistoloki ei kuittaa onnistunutta tietojen kopiointia
  @Override
  public void archiveLog(Date date) {
    
    /*
     * TODO tähän tyyliin: INSERT INTO log_archive (...) SELECT FROM log WHERE
     * timestamp > valittualkuhetki AND timestamp < valittualkuhetki; -> jos ei
     * löydy mitään, ilmoitus käyttäjälle
     * 
     * DELETE FROM log WHERE timestamp > valittualkuhetki AND timestamp <
     * valittualkuhetki; -> ilmoitus onnistumisesta/virheestä käyttäjälle kts.
     * http://dev.mysql.com/doc/refman/5.0/en/insert-select.html
     */
    // check if there is anything to archive
    
    Query selectQuery = em.createQuery("SELECT COUNT(l) FROM LogEntry l WHERE l.timestamp < :date");
    selectQuery.setParameter("date", date);
    Long logEntryCount = (Long)selectQuery.getSingleResult(); // en ole varma toimiiko tyypitys näin

    List archiveList = new ArrayList(); // FIXME
   
    // there is nothing to archive
    if (archiveList == null || archiveList.isEmpty()) {
      ServiceFaultDetailType sfdt = new ServiceFaultDetailType();
      sfdt.setCode(LogConstants.LOG_NOTHING_TO_ARCHIVE);
      logger.info("ei arkistoitavaa ennen päivää " + date);
   //TODO: Endpointiin?   throw new ServiceFault("ei löytynyt arkistoitavaa", sfdt);
    }

    logger.info("insert log entries to archive log (date="+date);
    Query archiveQuery = em.createNativeQuery("INSERT INTO " +
        "log_archive (data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message) " +
        "SELECT data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message " +
        "FROM log WHERE timestamp < :date");
    archiveQuery.setParameter("date", date);
    int updateCount = archiveQuery.executeUpdate();

    logger.info("Archived log to log_archive table up to " + date);
    // TODO: Millä komennolla arkistointi pitäisi ajaa? Kuuluuko palauttaa
    // jotain? archiveQuery.getSingleResult();
    // jos arkistointi onnistui, ajetaan tämä
    Query deleteQuery = em.createNativeQuery("DELETE FROM log WHERE timestamp < :date");
    deleteQuery.setParameter("date", date);
    int deletedRows = deleteQuery.executeUpdate();
    logger.info("Deleted " + deletedRows + " rows from log table");
  }

  // TODO: entä jos write menee pieleen? Mistä ServiceFault heitetään?
  /**
   * Write log 
   * (note: archive log is not written with this method!)
   */
  @Override
  public void writeLog(LogEntry entry) {
    em.persist(entry);
  }

  @Override
  public void writeAdminLog(AdminLogEntry entry){
    logger.debug("writeAdminLog: "+entry.getTimestamp().toString());
    logger.debug("customerPic: "+entry.getCustomerPic());
    em.persist(entry);
  }

  /**
   * Makes a query to the "normal" log and returns a list of
   * LogEntries for showing to the user in the portlet. (LOK-3)
   * 
   * @throws ServiceFault
   */
  @Override
  public List<LogEntry> queryLog(LogQueryCriteria criteria) {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();
logger.debug("queryLog-metodi: "+criteria.getLogType()+", "+criteria.getCustomerPic()+", "+criteria.getStartTime()+", "+criteria.getEndTime());
    // TODO:
    // ennen tätä tsekkaa nullit:
    // -starttime, enddate, logtype
    // in both log types, start and end time are given for the search
    // these are required and the null check has been made already!

logger.debug("log type: "+criteria.getLogType());

   String entity = "";
    // choose the table here
  
     if (LogConstants.LOG_NORMAL.equalsIgnoreCase(criteria.getLogType())) { // tapahtumaloki
      entity = "LogEntry";
    } else {
      logger.error("Joku virhe"); //TODO: parempi virheenkäsittely
    }
    
    sb.append("SELECT e FROM "+entity+" e WHERE ");

    // starttime and enddate are required and are null-checked earlier
    //TODO: vai ovatko pakollisia???
    sb.append("e.timestamp >= :startTime");
    params.add(new Object[] { "startTime", criteria.getStartTime() });
    logger.debug("end: "+criteria.getStartTime().toString());
    
    sb.append(" AND ");
    
    sb.append("e.timestamp <= :endTime");
    params.add(new Object[] { "endTime", criteria.getEndTime() });

    logger.debug("end: "+criteria.getEndTime().toString());
    
    // add the customer pic and data item type to search criteria for LOK-3
//TODO: tässä pitää miettiä, mitkä kentät ovat pakollisia
      sb.append(" AND ");
      
      // pic of the child is null-checked earlier
      // pic has to be given!
      sb.append("e.customerPic = :pic");
      params.add(new Object[] { "pic", criteria.getCustomerPic() });

    
      // TODO: ei pakollinen, jos vetovalikossa myös tyhjä vaihtoehto
      if (criteria.getDataItemType() != null && !criteria.getDataItemType().isEmpty()) {
        
        sb.append(" AND ");
        
        sb.append("e.dataItemType = :dataItemType");
        params.add(new Object[] { "dataItemType", criteria.getDataItemType() });
      }
    

    if (params.size() == 0) {
      throw new RuntimeException("missing criteria");
    }

    try {
      logger.debug("query: "+sb.toString());
      Query q = em.createQuery(sb.toString());

      // TODO: lisää info-lokitus!

      // build the query
      for (int i = 0; i < params.size(); i++) {
        q.setParameter((String) params.get(i)[0], params.get(i)[1]);
      }

      // query the database
      if(LogConstants.LOG_NORMAL.equalsIgnoreCase(criteria.getLogType())){
        return q.getResultList();
      }

    } catch (IllegalStateException e) {
      // TODO
    } catch (IllegalArgumentException ex) {
      // TODO
    }
    return null; // if something went wrong
  }


  /**
   * Makes a query to the admin log and returns a list of
   * AdminLogEntries for showing to the super user in the portlet. (LOK-4)
   * 
   * @throws ServiceFault
   */
  @Override
  public Collection<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria) {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();
    String entity = "";
    
    if (LogConstants.LOG_ADMIN.equalsIgnoreCase(criteria.getLogType())) { // seurantaloki
      entity = "AdminLogEntry";
    } else {
      logger.error("joku virhe"); // TODO: parempi virheenkäsittely
    }
    
    sb.append("SELECT e FROM "+entity+" e WHERE ");

    // starttime and enddate are required and are null-checked earlier
    //TODO: vai ovatko pakollisia???
    sb.append("e.timestamp >= :startTime");
    params.add(new Object[] { "startTime", criteria.getStartTime() });
    logger.debug("end: "+criteria.getStartTime().toString());
    
    sb.append(" AND ");
    
    sb.append("e.timestamp <= :endTime");
    params.add(new Object[] { "endTime", criteria.getEndTime() });

    logger.debug("end: "+criteria.getEndTime().toString());
    
    if (params.size() == 0) {
      throw new RuntimeException("missing criteria");
    }

    try {
      logger.debug("query: "+sb.toString());
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
  
    return null;

  }
  
  public List<LogEntry> adminListToLogList(List<AdminLogEntry> list){
    List<LogEntry> entryList = new ArrayList(); //TODO: onko oikein?
    ListIterator<AdminLogEntry> i = list.listIterator();
    while(i.hasNext()){
      AdminLogEntry aentry = (AdminLogEntry)i.next();
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
}