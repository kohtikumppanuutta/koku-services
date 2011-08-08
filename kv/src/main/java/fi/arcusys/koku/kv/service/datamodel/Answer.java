package fi.arcusys.koku.kv.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
public class Answer extends AbstractEntity {
	private int index_;
	private String comment;
	private String value;
	
	/**
	 * @return the index_
	 */
	public int getIndex() {
		return index_;
	}
	/**
	 * @param index the index_ to set
	 */
	public void setIndex(int index) {
		index_ = index;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getValue() {
		return value; 
	}
	
	public void setValue(final String value) {
		this.value = value;
	}
	
	public String getValueAsString() {
		return value;
	}
}
