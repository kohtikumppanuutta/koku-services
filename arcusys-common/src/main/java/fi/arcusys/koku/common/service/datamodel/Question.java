package fi.arcusys.koku.common.service.datamodel;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
 * Entity for representing single question in KV-Requests functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Entity
public class Question extends AbstractEntity {
	
	private int index_;
	private String description;

	@Enumerated(EnumType.STRING)
	private QuestionType type;
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<MultipleChoice> choices;
	
	/**
     * @return the choices
     */
    public Set<MultipleChoice> getChoices() {
        return choices;
    }
    /**
     * @param choices the choices to set
     */
    public void setChoices(Set<MultipleChoice> choices) {
        this.choices = choices;
    }
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
