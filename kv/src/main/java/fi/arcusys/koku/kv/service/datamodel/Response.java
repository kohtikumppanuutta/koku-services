package fi.arcusys.koku.kv.service.datamodel;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
public class Response extends AbstractEntity {
	@ManyToOne
	private Request request;
	
	@ManyToOne
	private User replier;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Answer> answers;

	/**
	 * @return the replier
	 */
	public User getReplier() {
		return replier;
	}

	/**
	 * @param replier the replier to set
	 */
	public void setReplier(User replier) {
		this.replier = replier;
	}

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(Request request) {
		this.request = request;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		if (this.answers == null) {
			return Collections.emptyList();
		}
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
}
