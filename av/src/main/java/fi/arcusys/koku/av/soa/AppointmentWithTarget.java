package fi.arcusys.koku.av.soa;

import javax.xml.bind.annotation.XmlType;

import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 24, 2011
 */
@XmlType (name = "appointmentWithTarget", namespace = "http://soa.av.koku.arcusys.fi/")
public class AppointmentWithTarget extends AppointmentSummary {
    private String targetPerson;
    private UserInfo targetPersonUserInfo;
    
    /**
     * @return the targetPersonUserInfo
     */
    public UserInfo getTargetPersonUserInfo() {
        return targetPersonUserInfo;
    }

    /**
     * @param targetPersonUserInfo the targetPersonUserInfo to set
     */
    public void setTargetPersonUserInfo(UserInfo targetPersonUserInfo) {
        this.targetPersonUserInfo = targetPersonUserInfo;
    }

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
