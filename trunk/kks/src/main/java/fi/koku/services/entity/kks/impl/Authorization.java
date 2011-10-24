package fi.koku.services.entity.kks.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import fi.koku.services.entity.tiva.v1.Consent;
import fi.koku.services.utility.authorizationinfo.v1.model.Registry;

/**
 * Authorization services for KKS
 * 
 * @author Ixonos / tuomape
 */
@Local
public interface Authorization {

  /**
   * Gets consent map for given customer, user and collection types
   * 
   * @param customer
   * @param user
   * @param collectionTypes
   * @return consent map containing consenst for each collection consent type
   */
  Map<String, List<Consent>> getConsentMap(String customer, String user, List<KksCollectionClass> collectionTypes);

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
  List<String> getAuthorizedRegistryNames(String user);

  /**
   * Checks does user has consent for given consent type
   * 
   * @param customer
   * @param user
   *          which consents are checked
   * @param consentType
   *          of the collection that is requested to see
   * @return true if user has consent false if not
   */
  boolean hasConsent(String customer, String user, String consentType);

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

  /**
   * Removes unauthorized content from given collection
   * 
   * @param collection
   * @param metadata
   * @param entryRegisters
   * @param user
   * @return authorized collection
   */
  public KksCollection removeUnauthorizedContent(KksCollection c, KksCollectionClass metadata,
      Map<Integer, String> entryRegisters, String user);

}
