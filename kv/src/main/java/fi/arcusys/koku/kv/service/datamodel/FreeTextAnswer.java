package fi.arcusys.koku.kv.service.datamodel;

import javax.persistence.Entity;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
public class FreeTextAnswer  {
	private String value;
	
	/**
	 * @return
	 */
//	@Override
	public String getValue() {
		return this.value;
	}
	
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
//	@Override
	public String getValueAsString() {
		return this.value;
	}

}