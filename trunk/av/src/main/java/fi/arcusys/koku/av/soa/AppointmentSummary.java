package fi.arcusys.koku.av.soa;


import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@XmlType (name = "appointmentSummary", namespace = "http://soa.av.koku.arcusys.fi/",
propOrder={"appointmentId", "status", "sender" , "subject", "description"})
public class AppointmentSummary {


    private long appointmentId;
    private String sender;
    private String subject;
    private String description;
    private AppointmentSummaryStatus status;

    /**
     * @return the appointmentId
     */
    public long getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the status
     */
    public AppointmentSummaryStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AppointmentSummaryStatus status) {
        this.status = status;
    }
}
