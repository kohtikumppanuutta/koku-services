package fi.arcusys.koku.common.service.datamodel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 24, 2011
 */
@MappedSuperclass
public class AbstractEntity {
	@Id
	@GeneratedValue
	private Long id;
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	@PrePersist
	public void setDefaults() {
		if (this.createdDate == null) {
			this.setCreatedDate(new Date());
		}
	}

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractEntity)) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		return id != null && id.equals(other.id);
	}
	
	@Override
	public String toString() {
	    return getClass().getSimpleName() + ":" + id;
	}
}