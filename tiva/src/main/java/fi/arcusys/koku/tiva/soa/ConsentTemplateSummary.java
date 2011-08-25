package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@XmlType (name = "suostumuspohjaShort", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"consentTemplateId", "title"})
public class ConsentTemplateSummary {
    private long consentTemplateId;
    private String title;
    /**
     * @return the consentTemplateId
     */
    @XmlElement(name = "suostumuspohjaId")
    public long getConsentTemplateId() {
        return consentTemplateId;
    }
    /**
     * @param consentTemplateId the consentTemplateId to set
     */
    public void setConsentTemplateId(long consentTemplateId) {
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
