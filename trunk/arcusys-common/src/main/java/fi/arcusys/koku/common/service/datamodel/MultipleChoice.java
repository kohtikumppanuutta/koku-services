package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
@Entity
public class MultipleChoice extends AbstractEntity {
    private int number;
    private String description;
    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }
    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
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
    
    
}
