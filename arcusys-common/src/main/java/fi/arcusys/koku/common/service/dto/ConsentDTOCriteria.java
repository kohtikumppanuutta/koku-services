package fi.arcusys.koku.common.service.dto;

/**
 * Data transfer object for passing search criteria to consent search.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 3, 2011
 */
public class ConsentDTOCriteria {
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
    
    public boolean isEmpty() {
        return consentTemplateId == null && (receipientUid == null || "".equals(receipientUid.trim()));
    }
}
