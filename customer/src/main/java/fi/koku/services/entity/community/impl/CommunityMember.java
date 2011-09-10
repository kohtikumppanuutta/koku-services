package fi.koku.services.entity.community.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Community member entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name="community_member")
public class CommunityMember {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name="member_id")
  private String memberId;
  
  private String role;
  
  public CommunityMember() {
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
  
  
}
