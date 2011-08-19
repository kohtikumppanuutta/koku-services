package fi.arcusys.koku.av.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 18, 2011
 */
public class AppointmentForEditTO extends AppointmentForReplyTO {
    private List<AppointmentReceipientTO> receipients;

    private String status;
    private String replier;
    private Integer approvedSlotNumber;
    private String replierComment;

    
    /**
     * @return the receipients
     */
    public List<AppointmentReceipientTO> getReceipients() {
        return receipients;
    }

    /**
     * @param receipients the receipients to set
     */
    public void setReceipients(List<AppointmentReceipientTO> receipients) {
        this.receipients = receipients;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the approvedSlotNumber
     */
    public Integer getApprovedSlotNumber() {
        return approvedSlotNumber;
    }

    /**
     * @param approvedSlotNumber the approvedSlotNumber to set
     */
    public void setApprovedSlotNumber(Integer approvedSlotNumber) {
        this.approvedSlotNumber = approvedSlotNumber;
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
    
    
}
