/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
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
  
  @Column(name="member_pic")
  private String memberPic;
  
  private String role;
  
  @ManyToOne
  @JoinColumn(name="community_id")
  private Community community;

  
  public CommunityMember() {
  }
  
  public CommunityMember(Community c, String memberId, String memberPic, String role) {
    this.community = c;
    this.memberId = memberId;
    this.memberPic = memberPic;
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

  public String getMemberPic() {
    return memberPic;
  }

  public void setMemberPic(String memberPic) {
    this.memberPic = memberPic;
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
  
  
}
