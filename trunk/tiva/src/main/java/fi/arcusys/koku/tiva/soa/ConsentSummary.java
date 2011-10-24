package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentSummary extends ConsentShortSummary {
    private List<String> receipients;
    private ConsentApprovalStatus approvalStatus;
    private ConsentStatus status;
    private XMLGregorianCalendar givenAt;
    private XMLGregorianCalendar validTill;
    
    /**
     * @return the receipients
     */
    public List<String> getReceipients() {
        return receipients;
    }

    /**
     * @param receipients the receipients to set
     */
    public void setReceipients(List<String> receipients) {
        this.receipients = receipients;
    }

    /**
     * @return the approvalStatus
     */
    public ConsentApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * @param approvalStatus the approvalStatus to set
     */
    public void setApprovalStatus(ConsentApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    /**
     * @return the status
     */
    public ConsentStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ConsentStatus status) {
        this.status = status;
    }

    /**
     * @return the givenAt
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getGivenAt() {
        return givenAt;
    }

    /**
     * @param givenAt the givenAt to set
     */
    public void setGivenAt(XMLGregorianCalendar givenAt) {
        this.givenAt = givenAt;
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
    
    
}
