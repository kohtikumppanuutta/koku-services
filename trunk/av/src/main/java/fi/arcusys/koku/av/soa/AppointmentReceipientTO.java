package fi.arcusys.koku.av.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 18, 2011
 */
public class AppointmentReceipientTO {
    private String targetPerson;
    private List<String> receipients;
    /**
     * @return the targetPerson
     */
    public String getTargetPerson() {
        return targetPerson;
    }
    /**
     * @param targetPerson the targetPerson to set
     */
    public void setTargetPerson(String targetPerson) {
        this.targetPerson = targetPerson;
    }
    /**
     * @return the receipients
     */
    public List<String> getReceipients() {
        return receipients;
    }
    /**
     * @param receipients the receipients to set
     */
    public void setReceipients(List<String> receipients) {
        this.receipients = receipients;
    }
    
    
}
