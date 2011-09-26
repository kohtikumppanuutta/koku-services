package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 23, 2011
 */
public class InformationRequestDetail extends InformationRequestSummary {
    private String description;
    private String requestPurpose;
    private String legislationInfo;
    private String additionalInfo;
    private XMLGregorianCalendar createdDate;
    
    private String replyDescription;
    private String informationDetails;
    private String additionalReplyInfo;
    private String attachmentURL;
    private InformationAccessType accessType;

    private List<String> categories;

    /**
     * @return the createdDate
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(XMLGregorianCalendar createdDate) {
        this.createdDate = createdDate;
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
     * @return the replyDescription
     */
    public String getReplyDescription() {
        return replyDescription;
    }

    /**
     * @param replyDescription the replyDescription to set
     */
    public void setReplyDescription(String replyDescription) {
        this.replyDescription = replyDescription;
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
     * @return the additionalReplyInfo
     */
    public String getAdditionalReplyInfo() {
        return additionalReplyInfo;
    }

    /**
     * @param additionalReplyInfo the additionalReplyInfo to set
     */
    public void setAdditionalReplyInfo(String additionalReplyInfo) {
        this.additionalReplyInfo = additionalReplyInfo;
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
    public InformationAccessType getAccessType() {
        return accessType;
    }

    /**
     * @param accessType the accessType to set
     */
    public void setAccessType(InformationAccessType accessType) {
        this.accessType = accessType;
    }

    /**
     * @return the categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
