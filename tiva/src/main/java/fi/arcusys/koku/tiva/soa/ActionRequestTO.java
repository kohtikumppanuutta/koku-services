package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@XmlType (name = "toimenpidepyynto", namespace = "http://soa.tiva.koku.arcusys.fi/",
    propOrder={"number", "name", "description"})
public class ActionRequestTO {
    private int number;
    private String name;
    private String description;
    
    
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
