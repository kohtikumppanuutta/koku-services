package fi.arcusys.koku.common.service.datamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity for representing target person in AV.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 22, 2011
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findTargetPersonByUid", query = "SELECT DISTINCT tp FROM TargetPerson tp WHERE tp.targetUser.uid = :uid")
}) 
public class TargetPerson extends AbstractEntity {

    @ManyToOne
    private User targetUser;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> guardians;
    
    /**
     * @return the targetUser
     */
    public User getTargetUser() {
        return targetUser;
    }
    /**
     * @param targetUser the targetUser to set
     */
    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
    /**
     * @return the guardians
     */
    public Set<User> getGuardians() {
        if (guardians == null) {
            this.guardians = new HashSet<User>();
        }
        return guardians;
    }
    /**
     * @param guardians the guardians to set
     */
    public void setGuardians(Set<User> guardians) {
        this.guardians = guardians;
    }
    
    public User getGuardianByUid(final String userUid) {
        for (final User guardian : this.getGuardians()) {
            if (guardian.getUid().equals(userUid)) {
                return guardian;
            }
        }
        return null;
    }
}
