package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@Entity
public class InformationRequest extends AbstractEntity {
    private String title;
    private String description;
    private String requestPurpose;
    private String legislationInfo;
    private String additionalInfo;
    private Date validTill;
    
    @Embedded
    private InformationRequestReply reply;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User targetPerson;

    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InformationRequestCategory> categories;

    /**
     * @return the reply
     */
    public InformationRequestReply getReply() {
        return reply;
    }

    /**
     * @param reply the reply to set
     */
    public void setReply(InformationRequestReply reply) {
        this.reply = reply;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the requestPurpose
     */
    public String getRequestPurpose() {
        return requestPurpose;
    }

    /**
     * @param requestPurpose the requestPurpose to set
     */
    public void setRequestPurpose(String requestPurpose) {
        this.requestPurpose = requestPurpose;
    }

    /**
     * @return the legislationInfo
     */
    public String getLegislationInfo() {
        return legislationInfo;
    }

    /**
     * @param legislationInfo the legislationInfo to set
     */
    public void setLegislationInfo(String legislationInfo) {
        this.legislationInfo = legislationInfo;
    }

    /**
     * @return the additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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
     * @return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * @return the receiver
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(User receiver) {
        this.receiver = receiver;
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
     * @return the categories
     */
    public Set<InformationRequestCategory> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(Set<InformationRequestCategory> categories) {
        this.categories = categories;
    }
}
