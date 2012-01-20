package fi.arcusys.koku.av.soa;

import java.util.List;

import fi.arcusys.koku.common.soa.UserInfo;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about recipients of the appointment.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 18, 2011
 */
public class AppointmentReceipientTO {
    private String targetPerson;
    private UserInfo targetPersonUserInfo;
    private List<String> receipients;
    private List<UserInfo> receipientUserInfos;
    
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
     * @return the receipientUserInfos
     */
    public List<UserInfo> getReceipientUserInfos() {
        return receipientUserInfos;
    }
    /**
     * @param receipientUserInfos the receipientUserInfos to set
     */
    public void setReceipientUserInfos(List<UserInfo> receipientUserInfos) {
        this.receipientUserInfos = receipientUserInfos;
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
