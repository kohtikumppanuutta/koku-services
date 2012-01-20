package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * UI service interface for citizen-related operations in TIVA-Suostumus functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuKunpoSuostumusService {
    public int getTotalAssignedConsents(@WebParam(name = "user") final String user);

    public List<ConsentShortSummary> getAssignedConsents(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);

    public int getTotalOwnConsents(@WebParam(name = "user") final String user);

    public List<ConsentSummary> getOwnConsents(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);
    
    public int getTotalOldConsents(@WebParam(name = "user") final String user);

    public List<ConsentSummary> getOldConsents(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);

    public ConsentTO getConsentById(@WebParam(name = "suostumusId") final long suostumusId, final String userUid);
    
    public void revokeOwnConsent(@WebParam(name = "suostumusId") final long suostumusId, final String userUid);
}
