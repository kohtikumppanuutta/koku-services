package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Entity for representing reply to InformationRequest in TIVA-Tietopyyntö functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@Embeddable
public class InformationRequestReply {
    private Date replyCreatedDate;
    private String replyDescription;
    private String informationDetails;
    private String additionalReplyInfo;
    private String attachmentURL;
    private InformationReplyAccessType accessType;

    @Enumerated(EnumType.STRING)
    private InformationReplyStatus replyStatus;
    
    @ManyToOne
    private User replier;

    /**
     * @return the replier
     */
    public User getReplier() {
        return replier;
    }

    /**
     * @param replier the replier to set
     */
    public void setReplier(User replier) {
        this.replier = replier;
    }

    /**
     * @return the createdDate
     */
    public Date getReplyCreatedDate() {
        return replyCreatedDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setReplyCreatedDate(Date createdDate) {
        this.replyCreatedDate = createdDate;
    }

    /**
     * @return the description
     */
    public String getReplyDescription() {
        return replyDescription;
    }

    /**
     * @param description the description to set
     */
    public void setReplyDescription(String description) {
        this.replyDescription = description;
    }

    /**
     * @return the informationDetails
     */
    public String getInformationDetails() {
        return informationDetails;
    }

    /**
     * @param informationDetails the informationDetails to set
     */
    public void setInformationDetails(String informationDetails) {
        this.informationDetails = informationDetails;
    }

    /**
     * @return the additionalInfo
     */
    public String getAdditionalReplyInfo() {
        return additionalReplyInfo;
    }

    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalReplyInfo(String additionalInfo) {
        this.additionalReplyInfo = additionalInfo;
    }

    /**
     * @return the attachmentURL
     */
    public String getAttachmentURL() {
        return attachmentURL;
    }

    /**
     * @param attachmentURL the attachmentURL to set
     */
    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }

    /**
     * @return the accessType
     */
    public InformationReplyAccessType getAccessType() {
        return accessType;
    }

    /**
     * @param accessType the accessType to set
     */
    public void setAccessType(InformationReplyAccessType accessType) {
        this.accessType = accessType;
    }

    /**
     * @return the replyStatus
     */
    public InformationReplyStatus getReplyStatus() {
        return replyStatus;
    }

    /**
     * @param replyStatus the replyStatus to set
     */
    public void setReplyStatus(InformationReplyStatus replyStatus) {
        this.replyStatus = replyStatus;
    }
}
