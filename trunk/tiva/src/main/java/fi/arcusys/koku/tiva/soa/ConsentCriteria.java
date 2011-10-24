package fi.arcusys.koku.tiva.soa;

import fi.arcusys.koku.common.service.dto.ConsentDTOCriteria;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentCriteria {
    private Long consentTemplateId;
    private String receipientUid;
    /**
     * @return the consentTemplateId
     */
    public Long getConsentTemplateId() {
        return consentTemplateId;
    }
    /**
     * @param consentTemplateId the consentTemplateId to set
     */
    public void setConsentTemplateId(Long consentTemplateId) {
        this.consentTemplateId = consentTemplateId;
    }
    /**
     * @return the receipientUid
     */
    public String getReceipientUid() {
        return receipientUid;
    }
    /**
     * @param receipientUid the receipientUid to set
     */
    public void setReceipientUid(String receipientUid) {
        this.receipientUid = receipientUid;
    }
    /**
     * @return
     */
    public ConsentDTOCriteria toDtoCriteria() {
        final ConsentDTOCriteria dtoCriteria = new ConsentDTOCriteria();
        dtoCriteria.setConsentTemplateId(consentTemplateId);
        dtoCriteria.setReceipientUid(receipientUid);
        return dtoCriteria;
    }
    
    
}
