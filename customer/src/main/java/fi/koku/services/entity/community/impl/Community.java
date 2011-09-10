package fi.koku.services.entity.community.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Community entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
public class Community {
  @Id
  @GeneratedValue
  private Long id;

  private String type;
  
  private String name;

  @OneToMany
  @JoinColumn(name="community_id")
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

  public Collection<CommunityMember> getCommunityMembers() {
    return communityMembers;
  }

  public void setCommunityMembers(Collection<CommunityMember> communityMembers) {
    this.communityMembers = communityMembers;
  }
  
}
