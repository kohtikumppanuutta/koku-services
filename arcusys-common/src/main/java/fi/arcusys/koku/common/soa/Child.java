package fi.arcusys.koku.common.soa;

import java.util.List;

/**
 * Entity for representing child in communications with external components (UI, Intalio Forms etc.)
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 19, 2011
 */
public class Child extends UserInfo {
    private List<UserInfo> parents;
    
    public Child() {
    }
    
    public Child(UserInfo user) {
        super(user.getUid(), user.getDisplayName());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        setEmail(user.getEmail());
        setPhoneNumber(user.getPhoneNumber());
    }
    
    /**
     * @return the parentUids
     */
    public List<UserInfo> getParents() {
        return parents;
    }
    /**
     * @param parentUids the parentUids to set
     */
    public void setParents(List<UserInfo> parents) {
        this.parents = parents;
    }
}
