package fi.arcusys.koku.kv.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
public class Question extends AbstractEntity {
	
	private int index_;
	private String description;

	@Enumerated(EnumType.STRING)
	private QuestionType type;
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index_;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index_ = index;
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
	 * @return the type
	 */
	public QuestionType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(QuestionType type) {
		this.type = type;
	}
}
