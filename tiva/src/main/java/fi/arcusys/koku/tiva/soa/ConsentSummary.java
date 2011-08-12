package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentSummary extends ConsentShortSummary {
    private ConsentStatus status;
    private XMLGregorianCalendar givenAt;
    private XMLGregorianCalendar validTill;
    private ConsentCreateType createType;
    private List<String> receipients;
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
    public XMLGregorianCalendar getValidTill() {
        return validTill;
    }
    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(XMLGregorianCalendar validTill) {
        this.validTill = validTill;
    }
    /**
     * @return the createType
     */
    public ConsentCreateType getCreateType() {
        return createType;
    }
    /**
     * @param createType the createType to set
     */
    public void setCreateType(ConsentCreateType createType) {
        this.createType = createType;
    }
}
