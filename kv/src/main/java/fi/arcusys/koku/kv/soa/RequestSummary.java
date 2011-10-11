package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "requestSummary", namespace = "http://soa.kv.koku.arcusys.fi/")
public class RequestSummary extends RequestShortSummary {
	private int respondedAmount;
	private int missedAmout;
	/**
	 * @return the respondedAmount
	 */
	public int getRespondedAmount() {
		return respondedAmount;
	}
	/**
	 * @param respondedAmount the respondedAmount to set
	 */
	public void setRespondedAmount(int respondedAmount) {
		this.respondedAmount = respondedAmount;
	}
	/**
	 * @return the missedAmout
	 */
	public int getMissedAmout() {
		return missedAmout;
	}
	/**
	 * @param missedAmout the missedAmout to set
	 */
	public void setMissedAmout(int missedAmout) {
		this.missedAmout = missedAmout;
	}
}
