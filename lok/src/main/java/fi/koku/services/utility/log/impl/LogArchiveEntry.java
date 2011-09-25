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

/**
 * LogArchiveEntry entity, used for archiving log events (LOK-2).
 * This has exactly the same structure as LogEntry.
 * 
 * @author aspluma
 * @author makinsu
 */

@Entity
@NamedQueries({
/*  @NamedQuery(name = "selectLogByDate", query = "SELECT * FROM log WHERE timestamp < :date"), 
  @NamedQuery(name = "archiveLogByDate", query = "INSERT INTO " +
  	"log_archive (data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message) " +
    "SELECT data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message " +
    "FROM log WHERE timestamp < :date"),
  @NamedQuery(name = "deleteLogByDate", query = "DELETE FROM log WHERE timestamp < :date"),  
*/
  //  @NamedQuery(name = "writeLog", query = "INSERT INTO log (data_item_id, timestamp, user_pic, customer_pic, data_item_type, operation, client_system_id, message) "+
//    "VALUES (:data_item_id, :timestamp, :user_pic, :customer_pic, :data_item_type, :operation, :client_system_id, :message)")
})

@Table(name = "log_archive")
public class LogArchiveEntry {
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


  public Long getLogId() {
    return id;
  }

  public void setLogId(Long logId) {
    this.id = logId;
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

  public LogArchiveEntry(){  
  }
  
  public LogArchiveEntry(String message) {
    this.message = message;
  }
  
  public String getDataItemId() {
    return dataItemId;
  }

  public void setDataItemId(String dataItemId) {
    this.dataItemId = dataItemId;
  }
  
  public String getMessage() {
    message = id +" "+ timestamp +" "+ userPic +" "+ customerPic +" "+ dataItemType +" "+ 
      operation +" "+ clientSystemId;
    
    return message;
  }
 
}
