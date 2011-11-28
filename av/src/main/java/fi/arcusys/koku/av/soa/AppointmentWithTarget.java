package fi.arcusys.koku.av.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 24, 2011
 */
@XmlType (name = "appointmentWithTarget", namespace = "http://soa.av.koku.arcusys.fi/")
public class AppointmentWithTarget extends AppointmentSummary {
    private String targetPerson;
    
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
    
    
}
