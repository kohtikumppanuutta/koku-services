package fi.arcusys.koku.tiva.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ActionRequestSummary {
    private String name;
    private String description;
    private ActionRequestStatus status;
    
    
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
     * @return the status
     */
    public ActionRequestStatus getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(ActionRequestStatus status) {
        this.status = status;
    }
}
