package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Data transfer object for communication with UI/Intalio process. Holds summary data about consent.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@XmlType (name = "suostumuspohjaShort", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"consentTemplateId", "code", "title"})
public class ConsentTemplateSummary {
    private Long consentTemplateId;
    private String code;
    private String title;
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the consentTemplateId
     */
    @XmlElement(name = "suostumuspohjaId")
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
     * @return the name
     */
    @XmlElement(name = "otsikko")
    public String getTitle() {
        return title;
    }
    /**
     * @param name the name to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
