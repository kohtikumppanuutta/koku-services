package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity for representing authorization in TIVA-Valtakirja functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Sep 12, 2011
 */
@Entity
@NamedQueries({
    @NamedQuery( name = "findReceivedAuthorizations", query = "SELECT DISTINCT auth FROM Authorization auth WHERE auth.toUser = :user AND auth.replyStatus IS NOT NULL ORDER BY auth.id DESC"),
    @NamedQuery( name = "countReceivedAuthorizations", query = "SELECT COUNT(DISTINCT auth) FROM Authorization auth WHERE auth.toUser = :user AND auth.replyStatus IS NOT NULL"),
    @NamedQuery( name = "findSentAuthorizations", query = "SELECT DISTINCT auth FROM Authorization auth WHERE auth.fromUser = :user ORDER BY auth.id DESC"),
    @NamedQuery( name = "countSentAuthorizations", query = "SELECT COUNT(DISTINCT auth) FROM Authorization auth WHERE auth.fromUser = :user")
})
public class Authorization extends AbstractEntity {

    @ManyToOne
    private AuthorizationTemplate template;
    
    private String comment;
    
    @ManyToOne
    private User fromUser;
    
    @ManyToOne
    private User toUser;
    
    @ManyToOne
    private User targetPerson;
    
    private Date replyTill;
    private Date givenAt;
    private Date validTill;
    
    @Enumerated(EnumType.STRING)
    private AuthorizationType creationType;
    
    @Enumerated(EnumType.STRING)
    private AuthorizationReplyStatus replyStatus;
    private String replyComment;
    
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
     * @return the replyComment
     */
    public String getReplyComment() {
        return replyComment;
    }

    /**
     * @param replyComment the replyComment to set
     */
    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }

    /**
     * @return the status
     */
    public AuthorizationReplyStatus getStatus() {
        return replyStatus;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AuthorizationReplyStatus status) {
        this.replyStatus = status;
    }

    /**
     * @return the replyTill
     */
    public Date getReplyTill() {
        return replyTill;
    }

    /**
     * @param replyTill the replyTill to set
     */
    public void setReplyTill(Date replyTill) {
        this.replyTill = replyTill;
    }

    /**
     * @return the template
     */
    public AuthorizationTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(AuthorizationTemplate template) {
        this.template = template;
    }

    /**
     * @return the fromUser
     */
    public User getFromUser() {
        return fromUser;
    }

    /**
     * @param fromUser
     *            the fromUser to set
     */
    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * @return the toUser
     */
    public User getToUser() {
        return toUser;
    }

    /**
     * @param toUser
     *            the toUser to set
     */
    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    /**
     * @return the targetPerson
     */
    public User getTargetPerson() {
        return targetPerson;
    }

    /**
     * @param targetPerson
     *            the targetPerson to set
     */
    public void setTargetPerson(User targetPerson) {
        this.targetPerson = targetPerson;
    }

    /**
     * @return the givenAt
     */
    public Date getGivenAt() {
        return givenAt;
    }

    /**
     * @param givenAt
     *            the givenAt to set
     */
    public void setGivenAt(Date givenAt) {
        this.givenAt = givenAt;
    }

    /**
     * @return the validTill
     */
    public Date getValidTill() {
        return validTill;
    }

    /**
     * @param validTill
     *            the validTill to set
     */
    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    /**
     * @return the creationType
     */
    public AuthorizationType getCreationType() {
        return creationType;
    }

    /**
     * @param creationType
     *            the creationType to set
     */
    public void setCreationType(AuthorizationType creationType) {
        this.creationType = creationType;
    }

}
