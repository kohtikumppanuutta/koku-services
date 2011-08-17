package fi.arcusys.koku.tiva.soa;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 16, 2011
 */
public class AuthorizationDetailTO {
    private long authorizationId;
    private AuthorizationTemplateTO template;
    private String senderUid;
    private String receiverUid;
    private XMLGregorianCalendar createDate;
    private XMLGregorianCalendar validTill;
    /**
     * @return the authorizationId
     */
    public long getAuthorizationId() {
        return authorizationId;
    }
    /**
     * @param authorizationId the authorizationId to set
     */
    public void setAuthorizationId(long authorizationId) {
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
     * @return the createDate
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(XMLGregorianCalendar createDate) {
        this.createDate = createDate;
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
