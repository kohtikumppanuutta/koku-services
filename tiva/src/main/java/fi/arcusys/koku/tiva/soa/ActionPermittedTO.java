package fi.arcusys.koku.tiva.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about reply to single action requested in consent. 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@XmlType (name = "toimenpidevastaus", namespace = "http://soa.tiva.koku.arcusys.fi/",
    propOrder={"actionRequestNumber", "permitted"})
public class ActionPermittedTO {
    private int actionRequestNumber;
    private boolean permitted;
    /**
     * @return the actionRequestNumber
     */
    public int getActionRequestNumber() {
        return actionRequestNumber;
    }
    /**
     * @param actionRequestNumber the actionRequestNumber to set
     */
    public void setActionRequestNumber(int actionRequestNumber) {
        this.actionRequestNumber = actionRequestNumber;
    }
    /**
     * @return the isPermitted
     */
    public boolean isPermitted() {
        return permitted;
    }
    /**
     * @param isPermitted the isPermitted to set
     */
    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }
}
