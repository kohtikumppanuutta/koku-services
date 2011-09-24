package fi.koku.services.utility.log.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Log service implementation class
 * 
 * @author makinsu
 *
 */
@Stateless
public class LogServiceBean implements LogService{

  @EJB
  LogDAO logDAO;
  
  @Override
  public void archive(Date date) {
    logDAO.archiveLog(date);
  }

  @Override
  public void write(LogEntry entry) {
    logDAO.writeLog(entry);
  }

  @Override
  public List<LogEntry> query(LogQueryCriteria criteria) {
    return (List<LogEntry>) logDAO.queryLog(criteria);
  }

  
}
