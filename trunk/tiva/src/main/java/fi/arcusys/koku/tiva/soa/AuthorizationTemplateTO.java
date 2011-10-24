package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 16, 2011
 */
@XmlType (name = "valtakirjapohja", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"templateId", "templateName", "description"})
public class AuthorizationTemplateTO {
    private long templateId;
    private String templateName;
    private String description;
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
     * @return the templateId
     */
    public long getTemplateId() {
        return templateId;
    }
    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(long templateId) {
        this.templateId = templateId;
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
    
    
}
