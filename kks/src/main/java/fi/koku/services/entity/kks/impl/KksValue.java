package fi.koku.services.entity.kks.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for kks entity value
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@Table(name = "kks_value")
public class KksValue implements Serializable {

  private static final long serialVersionUID = -6283807100612085012L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String value;

  @ManyToOne
  @JoinColumn(name = "entry_id")
  private KksEntry entry;

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

}
