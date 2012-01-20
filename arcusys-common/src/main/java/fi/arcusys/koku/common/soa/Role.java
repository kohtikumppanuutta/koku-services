package fi.arcusys.koku.common.soa;

/**
 * Entity for representing Role in communications with external components (UI, Intalio Forms etc.) 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 12, 2011
 */
public class Role {
    private String roleUid;
    private String roleName;
    /**
     * @return the roleUid
     */
    public String getRoleUid() {
        return roleUid;
    }
    /**
     * @param roleUid the roleUid to set
     */
    public void setRoleUid(String roleUid) {
        this.roleUid = roleUid;
    }
    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
}
