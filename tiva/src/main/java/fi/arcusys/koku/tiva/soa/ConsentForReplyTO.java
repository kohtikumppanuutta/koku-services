package fi.arcusys.koku.tiva.soa;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 9, 2011
 */
@XmlType (name = "suostumusForReply", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"consentId", "template", "replierUid" , "endDate", "replyComment"})
public class ConsentForReplyTO {
    private Long consentId;
    private ConsentTemplateTO template;
    private String replierUid;
    private XMLGregorianCalendar endDate;
    private String replyComment;
    
    
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
