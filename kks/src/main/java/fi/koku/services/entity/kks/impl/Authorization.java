package fi.koku.services.entity.kks.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import fi.koku.services.entity.userinfo.v1.model.Registry;

/**
 * Authorization services for KKS
 * 
 * @author Ixonos / tuomape
 */
@Local
public interface Authorization {

  /**
   * Creates authorized registries map for user
   * 
   * @param user
   * @return authorized registries map
   */
  Map<String, Registry> getAuthorizedRegistries(String user);

  /**
   * Creates authorized registries list for user
   * 
   * @param user
   * @return authorized registries list
   */
  public List<String> getAuthorizedRegistryNames(String user);

  /**
   * Checks is user master user for given collection.
   * 
   * User is Master user if he/she has created given collection or if user
   * parent for collections customer
   * 
   * @param user
   * @param collection
   * @return true if user is master false if not
   */
  boolean isMasterUser(String user, KksCollection collection);

  /**
   * Checks is user customers parent
   * 
   * @param user
   * @param customer
   * @return true if user is customers parent
   */
  boolean isParent(String user, String customer);

  /**
   * Checks is user authorized to use given registers
   * 
   * @param registers
   * @param user
   * @return true if user is authorized false if not
   */
  public boolean hasAuthorizedRegisters(List<String> registers, String user);

}
