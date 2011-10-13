package fi.arcusys.koku.hak.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.DaycareRequestDAO;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.DaycareRequest;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.hak.service.DaycareRequestServiceFacade;
import fi.arcusys.koku.hak.soa.DaycareRequestTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
@Stateless
public class DaycareRequestServiceFacadeImpl implements DaycareRequestServiceFacade {

    private static final String NEW_DAYCARE_REQUEST_BODY = "new_daycare_request.body";
    private static final String NEW_DAYCARE_REQUEST_SUBJECT = "new_daycare_request.subject";

    private static final String DAYCARE_SUGGESTED_BODY = "daycare.suggested.body";
    private static final String DAYCARE_SUGGESTED_SUBJECT = "daycare.suggested.subject";

    private static final String DAYCARE_ACCEPTED_BODY = "daycare.accepted.body";
    private static final String DAYCARE_ACCEPTED_SUBJECT = "daycare.accepted.subject";

    private static final String DAYCARE_DECLINED_BODY = "daycare.declined.body";
    private static final String DAYCARE_DECLINED_SUBJECT = "daycare.declined.subject";


    @EJB
    private DaycareRequestDAO daycareRequestDao;
    
    @EJB
    private UserDAO userDao;

    @EJB
    private KokuSystemNotificationsService notificationService;
    
    private String defaultDaycareEmployeeName = "paivi.paivakoti";
    @EJB
    private UsersAndGroupsService usersService;

    private String notificationsBundleName = "daycare.msg";
    private Properties messageTemplates;
    
    @PostConstruct
    public void init() {
        messageTemplates = new Properties();
        try {
            final InputStream in = getClass().getClassLoader().getResourceAsStream(notificationsBundleName + ".properties");
            try {
                messageTemplates.load(in);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e) {
            throw new EJBException("Incorrect configuration, failed to load message templates:", e);
        }
    } 

    private String getValueFromBundle(final String key) {
        return messageTemplates.getProperty(key);
    }
    
    /**
     * @param request
     * @return
     */
    @Override
    public long requestForDaycare(DaycareRequestTO request) {
        final DaycareRequest newRequest = new DaycareRequest();

        newRequest.setCreator(userDao.getOrCreateUser(request.getCreatorUid()));
        newRequest.setTargetPerson(userDao.getOrCreateUser(request.getTargetPersonUid()));

        newRequest.setFormContent(request.getFormContent());
        newRequest.setNeededFromDate(CalendarUtil.getSafeDate(request.getDaycareNeededFromDate()));
        
        final DaycareRequest entity = daycareRequestDao.create(newRequest);
        
        notificationService.sendNotification(getValueFromBundle(NEW_DAYCARE_REQUEST_SUBJECT), Collections.singletonList(usersService.getUserUidByLooraName(defaultDaycareEmployeeName)), 
                MessageFormat.format(getValueFromBundle(NEW_DAYCARE_REQUEST_BODY), new Object[] {entity.getId()}));
        
        return entity.getId();
    }

    /**
     * @param requestId
     * @return
     */
    @Override
    public DaycareRequestTO getDaycareRequestById(long requestId) {
        final DaycareRequest request = loadRequest(requestId);
        
        final DaycareRequestTO requestTO = new DaycareRequestTO();
        requestTO.setCreatorUid(request.getCreator().getUid());
        requestTO.setDaycareNeededFromDate(CalendarUtil.getXmlDate(request.getNeededFromDate()));
        requestTO.setFormContent(request.getFormContent());
        requestTO.setRequestId(request.getId());
        requestTO.setTargetPersonUid(request.getTargetPerson().getUid());
        
        return requestTO;
    }

    protected DaycareRequest loadRequest(long requestId) {
        final DaycareRequest request = daycareRequestDao.getById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("DaycareRequest with ID " + requestId + " is not found.");
        }
        return request;
    }

    /**
     * @param requestId
     * @param userUid
     */
    @Override
    public void processDaycareRequest(long requestId, String userUid) {
        final DaycareRequest request = loadRequest(requestId);

        notificationService.sendNotification(getValueFromBundle(DAYCARE_SUGGESTED_SUBJECT), Collections.singletonList(request.getCreator().getUid()), 
                MessageFormat.format(getValueFromBundle(DAYCARE_SUGGESTED_BODY), new Object[] {}));
    }

    /**
     * @param requestId
     * @param userUid
     * @param comment
     */
    @Override
    public void rejectDaycarePlace(long requestId, String userUid, String comment) {
        final DaycareRequest request = loadRequest(requestId);

        notificationService.sendNotification(getValueFromBundle(DAYCARE_DECLINED_SUBJECT), Collections.singletonList(usersService.getUserUidByLooraName(defaultDaycareEmployeeName)), 
                MessageFormat.format(getValueFromBundle(DAYCARE_DECLINED_BODY), new Object[] {comment}));
    }

    /**
     * @param requestId
     * @param userUid
     * @param location
     * @param highestPrice
     * @param comment
     */
    @Override
    public void approveDaycarePlace(long requestId, String userUid,
            String location, boolean highestPrice, String comment) {
        final DaycareRequest request = loadRequest(requestId);

        notificationService.sendNotification(getValueFromBundle(DAYCARE_ACCEPTED_SUBJECT), Collections.singletonList(usersService.getUserUidByLooraName(defaultDaycareEmployeeName)), 
                MessageFormat.format(getValueFromBundle(DAYCARE_ACCEPTED_BODY), new Object[] {comment}));
    }

}
