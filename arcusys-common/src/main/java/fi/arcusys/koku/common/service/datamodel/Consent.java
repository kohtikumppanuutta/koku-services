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
//    @NamedQuery(name = "findConsentsByUser", query = "SELECT cn FROM Consent cn WHERE :user MEMBER OF cn.receipients " +
//    		" AND :userUid NOT IN (SELECT cr.replier.uid FROM ConsentReply cr WHERE cr.consent = cn)")
    @NamedQuery(name = "findConsentsByUser", query = "SELECT DISTINCT cn FROM Consent cn JOIN cn.receipients rs WHERE " +
    		"(NOT EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn AND cr.replier.uid = :userUid))" +
//    		" AND (:user MEMBER OF (cn.receipients)) " +
    		" AND rs.uid = :userUid" +
    		"")
})
public class Consent extends AbstractEntity {
    private Date validTill;
    
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
    
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "consent")
//    private Set<ConsentReply> replies;
//    
//    
//
//    /**
//     * @return the replies
//     */
//    public Set<ConsentReply> getReplies() {
//        return replies;
//    }
//
//    /**
//     * @param replies the replies to set
//     */
//    public void setReplies(Set<ConsentReply> replies) {
//        this.replies = replies;
//    }
//
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
