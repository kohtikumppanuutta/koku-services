package fi.koku.services.utility.log.impl;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;
import fi.koku.services.utility.log.v1.LogServicePortType;

/**
 * KoKu log service implementation class.
 * 
 * @author aspluma
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/logService.wsdl",
  endpointInterface="fi.koku.services.utility.log.v1.LogServicePortType",
  targetNamespace="http://services.koku.fi/utility/log/v1",
  portName="logService-soap11-port",
  serviceName="logService"
)
@RolesAllowed("koku-role")
public class LogServiceBean implements LogServicePortType {
  private static final Logger logger = LoggerFactory.getLogger(LogServiceBean.class);
  
  @Override
  public void opLog(LogEntryType logEntry) {
    logger.info("opLog");
  }

  @Override
  public LogEntriesType opQueryLog(LogQueryCriteriaType queryCriteria) {
    logger.info("opQueryLog");
    LogEntriesType r = new LogEntriesType();
    r.getLogEntry().add(new LogEntryType());
    r.getLogEntry().add(new LogEntryType());
    return r;
  }

  @Override
  public void opArchiveLog(LogArchivalParametersType archivalParameters) {
    logger.info("opArchiveLog");
  }
  
}
