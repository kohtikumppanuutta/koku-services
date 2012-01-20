package fi.arcusys.koku.hak.soa;

import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about daycare request.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 20, 2011
 */
@XmlType (name = "daycareRequest", namespace = "http://soa.hak.koku.arcusys.fi/")
public class DaycareRequestTO {
    private Long requestId;
    private String creatorUid;
    private String targetPersonUid;
    private XMLGregorianCalendar daycareNeededFromDate;
    private String formContent;
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
     * @return the creatorUid
     */
    public String getCreatorUid() {
        return creatorUid;
    }
    /**
     * @param creatorUid the creatorUid to set
     */
    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
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
     * @return the daycareNeededFromDate
     */
    public XMLGregorianCalendar getDaycareNeededFromDate() {
        return daycareNeededFromDate;
    }
    /**
     * @param daycareNeededFromDate the daycareNeededFromDate to set
     */
    public void setDaycareNeededFromDate(XMLGregorianCalendar daycareNeededFromDate) {
        this.daycareNeededFromDate = daycareNeededFromDate;
    }
    /**
     * @return the formContent
     */
    public String getFormContent() {
        return formContent;
    }
    /**
     * @param formContent the formContent to set
     */
    public void setFormContent(String formContent) {
        this.formContent = formContent;
    }
}
