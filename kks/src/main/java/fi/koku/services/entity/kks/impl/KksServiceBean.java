package fi.koku.services.entity.kks.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.kks.v1.AuditInfoType;
import fi.koku.services.entity.kks.v1.KksCollectionClassType;
import fi.koku.services.entity.kks.v1.KksCollectionClassesType;
import fi.koku.services.entity.kks.v1.KksCollectionType;
import fi.koku.services.entity.kks.v1.KksCollectionsCriteriaType;
import fi.koku.services.entity.kks.v1.KksCollectionsType;
import fi.koku.services.entity.kks.v1.KksEntriesType;
import fi.koku.services.entity.kks.v1.KksEntryClassType;
import fi.koku.services.entity.kks.v1.KksEntryClassesType;
import fi.koku.services.entity.kks.v1.KksGroupType;
import fi.koku.services.entity.kks.v1.KksGroupsType;
import fi.koku.services.entity.kks.v1.KksQueryCriteriaType;
import fi.koku.services.entity.kks.v1.KksServicePortType;
import fi.koku.services.entity.kks.v1.KksTagIdsType;
import fi.koku.services.entity.kks.v1.KksTagType;
import fi.koku.services.entity.kks.v1.KksTagsType;
import fi.koku.services.entity.kks.v1.KksType;
import fi.koku.services.entity.kks.v1.ValueSpacesType;

/**
 * KoKu KKS service implementation class.
 * 
 * @author Ixonos / tuomape
 *
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/kksService.wsdl",
    endpointInterface="fi.koku.services.entity.kks.v1.KksServicePortType",
    targetNamespace="http://services.koku.fi/entity/kks/v1",
    portName="kksService-soap11-port",
    serviceName="kksService"
)
@RolesAllowed("koku-role")
public class KksServiceBean implements KksServicePortType {
  
  private static final Logger LOG = LoggerFactory.getLogger(KksServiceBean.class);

  @Override
  public KksTagsType opGetKksTags(KksTagIdsType kksTagIds, AuditInfoType auditHeader) {
    
    LOG.debug("opGetKksTags");    
    KksTagType kt = new KksTagType();
    kt.setDescription("Tagin kuvaus 2" );
    kt.setId("Taging id 2");
    kt.setName("Tagin nimi 2");
    KksTagsType t = new KksTagsType();
    t.getKksTag().add( kt );
    LOG.debug("opGetKksTags");    
    LOG.debug("opGetKksTags");    
    
    return t;
  }

  @Override
  public KksCollectionClassesType opGetKksCollectionClasses(String kksScope, AuditInfoType auditHeader) {
    LOG.debug("opGetKksCollectionClasses");
    
    KksCollectionClassType ct = new KksCollectionClassType();
    KksGroupsType kksGroupsType = new KksGroupsType();
    KksGroupType kksGroupType = new KksGroupType();
    KksEntryClassesType kksEntryClassesType = new KksEntryClassesType();
    KksEntryClassType kksEntryClassType = new KksEntryClassType();
    
    kksEntryClassType.setId("Entry class id");
    kksEntryClassType.setDataType("Entry data type");
    kksEntryClassType.setDescription("Entry class desc");
    kksEntryClassType.setGroupId("Entry class group id");
    kksEntryClassType.setMultiValue(false);
    kksEntryClassType.setName("Entry class name");
    
    kksEntryClassType.setSortOrder(new BigInteger("1"));
    
    ValueSpacesType vs = new ValueSpacesType();
    vs.getValueSpace().add("Empty");
    kksEntryClassType.setValueSpaces(vs);
    
    KksTagIdsType tt = new KksTagIdsType();
    tt.getKksTagId().add("Tag ids");
    kksEntryClassType.setKksTagIds(tt);
    
    kksEntryClassesType.getKksEntryClass().add(kksEntryClassType);
    kksGroupType.setDescription("Group desc");
    kksGroupType.setId("Group id");
    kksGroupType.setName("Group name");
    kksGroupType.setOrder(new BigInteger("3"));
    kksGroupType.setRegister("Group register");
    kksGroupType.setSubGroups(new KksGroupsType() );
    kksGroupType.setKksEntryClasses(kksEntryClassesType);
    
    kksGroupsType.getKksGroup().add(kksGroupType);
      
    ct.setKksGroups(kksGroupsType);
    ct.setId("Collection id");
    ct.setName("Collection name");
    
    KksCollectionClassesType c = new KksCollectionClassesType();
      c.getKksCollectionClass().add(ct);
    return c;
  }

  @Override
  public KksType opGetKks(KksCollectionsCriteriaType kksCollectionCriteria, AuditInfoType auditHeader) {
    LOG.debug("opGetKks");
    KksType k = new KksType();
    KksCollectionsType kksCollectionsType = new KksCollectionsType();
    kksCollectionsType.getKksCollection().add(opGetKksCollection(null,null));
    
    return k;
  }

  @Override
  public KksCollectionType opAddKksCollection(String kksCollectionClassName, AuditInfoType auditHeader) {
    LOG.debug("opAddKksCollection");

    return null;
  }

  @Override
  public KksCollectionType opGetKksCollection(String kksCollectionId, AuditInfoType auditHeader) {
    LOG.debug("opGetKksTags");
    return null;
  }

  @Override
  public boolean opUpdateKksCollection(KksCollectionType kksCollection, AuditInfoType auditHeader) {
    LOG.debug("opGetKksTags");
    return false;
  }

  @Override
  public KksCollectionsType opQueryKks(KksQueryCriteriaType kksQueryCriteria, AuditInfoType auditHeader) {
    LOG.debug("opGetKksTags");
    return null;
  }

}


