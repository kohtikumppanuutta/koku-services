package fi.arcusys.koku.tiva.soa;

import java.util.List;

/**
 * Data transfer object for communication with UI/Intalio process. Holds detailed data about consent.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentTO extends ConsentSummary {
    private String comment;
    private List<ActionRequestSummary> actionRequests;

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the actionRequests
     */
    public List<ActionRequestSummary> getActionRequests() {
        return actionRequests;
    }

    /**
     * @param actionRequests the actionRequests to set
     */
    public void setActionRequests(List<ActionRequestSummary> actionRequests) {
        this.actionRequests = actionRequests;
    }
}
