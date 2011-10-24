package fi.koku.services.utility.log.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

/**
 * Service API for log service.
 * 
 * @author makinsu
 *
 */
@Local
public interface LogService {

    int archive(Date date);
    
    void write(LogEntry entry);
    
    void writeAdmin(AdminLogEntry entry);
    
    List<LogEntry> query(LogQueryCriteria criteria);
    
    List<AdminLogEntry> queryAdmin(LogQueryCriteria criteria);
    
    Date getEarliest(Date date);
  }

