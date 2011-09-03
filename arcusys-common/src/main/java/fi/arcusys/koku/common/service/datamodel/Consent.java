package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAssignedConsentsByUser", query = "SELECT DISTINCT cn FROM Consent cn JOIN cn.receipients rs WHERE " +
    		"(NOT EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn AND cr.replier.uid = :userUid))" +
    		" AND rs.uid = :userUid ORDER BY cn.id DESC"),
    @NamedQuery(name = "countAssignedConsentsByUser", query = "SELECT COUNT(DISTINCT cn) FROM Consent cn JOIN cn.receipients rs WHERE " +
            "(NOT EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn AND cr.replier.uid = :userUid))" +
            " AND rs.uid = :userUid"),
    @NamedQuery(name = "findProcessedConsentsBySender", query = "SELECT DISTINCT cn FROM Consent cn WHERE " +
            "(EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn))" +
            " AND cn.creator = :sender ORDER BY cn.id DESC"),
    @NamedQuery(name = "countProcessedConsentsBySender", query = "SELECT COUNT(DISTINCT cn) FROM Consent cn WHERE " +
            "(EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn))" +
            " AND cn.creator = :sender")
})
public class Consent extends AbstractEntity {
    private Date validTill;
    private Boolean endDateMandatory;
    
    @ManyToOne
    private ConsentTemplate template;
    
    @Enumerated(EnumType.STRING)
    private ConsentType creationType;

    @ManyToOne
    private User creator;
    
    @ManyToOne
    private User targetPerson;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> receipients;

    /**
     * @return the isEndDateMandatory
     */
    public Boolean getEndDateMandatory() {
        return endDateMandatory;
    }

    /**
     * @param isEndDateMandatory the isEndDateMandatory to set
     */
    public void setEndDateMandatory(Boolean isEndDateMandatory) {
        this.endDateMandatory = isEndDateMandatory;
    }

    /**
     * @return the validTill
     */
    public Date getValidTill() {
        return validTill;
    }

    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    /**
     * @return the template
     */
    public ConsentTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(ConsentTemplate template) {
        this.template = template;
    }

    /**
     * @return the creationType
     */
    public ConsentType getCreationType() {
        return creationType;
    }

    /**
     * @param creationType the creationType to set
     */
    public void setCreationType(ConsentType creationType) {
        this.creationType = creationType;
    }

    /**
     * @return the creator
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * @return the targetPerson
     */
    public User getTargetPerson() {
        return targetPerson;
    }

    /**
     * @param targetPerson the targetPerson to set
     */
    public void setTargetPerson(User targetPerson) {
        this.targetPerson = targetPerson;
    }

    /**
     * @return the receipients
     */
    public Set<User> getReceipients() {
        return receipients;
    }

    /**
     * @param receipients the receipients to set
     */
    public void setReceipients(Set<User> receipients) {
        this.receipients = receipients;
    }
}
