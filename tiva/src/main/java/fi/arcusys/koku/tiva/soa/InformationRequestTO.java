package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
public class InformationRequestTO {
    private Long requestId;
    private String title;
    private String targetPersonUid;
    private List<InformationCategoryTO> categories;
    private String informationRequest;
    private String receiverUid;
    private String description;
    private String requestPurpose;
    private String legislationInfo;
    private XMLGregorianCalendar validTill;
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
    public List<InformationCategoryTO> getCategories() {
        return categories;
    }
    /**
     * @param categories the categories to set
     */
    public void setCategories(List<InformationCategoryTO> categories) {
        this.categories = categories;
    }
    /**
     * @return the informationRequest
     */
    public String getInformationRequest() {
        return informationRequest;
    }
    /**
     * @param informationRequest the informationRequest to set
     */
    public void setInformationRequest(String informationRequest) {
        this.informationRequest = informationRequest;
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
