package fi.koku.services.entity.community.impl;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Community entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name = "community")
public class Community implements Serializable {
  private static final long serialVersionUID = -6318987012290421231L;

  @Id
  @GeneratedValue
  private Long id;

  private String type;
  
  private String name;

  @OneToMany(mappedBy="community", cascade={PERSIST, REMOVE})
  private Collection<CommunityMember> members = new ArrayList<CommunityMember>();
  
  
  @Version
  private int version;

  public Community() {
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getVersion() {
    return version;
  }

  public Long getId() {
    return id;
  }

  protected void setId(Long id) {
    this.id = id;
  }

  public Collection<CommunityMember> getMembers() {
    return members;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Community other = (Community) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
