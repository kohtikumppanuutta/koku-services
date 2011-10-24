package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@Entity
public class InformationRequestCategory extends AbstractEntity {
    private String categoryUid;
    
    @ManyToOne
    private InformationRequest request;

    /**
     * @return the request
     */
    public InformationRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(InformationRequest request) {
        this.request = request;
    }

    /**
     * @return the categoryUid
     */
    public String getCategoryUid() {
        return categoryUid;
    }

    /**
     * @param categoryUid the categoryUid to set
     */
    public void setCategoryUid(String categoryUid) {
        this.categoryUid = categoryUid;
    }
}
