package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentShortSummary {
    private Long consentId;
    private String templateName;
    private String anotherPermitterUid;
    private String requestor;
    private ConsentCreateType createType;
    private XMLGregorianCalendar replyTill;
    /**
     * @return the consentId
     */
    public Long getConsentId() {
        return consentId;
    }
    /**
     * @param consentId the consentId to set
     */
    public void setConsentId(Long consentId) {
        this.consentId = consentId;
    }
    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }
    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    /**
     * @return the anotherPermitterUid
     */
    public String getAnotherPermitterUid() {
        return anotherPermitterUid;
    }
    /**
     * @param anotherPermitterUid the anotherPermitterUid to set
     */
    public void setAnotherPermitterUid(String anotherPermitterUid) {
        this.anotherPermitterUid = anotherPermitterUid;
    }
    /**
     * @param string
     */
    public void setRequestor(final String userUid) {
        this.requestor = userUid;
    }
    public String getRequestor() {
        return this.requestor;
    }
    /**
     * @return the createType
     */
    public ConsentCreateType getCreateType() {
        return createType;
    }
    /**
     * @param createType the createType to set
     */
    public void setCreateType(ConsentCreateType createType) {
        this.createType = createType;
    }
    /**
     * @return the replyTill
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getReplyTill() {
        return replyTill;
    }
    /**
     * @param replyTill the replyTill to set
     */
    public void setReplyTill(XMLGregorianCalendar replyTill) {
        this.replyTill = replyTill;
    }
}