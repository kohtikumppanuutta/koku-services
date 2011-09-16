package fi.arcusys.koku.tiva.soa;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
public class AuthorizationShortSummary {

    private Long authorizationId;
    private AuthorizationTemplateTO template;
    private String senderUid;
    private String receiverUid;
    private String targetPersonUid;
    private AuthorizationStatus status;
    private XMLGregorianCalendar validTill;

    /**
     * @return the authorizationId
     */
    public Long getAuthorizationId() {
        return authorizationId;
    }

    /**
     * @param authorizationId the authorizationId to set
     */
    public void setAuthorizationId(Long authorizationId) {
        this.authorizationId = authorizationId;
    }

    /**
     * @return the template
     */
    public AuthorizationTemplateTO getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(AuthorizationTemplateTO template) {
        this.template = template;
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
     * @return the receiverUid
     */
    public String getReceiverUid() {
        return receiverUid;
    }

    /**
     * @param receiverUid the receiverUid to set
     */
    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
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
     * @return the status
     */
    public AuthorizationStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AuthorizationStatus status) {
        this.status = status;
    }

    /**
     * @return the validTill
     */
    public XMLGregorianCalendar getValidTill() {
        return validTill;
    }

    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(XMLGregorianCalendar validTill) {
        this.validTill = validTill;
    }

}
