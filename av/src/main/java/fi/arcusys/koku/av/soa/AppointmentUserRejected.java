package fi.arcusys.koku.av.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Nov 28, 2011
 */
public class AppointmentUserRejected {
    private String userUid;
    private String userDisplayName;
    private String rejectComment;

    /**
     * @return the userUid
     */
    public String getUserUid() {
        return userUid;
    }
    /**
     * @param userUid the userUid to set
     */
    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
    /**
     * @return the userDisplayName
     */
    public String getUserDisplayName() {
        return userDisplayName;
    }
    /**
     * @param userDisplayName the userDisplayName to set
     */
    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
    /**
     * @return the rejectComment
     */
    public String getRejectComment() {
        return rejectComment;
    }
    /**
     * @param rejectComment the rejectComment to set
     */
    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }
    
    
}
