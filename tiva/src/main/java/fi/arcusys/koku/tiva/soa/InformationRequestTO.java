package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about information request (tietopyyntö).
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
public class InformationRequestTO {
    private Long requestId;
    private String title;
    private String targetPersonUid;
    private List<String> categories;
    private String senderUid;
    private String receiverUid;
    private String receiverRoleUid;
    private String description;
    private String requestPurpose;
    private String legislationInfo;
    private String additionalInfo;
    private XMLGregorianCalendar validTill;
    
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
     * @return the additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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
     * @return the categories
     */
    @XmlElement (name = "category")
    public List<String> getCategories() {
        return categories;
    }
    /**
     * @param categories the categories to set
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
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
     * @return the requestPurpose
     */
    public String getRequestPurpose() {
        return requestPurpose;
    }
    /**
     * @param requestPurpose the requestPurpose to set
     */
    public void setRequestPurpose(String requestPurpose) {
        this.requestPurpose = requestPurpose;
    }
    /**
     * @return the legislationInfo
     */
    public String getLegislationInfo() {
        return legislationInfo;
    }
    /**
     * @param legislationInfo the legislationInfo to set
     */
    public void setLegislationInfo(String legislationInfo) {
        this.legislationInfo = legislationInfo;
    }
    /**
     * @return the validTill
     */
    public XMLGregorianCalendar getValidTill() {
        return validTill;
    }
    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(XMLGregorianCalendar validTill) {
        this.validTill = validTill;
    }
    
    
}
