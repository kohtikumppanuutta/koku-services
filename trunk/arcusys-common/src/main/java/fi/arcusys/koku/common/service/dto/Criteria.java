package fi.arcusys.koku.common.service.dto;

import java.util.Set;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 19, 2011
 */
public class Criteria {
	private Set<String> keywords;
	private Set<MessageQuery.Fields> fields;
	/**
	 * @return the keywords
	 */
	public Set<String> getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	/**
	 * @return the fields
	 */
	public Set<MessageQuery.Fields> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(Set<MessageQuery.Fields> fields) {
		this.fields = fields;
	}
	
	
}
