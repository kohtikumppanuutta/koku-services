package fi.koku.services.utility.log.impl;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * AdminLogEntry entity, used for admin log (LOK-4).
 * 
 * @author makinsu
 *
 */
@Entity
@Table(name = "log_admin")
public class AdminLogEntry {
  
  private static final Logger logger = LoggerFactory.getLogger(AdminLogEntry.class);
  
  @Id
  @GeneratedValue
  @Column(name="id", unique=true, nullable=false)
  private String id; // id given by the logging system
  
  @Column(name="timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp; // timestamp
  
  @Column(name="user_pic")
  private String userPic;  // pic of the user
  
  @Column(name="customer_pic")
  private String customerPic; // pic of the child
  
  private String operation;
  
  private String message;
  
  public Date getTimestamp() {
    logger.debug("service get timestamp: "+timestamp);
    return timestamp;
  }
  
  public void setTimestamp(Date timestamp) {
    logger.debug("service set timestamp: "+timestamp);
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
 
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
}
