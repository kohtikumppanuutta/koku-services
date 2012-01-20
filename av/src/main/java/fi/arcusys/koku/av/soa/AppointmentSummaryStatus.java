package fi.arcusys.koku.av.soa;

import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;

/**
 * Types of appointment status for displaying on UI.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public enum AppointmentSummaryStatus {
    Created, Approved, Cancelled;
    
    public static AppointmentSummaryStatus valueOf(final AppointmentStatus dmStatus) {
        if (dmStatus == null) {
            return null;
        }
        
        final AppointmentSummaryStatus soaStatus = valueOf(dmStatus.name());
        if (soaStatus == null) {
            throw new IllegalArgumentException("SOA Appointment Status missing for value " + dmStatus);
        }
        return soaStatus;
    } 
}
