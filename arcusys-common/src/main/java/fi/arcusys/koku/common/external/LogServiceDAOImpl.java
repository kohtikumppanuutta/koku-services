package fi.arcusys.koku.common.external;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogServiceFactory;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 4, 2011
 */
@Stateless
public class LogServiceDAOImpl implements LogServiceDAO {

    private final static Logger logger = LoggerFactory.getLogger(LogServiceDAOImpl.class); 
    
    private String logUserUid;
    private String logUserPwd;
    private String serviceEndpointBaseUrl;
    
    private LogServiceFactory logServiceFactory;
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    @PostConstruct
    void init() {
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
        logEntry.setTimestamp(Calendar.getInstance());
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
