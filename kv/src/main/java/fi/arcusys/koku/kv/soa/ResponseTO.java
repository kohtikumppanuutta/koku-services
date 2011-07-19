package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "response", namespace = "http://soa.kv.koku.arcusys.fi/")
public class ResponseTO {
	private String name;
	private List<AnswerTO> answers;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the answers
	 */
	public List<AnswerTO> getAnswers() {
		return answers;
	}
	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<AnswerTO> answers) {
		this.answers = answers;
	}
}
