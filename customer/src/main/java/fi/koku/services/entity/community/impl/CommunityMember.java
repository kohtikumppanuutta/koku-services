package fi.koku.services.entity.community.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Community member entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name="community_member")
public class CommunityMember implements Serializable {
  private static final long serialVersionUID = -4604215142623220332L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(name="member_id")
  private String memberId;
  
  private String role;
  
  @ManyToOne
  @JoinColumn(name="community_id")
  private Community community;

  
  public CommunityMember() {
  }
  
  public CommunityMember(Community c, String memberId, String role) {
    this.community = c;
    this.memberId = memberId;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
  
  public Community getCommunity() {
    return community;
  }
  
  public void setCommunity(Community c) {
    this.community = c;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
    result = prime * result + ((role == null) ? 0 : role.hashCode());
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
    CommunityMember other = (CommunityMember) obj;
    if (memberId == null) {
      if (other.memberId != null)
        return false;
    } else if (!memberId.equals(other.memberId))
      return false;
    if (role == null) {
      if (other.role != null)
        return false;
    } else if (!role.equals(other.role))
      return false;
    return true;
  }
  
  
}
