package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 9, 2011
 */
@XmlType (name = "suostumusForReply", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"consentId", "alreadyReplied", "template", "actionReplies", "replierUid" , "targetPersonUid", "replyTillDate", 
        "endDate", "endDateMandatory", "replyComment", "informationTarget", "givenToParties"})
public class ConsentForReplyTO {
    private Long consentId;
    private ConsentTemplateTO template;
    private String replierUid;
    private String targetPersonUid;
    private XMLGregorianCalendar replyTillDate;
    private XMLGregorianCalendar endDate;
    private String replyComment;
    private Boolean endDateMandatory;
    private boolean alreadyReplied;
    private List<ActionPermittedTO> actionReplies;
    
    private String informationTarget;
    private List<ConsentExternalGivenTo> givenToParties;
    
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
     * @return the informationTarget
     */
    public String getInformationTarget() {
        return informationTarget;
    }
    /**
     * @param informationTarget the informationTarget to set
     */
    public void setInformationTarget(String informationTarget) {
        this.informationTarget = informationTarget;
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
     * @return the replyTillDate
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getReplyTillDate() {
        return replyTillDate;
    }
    /**
     * @param replyTillDate the replyTillDate to set
     */
    public void setReplyTillDate(XMLGregorianCalendar replyTillDate) {
        this.replyTillDate = replyTillDate;
    }
    /**
     * @return the actionReplies
     */
    public List<ActionPermittedTO> getActionReplies() {
        return actionReplies;
    }
    /**
     * @param actionReplies the actionReplies to set
     */
    public void setActionReplies(List<ActionPermittedTO> actionReplies) {
        this.actionReplies = actionReplies;
    }
    /**
     * @return the alreadyReplied
     */
    public boolean getAlreadyReplied() {
        return alreadyReplied;
    }
    /**
     * @param alreadyReplied the alreadyReplied to set
     */
    public void setAlreadyReplied(boolean alreadyReplied) {
        this.alreadyReplied = alreadyReplied;
    }
    /**
     * @return the endDateMandatory
     */
    public Boolean getEndDateMandatory() {
        return endDateMandatory;
    }
    /**
     * @param endDateMandatory the endDateMandatory to set
     */
    public void setEndDateMandatory(Boolean endDateMandatory) {
        this.endDateMandatory = endDateMandatory;
    }
    /**
     * @return the consentId
     */
    @XmlElement(name = "suostumusId")
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
    @XmlElement(name = "suostumuspohja")
    public ConsentTemplateTO getTemplate() {
        return template;
    }
    /**
     * @param template the template to set
     */
    public void setTemplate(ConsentTemplateTO template) {
        this.template = template;
    }
    /**
     * @return the replierUid
     */
    @XmlElement(name = "vastaanottaja")
    public String getReplierUid() {
        return replierUid;
    }
    /**
     * @param replierUid the replierUid to set
     */
    public void setReplierUid(String replierUid) {
        this.replierUid = replierUid;
    }
    /**
     * @return the endDate
     */
    @XmlElement(name = "maaraaika")
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the replyComment
     */
    @XmlElement(name = "kommentti")
    public String getReplyComment() {
        return replyComment;
    }
    /**
     * @param replyComment the replyComment to set
     */
    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }
    
    
}
