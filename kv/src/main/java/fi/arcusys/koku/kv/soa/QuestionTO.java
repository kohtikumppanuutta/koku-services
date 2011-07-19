package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "question", namespace = "http://soa.kv.koku.arcusys.fi/")
public class QuestionTO {
	private int number;
	private String description;
	private QuestionType type;
	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
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
