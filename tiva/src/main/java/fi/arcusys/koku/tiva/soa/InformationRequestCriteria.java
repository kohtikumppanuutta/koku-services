package fi.arcusys.koku.tiva.soa;

import static fi.arcusys.koku.common.service.CalendarUtil.getSafeDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.service.dto.InformationRequestDTOCriteria;

/**
 * Data transfer object for communication with UI/Intalio process. Holds criteria for searching information requests.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
public class InformationRequestCriteria {
    private String senderUid;
    private String receiverUid;
    private String targetPersonUid;
    
    private String informationContent;
    private String freeText;

    private XMLGregorianCalendar createdFromDate;
    private XMLGregorianCalendar createdToDate;
    private XMLGregorianCalendar repliedFromDate;
    private XMLGregorianCalendar repliedToDate;
    
    public InformationRequestDTOCriteria toDtoCriteria() {
        final InformationRequestDTOCriteria dtoCriteria = new InformationRequestDTOCriteria();
        dtoCriteria.setSenderUid(senderUid);
        dtoCriteria.setReceiverUid(receiverUid);
        dtoCriteria.setTargetPersonUid(targetPersonUid);
        
        dtoCriteria.setInformationContent(informationContent);
        dtoCriteria.setFreeText(freeText);
        
        dtoCriteria.setCreatedFromDate(getSafeDate(createdFromDate));
        dtoCriteria.setCreatedToDate(getSafeDate(createdToDate));
        dtoCriteria.setRepliedFromDate(getSafeDate(repliedFromDate));
        dtoCriteria.setRepliedToDate(getSafeDate(repliedToDate));
        return dtoCriteria;
    }
    
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

    /**
     * @return the createdFromDate
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getCreatedFromDate() {
        return createdFromDate;
    }

    /**
     * @param createdFromDate the createdFromDate to set
     */
    public void setCreatedFromDate(XMLGregorianCalendar createdFromDate) {
        this.createdFromDate = createdFromDate;
    }

    /**
     * @return the createdToDate
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getCreatedToDate() {
        return createdToDate;
    }

    /**
     * @param createdToDate the createdToDate to set
     */
    public void setCreatedToDate(XMLGregorianCalendar createdToDate) {
        this.createdToDate = createdToDate;
    }

    /**
     * @return the repliedFromDate
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getRepliedFromDate() {
        return repliedFromDate;
    }

    /**
     * @param repliedFromDate the repliedFromDate to set
     */
    public void setRepliedFromDate(XMLGregorianCalendar repliedFromDate) {
        this.repliedFromDate = repliedFromDate;
    }

    /**
     * @return the repliedToDate
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getRepliedToDate() {
        return repliedToDate;
    }

    /**
     * @param repliedToDate the repliedToDate to set
     */
    public void setRepliedToDate(XMLGregorianCalendar repliedToDate) {
        this.repliedToDate = repliedToDate;
    }
}
