package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Entity
public class ConsentActionReply extends AbstractEntity {
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
     * @return the permitted
     */
    public boolean isPermitted() {
        return permitted;
    }
    /**
     * @param permitted the permitted to set
     */
    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }
}
