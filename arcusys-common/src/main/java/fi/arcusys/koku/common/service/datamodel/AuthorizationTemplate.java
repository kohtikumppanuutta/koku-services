package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity for representing authorization template in TIVA-Valtakirja functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAuthorizationTemplatesByPrefix", query = "SELECT tmp FROM AuthorizationTemplate tmp WHERE tmp.name LIKE :prefix  ORDER BY tmp.id DESC")
})
public class AuthorizationTemplate extends AbstractEntity {
    private String name;
    private String description;
    private boolean validTillMandatory;
    private boolean consentsOnly;
    private boolean toSecondGuardianOnly;

    @Enumerated(EnumType.STRING)
    private AuthorizationArea area;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the validTillMandatory
     */
    public boolean isValidTillMandatory() {
        return validTillMandatory;
    }

    /**
     * @param validTillMandatory the validTillMandatory to set
     */
    public void setValidTillMandatory(boolean validTillMandatory) {
        this.validTillMandatory = validTillMandatory;
    }

    /**
     * @return the consentsOnly
     */
    public boolean isConsentsOnly() {
        return consentsOnly;
    }

    /**
     * @param consentsOnly the consentsOnly to set
     */
    public void setConsentsOnly(boolean consentsOnly) {
        this.consentsOnly = consentsOnly;
    }

    /**
     * @return the toSecondGuardianOnly
     */
    public boolean isToSecondGuardianOnly() {
        return toSecondGuardianOnly;
    }

    /**
     * @param toSecondGuardianOnly the toSecondGuardianOnly to set
     */
    public void setToSecondGuardianOnly(boolean toSecondGuardianOnly) {
        this.toSecondGuardianOnly = toSecondGuardianOnly;
    }

    /**
     * @return the area
     */
    public AuthorizationArea getAuthorizationArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setAuthorizationArea(AuthorizationArea area) {
        this.area = area;
    }
    
    
}
