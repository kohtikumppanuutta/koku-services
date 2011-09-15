package fi.koku.services.utility.log.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
  public void archiveLog(Date date) throws ServiceFault {
   
    /* TODO
     * tähän tyyliin:
INSERT INTO log_archive (...)
SELECT FROM log WHERE timestamp > valittualkuhetki AND timestamp < valittualkuhetki;
-> jos ei löydy mitään, ilmoitus käyttäjälle

DELETE FROM log WHERE timestamp > valittualkuhetki AND timestamp < valittualkuhetki;
-> ilmoitus onnistumisesta/virheestä käyttäjälle
kts. http://dev.mysql.com/doc/refman/5.0/en/insert-select.html
     */
    // check if there is anything to archive 
    Query selectQuery = em.createNamedQuery("selectLogByDate");
    selectQuery.setParameter("date", date);
   
    List archiveList = selectQuery.getResultList();

    // there is nothing to archive
    if(archiveList == null || archiveList.isEmpty()){
      ServiceFaultDetailType sfdt = new ServiceFaultDetailType();
      sfdt.setCode(LogConstants.LOG_NOTHING_TO_ARCHIVE);
      logger.info("ei arkistoitavaa ennen päivää "+date);
      throw new ServiceFault("ei löytynyt arkistoitavaa", sfdt);
    } 
    
   
    
    Query archiveQuery = em.createNamedQuery("archiveLogByDate");
    archiveQuery.setParameter("date", date);
    
    logger.info("Archived log to log_archive table up to "+date);
   //TODO: Millä komennolla arkistointi pitäisi ajaa? Kuuluuko palauttaa jotain? archiveQuery.getSingleResult();
    // jos arkistointi onnistui, ajetaan tämä
    Query deleteQuery = em.createNamedQuery("deleteLogByDate");
    deleteQuery.setParameter("date", date);
    int deletedRows = deleteQuery.executeUpdate();
    logger.info("Deleted "+deletedRows+" rows from log table");
  }
// final Query query = em.createNamedQuery(queryName); Arcusys:n koodissa
  
  // TODO: entä jos write menee pieleen? Mistä ServiceFault heitetään?
  @Override
  public void writeLog(LogEntry entry) {
    em.persist(entry); //TODO: riittääkö tämä tosiaan???

  }

  
  /**
   * Makes a query to the "normal" log or to the Admin log and returns a list of
   * LogEntries for showing to the user in the portlet. (LOK-3 and LOK-4)
   * 
   * @throws ServiceFault 
   */
  @Override
  public List<LogEntry> queryLog(LogQueryCriteria criteria) throws ServiceFault {
    StringBuilder sb = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();

  //TODO:
    //ennen tätä tsekkaa nullit:
    //-starttime, enddate, logtype
    // in both log types, start and end time are given for the search 
    // these are required and the null check has been made already!

    
    // choose the table here 
    if (criteria.getLogType().equalsIgnoreCase(LogConstants.LOG_ADMIN)) { // käsittelyloki
      sb.append("FROM AdminLogEntry entry WHERE ");
    }else if(criteria.getLogType().equalsIgnoreCase(LogConstants.LOG_NORMAL)){ // tapahtumaloki
      sb.append("FROM LogEntry entry WHERE "); 
    }
    
    // starttime and enddate are required and are null-checked earlier
      sb.append("entry.timestamp >= :startTime");
      params.add(new Object[] {"startTime", criteria.getStartTime() }); 
   
      sb.append("entry.timestamp <= :endDate");
      params.add(new Object[] {"endDate", criteria.getEndDate() });
           
      // add the customer pic and data item type to search criteria for LOK-3
      if (criteria.getLogType().equalsIgnoreCase(LogConstants.LOG_NORMAL)) {

        // pic of the child is null-checked earlier       
        sb.append("entry.customerPic = :pic");
        params.add(new Object[] {"pic", criteria.getCustomerPic() });

     //TODO: ei pakollinen, jos vetovalikossa myös tyhjä vaihtoehto
        if (criteria.getDataItemType() != null){ 
          sb.append("entry.dataItemType = :dataItemType");
          params.add(new Object[] {"dataItemType", criteria.getDataItemType() }); 
        }
      }
   
      if(params.size() == 0){
        throw new RuntimeException("missing criteria");
      }
 
    try{
      Query q = em.createQuery(sb.toString());

      //TODO: lisää info-lokitus!

      // build the query
      for(int i=0; i<params.size();i++){
        q.setParameter((String)params.get(i)[0], params.get(i)[1]);
      }

      // query the database
      return q.getResultList();

    }catch(IllegalStateException e){
      //TODO
    }catch(IllegalArgumentException ex){
      //TODO
    }
    return null; // if something went wrong
  }

}
