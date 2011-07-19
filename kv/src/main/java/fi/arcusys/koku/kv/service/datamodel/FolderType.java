package fi.arcusys.koku.kv.service.datamodel;

import java.io.Serializable;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
public enum FolderType implements Serializable {
	Inbox, Outbox, Archive_Inbox, Archive_Outbox
}
