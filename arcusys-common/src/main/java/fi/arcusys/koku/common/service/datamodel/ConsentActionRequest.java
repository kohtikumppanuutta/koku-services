package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * Entity for representing single action in consent request in TIVA-Suostumus functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Entity
public class ConsentActionRequest extends AbstractEntity {
    private int number;
    private String name;
    
    @Lob
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
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
