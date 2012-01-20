package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * Data transfer object for communication with UI/Intalio process. Wrapper object for list of answers.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public class Answers {
	private List<Answer> answers;

	/**
	 * @return the answers
	 */
	@XmlElement (name = "answer")
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(final List<Answer> answers) {
		this.answers = answers;
	}
}
