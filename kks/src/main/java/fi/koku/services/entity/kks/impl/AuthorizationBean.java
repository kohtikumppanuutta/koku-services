/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.community.v1.CommunitiesType;
import fi.koku.services.entity.community.v1.CommunityQueryCriteriaType;
import fi.koku.services.entity.community.v1.CommunityType;
import fi.koku.services.entity.community.v1.MemberPicsType;
import fi.koku.services.entity.community.v1.MemberType;
import fi.koku.services.entity.community.v1.MembersType;
import fi.koku.services.entity.community.v1.ServiceFault;
import fi.koku.services.entity.tiva.v1.Consent;
import fi.koku.services.entity.tiva.v1.ConsentSearchCriteria;
import fi.koku.services.entity.tiva.v1.ConsentStatus;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoService;
import fi.koku.services.utility.authorizationinfo.v1.model.OrgUnit;
import fi.koku.services.utility.authorizationinfo.v1.model.Registry;

/**
 * Implements authorization services for KKS
 * 
 * @author Ixonos / tuomape
 */
@Stateless
public class AuthorizationBean implements Authorization {

  public static final String ROLE_GUARDIAN = "guardian";
  public static final String ROLE_DEPENDANT = "dependant";
  public static final String COMMUNITY_TYPE_GUARDIAN_COMMUNITY = "guardian_community";
  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationBean.class);

  @Override
  public Map<String, List<Consent>> getConsentMap(String customer, String user, List<KksCollectionClass> collectionTypes) {
    Map<String, List<Consent>> tmp = new HashMap<String, List<Consent>>();

    try {
      for (KksCollectionClass cc : collectionTypes) {
        if (!tmp.containsKey(cc.getConsentType())) {
          List<Consent> consents = getConsents(customer, user, cc.getConsentType());

          if (consents != null) {
            tmp.put(cc.getConsentType(), consents);
          }
        }
      }
    } catch (Exception e) {
      LOG.error("Cannot get consents", e);
    }
    return tmp;
  }

  @Override
  public Map<String, Registry> getAuthorizedRegistries(String user) {
    AuthorizationInfoService uis = KksServiceContainer.getService().authorization();
    Map<String, Registry> tmp = new HashMap<String, Registry>();
    List<Registry> register = uis.getUsersAuthorizedRegistries(user);

    if (register != null) {
      for (Registry r : register) {
        tmp.put(r.getId(), r);
      }
    }
    return tmp;
  }

  @Override
  public List<String> getAuthorizedRegistryNames(String user) {
    AuthorizationInfoService uis = KksServiceContainer.getService().authorization();
    List<String> tmp = new ArrayList<String>();
    List<Registry> register = uis.getUsersAuthorizedRegistries(user);

    if (register != null) {
      for (Registry r : register) {
        tmp.add(r.getId());
      }
    }
    return tmp;
  }

  @Override
  public boolean hasConsent(String customer, String user, String consentType) {
    return getValidConsent(customer, user, consentType) != null;
  }

  /**
   * Gets consent for user with given consent type
   * 
   * @param customer
   * @param user
   *          which consents are checked
   * @param consentType
   *          of the collection that is requested to see
   * @return valid consent or NULL if no valid consent found
   */
  private Consent getValidConsent(String customer, String user, String consentType) {
    try {
      List<Consent> consents = getConsents(customer, user, consentType);
      
      if (consents.size() > 0) {
        for (Consent c : consents) {
          if (ConsentStatus.VALID.equals(c.getStatus())) {
            return c;
          }
        }
      }
    } catch (Exception e) {
      LOG.error("Failed to check consents", e);
    }

    return null;
  }

  /**
   * Gets consent for user with given consent type
   * 
   * @param customer
   * @param user
   *          which consents are checked
   * @param consentType
   *          of the collection that is requested to see
   * @return valid consent or NULL if no valid consent found
   */
  private List<Consent> getConsents(String customer, String user, String consentType) { 
    
    if ( "".equals(consentType) ) {
      return new ArrayList<Consent>();
    }
    ConsentSearchCriteria csc = new ConsentSearchCriteria(); 
    csc.setTargetPerson(customer); 
    csc.setTemplateNamePrefix(consentType); 
    csc.getGivenTo().addAll(getOrganizationIds(user));
    return KksServiceContainer.getService().tiva().queryConsents(csc);
  }

  /**
   * Gets organization ids for the user
   * 
   * @param user
   * @return organization ids
   */
  private List<String> getOrganizationIds(String user) {
    List<OrgUnit> units = KksServiceContainer.getService().authorization().getUsersOrgUnits("KKS", user);

    List<String> orgNames = new ArrayList<String>();

    if (units != null) {
      for (OrgUnit ou : units) {
        orgNames.add(ou.getId());
      }
    }
    return orgNames;
  }

  @Override
  public boolean isMasterUser(String user, KksCollection collection) {
    return isParent(user, collection.getCustomer());
  }

  @Override
  public boolean isParent(String user, String customer) {
    return getChilds(user).contains(customer);
  }

  @Override
  public boolean hasAuthorizedRegisters(List<String> registers, String user) {
    Map<String, Registry> tmp = getAuthorizedRegistries(user);
    for (String s : registers) {
      if (tmp.containsKey(s)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public KksCollection removeUnauthorizedContent(KksCollection c, KksCollectionClass metadata,
    Map<Integer, String> entryRegisters, String user) {

    boolean consent = hasConsent(c.getCustomer(), user, metadata.getConsentType());

    if (isParent(user, c.getCustomer()) || consent ) {
      if ( consent ) {
        c.setConsentRequested(true);
        c.setUserConsentStatus( "VALID" );
      }
      return c;
    }

    List<KksEntry> allowed = new ArrayList<KksEntry>();
    List<String> registries = getAuthorizedRegistryNames(user);
    for (KksEntry ke : c.getEntries()) {

      String register = entryRegisters.get(ke.getEntryClassId());
      if (registries.contains(register)) {
        allowed.add(ke);
      }
    }

    c.setEntries(allowed);

    if (allowed.size() == 0 && !c.getCreator().equals(user)) {
      // no auth content and user is not the collection creator => no rights for
      // the collection
      return null;
    }
    return c;
  }

  /**
   * Gets user childs
   * 
   * @param user
   *          pic
   * @return user childs
   */
  private List<String> getChilds(String user) {
    List<String> childs = new ArrayList<String>();
    try {
      CommunityQueryCriteriaType communityQueryCriteria = new CommunityQueryCriteriaType();
      communityQueryCriteria.setCommunityType(COMMUNITY_TYPE_GUARDIAN_COMMUNITY);
      MemberPicsType mpt = new MemberPicsType();
      mpt.getMemberPic().add(user);
      communityQueryCriteria.setMemberPics(mpt);
      CommunitiesType communitiesType = null;
      communitiesType = KksServiceContainer.getService().community()
          .opQueryCommunities(communityQueryCriteria, getCommynityAuditInfo(user));

      if (communitiesType != null) {
        List<CommunityType> communities = communitiesType.getCommunity();
        for (CommunityType community : communities) {
          MembersType membersType = community.getMembers();
          List<MemberType> members = membersType.getMember();

          for (MemberType member : members) {
            if (member.getRole().equals(ROLE_DEPENDANT)) {
              childs.add(member.getPic());
            }
          }
        }
      }
    } catch (ServiceFault e) {
      LOG.error("Failed to get user childs", e);
    }
    return childs;
  }

  /**
   * Gets audit info for the community
   * 
   * @param user
   * @return audit info for the community
   */
  public fi.koku.services.entity.community.v1.AuditInfoType getCommynityAuditInfo(String user) {
    fi.koku.services.entity.community.v1.AuditInfoType a = new fi.koku.services.entity.community.v1.AuditInfoType();
    a.setComponent("KKS");
    a.setUserId(user);
    return a;
  }

}
