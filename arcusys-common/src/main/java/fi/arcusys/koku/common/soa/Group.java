package fi.arcusys.koku.common.soa;

/**
 * Entity for representing Group in communication with external components (UI, Intalio Forms etc.)
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 19, 2011
 */
public class Group {
    private String groupUid;
    private String groupName;
    /**
     * @return the groupUid
     */
    public String getGroupUid() {
        return groupUid;
    }
    /**
     * @param groupUid the groupUid to set
     */
    public void setGroupUid(String groupUid) {
        this.groupUid = groupUid;
    }
    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
