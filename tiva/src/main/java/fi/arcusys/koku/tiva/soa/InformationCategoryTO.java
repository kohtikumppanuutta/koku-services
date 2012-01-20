package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about information category from KKS component.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
@XmlRootElement(name = "record")
public class InformationCategoryTO {
    private String categoryId;
    private String name;
    private String description;
    private List<InformationCategoryTO> subcategories;
    
    public boolean isLeaf() {
        return subcategories == null || subcategories.isEmpty();
    }

    /**
     * @return the categoryId
     */
    @XmlAttribute(name = "jsxid")
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the name
     */
    @XmlAttribute(name = "jsxtext")
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
    @XmlAttribute
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
     * @return the subcategories
     */
    @XmlElement(name = "record")
    public List<InformationCategoryTO> getSubcategories() {
        return subcategories;
    }

    /**
     * @param subcategories the subcategories to set
     */
    public void setSubcategories(List<InformationCategoryTO> subcategories) {
        this.subcategories = subcategories;
    }
    
}
