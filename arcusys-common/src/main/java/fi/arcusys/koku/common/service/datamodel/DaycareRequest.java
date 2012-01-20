package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Entity for representing request for daycare in HAK functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
@Entity
public class DaycareRequest extends AbstractEntity {
    private Date neededFromDate;

    @ManyToOne
    private User creator;

    @ManyToOne
    private User targetPerson;
    
    @Lob
    private String formContent;

    /**
     * @return the neededFromDate
     */
    public Date getNeededFromDate() {
        return neededFromDate;
    }

    /**
     * @param neededFromDate the neededFromDate to set
     */
    public void setNeededFromDate(Date neededFromDate) {
        this.neededFromDate = neededFromDate;
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
     * @return the formContent
     */
    public String getFormContent() {
        return formContent;
    }

    /**
     * @param formContent the formContent to set
     */
    public void setFormContent(String formContent) {
        this.formContent = formContent;
    }
    
    
}
