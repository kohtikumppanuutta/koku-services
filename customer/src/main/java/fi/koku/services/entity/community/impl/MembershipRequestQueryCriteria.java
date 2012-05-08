/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.entity.community.impl;

/**
 * Membership request query criteria.
 * 
 * @author aspluma
 */
public class MembershipRequestQueryCriteria {
  
  private String requesterPic;
  
  private String approverPic;

  public MembershipRequestQueryCriteria() {
  }

  public MembershipRequestQueryCriteria(String requesterPic, String approverPic) {
    this.requesterPic = requesterPic;
    this.approverPic = approverPic;
  }

  public String getRequesterPic() {
    return requesterPic;
  }

  public void setRequesterPic(String requesterPic) {
    this.requesterPic = requesterPic;
  }

  public String getApproverPic() {
    return approverPic;
  }

  public void setApproverPic(String approverPic) {
    this.approverPic = approverPic;
  }
}