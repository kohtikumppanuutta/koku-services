/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogEntry entity, used for logging all events (LOK-3).
 * 
 * @author aspluma
 * @author makinsu
 */

@Entity
@Table(name = "log")
public class LogEntry {
  private static final Logger logger = LoggerFactory.getLogger(LogEntry.class);

  @Id
  @GeneratedValue
  @Column(name="id", unique=true, nullable=false)
  private Long id; // id given by the logging system 

  @Column(name="data_item_id")
  private String dataItemId; // id of the data item that was read/written/etc.
 
  @Column(name="timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp; // timestamp
  
  @Column(name="user_pic")
  private String userPic;  // pic of the user
  
  @Column(name="customer_pic")
  private String customerPic; //pic of the child
  
  @Column(name="data_item_type")
  private String dataItemType;  // kks.vasu, kks.4v, family info, ..
  
  private String operation;  // create, read, write, ..
  
  @Column(name="client_system_id")
  private String clientSystemId; // pyh, kks, kunpo, pegasos..

  private String message;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public Date getTimestamp() {
    return timestamp;
  }

  // format for timestamp: yyyy-mm-dd
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getUserPic() {
    return userPic;
  }

  public void setUserPic(String userPic) {
    this.userPic = userPic;
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

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getClientSystemId() {
    return clientSystemId;
  }

  public void setClientSystemId(String clientSystemId) {
    this.clientSystemId = clientSystemId;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LogEntry(){  
  }
  
  public LogEntry(String message) {
    this.message = message;
  }
  
  public String getDataItemId() {
    return dataItemId;
  }

  public void setDataItemId(String dataItemId) {
    this.dataItemId = dataItemId;
  }
  
  public String getMessage() {
    
    return message;
  }
 
}
