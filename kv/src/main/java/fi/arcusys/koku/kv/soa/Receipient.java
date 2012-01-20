package fi.arcusys.koku.kv.soa;

/**
 * Data transfer object for communication with UI/Intalio process. Wrapper object for storing single recipient.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 15, 2011
 */
public class Receipient {
	private String userUid;

	/**
	 * @return the userUid
	 */
	public String getUserUid() {
		return userUid;
	}

	/**
	 * @param userUid the userUid to set
	 */
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
}
