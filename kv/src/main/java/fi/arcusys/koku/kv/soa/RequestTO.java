package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "request", namespace = "http://soa.kv.koku.arcusys.fi/")
public class RequestTO extends RequestSummary {
	private List<String> notResponded;
	private List<ResponseTO> responses;
	private List<QuestionTO> questions;
	private String content;
	
	/**
	 * @return the questions
	 */
	public List<QuestionTO> getQuestions() {
		return questions;
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<QuestionTO> questions) {
		this.questions = questions;
	}
	/**
	 * @return the recipients
	 */
	public List<String> getNotResponded() {
		return notResponded;
	}
	/**
	 * @param recipients the recipients to set
	 */
	public void setNotResponded(List<String> notResponded) {
		this.notResponded = notResponded;
	}
	/**
	 * @return the responses
	 */
	public List<ResponseTO> getResponses() {
		return responses;
	}
	/**
	 * @param responses the responses to set
	 */
	public void setResponses(List<ResponseTO> responses) {
		this.responses = responses;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
