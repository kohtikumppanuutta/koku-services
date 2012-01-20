package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * Data transfer object for communication with UI/Intalio process. Holds summary data about consent template.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Oct 3, 2011
 */
@XmlType(name = "consentTemplate", namespace = "http://services.koku.fi/entity/tiva/v1", propOrder = {
        "consentTemplateId", "templateName" })
public class ConsentTemplateShort {
    private Long consentTemplateId;
    private String templateName;

    /**
     * @return the consentTemplateId
     */
    public Long getConsentTemplateId() {
        return consentTemplateId;
    }

    /**
     * @param consentTemplateId
     *            the consentTemplateId to set
     */
    public void setConsentTemplateId(Long consentTemplateId) {
        this.consentTemplateId = consentTemplateId;
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName
     *            the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
