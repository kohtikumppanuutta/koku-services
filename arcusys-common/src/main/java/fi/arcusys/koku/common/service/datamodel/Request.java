package fi.arcusys.koku.common.service.datamodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Entity for representing Request in KV-Requests functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Request.GET_REQUESTS_BY_IDS, query = "SELECT DISTINCT r FROM Request r WHERE r.id in (:ids) ORDER BY r.id DESC"),
	
	@NamedQuery(name = "findRequestsByUserUid", query = "SELECT DISTINCT r FROM Request r WHERE r.fromUser = :user " +
			" AND (r.replyTill IS NULL OR r.replyTill >= CURRENT_DATE) " +
			" ORDER BY r.id DESC"),
    @NamedQuery(name = "countRequestsByUserUid", query = "SELECT COUNT(DISTINCT r) FROM Request r WHERE r.fromUser = :user" +
    		" AND (r.replyTill IS NULL OR r.replyTill >= CURRENT_DATE)"),
    		
    @NamedQuery(name = "findRequestsByUserUidOrRoles", query = "SELECT DISTINCT r FROM Request r " +
    		" WHERE (r.fromUser = :user OR r.fromRoleUid in (:userRoles)) " +
            " AND (r.replyTill IS NULL OR r.replyTill >= CURRENT_DATE) " +
            " ORDER BY r.id DESC"),
    @NamedQuery(name = "countRequestsByUserUidOrRoles", query = "SELECT COUNT(DISTINCT r) FROM Request r " +
    		" WHERE (r.fromUser = :user OR r.fromRoleUid in (:userRoles)) " +
            " AND (r.replyTill IS NULL OR r.replyTill >= CURRENT_DATE)"),
    		    		
    @NamedQuery(name = "findOldRequestsByUserUid", query = "SELECT DISTINCT r FROM Request r WHERE r.fromUser = :user " +
            " AND (r.replyTill IS NOT NULL AND r.replyTill < CURRENT_DATE) " +
            " ORDER BY r.id DESC"),
    @NamedQuery(name = "countOldRequestsByUserUid", query = "SELECT COUNT(DISTINCT r) FROM Request r WHERE r.fromUser = :user" +
            " AND (r.replyTill IS NOT NULL AND r.replyTill < CURRENT_DATE)"),

    @NamedQuery(name = "findOldRequestsByUserUidOrRoles", query = "SELECT DISTINCT r FROM Request r " +
    		" WHERE (r.fromUser = :user OR r.fromRoleUid in (:userRoles)) " +
            " AND (r.replyTill IS NOT NULL AND r.replyTill < CURRENT_DATE) " +
            " ORDER BY r.id DESC"),
    @NamedQuery(name = "countOldRequestsByUserUidOrRoles", query = "SELECT COUNT(DISTINCT r) FROM Request r " +
    		" WHERE (r.fromUser = :user OR r.fromRoleUid in (:userRoles)) " +
            " AND (r.replyTill IS NOT NULL AND r.replyTill < CURRENT_DATE)"),
    
    @NamedQuery(name = "countRequestsByTemplate", query = "SELECT COUNT(DISTINCT r) FROM Request r WHERE r.template = :template"),

    @NamedQuery(name = "findOpenRequestsByNotificationDate", query = "SELECT r FROM Request r " +
    		" WHERE (SELECT COUNT (rs) FROM Response rs WHERE rs.request = r) < (SELECT COUNT(repl) FROM User_ repl WHERE repl MEMBER OF r.receipients)" +
    		" AND r.notifyDate BETWEEN :notifyDateFrom AND :notifyDateTo ")
})
public class Request extends AbstractEntity {
	public static final String GET_REQUESTS_BY_IDS = "findRequestsByIds";
	public static final String GET_REQUESTS_BY_USER_UID = "findRequestsByUserUid";
	
	private Date replyTill;
	private Integer notifyBeforeDays;
	private Date notifyDate;

	@ManyToOne
	private RequestTemplate template;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
	private Set<Response> responses;
	
    private String subject;
    
    @ManyToOne
    private User fromUser;
    
    private String fromRoleUid;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> receipients;
    
    /**
     * @return the fromRole
     */
    public String getFromRoleUid() {
        return fromRoleUid;
    }

    /**
     * @param fromRole the fromRole to set
     */
    public void setFromRoleUid(String fromRoleUid) {
        this.fromRoleUid = fromRoleUid;
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
     * @return the fromUser
     */
    public User getFromUser() {
        return fromUser;
    }

    /**
     * @param fromUser the fromUser to set
     */
    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * @return the receipients
     */
    public Set<User> getReceipients() {
        return receipients;
    }

    /**
     * @param receipients the receipients to set
     */
    public void setReceipients(Set<User> receipients) {
        this.receipients = receipients;
    }

    /**
     * @return the template
     */
    public RequestTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(RequestTemplate template) {
        this.template = template;
    }

    /**
	 * @return the responses
	 */
	public List<Response> getResponses() {
		if (this.responses == null) {
			return Collections.emptyList();
		}
		return new ArrayList<Response>(responses);
	}

	/**
	 * @param responses the responses to set
	 */
	public void setResponses(List<Response> responses) {
		this.responses = new HashSet<Response>(responses);
	}

    /**
     * @return the replyTill
     */
    public Date getReplyTill() {
        return replyTill;
    }

    /**
     * @param replyTill the replyTill to set
     */
    public void setReplyTill(Date replyTill) {
        this.replyTill = replyTill;
    }

    /**
     * @return the notifyBeforeDays
     */
    public Integer getNotifyBeforeDays() {
        return notifyBeforeDays;
    }

    /**
     * @param notifyBeforeDays the notifyBeforeDays to set
     */
    public void setNotifyBeforeDays(Integer notifyBeforeDays) {
        this.notifyBeforeDays = notifyBeforeDays;
    }
    
    @PrePersist
    @PreUpdate
    public void updateNotifyDate() {
        if (this.replyTill != null && this.notifyBeforeDays != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(replyTill);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - this.notifyBeforeDays);
            this.notifyDate = calendar.getTime();
        }
    }
}
