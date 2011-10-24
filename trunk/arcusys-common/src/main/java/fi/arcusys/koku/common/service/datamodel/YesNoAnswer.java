package fi.arcusys.koku.common.service.datamodel;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
//@Entity
public class YesNoAnswer  {
	private Boolean value;
	
	/**
	 * @return
	 */
//	@Override
	public Boolean getValue() {
		return this.value;
	}
	
	public void setValue(final Boolean value) {
		this.value = value;
	}

	/**
	 * @return
	 */
//	@Override
	public String getValueAsString() {
		return Boolean.TRUE.equals(this.value) ? "Kyllä" : "Ei";
	}

}
