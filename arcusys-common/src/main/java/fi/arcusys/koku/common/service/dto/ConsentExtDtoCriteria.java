package fi.arcusys.koku.common.service.dto;

import java.util.List;

/**
 * Data transfer object for passing search criteria to consent search. Used by KKS-component related search.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 11, 2011
 */
public class ConsentExtDtoCriteria {
    private String targetPerson;
    private String templateNamePrefix;
    private String informationTargetId;
    private List<String> givenTo;
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
     * @return the givenTo
     */
    public List<String> getGivenTo() {
        return givenTo;
    }
    /**
     * @param givenTo the givenTo to set
     */
    public void setGivenTo(List<String> givenTo) {
        this.givenTo = givenTo;
    }
}
