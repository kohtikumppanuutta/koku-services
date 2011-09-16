package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.kks.v1.AuditInfoType;
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

/**
 * KoKu KKS service implementation class.
 * 
 * @author Ixonos / tuomape
 * 
 */
@Stateless
@WebService(wsdlLocation = "META-INF/wsdl/kksService.wsdl", endpointInterface = "fi.koku.services.entity.kks.v1.KksServicePortType", targetNamespace = "http://services.koku.fi/entity/kks/v1", portName = "kksService-soap11-port", serviceName = "kksService")
@RolesAllowed("koku-role")
public class KksServiceEndpointBean implements KksServicePortType {

  private static final Logger LOG = LoggerFactory.getLogger(KksServiceEndpointBean.class);

  @EJB
  private KksService kksService;

  @Override
  public KksTagsType opGetKksTags(KksTagIdsType kksTagIds, AuditInfoType auditHeader) throws ServiceFault {

    LOG.debug("opGetKksTags");
    List<KksTag> tags = kksService.getTags(kksTagIds.getKksTagId());

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
    List<KksCollectionClass> tmp = kksService.getCollectionClasses();
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
        kksCollectionCriteria.getKksScope());

    for (KksCollection c : tmp) {
      kksCollectionsType.getKksCollection().add(KksConverter.toWsType(c));
    }

    k.setKksCollections(kksCollectionsType);
    return k;
  }

  @Override
  public KksCollectionType opGetKksCollection(String kksCollectionId, AuditInfoType auditHeader) throws ServiceFault {
    LOG.debug("opGetKksCollection");
    return KksConverter.toWsType(kksService.get(kksCollectionId));
  }

  @Override
  public boolean opUpdateKksCollection(KksCollectionType kksCollection, AuditInfoType auditHeader) throws ServiceFault {
    LOG.debug("opUpdateKksCollection");

    // TODO
    kksService.update(KksConverter.fromWsType(kksCollection));
    return true;
  }

  @Override
  public KksCollectionsType opQueryKks(KksQueryCriteriaType kksQueryCriteria, AuditInfoType auditHeader)
      throws ServiceFault {
    LOG.debug("opQueryKks");

    // TODO:
    KksCollectionsType kksCollectionsType = new KksCollectionsType();

    List<String> tagNames = kksQueryCriteria.getKksTagNames().getKksTagName();

    List<KksCollection> tmp = kksService.getCollections(kksQueryCriteria.getPic(), tagNames);

    for (KksCollection c : tmp) {
      kksCollectionsType.getKksCollection().add(KksConverter.toWsType(c));
    }

    return kksCollectionsType;
  }

  @Override
  public KksCollectionType opAddKksCollection(KksCollectionCreationCriteriaType kksCollectionCreationCriteria,
      AuditInfoType auditHeader) throws ServiceFault {
    LOG.debug("opAddKksCollection");

    return KksConverter.toWsType(kksService.add(auditHeader.getUserId(), kksCollectionCreationCriteria.getPic(),
        kksCollectionCreationCriteria.getCollectionTypeId(), kksCollectionCreationCriteria.getCollectionName()));
  }

  @Override
  public boolean opAddEntry(KksEntryCriteriaType kksEntryAddType, AuditInfoType auditHeader) throws ServiceFault {
    // TODO muuta service-apia
    return false;
  }

  @Override
  public boolean opDeleteEntry(KksEntryCriteriaType kksEntryDeleteType, AuditInfoType auditHeader) throws ServiceFault {
    kksService.removeEntry(Long.parseLong(kksEntryDeleteType.getEntryId()));
    return true;
  }

  @Override
  public boolean opUpdateKksCollectionStatus(KksCollectionStateCriteriaType kksCollectionStateCriteriaType,
      AuditInfoType auditHeader) throws ServiceFault {
    kksService.updateCollectionStatus(kksCollectionStateCriteriaType.getCollectionId(),
        kksCollectionStateCriteriaType.getState());
    return true;
  }
}
