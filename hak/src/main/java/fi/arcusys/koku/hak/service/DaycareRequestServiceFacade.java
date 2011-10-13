package fi.arcusys.koku.hak.service;

import fi.arcusys.koku.hak.soa.DaycareRequestTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
public interface DaycareRequestServiceFacade {

    /**
     * @param newRequest
     * @return
     */
    long requestForDaycare(final DaycareRequestTO request);

    /**
     * @param requestId
     * @return
     */
    DaycareRequestTO getDaycareRequestById(long requestId);

    void processDaycareRequest(final long requestId, final String userUid);
    
    void rejectDaycarePlace(final long requestId, final String userUid, final String comment);
    
    void approveDaycarePlace(final long requestId, final String userUid, final String location, final boolean highestPrice, final String comment);
}
