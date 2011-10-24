package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 11, 2011
 */
@XmlType(name = "givenTo", namespace = "http://services.koku.fi/entity/tiva/v1", 
propOrder = {"partyId", "partyName"})
public class ConsentExternalGivenTo {
    private String partyId;
    private String partyName;
    /**
     * @return the partyId
     */
    @XmlElement(required = true)
    public String getPartyId() {
        return partyId;
    }
    /**
     * @param partyId the partyId to set
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    /**
     * @return the partyName
     */
    @XmlElement(required = true)
    public String getPartyName() {
        return partyName;
    }
    /**
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
