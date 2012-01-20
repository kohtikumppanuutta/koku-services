package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import fi.arcusys.koku.common.soa.UserInfo;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about response to request.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "response", namespace = "http://soa.kv.koku.arcusys.fi/")
public class ResponseTO {
	private String name;
	private UserInfo replierUserInfo;
	private List<AnswerTO> answers;
	private String comment;
	
	/**
     * @return the replierUserInfo
     */
    public UserInfo getReplierUserInfo() {
        return replierUserInfo;
    }
    /**
     * @param replierUserInfo the replierUserInfo to set
     */
    public void setReplierUserInfo(UserInfo replierUserInfo) {
        this.replierUserInfo = replierUserInfo;
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
