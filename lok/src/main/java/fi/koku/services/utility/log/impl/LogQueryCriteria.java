package fi.koku.services.utility.log.impl;

import java.util.Date;

public class LogQueryCriteria {
  private String logType;

  private String customerPic;
  private String dataItemType;
  private Date startTime;
  private Date endDate;
  
  public LogQueryCriteria(String logType, String customerPic, String dataItemType,
      Date startTime, Date endDate){
    this.logType = logType;
    this.customerPic = customerPic;
    this.dataItemType = dataItemType;
    this.startTime = startTime;
    this.endDate = endDate;
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

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}