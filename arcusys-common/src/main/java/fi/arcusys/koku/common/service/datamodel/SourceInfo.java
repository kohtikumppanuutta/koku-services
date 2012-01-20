package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Embeddable;

/**
 * Entity for representing source info of Consent.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 7, 2011
 */
@Embeddable
public class SourceInfo {
    private String additionalInfo;
    private String repository;
    private String attachmentUrl;
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
     * @return the repository
     */
    public String getRepository() {
        return repository;
    }
    /**
     * @param repository the repository to set
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }
    /**
     * @return the attachmentUrl
     */
    public String getAttachmentUrl() {
        return attachmentUrl;
    }
    /**
     * @param attachmentUrl the attachmentUrl to set
     */
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
    
    
}
