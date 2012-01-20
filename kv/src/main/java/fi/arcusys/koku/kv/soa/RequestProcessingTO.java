package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about request for usage in Intalio process.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 12, 2011
 */
@XmlType (name = "requestProcessing", namespace = "http://soa.kv.koku.arcusys.fi/")
public class RequestProcessingTO {
    private String fromUserUid;
    private String fromRole;
    private String subject;
    private List<String> receipients;
    private String content;
    private XMLGregorianCalendar replyTill; 
    private Integer notifyBeforeDays;
    /**
     * @return the fromUserUid
     */
    public String getFromUserUid() {
        return fromUserUid;
    }
    /**
     * @param fromUserUid the fromUserUid to set
     */
    public void setFromUserUid(String fromUserUid) {
        this.fromUserUid = fromUserUid;
    }
    /**
     * @return the fromRole
     */
    public String getFromRole() {
        return fromRole;
    }
    /**
     * @param fromRole the fromRole to set
     */
    public void setFromRole(String fromRole) {
        this.fromRole = fromRole;
    }
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * @return the receipients
     */
    public List<String> getReceipients() {
        return receipients;
    }
    /**
     * @param receipients the receipients to set
     */
    public void setReceipients(List<String> receipients) {
        this.receipients = receipients;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return the replyTill
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getReplyTill() {
        return replyTill;
    }
    /**
     * @param replyTill the replyTill to set
     */
    public void setReplyTill(XMLGregorianCalendar replyTill) {
        this.replyTill = replyTill;
    }
    /**
     * @return the notifyBeforeDays
     */
    public Integer getNotifyBeforeDays() {
        return notifyBeforeDays;
    }
    /**
     * @param notifyBeforeDays the notifyBeforeDays to set
     */
    public void setNotifyBeforeDays(Integer notifyBeforeDays) {
        this.notifyBeforeDays = notifyBeforeDays;
    }
    
    
}
