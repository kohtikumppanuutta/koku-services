package fi.arcusys.koku.tiva.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
public class InformationCategoryTO {
    private Long categoryId;
    private String name;
    private String description;
    private List<InformationCategoryTO> subcategories;
    
    public boolean isLeaf() {
        return subcategories == null || subcategories.isEmpty();
    }

    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    /**
     * @return the subcategories
     */
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
