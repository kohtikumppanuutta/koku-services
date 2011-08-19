package fi.arcusys.koku.common.service.datamodel;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Apr 6, 2011
 */
@Entity
public class Message extends AbstractEntity {

	@Lob
	@Column(name = "content")
	private String text;

	private String subject;
	
	@ManyToOne
	private User fromUser;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<User> receipients;
	
	public String getText() {
		return text;
	}
	
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * @param fromUser
	 */
	public void setFrom(final User fromUser) {
		this.fromUser = fromUser;
	}
	
	public User getUser() {
		return this.fromUser;
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
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	/**
	 * @return the receipients
	 */
	public Set<User> getReceipients() {
		if (receipients == null) {
			return Collections.emptySet();
		}
		return receipients;
	}

	/**
	 * @param receipients the receipients to set
	 */
	public void setReceipients(final Set<User> receipients) {
		this.receipients = receipients;
	}
}
