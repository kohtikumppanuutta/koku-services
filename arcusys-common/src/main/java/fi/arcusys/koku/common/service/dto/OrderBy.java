package fi.arcusys.koku.common.service.dto;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 19, 2011
 */
public class OrderBy {
	public static enum Type {
		ASC, DESC;
	}
	private Type type;
	private MessageQuery.Fields field;
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return the field
	 */
	public MessageQuery.Fields getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(MessageQuery.Fields field) {
		this.field = field;
	}
	
	
}
