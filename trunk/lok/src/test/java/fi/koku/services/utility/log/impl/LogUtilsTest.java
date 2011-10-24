package fi.koku.services.utility.log.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import fi.koku.KoKuFaultException;
import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.log.v1.LogEntryType;

public class LogUtilsTest {
  
  LogUtils lu = new LogUtils();
  
 

 
 /* 
  
  @Test
  public void testOkMoveOneDay(){
    
  }
  
  @Test
  public void testWrongMoveOneDay(){
    
  }
  */
  
  @Test
  public void testOkLogInputOk(){
    
    LogEntryType entry = createLogEntryType();
    try{
      assertEquals(true, lu.logInputOk(entry, LogConstants.LOG_NORMAL));
    }catch(Exception e){
      fail();
    }
  }
  
  @Test
  public void testOkAdminLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setDataItemId(null);
    try{
      assertEquals(true, lu.logInputOk(entry, LogConstants.LOG_ADMIN));
    }catch(Exception e){
      fail();
    }
  }
  
  @Test
  public void testWrongTimestampLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setTimestamp(null);
    try{
      boolean result = lu.logInputOk(entry, LogConstants.LOG_NORMAL);
      fail();
    }catch(KoKuFaultException e){
      assertEquals(LogServiceErrorCode.LOG_ERROR_MISSING_TIMESTAMP.getValue(), e.getErrorCode());
    } 
  }
  
  @Test
  public void testWrongUserpicLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setUserPic(null);
    try{
      boolean result = lu.logInputOk(entry, LogConstants.LOG_NORMAL);
      fail();
    }catch(KoKuFaultException e){
      assertEquals(LogServiceErrorCode.LOG_ERROR_MISSING_USERPIC.getValue(), e.getErrorCode());
    } 
  }
  
  @Test
  public void testWrongOperationLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setOperation(null);
    try{
      boolean result = lu.logInputOk(entry, LogConstants.LOG_NORMAL);
      fail();
    }catch(KoKuFaultException e){
      assertEquals(LogServiceErrorCode.LOG_ERROR_MISSING_OPERATION.getValue(), e.getErrorCode());
    } 
  }
  
  @Test
  public void testWrongDataitemidLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setDataItemId(null);
    try{
      boolean result = lu.logInputOk(entry, LogConstants.LOG_NORMAL);
      fail();
    }catch(KoKuFaultException e){
      assertEquals(LogServiceErrorCode.LOG_ERROR_MISSING_DATAITEMID.getValue(), e.getErrorCode());
    } 
  }
  
  @Test
  public void testWrongDataitemtypeLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setDataItemType(null);
    try{
      boolean result = lu.logInputOk(entry, LogConstants.LOG_NORMAL);
      fail();
    }catch(KoKuFaultException e){
      assertEquals(LogServiceErrorCode.LOG_ERROR_MISSING_DATAITEMTYPE.getValue(), e.getErrorCode());
    } 
  }
  
  @Test
  public void testWrongClientsystemidLogInputOk(){
    LogEntryType entry = createLogEntryType();
    entry.setClientSystemId(null);
    try{
      boolean result = lu.logInputOk(entry, LogConstants.LOG_NORMAL);
      fail();
    }catch(KoKuFaultException e){
      assertEquals(LogServiceErrorCode.LOG_ERROR_MISSING_CLIENTSYSTEMID.getValue(), e.getErrorCode());
    } 
  }
 

  /*
   * Creates a valid input for writing to normal log
   */
  private LogEntryType createLogEntryType(){
    LogEntryType entry = new LogEntryType();
    entry.setClientSystemId("kks");
    entry.setCustomerPic("111111-1111");
    entry.setDataItemId("123");
    entry.setDataItemType("kks.vasu");
    entry.setMessage("viesti");
    entry.setOperation("write");
    entry.setTimestamp(CalendarUtil.getXmlDateTime(new Date()));
    entry.setUserPic("222222-2222");
    
    return entry;
  }
 
  
}
