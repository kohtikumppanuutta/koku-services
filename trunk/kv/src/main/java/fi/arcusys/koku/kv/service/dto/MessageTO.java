package fi.arcusys.koku.kv.service.dto;

import javax.xml.bind.annotation.XmlType;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
@XmlType (name = "message", namespace = "http://soa.kv.koku.arcusys.fi/")
public class MessageTO extends fi.arcusys.koku.kv.soa.MessageSummary {
	private String content;
	
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(final String content) {
		this.content = content;
	}
	
	
}
