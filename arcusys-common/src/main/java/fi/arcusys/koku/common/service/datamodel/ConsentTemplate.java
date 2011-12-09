package fi.arcusys.koku.common.service.datamodel;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
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
    @NamedQuery(name = "findTemplatesByPrefix", query = "SELECT tmp FROM ConsentTemplate tmp WHERE tmp.title LIKE :prefix  ORDER BY tmp.id DESC")
})
public class ConsentTemplate extends AbstractEntity {
    private String title;

    @Lob
    private String description;
    
    private Date endDate;
    private Boolean endDateMandatory;
    
    @ManyToOne
    private User creator;
    
    @ManyToOne
    private AuthorizationTemplate authorizationTemplate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ConsentActionRequest> actions;
    
    /**
     * @return the authorizationTemplate
     */
    public AuthorizationTemplate getAuthorizationTemplate() {
        return authorizationTemplate;
    }
    /**
     * @param authorizationTemplate the authorizationTemplate to set
     */
    public void setAuthorizationTemplate(AuthorizationTemplate authorizationTemplate) {
        this.authorizationTemplate = authorizationTemplate;
    }
    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the endDateMandatory
     */
    public Boolean getEndDateMandatory() {
        return endDateMandatory;
    }
    /**
     * @param endDateMandatory the endDateMandatory to set
     */
    public void setEndDateMandatory(Boolean endDateMandatory) {
        this.endDateMandatory = endDateMandatory;
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
     * @return the actions
     */
    public Set<ConsentActionRequest> getActions() {
        if (actions == null) {
            return Collections.emptySet();
        }
        return actions;
    }
    /**
     * @param actions the actions to set
     */
    public void setActions(Set<ConsentActionRequest> actions) {
        this.actions = actions;
    }

    public Map<Integer, ConsentActionRequest> getNumberToActionMap() {
        final Map<Integer, ConsentActionRequest> actionRequests = new HashMap<Integer, ConsentActionRequest>();
        for (final ConsentActionRequest actionRequest : getActions()) {
            actionRequests.put(actionRequest.getNumber(), actionRequest);
        }
        return actionRequests;
    }
}
