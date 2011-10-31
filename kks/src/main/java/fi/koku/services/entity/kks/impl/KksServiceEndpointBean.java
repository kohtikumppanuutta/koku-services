/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.entity.kks.v1.AuditInfoType;
import fi.koku.services.entity.kks.v1.IdType;
import fi.koku.services.entity.kks.v1.KksCollectionClassesType;
import fi.koku.services.entity.kks.v1.KksCollectionCreationCriteriaType;
import fi.koku.services.entity.kks.v1.KksCollectionStateCriteriaType;
import fi.koku.services.entity.kks.v1.KksCollectionType;
import fi.koku.services.entity.kks.v1.KksCollectionsCriteriaType;
import fi.koku.services.entity.kks.v1.KksCollectionsType;
import fi.koku.services.entity.kks.v1.KksEntryCriteriaType;
import fi.koku.services.entity.kks.v1.KksQueryCriteriaType;
import fi.koku.services.entity.kks.v1.KksServicePortType;
import fi.koku.services.entity.kks.v1.KksTagIdsType;
import fi.koku.services.entity.kks.v1.KksTagsType;
import fi.koku.services.entity.kks.v1.KksType;
import fi.koku.services.entity.kks.v1.ServiceFault;
import fi.koku.services.entity.kks.v1.VoidType;

/**
 * KoKu KKS service implementation class.
 * 
 * @author Ixonos / tuomape
 * 
 */
@Stateless
@Interceptors({ KksServiceFaultInterceptor.class })
@WebService(wsdlLocation = "META-INF/wsdl/kksService.wsdl", endpointInterface = "fi.koku.services.entity.kks.v1.KksServicePortType", targetNamespace = "http://services.koku.fi/entity/kks/v1", portName = "kksService-soap11-port", serviceName = "kksService")
@RolesAllowed("koku-role")
public class KksServiceEndpointBean implements KksServicePortType {

  private static final Logger LOG = LoggerFactory.getLogger(KksServiceEndpointBean.class);

  @EJB
  private KksService kksService;

  @Override
  public KksTagsType opGetKksTags(KksTagIdsType kksTagIds, AuditInfoType auditHeader) throws ServiceFault {

    LOG.debug("opGetKksTags");
    List<KksTag> tags = kksService.getTags(kksTagIds.getKksTagId(), auditHeader);

    KksTagsType kksTagsType = new KksTagsType();
    for (KksTag t : tags) {
      kksTagsType.getKksTag().add(KksConverter.toWsType(t));
    }

    return kksTagsType;
  }

  @Override
  public KksCollectionClassesType opGetKksCollectionClasses(String kksScope, AuditInfoType auditHeader)
      throws ServiceFault {
    LOG.debug("opGetKksCollectionClasses");
    List<KksCollectionClass> tmp = kksService.getCollectionClasses(auditHeader);
    KksCollectionClassesType c = new KksCollectionClassesType();

    for (KksCollectionClass kc : tmp) {
      c.getKksCollectionClass().add(KksConverter.toWsType(kc));
    }
    return c;
  }

  @Override
  public KksType opGetKks(KksCollectionsCriteriaType kksCollectionCriteria, AuditInfoType auditHeader)
      throws ServiceFault {
    LOG.debug("opGetKks");
    KksType k = new KksType();
    KksCollectionsType kksCollectionsType = new KksCollectionsType();

    List<KksCollection> tmp = kksService.getCollections(kksCollectionCriteria.getPic(),
        kksCollectionCriteria.getKksScope(), auditHeader);

    for (KksCollection c : tmp) {
      kksCollectionsType.getKksCollection().add(KksConverter.toWsType(c));
    }

    k.setKksCollections(kksCollectionsType);
    return k;
  }

  @Override
  public KksCollectionType opGetKksCollection(String kksCollectionId, AuditInfoType auditHeader) throws ServiceFault {
    LOG.debug("opGetKksCollection");
    return KksConverter.toWsType(kksService.get(kksCollectionId, auditHeader));
  }

  @Override
  public VoidType opUpdateKksCollection(KksCollectionType kksCollection, AuditInfoType auditHeader) throws ServiceFault {
    LOG.debug("opUpdateKksCollection");
    kksService.update(KksConverter.fromWsType(kksCollection), auditHeader);
    return new VoidType();
  }

  @Override
  public KksCollectionsType opQueryKks(KksQueryCriteriaType kksQueryCriteria, AuditInfoType auditHeader)
      throws ServiceFault {
    LOG.debug("opQueryKks");
    KksCollectionsType kksCollectionsType = new KksCollectionsType();
    List<String> tagNames = kksQueryCriteria.getKksTagNames().getKksTagName();
    KksQueryCriteria qc = new KksQueryCriteria(kksQueryCriteria.getPic(), tagNames);
    List<KksCollection> tmp = kksService.search(qc, auditHeader);

    for (KksCollection c : tmp) {
      kksCollectionsType.getKksCollection().add(KksConverter.toWsType(c));
    }

    return kksCollectionsType;
  }

  @Override
  public IdType opAddKksCollection(KksCollectionCreationCriteriaType criteria, AuditInfoType auditHeader)
      throws ServiceFault {
    LOG.debug("opAddKksCollection");

    KksCollectionCreation c = new KksCollectionCreation(auditHeader.getUserId(), criteria.getPic(),
        criteria.getCollectionTypeId(), criteria.getCollectionName(), false);

    IdType id = new IdType();

    if (criteria.getKksScope().equals("new")) {
      String colId = kksService.add(c, auditHeader).toString();
      id.setId(colId);
    } else {

      if (criteria.getKksScope().equals("empty_version")) {
        c.setEmpty(true);
      }
      String colId = kksService.version(c, auditHeader).toString();
      id.setId(colId);
    }
    return id;
  }

  @Override
  public IdType opAddEntry(KksEntryCriteriaType kksEntryAddType, AuditInfoType auditHeader) throws ServiceFault {

    KksValue value = new KksValue();
    value.setId(kksEntryAddType.getValue().getId() == null || "".equals(kksEntryAddType.getValue().getId()) ? null
        : Long.parseLong(kksEntryAddType.getValue().getId()));
    value.setValue(kksEntryAddType.getValue().getValue());
    KksEntryCreation c = new KksEntryCreation(kksEntryAddType.getEntryId(), kksEntryAddType.getPic(),
        kksEntryAddType.getCreator(), CalendarUtil.getDate(kksEntryAddType.getModified()), kksEntryAddType.getCollectionId(),
        kksEntryAddType.getEntryClassId(), value);

    IdType id = new IdType();
    id.setId(kksService.addEntry(c, auditHeader).toString());
    return id;
  }

  @Override
  public VoidType opDeleteEntry(KksEntryCriteriaType kksEntryDeleteType, AuditInfoType auditHeader) throws ServiceFault {

    kksService.removeEntryValue(kksEntryDeleteType.getCollectionId(), kksEntryDeleteType.getPic(),
        Long.parseLong(kksEntryDeleteType.getValue().getId()), auditHeader);
    return new VoidType();
  }

  @Override
  public VoidType opUpdateKksCollectionStatus(KksCollectionStateCriteriaType kksCollectionStateCriteriaType,
      AuditInfoType auditHeader) throws ServiceFault {
    kksService.updateCollectionStatus(kksCollectionStateCriteriaType.getCustomer(),
        kksCollectionStateCriteriaType.getCollectionId(), kksCollectionStateCriteriaType.getState(), auditHeader);
    return new VoidType();
  }
}
