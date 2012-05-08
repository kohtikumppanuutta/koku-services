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
package fi.koku.services.utility.log.impl;

import java.util.Date;

/**
 * LogQueryCriteria. Class used to describe log query criteria from UI to
 * service.
 * 
 * @author makinsu
 */
public class LogQueryCriteria {

//TODO: KOKU-1187
  private String logType;
  private String customerPic;
  private String userPic;
  private String dataItemType;
  private Date startTime;
  private Date endTime;

  public LogQueryCriteria(String logType, String customerPic, String dataItemType, String userPic,  Date startTime, Date endTime ) {
    this.logType = logType;
    this.customerPic = customerPic;    
    this.dataItemType = dataItemType;
    this.userPic = userPic;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public String getLogType() {
    return logType;
  }

  public void setLogType(String logType) {
    this.logType = logType;
  }

  public String getCustomerPic() {
    return customerPic;
  }

  public void setCustomerPic(String customerPic) {
    this.customerPic = customerPic;
  }

  public String getUserPic() {
	    return userPic;
	  }

  public void setUserPic(String userPic) {
	    this.userPic = userPic;
	  }
    
  public String getDataItemType() {
    return dataItemType;
  }

  public void setDataItemType(String dataItemType) {
    this.dataItemType = dataItemType;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  

}
