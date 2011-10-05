package fi.koku.services.entity.kks.impl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity for kks entity value
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = KksValue.NAMED_QUERY_DELETE_VALUES_BY_IDS, query = "DELETE FROM KksValue k WHERE k.id IN (:ids)") })
@Table(name = "kks_value")
public class KksValue implements Serializable {

  public static final String NAMED_QUERY_DELETE_VALUES_BY_IDS = "deleteAllValuesByIds";

  private static final long serialVersionUID = -6283807100612085012L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String value;

  @ManyToOne
  @JoinColumn(name = "entry_id")
  private KksEntry entry;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date modified;

  @Column
  private String modifier;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public KksEntry getEntry() {
    return entry;
  }

  public void setEntry(KksEntry entry) {
    this.entry = entry;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public String getModifier() {
    return modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("id: ").append(getId()).append(" value:").append(getValue());
    return sb.toString();
  }
}
