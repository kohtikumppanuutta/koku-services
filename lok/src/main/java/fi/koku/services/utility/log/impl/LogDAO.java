/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.utility.log.impl;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;

/**
 * Log related data access facilities.
 * 
 * @author makinsu
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