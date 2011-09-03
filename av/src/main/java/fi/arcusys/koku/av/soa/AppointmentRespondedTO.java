package fi.arcusys.koku.av.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
public class AppointmentRespondedTO extends AppointmentSummary {
    private String targetPerson;
    private String replier;
    private String replierComment;
    private AppointmentSlotTO approvedSlot;
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
     * @return the replier
     */
    public String getReplier() {
        return replier;
    }
    /**
     * @param replier the replier to set
     */
    public void setReplier(String replier) {
        this.replier = replier;
    }
    /**
     * @return the replierComment
     */
    public String getReplierComment() {
        return replierComment;
    }
    /**
     * @param replierComment the replierComment to set
     */
    public void setReplierComment(String replierComment) {
        this.replierComment = replierComment;
    }
    /**
     * @return the approvedSlot
     */
    public AppointmentSlotTO getApprovedSlot() {
        return approvedSlot;
    }
    /**
     * @param approvedSlot the approvedSlot to set
     */
    public void setApprovedSlot(AppointmentSlotTO approvedSlot) {
        this.approvedSlot = approvedSlot;
    }
}
