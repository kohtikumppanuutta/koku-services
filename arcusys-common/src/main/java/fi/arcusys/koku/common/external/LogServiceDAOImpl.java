package fi.arcusys.koku.common.external;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogServiceFactory;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * DAO implementation for acccessing LOK-component: logging important system events. 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
@Stateless
public class LogServiceDAOImpl implements LogServiceDAO {

    private final static Logger logger = LoggerFactory.getLogger(LogServiceDAOImpl.class); 
    
    private String logUserUid = KoKuPropertiesUtil.get("arcusys.lok.service.user.id");
    private String logUserPwd = KoKuPropertiesUtil.get("arcusys.lok.service.password");
    private String serviceEndpointBaseUrl;
    
    private LogServiceFactory logServiceFactory;
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    @PostConstruct
    void init() {
        try {
            final InitialContext ctx = new InitialContext();
            serviceEndpointBaseUrl = (String) ctx.lookup("koku/urls/lokservice-baseurl");
            logger.debug("Overwrite lokServiceEndpointBaseUrl with " + serviceEndpointBaseUrl);
        } catch (NamingException e) {
            logger.error(null, e);
        }
        
        logServiceFactory = new LogServiceFactory(logUserUid, logUserPwd, serviceEndpointBaseUrl);
    }
    
    /**
     * @param message
     */
    @Override
    public void logMessage(LogMessage message) {
        final LogEntryType logEntry = new LogEntryType();
        logEntry.setClientSystemId(message.getSystemArea().name());
        logEntry.setDataItemId(message.getDataItemId());
        logEntry.setDataItemType(message.getDataItemType());
        logEntry.setMessage(message.getMessage());
        logEntry.setOperation(message.getOperation().name());
        logEntry.setTimestamp(CalendarUtil.getXmlGregorianCalendar(new Date()));
        logEntry.setCustomerPic(getHetuByUserUid(message.getCustomerPic()));
        logEntry.setUserPic(getHetuByUserUid(message.getUserPic()));
        
        final AuditInfoType auditHeader = new AuditInfoType();
        auditHeader.setComponent(logEntry.getClientSystemId());
        auditHeader.setUserId(logEntry.getUserPic());
        
        try {
            final LogEntriesType logEntries = new LogEntriesType();
            logEntries.getLogEntry().add(logEntry);
            logServiceFactory.getLogService().opLog(logEntries, auditHeader);
        } catch (Exception e) {
            logger.error("logMessage failed with error: " + e.getMessage());
            if (logger.isDebugEnabled()) {
                logger.debug("Detailed error: ", e);
            }
        }
    }

    private String getHetuByUserUid(String userUid) {
        if (userUid == null || userUid.isEmpty()) {
            return "";
        } 
        return customerDao.getSsnByUserUid(userUid);
    }
}
