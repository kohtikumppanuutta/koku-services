package fi.arcusys.koku.av.soa;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about appointment for reply by recipient.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 18, 2011
 */
public class AppointmentForReplyTO extends AppointmentSummary {
    private List<AppointmentSlotTO> slots;

    /**
     * @return the slots
     */
    public List<AppointmentSlotTO> getSlots() {
        if (this.slots == null) {
            this.slots = new ArrayList<AppointmentSlotTO>();
        }
        return slots;
    }

    /**
     * @param slots the slots to set
     */
    public void setSlots(List<AppointmentSlotTO> slots) {
        this.slots = slots;
    }
    
    
}
