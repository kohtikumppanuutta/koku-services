package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
public class InformationRequestSummary {
    private Long requestId;
    private String title;
    private String targetPersonUid;
    private String senderUid;
    private String receiverUid;
    private String receiverRoleUid;
    private XMLGregorianCalendar validTill;
    private InformationRequestStatus status;
    
    /**
     * @return the receiverRoleUid
     */
    public String getReceiverRoleUid() {
        return receiverRoleUid;
    }
    /**
     * @param receiverRoleUid the receiverRoleUid to set
     */
    public void setReceiverRoleUid(String receiverRoleUid) {
        this.receiverRoleUid = receiverRoleUid;
    }
    /**
     * @return the requestId
     */
    public Long getRequestId() {
        return requestId;
    }
    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
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
     * @return the senderUid
     */
    public String getSenderUid() {
        return senderUid;
    }
    /**
     * @param senderUid the senderUid to set
     */
    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }
    /**
     * @return the receiverUid
     */
    public String getReceiverUid() {
        return receiverUid;
    }
    /**
     * @param receiverUid the receiverUid to set
     */
    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }
    /**
     * @return the validTill
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getValidTill() {
        return validTill;
    }
    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(XMLGregorianCalendar validTill) {
        this.validTill = validTill;
    }
    /**
     * @return the status
     */
    public InformationRequestStatus getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(InformationRequestStatus status) {
        this.status = status;
    }
}
