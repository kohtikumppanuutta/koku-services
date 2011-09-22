package fi.koku.services.utility.log.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.services.utility.log.v1.ServiceFault;
import fi.koku.services.utility.log.v1.ServiceFaultDetailType;
import fi.koku.services.utility.log.v1.VoidType;

/**
 * KoKu log service implementation class.
 * 
 * @author aspluma
 * @author makinsu
 */
@Stateless
@WebService(wsdlLocation = "META-INF/wsdl/logService.wsdl", 
    endpointInterface = "fi.koku.services.utility.log.v1.LogServicePortType", 
    targetNamespace = "http://services.koku.fi/utility/log/v1", 
    portName = "logService-soap11-port", 
    serviceName = "logService")
@RolesAllowed("koku-role")
public class LogServiceEndpointBean implements LogServicePortType {
  private static final Logger logger = LoggerFactory.getLogger(LogServiceEndpointBean.class);

  @Resource
  private WebServiceContext wsCtx;

  @EJB
  private LogService logService;
  
  private LogConverter logConverter;

  public LogServiceEndpointBean() {
    logConverter = new LogConverter();
  }

  /**
   * Implements the use case LOK-1 (Tallenna lokitieto).
   * 
   * @return
   */
  @Override
  public VoidType opLog(LogEntryType logEntryType, AuditInfoType auditInfoType) throws ServiceFault {

    logger.info("opLog");

    // TODO: NÄITÄ EI KAI SINÄNSÄ KÄYTETÄ MIHINKÄÄN MUUHUN KUIN LOGGAAMISEEN??
    // message context
    Set<String> keys = wsCtx.getMessageContext().keySet();
    for (Iterator<String> i = keys.iterator(); i.hasNext();)
      logger.debug("i: " + i.next());
    String caller = wsCtx.getUserPrincipal().getName();
    logger.debug("caller: " + caller);
    // -------------------------

    // call to the actual writing
    logService.write(logConverter.fromWsType(logEntryType));

    return new VoidType(); 
  }

  /**
   * Implements the use cases LOK-3 (Etsi lokitieto) and LOK-4 (Tarkista lokin
   * käsittelyloki).
   */
  @Override
  public LogEntriesType opQueryLog(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) throws ServiceFault{
    logger.info("opQueryLog");
    LogEntriesType logEntriesType = new LogEntriesType();
    List<LogEntry> entries;

    try {
    // call to the log database
      entries = logService.query(logConverter.fromWsType(criteriaType));

      Iterator<LogEntry> i = entries.iterator();
      while (i.hasNext()) {
        LogEntry entry = (LogEntry) i.next();
        // convert log entry to Web Service type and add it to the collection

        logEntriesType.getLogEntry().add(logConverter.toWsType(entry));
      }
    } catch (ParseException e){
      ServiceFaultDetailType sfdt = new ServiceFaultDetailType();
      sfdt.setCode(LogConstants.LOG_ERROR_PARSING);
      sfdt.setMessage("TODO. Message: String to calendar epäonnistui.");
      
      throw new ServiceFault(e.getMessage(), sfdt);
    }

    return logEntriesType;
  }

  
  /**
   * Implements the use case LOK-2 (Arkistoi lokitietoa).
   */
  @Override
  public VoidType opArchiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType) throws ServiceFault{
    logger.info("opArchiveLog");
    
    // call to the actual archiving
    logService.archive(archivalParameters.getEndDate().getTime());

    return new VoidType();
  }

  /**
   * Convert from internal object representation (LogEntry) to webservice type
   * (LogEntryType).
   * 
   * @author makinsu
   */
  private static class LogConverter {

    SimpleDateFormat df = new SimpleDateFormat(LogConstants.DATE_FORMAT);

    public LogConverter() {
    }

    public LogQueryCriteria fromWsType(LogQueryCriteriaType type) {
      // TODO: onko null-tsekkaus tehty jo aiemmin?
      LogQueryCriteria criteria = new LogQueryCriteria(type.getLogType(), type.getCustomerPic(),
          type.getDataItemType(), type.getStartTime().getTime(), type.getEndTime().getTime());

      return criteria;
    }

    public LogEntryType toWsType(LogEntry entry) throws ParseException {
      LogEntryType et = new LogEntryType();

      // TODO: onko null-tsekkaus tehty jo aiemmin?
      // voiko jotkut näistä olla tyhjiä?
      et.setClientSystemId(entry.getClientSystemId());
      et.setCustomerPic(entry.getCustomerPic());
      et.setDataItemId(entry.getDataItemId());
      et.setDataItemType(entry.getDataItemType());
      et.setMessage(entry.getMessage());
      et.setOperation(entry.getOperation());
    /*  try {
        et.setTimestamp(stringToCalendar(entry.getTimestamp()));
      } catch (ParseException e) {
        e.printStackTrace();
        ServiceFaultDetailType sfdt = new ServiceFaultDetailType();
        sfdt.setCode(LogConstants.LOG_ERROR_PARSING);
        sfdt.setMessage("TODO. String to calendar epäonnistui.");
        
        throw new ServiceFault("", sfdt);
        
      }*/

      et.setUserPic(entry.getUserPic());

      return et;
    }

    /**
     * Convert from webservice type (LogEntryType) to internal object
     * representation (LogEntry).
     * 
     * @author makinsu
     * @param logt
     * @return
     */
    public LogEntry fromWsType(LogEntryType logt) {
      LogEntry entry = new LogEntry();

      entry.setCustomerPic(logt.getCustomerPic());
      entry.setClientSystemId(logt.getClientSystemId());
      entry.setDataItemType(logt.getDataItemType());
      entry.setDataItemId(logt.getDataItemId());
      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      entry.setTimestamp(logt.getTimestamp().getTime()); 
      entry.setUserPic(logt.getUserPic());

      return entry;
    }

    //TODO: Voiko tässä tulla joku format-error??
    private String calendarToString(Calendar cal) {
      String str = df.format(cal.getTime());
     
      return str;
    }

    /**
     * Method for transforming String to Calendar format
     * 
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     * @throws ParseException
     */
    private Calendar stringToCalendar(String dateStr) throws ParseException {
      Calendar cal = Calendar.getInstance();

      Date date = df.parse(dateStr);
      cal.setTime(date);

      return cal;
    }

    /**
     * Method for transforming String to XMLGregorianCalendar format
     * 
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     * @throws ParseException
     */
/*   
*/
  }

}
