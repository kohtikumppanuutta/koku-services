package fi.arcusys.koku.tiva.soa;

import fi.arcusys.koku.common.service.dto.AuthorizationDTOCriteria;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
public class AuthorizationCriteria {
    private Long authorizationTemplateId;
    private String senderUid;
    private String receipientUid;
    private String targetPersonUid;

    public AuthorizationDTOCriteria toDtoCriteria() {
        final AuthorizationDTOCriteria dtoCriteria = new AuthorizationDTOCriteria();
        dtoCriteria.setAuthorizationTemplateId(authorizationTemplateId);
        dtoCriteria.setReceipientUid(receipientUid);
        dtoCriteria.setSenderUid(senderUid);
        dtoCriteria.setTargetPersonUid(targetPersonUid);
        return dtoCriteria;
    }

    /**
     * @return the targetPersonUid
     */
    public String getTargetPersonUid() {
        return targetPersonUid;
    }



    /**
     * @param targetPersonUid the targetPersonUid to set
     */
    public void setTargetPersonUid(String targetPersonUid) {
        this.targetPersonUid = targetPersonUid;
    }



    /**
     * @return the authorizationTemplateId
     */
    public Long getAuthorizationTemplateId() {
        return authorizationTemplateId;
    }

    /**
     * @param authorizationTemplateId the authorizationTemplateId to set
     */
    public void setAuthorizationTemplateId(Long authorizationTemplateId) {
        this.authorizationTemplateId = authorizationTemplateId;
    }

    /**
     * @return the senderUid
     */
    public String getSenderUid() {
        return senderUid;
    }

    /**
     * @param senderUid the senderUid to set
     */
    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
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
    
}
