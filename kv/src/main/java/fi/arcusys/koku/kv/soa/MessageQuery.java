package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 19, 2011
 */
@XmlType(propOrder={"criteria", "orderBy" , "startNum", "maxNum" })
public class MessageQuery {
	public static enum Fields {
		Sender,
		Receiver,
		Subject,
		Content,
		CreatedDate;
	}
	
	private int startNum;
	private int maxNum;
	
	private Criteria criteria;
	private List<OrderBy> orderBy;
	
	public MessageQuery() {
	}
	
	/**
	 * @param startNum
	 * @param maxNum
	 */
	public MessageQuery(final int startNum, final int maxNum) {
		this();
		this.startNum = startNum;
		this.maxNum = maxNum;
	}
	
	/**
	 * @return the startNum
	 */
	public int getStartNum() {
		return startNum;
	}
	/**
	 * @param startNum the startNum to set
	 */
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	/**
	 * @return the maxNum
	 */
	public int getMaxNum() {
		return maxNum;
	}
	/**
	 * @param maxNum the maxNum to set
	 */
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	/**
	 * @return the criteria
	 */
	public Criteria getCriteria() {
		return criteria;
	}
	/**
	 * @param criteria the criteria to set
	 */
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	/**
	 * @return the orderBy
	 */
	public List<OrderBy> getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(List<OrderBy> orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
