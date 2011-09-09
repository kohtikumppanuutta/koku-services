package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * KKS service implementation class
 * 
 * @author Ixonos / tuomape
 * 
 */
@Stateless
public class KksServiceBean implements KksService {

  @EJB
  KksServiceDAO serviceDAO;

  @Override
  public List<KksTag> getTags() {
    List<KksTag> tmp = new ArrayList<KksTag>();
    KksTag kt = new KksTag();
    kt.setDescription("Tagin kuvaus");
    kt.setId(1);
    kt.setName("Tagin nimi");
    tmp.add(kt);
    return tmp;
  }

  private KksEntryClass createEntryClass(int id, String name, String desc, String type, KksGroup group) {
    KksEntryClass ec = new KksEntryClass();
    ec.setId(1);
    ec.setMultiValue(false);
    ec.setDescription(desc);
    ec.setGroup(group);
    ec.setDataType(type);
    return ec;
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses() {
    KksCollectionClass tmp = createCollectionClass();

    List<KksCollectionClass> cList = new ArrayList<KksCollectionClass>();
    cList.add(tmp);
    return cList;
  }

  private KksCollectionClass createCollectionClass() {
    KksCollectionClass tmp = new KksCollectionClass();
    tmp.setId(1);
    tmp.setDescription("Kuvaus");
    tmp.setName("Nimi");

    KksGroup group = new KksGroup();
    group.setId(1);
    group.setName("Ryhma");
    group.setRegister("Rekisteri");
    group.setSortOrder(1);
    group.setDescription("Ryhmäkuvaus");

    List<KksEntryClass> ecList = new ArrayList<KksEntryClass>();
    ecList.add(createEntryClass(1, "Entry1", "Desc1", "FREE_TEXT", group));
    ecList.add(createEntryClass(2, "Entry2", "Desc2", "FREE_TEXT", group));
    ecList.add(createEntryClass(2, "Entry3", "Desc3", "FREE_TEXT", group));

    KksGroup subgroup = new KksGroup();
    subgroup.setId(1);
    subgroup.setName("Aliryhma");
    subgroup.setRegister("Rekisteri");
    subgroup.setSortOrder(1);
    subgroup.setDescription("Aliryhmäkuvaus");

    List<KksEntryClass> ecList2 = new ArrayList<KksEntryClass>();
    ecList2.add(createEntryClass(1, "SUB Entry1", "Desc1", "FREE_TEXT", group));
    ecList2.add(createEntryClass(2, "SUB Entry2", "Desc2", "FREE_TEXT", group));
    ecList2.add(createEntryClass(2, "SUB Entry3", "Desc3", "FREE_TEXT", group));

    List<KksGroup> tmp1 = new ArrayList<KksGroup>();
    tmp1.add(group);

    List<KksGroup> tmp2 = new ArrayList<KksGroup>();
    tmp2.add(subgroup);

    group.setEntryClasses(ecList);
    subgroup.setEntryClasses(ecList2);
    group.setSubGroups(tmp2);
    tmp.setGroups(tmp1);
    return tmp;
  }

  @Override
  public List<KksCollection> getCollections(String customer, String scope) {

    List<KksCollection> cList = new ArrayList<KksCollection>();
    cList.add(createCollection(customer));
    return cList;
  }

  private KksCollection createCollection(String customer) {
    KksCollection c = new KksCollection();
    c.setCollectionClass(createCollectionClass());
    c.setCreated(new Date());
    c.setCustomer(customer);
    c.setDescription("DESC");
    c.setEntries(new ArrayList<KksEntry>());
    c.setId(1);
    c.setName("NAME");
    c.setNextVersion(null);
    c.setPrevVersion(null);
    c.setStatus("Active");
    c.setVersion("1.0");
    return c;
  }

  @Override
  public boolean update(KksCollection collection) {

    return true;
  }

  @Override
  public KksCollection get(String collectionId) {

    return createCollection(collectionId);
  }

  @Override
  public List<KksCollection> getCollections(String customer, List<String> tagIds) {
    return getCollections(customer, "");
  }

  @Override
  public KksCollection add(String customer, String collectionClassId, String name) {

    return createCollection(customer);
  }

  @Override
  public void injectMetadata(KksCollection collection, String collectionClassId) {

    collection.setCollectionClass(serviceDAO.getCollectionClass(collectionClassId));

  }

}
