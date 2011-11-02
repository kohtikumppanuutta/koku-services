/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
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

  Date getEarliest(Date date);
  
}

