package fi.koku.services.utility.log.impl;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;

/**
 * LogDAO.
 * 
 * @author makinsu
 *
 */
@Local
public interface LogDAO {
  
  int archiveLog(Date date);
  
  void writeLog(LogEntry entry);

  Collection<LogEntry> queryLog(LogQueryCriteria criteria);
  
  Collection<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria);

  void writeAdminLog(AdminLogEntry entry);
}

