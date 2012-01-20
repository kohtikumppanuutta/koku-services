package fi.arcusys.koku.common.service.dto;

import java.util.Date;

/**
 * Data transfer object for passing search criteria to information request search.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
public class InformationRequestDTOCriteria {
    private String senderUid;
    private String receiverUid;
    private String targetPersonUid;
    private String informationContent;
    private String freeText;
    private Date createdFromDate;
    private Date createdToDate;
    private Date repliedFromDate;
    private Date repliedToDate;

    /**
     * @return the informationContent
     */
    public String getInformationContent() {
        return informationContent;
    }
    /**
     * @param informationContent the informationContent to set
     */
    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }
    /**
     * @return the freeText
     */
    public String getFreeText() {
        return freeText;
    }
    /**
     * @param freeText the freeText to set
     */
    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }
    /**
     * @return the createdFromDate
     */
    public Date getCreatedFromDate() {
        return createdFromDate;
    }
    /**
     * @param createdFromDate the createdFromDate to set
     */
    public void setCreatedFromDate(Date createdFromDate) {
        this.createdFromDate = createdFromDate;
    }
    /**
     * @return the createdToDate
     */
    public Date getCreatedToDate() {
        return createdToDate;
    }
    /**
     * @param createdToDate the createdToDate to set
     */
    public void setCreatedToDate(Date createdToDate) {
        this.createdToDate = createdToDate;
    }
    /**
     * @return the repliedFromDate
     */
    public Date getRepliedFromDate() {
        return repliedFromDate;
    }
    /**
     * @param repliedFromDate the repliedFromDate to set
     */
    public void setRepliedFromDate(Date repliedFromDate) {
        this.repliedFromDate = repliedFromDate;
    }
    /**
     * @return the repliedToDate
     */
    public Date getRepliedToDate() {
        return repliedToDate;
    }
    /**
     * @param repliedToDate the repliedToDate to set
     */
    public void setRepliedToDate(Date repliedToDate) {
        this.repliedToDate = repliedToDate;
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
}
