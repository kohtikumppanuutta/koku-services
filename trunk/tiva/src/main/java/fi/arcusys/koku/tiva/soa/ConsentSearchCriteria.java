package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import fi.arcusys.koku.common.service.dto.ConsentExtDtoCriteria;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@XmlType(name = "ConsentSearchCriteria", namespace = "http://services.koku.fi/entity/tiva/v1", 
propOrder = {"targetPerson", "templateNamePrefix", "informationTargetId", "givenTo"})
public class ConsentSearchCriteria {
    private String targetPerson;
    private String templateNamePrefix;
    private String informationTargetId;
    private List<String> givenTo;
    
    public ConsentExtDtoCriteria toDtoCriteria() {
        final ConsentExtDtoCriteria dtoCriteria = new ConsentExtDtoCriteria();
        dtoCriteria.setGivenTo(givenTo);
        dtoCriteria.setInformationTargetId(informationTargetId);
        dtoCriteria.setTargetPerson(targetPerson);
        dtoCriteria.setTemplateNamePrefix(templateNamePrefix);
        return dtoCriteria;
    }
    
    /**
     * @return the targetPerson
     */
    @XmlElement(required = true)
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
    @XmlElement(required = true)
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
    @XmlElement(required = true)
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
