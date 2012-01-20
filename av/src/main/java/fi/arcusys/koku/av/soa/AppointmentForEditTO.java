package fi.arcusys.koku.av.soa;

import java.util.List;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data for editing appointment.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 18, 2011
 */
public class AppointmentForEditTO extends AppointmentForReplyTO {
    private List<AppointmentReceipientTO> receipients;

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

}
