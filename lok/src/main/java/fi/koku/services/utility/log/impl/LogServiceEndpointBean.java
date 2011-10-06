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
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.ArchivalResultsType;
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
@Interceptors({LogServiceFaultInterceptor.class})
@WebService(wsdlLocation = "META-INF/wsdl/logService.wsdl", endpointInterface = "fi.koku.services.utility.log.v1.LogServicePortType", targetNamespace = "http://services.koku.fi/utility/log/v1", portName = "logService-soap11-port", serviceName = "logService")
@RolesAllowed("koku-role")
public class LogServiceEndpointBean implements LogServicePortType {
  private static final Logger logger = LoggerFactory.getLogger(LogServiceEndpointBean.class);
  SimpleDateFormat df = new SimpleDateFormat(LogConstants.DATE_FORMAT);
  @Resource
  private WebServiceContext wsCtx;

  @EJB
  private LogService logService;

  private LogConverter logConverter;

 // LogUtils lu = new LogUtils();
  
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
    logger.debug("got timestamp: " + logEntryType.getTimestamp().getTime().toString());

  
    // call to the actual writing
    // TODO: tee "log":sta static
    
    // ClientSystemId tells to which log table we write!
    if (("log").equalsIgnoreCase(logEntryType.getClientSystemId())) {
      logger.debug("write to admin log");
      logService.writeAdmin(logConverter.fromWsTypeToAdmin(logEntryType));
    } else { 
      logger.debug("write to normal log");
      logService.write(logConverter.fromWsType(logEntryType));
    }
    return new VoidType();
  }

  /**
   * Implements the use cases LOK-3 (Etsi lokitieto) and LOK-4 (Tarkista lokin käsittelyloki). 
   */
  @Override
  public LogEntriesType opQueryLog(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) throws ServiceFault {
    logger.info("opQueryLog");
    LogEntriesType logEntriesType = new LogEntriesType();
   

    if(LogConstants.LOG_NORMAL.equals(criteriaType.getLogType())){
      List<LogEntry> entries;
    
      try {
       
        // call to the log database
        entries = logService.query(logConverter.fromWsType(criteriaType));
        logger.debug("entries: " + entries.size());
        logger.debug("normal log");
        if (entries == null) {
          logger.debug("No entries found in log table!");
        } else {
          Iterator<LogEntry> i = entries.iterator();
          while (i.hasNext()) {
            LogEntry entry = (LogEntry) i.next();
            // convert log entry to Web Service type and add it to the
            // collection
            logEntriesType.getLogEntry().add(logConverter.toWsType(entry));
          }
        }

      } catch (ParseException e) {
        ServiceFaultDetailType sfdt = new ServiceFaultDetailType();
        sfdt.setCode(LogConstants.LOG_ERROR_PARSING);
        sfdt.setMessage("TODO. Message: String to calendar epäonnistui.");

        throw new ServiceFault(e.getMessage(), sfdt);
      }
      
      logger.debug("write to admin log");
      AdminLogEntry adminLogEntry = new AdminLogEntry();
      adminLogEntry.setTimestamp(Calendar.getInstance().getTime());
      adminLogEntry.setUserPic(auditInfoType.getUserId());
      adminLogEntry.setCustomerPic(criteriaType.getCustomerPic());
      adminLogEntry.setOperation("view log");
     
      // set end date back to 1 day earlier so that the search criteria given by the user is written to admin log
      Calendar end = criteriaType.getEndTime();
      end.set(Calendar.DATE, end.get(Calendar.DATE) -1);
      criteriaType.setEndTime(end);
      // LOK-3: "tapahtumatietona hakuehdot"
      adminLogEntry.setMessage(criteriaType.getCustomerPic()+" "+criteriaType.getDataItemType()+" "+df.format(criteriaType.getStartTime().getTime())+" - "+df.format(criteriaType.getEndTime().getTime()));

      logService.writeAdmin(adminLogEntry); 
      
    } else if(LogConstants.LOG_ADMIN.equals(criteriaType.getLogType())){
      List<AdminLogEntry> entries;
      
      try {     
        // call to the log database
        entries = logService.queryAdmin(logConverter.fromWsType(criteriaType));
        logger.debug("entries: " + entries.size());

        logger.debug("Admin log");

        if (entries == null) {
          logger.debug("No entries found in log_admin table!");
        } else {
          Iterator<AdminLogEntry> j = entries.iterator();

          // Iterator<AdminLogEntry> i = entries.iterator();
          while (j.hasNext()) {

            // convert log entry to AdminLog Web Service type and 
            // add it to the collection
            AdminLogEntry entry = (AdminLogEntry)j.next();  //logConverter.convertToAdminLogEntry(j.next());
            logger.debug("adminlogentryn message: "+entry.getMessage());
            logEntriesType.getLogEntry().add(logConverter.toWsFromAdminType(entry));
          }
        }
      }catch (ParseException e) {
        ServiceFaultDetailType sfdt = new ServiceFaultDetailType();
        sfdt.setCode(LogConstants.LOG_ERROR_PARSING);
        sfdt.setMessage("TODO. Message: String to calendar epäonnistui.");

        throw new ServiceFault(e.getMessage(), sfdt);
      }
      
      // log the query to normal log
      logger.debug("write to log");
      LogEntry logEntry = new LogEntry();
      logEntry.setUserPic(auditInfoType.getUserId());
      // LOK-4: "Tapahtumatietona hakuehdot"
      // set end date back to 1 day earlier so that the real query end date given by the user is written to log
      Calendar end = criteriaType.getEndTime();
      end.set(Calendar.DATE, end.get(Calendar.DATE) -1);
      logEntry.setMessage("start: "+df.format(criteriaType.getStartTime().getTime())+", end: "+df.format(end.getTime()));
      logEntry.setTimestamp(Calendar.getInstance().getTime());
      logEntry.setOperation("search");
      logEntry.setClientSystemId("adminlog");
      logEntry.setDataItemType("log");
      logEntry.setDataItemId("dataitemid");
      // call to lok service
      logService.write(logEntry);
      
    }
    return logEntriesType;
  }

 
  /**
   * Implements the use case LOK-2 (Arkistoi lokitietoa).
   */
  @Override
