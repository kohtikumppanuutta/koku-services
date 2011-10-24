package fi.arcusys.koku.common.soa;

import java.util.List;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 19, 2011
 */
public class Child extends User {
    private List<User> parents;
    
    public Child() {
    }
    
    public Child(User user) {
        super(user.getUid(), user.getDisplayName());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        setEmail(user.getEmail());
        setPhoneNumber(user.getPhoneNumber());
    }
    
    /**
     * @return the parentUids
     */
    public List<User> getParents() {
        return parents;
    }
    /**
     * @param parentUids the parentUids to set
     */
    public void setParents(List<User> parents) {
        this.parents = parents;
    }
}
