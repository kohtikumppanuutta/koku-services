package fi.arcusys.koku.kv.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public abstract class AnswerValue<T> {
	private T value;
	
	public T getValue() {
		return this.value;
	}
	
	public void setValue(final T value) {
		this.value = value;
	}
}
