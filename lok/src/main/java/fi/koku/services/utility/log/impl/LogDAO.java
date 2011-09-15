package fi.koku.services.utility.log.impl;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;

import fi.koku.services.utility.log.v1.ServiceFault;

/**
 * LogDAO.
 * 
 * @author makinsu
 *
 */
@Local
public interface LogDAO {
  
  void archiveLog(Date date) throws ServiceFault;
  
  void writeLog(LogEntry entry) throws ServiceFault;

  Collection<LogEntry>  queryLog(LogQueryCriteria criteria) throws ServiceFault;
}

