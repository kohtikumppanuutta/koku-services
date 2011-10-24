package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public class Answer {
	private int questionNumber;
	private Boolean yesNoValue;
	private String textValue;
	private String comment;
	/**
	 * @return the questionNumber
	 */
	public int getQuestionNumber() {
		return questionNumber;
	}
	/**
	 * @param questionNumber the questionNumber to set
	 */
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	/**
	 * @return the value
	 */
	@XmlElement (name = "yesNoValue")
	public Boolean getValue() {
		return yesNoValue;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Boolean value) {
		this.yesNoValue = value;
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
	/**
	 * @return the textValue
	 */
	public String getTextValue() {
		return textValue;
	}
	/**
	 * @param textValue the textValue to set
	 */
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
}
