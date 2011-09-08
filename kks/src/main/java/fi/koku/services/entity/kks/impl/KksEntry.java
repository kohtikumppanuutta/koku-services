package fi.koku.services.entity.kks.impl;

import java.util.Date;
import java.util.List;

/**
 * Entity for KKS entry
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksEntry {

  private int id;
  private Date modified;
  private String creator;
  private String version;
  private KksEntryClass entryClass;
  private List<KksTag> tags;
  private List<KksValue> values;
  private String customer;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public KksEntryClass getEntryClass() {
    return entryClass;
  }

  public void setEntryClass(KksEntryClass entryClass) {
    this.entryClass = entryClass;
  }

  public List<KksTag> getTags() {
    return tags;
  }

  public void setTags(List<KksTag> tags) {
    this.tags = tags;
  }

  public List<KksValue> getValues() {
    return values;
  }

  public void setValues(List<KksValue> values) {
    this.values = values;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

}
