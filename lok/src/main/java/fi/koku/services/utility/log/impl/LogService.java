package fi.koku.services.utility.log.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import fi.koku.services.utility.log.v1.ServiceFault;

/**
 * Service API for log service.
 * 
 * @author makinsu
 *
 */
@Local
public interface LogService {

    void archive(Date date) throws ServiceFault;
    
    void write(LogEntry entry);
    
    void writeAdmin(AdminLogEntry entry);
    
    List<LogEntry> query(LogQueryCriteria criteria);
    
    List<AdminLogEntry> queryAdmin(LogQueryCriteria criteria);
  }

