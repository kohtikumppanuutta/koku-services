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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Request.GET_REQUESTS_BY_IDS, query = "SELECT DISTINCT r FROM Request r WHERE r.id in (:ids) ORDER BY r.id DESC"),
	@NamedQuery(name = Request.GET_REQUESTS_BY_USER_UID, query = "SELECT DISTINCT r FROM Request r WHERE r.fromUser = :user ORDER BY r.id DESC")
})
public class Request extends Message {
	public static final String GET_REQUESTS_BY_IDS = "getRequestsByIds";
	public static final String GET_REQUESTS_BY_USER_UID = "getRequestsByUserUid";
	
	private Date endDate;

	@ManyToOne
	private RequestTemplate template;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
	private Set<Response> responses;
	
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
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
