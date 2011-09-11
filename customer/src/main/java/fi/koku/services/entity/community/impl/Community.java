package fi.koku.services.entity.community.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.CascadeType.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Community entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
public class Community implements Serializable {
  private static final long serialVersionUID = -6318987012290421231L;

  @Id
  @GeneratedValue
  private Long id;

  private String type;
  
  private String name;

  @OneToMany(mappedBy="community", cascade={PERSIST, REMOVE})
  private Collection<CommunityMember> communityMembers = new ArrayList<CommunityMember>();
  
  
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

  public Collection<CommunityMember> getCommunityMembers() {
    return communityMembers;
  }

}
