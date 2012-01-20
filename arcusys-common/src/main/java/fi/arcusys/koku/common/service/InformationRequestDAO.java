package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.common.service.dto.InformationRequestDTOCriteria;

/**
 * DAO interface for CRUD operations with 'InformationRequest' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
public interface InformationRequestDAO extends AbstractEntityDAO<InformationRequest> {

    /**
     * @param criteria
     * @param startNum
     * @param i
     * @return
     */
    List<InformationRequest> getRepliedRequests(InformationRequestDTOCriteria criteria, int startNum, int maxResults);

    /**
     * @param criteria
     * @param startNum
     * @param i
     * @return
     */
     List<InformationRequest> getSentRequests(InformationRequestDTOCriteria criteria, int startNum, int maxResults);

    /**
     * @param dtoCriteria
     * @return
     */
    Long getTotalRepliedRequests(InformationRequestDTOCriteria criteria);

    /**
     * @param dtoCriteria
     * @return
     */
    Long getTotalSentRequests(InformationRequestDTOCriteria criteria);
}
