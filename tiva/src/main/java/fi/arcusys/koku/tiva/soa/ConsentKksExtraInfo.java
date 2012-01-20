package fi.arcusys.koku.tiva.soa;

import java.util.List;

/**
 * Data transfer object for communication with UI/Intalio process. Holds KKS-specific data about consent.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 11, 2011
 */
public class ConsentKksExtraInfo {
    private String informationTargetId;
    private List<ConsentExternalGivenTo> givenTo;
    private String metaInfo;
    /**
     * @return the informationTargetId
     */
    public String getInformationTargetId() {
        return informationTargetId;
    }
    /**
     * @param informationTargetId the informationTargetId to set
     */
    public void setInformationTargetId(String informationTargetId) {
        this.informationTargetId = informationTargetId;
    }
    /**
     * @return the givenTo
     */
    public List<ConsentExternalGivenTo> getGivenTo() {
        return givenTo;
    }
    /**
     * @param givenTo the givenTo to set
     */
    public void setGivenTo(List<ConsentExternalGivenTo> givenTo) {
        this.givenTo = givenTo;
    }
    /**
     * @return the metaInfo
     */
    public String getMetaInfo() {
        return metaInfo;
    }
    /**
     * @param metaInfo the metaInfo to set
     */
    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }
    
    
}
