/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
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
  public int archive(Date date) {
    return logDAO.archiveLog(date);
  }

  @Override
  public void write(LogEntry entry) {
    logDAO.writeLog(entry);
  }

  @Override
  public List<LogEntry> query(LogQueryCriteria criteria) {
    return (List<LogEntry>) logDAO.queryLog(criteria);
  }

  @Override
  public void writeAdmin(AdminLogEntry entry) {
    logDAO.writeAdminLog(entry);
  }

  @Override
  public List<AdminLogEntry> queryAdmin(LogQueryCriteria criteria) {
   return (List<AdminLogEntry>) logDAO.queryAdminLog(criteria);
  }

  @Override
  public Date getEarliest(Date date) {
    return (Date) logDAO.getEarliest(date);
  }

}
