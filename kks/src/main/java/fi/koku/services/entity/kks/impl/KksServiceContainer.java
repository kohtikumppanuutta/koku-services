/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import fi.koku.services.entity.community.v1.CommunityServiceFactory;
import fi.koku.services.entity.community.v1.CommunityServicePortType;
import fi.koku.services.entity.tiva.v1.KokuTivaToKksService;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoService;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoServiceFactory;
import fi.koku.services.utility.log.v1.LogServiceFactory;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * Container for outside services used in KKS
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksServiceContainer {

  public static final String CUSTOMER_ENDPOINT = KoKuPropertiesUtil.get("community.service.endpointaddress");
  public static final String COMMUNITY_SERVICE_USER_ID = KoKuPropertiesUtil.get("kks.community.service.user.id");
  public static final String COMMUNITY_SERVICE_PASSWORD = KoKuPropertiesUtil.get("kks.community.service.password");
  public static final String LOG_ENDPOINT = KoKuPropertiesUtil.get("lok.service.endpointaddress");
  public static final String LOG_SERVICE_USER_ID = KoKuPropertiesUtil.get("kks.lok.service.user.id");
  public static final String LOG_SERVICE_PASSWORD = KoKuPropertiesUtil.get("kks.lok.service.password");
  public static final String AUTH_ENDPOINT = KoKuPropertiesUtil.get("authentication.service.endpointaddress");
  public static final String AUTH_SERVICE_USER_ID = KoKuPropertiesUtil
      .get("kks.authentication.community.service.user.id");
  public static final String AUTH_SERVICE_PASSWORD = KoKuPropertiesUtil
      .get("kks.authentication.community.service.password");

  private KokuTivaToKksService tivaService;
  private CommunityServicePortType communityService;
  private LogServicePortType logService;
  private AuthorizationInfoService authorizationService;

  private static KksServiceContainer serviceContainer;

  private KksServiceContainer() {
    tivaService = createTivaService();
    communityService = createCommunityService();
    logService = createLogService();
    authorizationService = getAuthorizationService();
  }

  public static synchronized KksServiceContainer getService() {
    if (serviceContainer == null) {
      serviceContainer = new KksServiceContainer();
    }
    return serviceContainer;
  }

  private CommunityServicePortType createCommunityService() {
    CommunityServiceFactory csf = new CommunityServiceFactory(COMMUNITY_SERVICE_USER_ID, COMMUNITY_SERVICE_PASSWORD,
        CUSTOMER_ENDPOINT);
    return csf.getCommunityService();
  }

  private KokuTivaToKksService createTivaService() {
    try {
      ConsentServiceFactory csf = new ConsentServiceFactory();
      return csf.getService();
    } catch (Exception e) {
    }
    return null;
  }

  private LogServicePortType createLogService() {
    LogServiceFactory log = new LogServiceFactory(LOG_SERVICE_USER_ID, LOG_SERVICE_PASSWORD, LOG_ENDPOINT);
    return log.getLogService();
  }

  public KokuTivaToKksService tiva() {
    return tivaService;
  }

  public CommunityServicePortType community() {
    return communityService;
  }

  public LogServicePortType log() {
    return logService;
  }

  public AuthorizationInfoService authorization() {
    return authorizationService;
  }

  private AuthorizationInfoService getAuthorizationService() {
    AuthorizationInfoServiceFactory f = new AuthorizationInfoServiceFactory(AUTH_SERVICE_USER_ID,
        AUTH_SERVICE_PASSWORD, AUTH_ENDPOINT);
    AuthorizationInfoService s = f.getAuthorizationInfoService();
    return s;
  }
}
