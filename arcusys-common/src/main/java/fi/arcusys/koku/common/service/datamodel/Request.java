package fi.arcusys.koku.common.service.datamodel;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;

import fi.arcusys.koku.common.service.CalendarUtil;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Request.GET_REQUESTS_BY_IDS, query = "SELECT DISTINCT r FROM Request r WHERE r.id in (:ids) ORDER BY r.id DESC"),
	@NamedQuery(name = Request.GET_REQUESTS_BY_USER_UID, query = "SELECT DISTINCT r FROM Request r WHERE r.fromUser = :user " +
			" AND (r.replyTill IS NULL OR r.replyTill >= CURRENT_DATE) " +
			" ORDER BY r.id DESC"),
    @NamedQuery(name = "countRequestsByUserUid", query = "SELECT COUNT(DISTINCT r) FROM Request r WHERE r.fromUser = :user" +
    		" AND (r.replyTill IS NULL OR r.replyTill >= CURRENT_DATE)"),
    @NamedQuery(name = "findOldRequestsByUserUid", query = "SELECT DISTINCT r FROM Request r WHERE r.fromUser = :user " +
            " AND (r.replyTill IS NOT NULL AND r.replyTill < CURRENT_DATE) " +
            " ORDER BY r.id DESC"),
    @NamedQuery(name = "countOldRequestsByUserUid", query = "SELECT COUNT(DISTINCT r) FROM Request r WHERE r.fromUser = :user" +
            " AND (r.replyTill IS NOT NULL AND r.replyTill < CURRENT_DATE)"),
    @NamedQuery(name = "countRequestsByTemplate", query = "SELECT COUNT(DISTINCT r) FROM Request r WHERE r.template = :template")
})
public class Request extends AbstractEntity {
	public static final String GET_REQUESTS_BY_IDS = "findRequestsByIds";
	public static final String GET_REQUESTS_BY_USER_UID = "findRequestsByUserUid";
	
	private Date replyTill;
	private Integer notifyBeforeDays;

	@ManyToOne
	private RequestTemplate template;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
	private Set<Response> responses;
	
    private String subject;
    
    @ManyToOne
    private User fromUser;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> receipients;
    
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
}
