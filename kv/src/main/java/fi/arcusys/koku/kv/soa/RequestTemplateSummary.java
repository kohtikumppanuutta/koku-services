package fi.arcusys.koku.kv.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public class RequestTemplateSummary {
    private Long requestTemplateId;
    private String subject;
    /**
     * @return the requestTemplateId
     */
    public Long getRequestTemplateId() {
        return requestTemplateId;
    }
    /**
     * @param requestTemplateId the requestTemplateId to set
     */
    public void setRequestTemplateId(Long requestTemplateId) {
        this.requestTemplateId = requestTemplateId;
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
    
    
}
