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
    @NamedQuery(name = "findUserByEmployeePortalName", query = "SELECT DISTINCT u FROM User_ u WHERE u.employeePortalName = :portalName"),
    @NamedQuery(name = "findUserByCitizenPortalName", query = "SELECT DISTINCT u FROM User_ u WHERE u.citizenPortalName = :portalName")
}) 
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uid"}),
    @UniqueConstraint(columnNames = {"citizenPortalName"}),
    @UniqueConstraint(columnNames = {"employeePortalName"})
})
public class User extends AbstractEntity {
	/**
	 * Natural ID, e.g. in LDAP (uid=user,cn=people,dn=example,dn=com)
	 */
	private String uid;
	
	private String employeePortalName;
    private String citizenPortalName;
	
    /**
     * @return the employeePortalName
     */
    public String getEmployeePortalName() {
        return employeePortalName;
    }

    /**
     * @param employeePortalName the employeePortalName to set
     */
    public void setEmployeePortalName(String employeePortalName) {
        this.employeePortalName = employeePortalName;
    }

    /**
     * @return the citizenPortalName
     */
    public String getCitizenPortalName() {
        return citizenPortalName;
    }

    /**
     * @param citizenPortalName the citizenPortalName to set
     */
    public void setCitizenPortalName(String citizenPortalName) {
        this.citizenPortalName = citizenPortalName;
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
