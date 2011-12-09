package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentShortSummary {
    private Long consentId;
    private Long templateId;
    private String templateName;
    private String templateDescription;
    private Long templateTypeId;
    private String templateTypeName;
    private String targetPersonUid;
    private String anotherPermitterUid;
    private String requestor;
    private ConsentCreateType createType;
    private XMLGregorianCalendar replyTill;
    
    private String informationTargetId;
    private String metaInfo;
    private List<ConsentExternalGivenTo> givenToParties;
    
    /**
     * @return the templateTypeId
     */
    public Long getTemplateTypeId() {
        return templateTypeId;
    }
    /**
     * @param templateTypeId the templateTypeId to set
     */
    public void setTemplateTypeId(Long templateTypeId) {
        this.templateTypeId = templateTypeId;
    }
    /**
     * @return the templateTypeName
     */
    public String getTemplateTypeName() {
        return templateTypeName;
    }
    /**
     * @param templateTypeName the templateTypeName to set
     */
    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }
    /**
     * @return the templateId
     */
    public Long getTemplateId() {
        return templateId;
    }
    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
     * @return the templateDescription
     */
    public String getTemplateDescription() {
        return templateDescription;
    }
    /**
     * @param templateDescription the templateDescription to set
     */
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }
    /**
     * @return the informationTargetId
     */
    public String getInformationTargetId() {
        return informationTargetId;
    }
    /**
     * @param informationTargetId the informationTargetId to set
     */
    public void setInformationTargetId(String informationTargetId) {
        this.informationTargetId = informationTargetId;
    }
    /**
     * @return the metaInfo
     */
    public String getMetaInfo() {
        return metaInfo;
    }
    /**
     * @param metaInfo the metaInfo to set
     */
    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }
    /**
     * @return the givenToParties
     */
    public List<ConsentExternalGivenTo> getGivenToParties() {
        return givenToParties;
    }
    /**
     * @param givenToParties the givenToParties to set
     */
    public void setGivenToParties(List<ConsentExternalGivenTo> givenToParties) {
        this.givenToParties = givenToParties;
    }
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