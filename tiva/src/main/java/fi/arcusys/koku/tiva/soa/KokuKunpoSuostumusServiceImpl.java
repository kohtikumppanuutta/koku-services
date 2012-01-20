package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.tiva.service.ConsentServiceFacade;

/**
 * UI service implementation for citizen-related operations in TIVA-Suostumus functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoSuostumusService", portName = "KokuKunpoSuostumusServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuKunpoSuostumusService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuSuostumusInterceptor.class)
public class KokuKunpoSuostumusServiceImpl implements KokuKunpoSuostumusService {

    private final static Logger logger = LoggerFactory.getLogger(KokuKunpoSuostumusServiceImpl.class);
    
    @EJB
    private ConsentServiceFacade serviceFacade;
    
    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalAssignedConsents(String user) {
        return serviceFacade.getTotalAssignedConsents(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentShortSummary> getAssignedConsents(String user, int startNum,
            int maxNum) {
        return serviceFacade.getAssignedConsents(user, startNum, maxNum);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalOwnConsents(String user) {
        return serviceFacade.getTotalOwnConsents(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentSummary> getOwnConsents(String user, int startNum,
            int maxNum) {
        if (logger.isDebugEnabled()) {
            logger.debug("getOwnConsents: " + user);
        }
        return serviceFacade.getOwnConsents(user, startNum, maxNum);
    }

    /**
     * @param suostumusId
     * @return
     */
    @Override
    public ConsentTO getConsentById(long suostumusId, final String userUid) {
        return serviceFacade.getConsentById(suostumusId, userUid);
    }

    /**
     * @param suostumusId
     */
    @Override
    public void revokeOwnConsent(long suostumusId, final String userUid) {
        if (logger.isDebugEnabled()) {
            logger.debug("revokeConsent: " + suostumusId);
        }
        serviceFacade.revokeConsent(suostumusId, userUid, "");
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalOldConsents(String user) {
        return serviceFacade.getTotalOldConsents(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentSummary> getOldConsents(String user, int startNum,
            int maxNum) {
        return serviceFacade.getOldConsents(user, startNum, maxNum);
    }

}
