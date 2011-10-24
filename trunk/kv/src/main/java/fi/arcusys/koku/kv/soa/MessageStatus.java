package fi.arcusys.koku.kv.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 9, 2011
 */
public enum MessageStatus {
	Read(true),
	Unread(false);
	
	private final boolean isRead;
	
	private MessageStatus(final boolean isRead) {
		this.isRead = isRead;
	}
	
	public boolean isRead() {
		return this.isRead;
	}
	
	public static MessageStatus getStatus(final boolean isRead) {
		return isRead ? Read : Unread;
	}
}
