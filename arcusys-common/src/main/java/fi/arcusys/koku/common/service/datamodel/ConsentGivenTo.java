package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity for representing party, which getting consent in TIVA-Suostumus functionality. Used mainly by KKS-component.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 11, 2011
 */
@Entity
public class ConsentGivenTo extends AbstractEntity {
    private String partyId;
    private String partyName;
    
    @ManyToOne
    private Consent consent;
    
    /**
     * @return the consent
     */
    public Consent getConsent() {
        return consent;
    }

    /**
     * @param consent the consent to set
     */
    public void setConsent(Consent consent) {
        this.consent = consent;
    }

    /**
     * @return the partyId
     */
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
