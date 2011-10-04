package fi.arcusys.koku.tiva.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentTO extends ConsentSummary {
    private List<ActionRequestSummary> actionRequests;

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
