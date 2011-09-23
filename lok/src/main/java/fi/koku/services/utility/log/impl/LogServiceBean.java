package fi.koku.services.utility.log.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class LogServiceBean implements LogService {

  @Override
  public void archive(Date date) {
  }

  @Override
  public void write(LogEntry entry) {
  }

  @Override
  public List<LogEntry> query(LogQueryCriteria criteria) {
    return Collections.emptyList();
  }

}
