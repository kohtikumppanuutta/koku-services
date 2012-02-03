package fi.koku.services.utility.userinfo.impl.model;

import fi.koku.services.utility.user.v1.UserType;

/**
 * Extension for UserType to contain also dn.
 * 
 * @author hanhian
 */
public class LDAPUser extends UserType{
  
  private String dn;

  public String getDn() {
    return dn;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }   
}