//  public VoidType opArchiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType)
  public ArchivalResultsType opArchiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType)
      throws ServiceFault {
    logger.info("opArchiveLog");
   
    int entryCount = 0;
    
    try{
      // call to the actual archiving
      entryCount = logService.archive(archivalParameters.getEndDate().getTime());

      if(entryCount < 1) {
        // do not throw a servicefault
        logger.info("Ei arkistoitavaa ennen päivää "+archivalParameters.getEndDate().getTime());
      }
      
      // log this query to admin log 
      logger.debug("write to admin log");
      AdminLogEntry adminLogEntry = new AdminLogEntry();
      adminLogEntry.setTimestamp(Calendar.getInstance().getTime());
      adminLogEntry.setUserPic(auditInfoType.getUserId());
      adminLogEntry.setOperation("archive");
      //TODO: Pitääkö servicen selvittää aikaisin log:n tieto ja kirjata tähän myös alkupäivä?
        
      adminLogEntry.setMessage("archive log up to "+df.format(archivalParameters.getEndDate().getTime()));

      logService.writeAdmin(adminLogEntry); 
    }catch(ServiceFault f){
      logger.debug("endpoint: ei arkistoitavaa, throw ServiceFault");
      throw f;
    }
    
    ArchivalResultsType count = new ArchivalResultsType();
    count.setLogEntryCount(entryCount);
    
    return count;
  }

  /**
   * Convert from internal object representation (LogEntry) to webservice type
   * (LogEntryType).
   * 
   * @author makinsu
   */
  private static class LogConverter {

    SimpleDateFormat df = new SimpleDateFormat(LogConstants.DATE_FORMAT);

    LogUtils lu = new LogUtils();
    
    public LogConverter() {
    }

    public LogQueryCriteria fromWsType(LogQueryCriteriaType type) {
      // TODO: onko null-tsekkaus tehty jo aiemmin?
      LogQueryCriteria criteria = new LogQueryCriteria(type.getLogType(), type.getCustomerPic(),
          type.getDataItemType(), type.getStartTime().getTime(), type.getEndTime().getTime());

      return criteria;
    }


    public LogEntryType toWsFromAdminType(AdminLogEntry entry) throws ParseException {
 
      logger.debug("toWsFromAdminType timestamp: "+entry.getTimestamp().toString());
                      
      LogEntryType entryType = new LogEntryType();
      entryType.setCustomerPic(entry.getCustomerPic());
      entryType.setUserPic(entry.getUserPic());
      entryType.setOperation(entry.getOperation());
      entryType.setMessage(entry.getMessage());
      entryType.setTimestamp(lu.parseToCal(entry.getTimestamp()));
      return entryType;
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
      et.setTimestamp(lu.parseToCal(entry.getTimestamp()));
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
      logger.debug("from WsType customer pic: "+logt.getCustomerPic());
      entry.setClientSystemId(logt.getClientSystemId());
      entry.setDataItemType(logt.getDataItemType());
      entry.setDataItemId(logt.getDataItemId());
      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      entry.setTimestamp(logt.getTimestamp().getTime());
      entry.setUserPic(logt.getUserPic());

      return entry;
    }

    /**
     * Converts the LogEntryType ws type to AdminLogEntry type
     * 
     * @param logt
     * @return
     */
    public AdminLogEntry fromWsTypeToAdmin(LogEntryType logt) {
      AdminLogEntry entry = new AdminLogEntry();

      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      logger.debug("timestamp: " + logt.getTimestamp().getTime().toString());
      entry.setTimestamp(logt.getTimestamp().getTime());
      entry.setUserPic(logt.getUserPic());
      entry.setCustomerPic(logt.getCustomerPic());

      return entry;
    }
  
  }

}
