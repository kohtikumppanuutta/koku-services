package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findReplyByConsentAndUser", query = "SELECT DISTINCT rp FROM ConsentReply rp WHERE rp.consent = :consent AND rp.replier = :user ORDER BY rp.id DESC"),
    @NamedQuery(name = "findRepliedConsentsByUser", query = "SELECT DISTINCT rp FROM ConsentReply rp WHERE rp.replier = :user ORDER BY rp.id DESC"),
    @NamedQuery(name = "countRepliedConsentsByUser", query = "SELECT COUNT(DISTINCT rp) FROM ConsentReply rp WHERE rp.replier = :user"),
    @NamedQuery(name = "findRepliesByConsent", query = "SELECT DISTINCT rp FROM ConsentReply rp WHERE rp.consent = :consent ORDER BY rp.id DESC")
})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"consent_id", "replier_id"}))
public class ConsentReply extends AbstractEntity {
    private Date validTill;
    private String comment;
    private ConsentReplyStatus status;

    @ManyToOne
    private Consent consent;
    
    @ManyToOne
    private User replier;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ConsentActionReply> actions;

    
    
    /**
     * @return the status
     */
    public ConsentReplyStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ConsentReplyStatus status) {
        this.status = status;
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

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
     * @return the replier
     */
    public User getReplier() {
        return replier;
    }

    /**
     * @param replier the replier to set
     */
    public void setReplier(User replier) {
        this.replier = replier;
    }

    /**
     * @return the actions
     */
    public Set<ConsentActionReply> getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(Set<ConsentActionReply> actions) {
        this.actions = actions;
    }
}
