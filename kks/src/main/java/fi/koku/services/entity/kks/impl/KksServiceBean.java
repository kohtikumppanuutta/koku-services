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
    ec.setId(id);
    ec.setName(name);
    ec.setMultiValue(false);
    ec.setDescription(desc);
    ec.setGroup(group);
    ec.setDataType(type);
    ec.setValueSpaces("value,value1,value3");
    return ec;
  }

  private KksEntryClass createMultiEntryClass(int id, String name, String desc, String type, KksGroup group) {
    KksEntryClass ec = new KksEntryClass();
    ec.setId(id);
    ec.setName(name);
    ec.setMultiValue(true);
    ec.setDescription(desc);
    ec.setGroup(group);
    ec.setDataType(type);
    ec.setValueSpaces("value,value1,value3");
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

    KksGroup group = createGroup(1, "Ryhmä", "Ryhmäkuvaus");
    KksGroup subgroup = createGroup(2, "Aliryhmä", "Aliryhmäkuvaus");
    KksGroup subgroup1 = createGroup(2, "Aliryhmä 2", "Aliryhmäkuvaus");
    KksGroup group2 = createGroup(1, "Ryhmä 2", "Ryhmäkuvaus");
    KksGroup subgroup2 = createGroup(2, "Aliryhmä 3", "Aliryhmäkuvaus");

    List<KksGroup> tmp1 = new ArrayList<KksGroup>();
    tmp1.add(group);
    tmp1.add(group2);

    List<KksGroup> tmp2 = new ArrayList<KksGroup>();
    tmp2.add(subgroup);
    tmp2.add(subgroup1);

    List<KksGroup> tmp3 = new ArrayList<KksGroup>();
    tmp3.add(subgroup2);

    group2.setSubGroups(tmp3);
    group.setSubGroups(tmp2);
    tmp.setGroups(tmp1);
    return tmp;
  }

  private KksGroup createGroup(int id, String name, String desc) {
    KksGroup subgroup = new KksGroup();
    subgroup.setId(id);
    subgroup.setName(name);
    subgroup.setRegister("Rekisteri");
    subgroup.setSortOrder(id);
    subgroup.setDescription(desc);

    List<KksEntryClass> ecList2 = new ArrayList<KksEntryClass>();
    ecList2.add(createEntryClass(1, "SUB Entry1", "Desc1", "FREE_TEXT", subgroup));
    ecList2.add(createEntryClass(2, "SUB Entry2", "Desc2", "FREE_TEXT", subgroup));
    ecList2.add(createEntryClass(3, "SUB Entry3", "Desc3", "FREE_TEXT", subgroup));
    ecList2.add(createEntryClass(4, "SUB Entry4", "Desc4", "FREE_TEXT", subgroup));
    ecList2.add(createEntryClass(5, "SUB Entry5", "Desc5", "FREE_TEXT", subgroup));
    ecList2.add(createEntryClass(6, "SUB Entry6", "Desc6", "TEXT", subgroup));
    ecList2.add(createEntryClass(7, "SUB Entry7", "Desc7", "MULTI_SELECT", subgroup));
    ecList2.add(createEntryClass(8, "SUB Entry8", "Desc8", "SELECT", subgroup));
    ecList2.add(createMultiEntryClass(9, "SUB Entry9", "Desc9", "FREE_TEXT", subgroup));
    subgroup.setEntryClasses(ecList2);
    return subgroup;
  }

  @Override
  public List<KksCollection> getCollections(String customer, String scope) {

    List<KksCollection> cList = new ArrayList<KksCollection>();
    cList.add(createCollection(1, "Kokoelman nimi", customer));
    cList.add(createCollection(2, "Kokoelman nimi 2", customer));
    cList.add(createCollection(3, "Kokoelman nimi 3", customer));
    return cList;
  }

  private KksCollection createCollection(int id, String name, String customer) {
    KksCollection c = new KksCollection();
    c.setCollectionClass(createCollectionClass());
    c.setCreated(new Date());
    c.setCustomer(customer);
    c.setDescription("DESC");
    c.setEntries(new ArrayList<KksEntry>());
    c.setId(id);
    c.setName(name);
    c.setPrevVersion("1");

    if (id == 2) {
      c.setStatus("LOCKED");
      c.setVersion("1");

    } else {
      c.setStatus("ACTIVE");
      c.setVersion("1");

    }
    List<KksEntry> tmp = new ArrayList<KksEntry>();
    KksEntry e = createEntry(1, 1, "value1");
    KksEntry e1 = createEntry(6, 6, "value6");
    KksEntry e2 = createEntry(7, 7, "value1");
    KksEntry e3 = createEntry(8, 8, "value");
    KksEntry e4 = createMultiEntry(9, 9, "value", "value 2", "Value 3");

    tmp.add(e);
    tmp.add(e1);
    tmp.add(e2);
    tmp.add(e3);
    tmp.add(e4);

    c.setEntries(tmp);
    return c;
  }

  private KksEntry createEntry(int id, int entryId, String value) {
    KksEntry e = new KksEntry();
    e.setCreator("Luoja");
    e.setCustomer("Customer");
    e.setId(id);
    e.setModified(new Date());
    e.setEntryClassId(entryId);

    List<KksValue> values = new ArrayList<KksValue>();
    KksValue v = new KksValue();
    v.setId(1);
    v.setValue(value);
    values.add(v);
    e.setValues(values);
    e.setVersion("1");

    List<Integer> tagIds = new ArrayList<Integer>();
    tagIds.add(1);
    tagIds.add(2);
    e.setTagIds(tagIds);
    return e;
  }

  private KksEntry createMultiEntry(int id, int entryId, String... value) {
    KksEntry e = new KksEntry();
    e.setCreator("Luoja");
    e.setCustomer("Customer");
    e.setId(id);
    e.setModified(new Date());
    e.setEntryClassId(entryId);

    List<KksValue> values = new ArrayList<KksValue>();

    for (String sv : value) {
      KksValue v = new KksValue();
      v.setId(1);
      v.setValue(sv);
      values.add(v);
    }
    e.setValues(values);
    e.setVersion("1");

    List<Integer> tagIds = new ArrayList<Integer>();
    tagIds.add(1);
    tagIds.add(2);
    e.setTagIds(tagIds);
    return e;
  }

  @Override
  public boolean update(KksCollection collection) {

    return true;
  }

  @Override
  public KksCollection get(String collectionId) {

    return createCollection(Integer.parseInt(collectionId), "Kokoelman nimi", "");
  }

  @Override
  public List<KksCollection> getCollections(String customer, List<String> tagNames) {
    return getCollections(customer, "");
  }

  @Override
  public KksCollection add(String customer, String collectionClassId, String name) {

    return createCollection(100, name, customer);
  }

  @Override
  public void injectMetadata(KksCollection collection, String collectionClassId) {

    collection.setCollectionClass(serviceDAO.getCollectionClass(collectionClassId));

  }

}
