package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@XmlType(name = "ConsentSearchCriteria", namespace = "http://services.koku.fi/entity/tiva/v1", 
propOrder = {"targetPerson", "templateNamePrefix", "informationTargetId", "statuses", "givenTo"})
public class ConsentSearchCriteria {
    private String targetPerson;
    private String templateNamePrefix;
    private String informationTargetId;
    private List<ConsentStatus> statuses;
    private String givenTo;
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
     * @return the templateNamePrefix
     */
    public String getTemplateNamePrefix() {
        return templateNamePrefix;
    }
    /**
     * @param templateNamePrefix the templateNamePrefix to set
     */
    public void setTemplateNamePrefix(String templateNamePrefix) {
        this.templateNamePrefix = templateNamePrefix;
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
     * @return the statuses
     */
    public List<ConsentStatus> getStatuses() {
        return statuses;
    }
    /**
     * @param statuses the statuses to set
     */
    public void setStatuses(List<ConsentStatus> statuses) {
        this.statuses = statuses;
    }
    /**
     * @return the givenTo
     */
    public String getGivenTo() {
        return givenTo;
    }
    /**
     * @param givenTo the givenTo to set
     */
    public void setGivenTo(String givenTo) {
        this.givenTo = givenTo;
    }
    
    
}
