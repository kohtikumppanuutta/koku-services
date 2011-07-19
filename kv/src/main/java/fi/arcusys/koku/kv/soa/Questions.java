package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public class Questions {
	private List<QuestionTO> questions;

	/**
	 * @return the questions
	 */
	@XmlElement (name = "question")
	public List<QuestionTO> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(final List<QuestionTO> questions) {
		this.questions = questions;
	}
}
