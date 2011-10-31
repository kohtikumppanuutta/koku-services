/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

import java.util.Date;

public class LogQueryCriteria {
  private String logType;

  private String customerPic;
  private String dataItemType;
  private Date startTime;
  private Date endTime;
  
  public LogQueryCriteria(String logType, String customerPic, String dataItemType,
      Date startTime, Date endTime){
    this.logType = logType;
    this.customerPic = customerPic;
    this.dataItemType = dataItemType;
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
