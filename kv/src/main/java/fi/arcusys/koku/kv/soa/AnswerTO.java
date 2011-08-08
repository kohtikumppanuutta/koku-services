package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "answer", namespace = "http://soa.kv.koku.arcusys.fi/")
public class AnswerTO {
	private int questionNumber;
	private String answer;
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
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
}
