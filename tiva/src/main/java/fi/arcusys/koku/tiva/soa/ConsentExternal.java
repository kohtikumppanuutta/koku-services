package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about consent for KKS component.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@XmlType(name = "consent", namespace = "http://services.koku.fi/entity/tiva/v1", 
    propOrder = {"consentId", "template", "consentRequestor", "targetPerson", "consentProviders", "informationTargetId", "givenAt", "validTill", "givenTo", "description", "status", "metaInfo"})
public class ConsentExternal {
    private Long consentId;
    private ConsentTemplateShort template;
    private String consentRequestor;
    private String targetPerson;
    private List<String> consentProviders;
    private String informationTargetId;
    private XMLGregorianCalendar givenAt;
    private XMLGregorianCalendar validTill;
    private List<ConsentExternalGivenTo> givenTo;
    private String description;
    private ConsentStatus status;
    private String metaInfo;
    
    /**
     * @return the consentRequestor
     */
    public String getConsentRequestor() {
        return consentRequestor;
    }
    /**
     * @param consentRequestor the consentRequestor to set
     */
    public void setConsentRequestor(String consentRequestor) {
        this.consentRequestor = consentRequestor;
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
     * @return the template
     */
    public ConsentTemplateShort getTemplate() {
        return template;
    }
    /**
     * @param template the template to set
     */
    public void setTemplate(ConsentTemplateShort template) {
        this.template = template;
    }
    /**
     * @return the targetPerson
     */
    public String getTargetPerson() {
        return targetPerson;
    }
    /**
     * @param targetPerson the targetPerson to set
     */
    public void setTargetPerson(String targetPerson) {
        this.targetPerson = targetPerson;
    }
    /**
     * @return the consentProviders
     */
    @XmlElement(required = true)
    public List<String> getConsentProviders() {
        return consentProviders;
    }
    /**
     * @param consentProviders the consentProviders to set
     */
    public void setConsentProviders(List<String> consentProviders) {
        this.consentProviders = consentProviders;
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
     * @return the givenAt
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getGivenAt() {
        return givenAt;
    }
    /**
     * @param givenAt the givenAt to set
     */
    public void setGivenAt(XMLGregorianCalendar givenAt) {
        this.givenAt = givenAt;
    }
    /**
     * @return the validTill
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getValidTill() {
        return validTill;
    }
    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(XMLGregorianCalendar validTill) {
        this.validTill = validTill;
    }
    /**
     * @return the consentRequestor
     */
    @XmlElement(required = true)
    public List<ConsentExternalGivenTo> getGivenTo() {
        return givenTo;
    }
    /**
     * @param consentRequestor the consentRequestor to set
     */
    public void setGivenTo(List<ConsentExternalGivenTo> givenTo) {
        this.givenTo = givenTo;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the status
     */
    public ConsentStatus getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(ConsentStatus status) {
        this.status = status;
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
}
