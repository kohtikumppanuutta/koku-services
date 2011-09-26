package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Entity(name = "User_")
@NamedQueries({
	@NamedQuery(name = "findUserByUid", query = "SELECT DISTINCT u FROM User_ u WHERE u.uid = :uid"),
    @NamedQuery(name = "findUserByKunpoName", query = "SELECT DISTINCT u FROM User_ u WHERE u.portalName = :portalName")
}) 
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uid"})
})
public class User extends AbstractEntity {
	/**
	 * Natural ID, e.g. in LDAP (uid=user,cn=people,dn=example,dn=com)
	 */
	private String uid;
	
	private String portalName;
	private String ssn;
	
	/**
     * @return the ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * @param ssn the ssn to set
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * @return the citizenPortalName
     */
    public String getPortalName() {
        return portalName;
    }

    /**
     * @param citizenPortalName the citizenPortalName to set
     */
    public void setPortalName(String portalName) {
        this.portalName = portalName;
    }

    /**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
