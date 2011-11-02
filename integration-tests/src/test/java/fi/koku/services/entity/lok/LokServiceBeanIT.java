package fi.koku.services.entity.lok;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.test.util.TestDbUtils;
import fi.koku.services.test.util.TestPropertiesUtil;
import fi.koku.services.utility.log.v1.ArchivalResultsType;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;
import fi.koku.services.utility.log.v1.LogServiceFactory;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.services.utility.log.v1.ServiceFault;

/**
 * LokServiceBeanIT.
 * 
 * @author makinsu
 *
 */
public class LokServiceBeanIT {

  JdbcTemplate jdbcTemplate = TestDbUtils.getJdbcTemplateInstance();
  
  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  @Before
  public void beforeTest() {
    TestDbUtils.deleteFromAllTables(jdbcTemplate);
  }
  
  /**
   * Tests the opLog operation.
   * 
   * @throws ServiceFault
   */
  @Test
  public void testLog() throws ServiceFault {
    LogServicePortType port = getLogService();
    
    AuditInfoType audit = getAudit("log");
    
    LogEntriesType logEntries = new LogEntriesType();
    
    Date date = new Date();
    
    LogEntryType logEntryType = new LogEntryType();
    logEntryType.setClientSystemId("kks");
    logEntryType.setCustomerPic("123456-123A");
    logEntryType.setDataItemId("it-id");
    logEntryType.setDataItemType("it-type");
    logEntryType.setMessage("Integration test log");
    logEntryType.setOperation("Test");
    logEntryType.setTimestamp(CalendarUtil.getXmlDateTime(date));
    logEntryType.setUserPic("123456-123A");
    logEntries.getLogEntry().add(logEntryType);
    
    logEntryType = new LogEntryType();
    logEntryType.setClientSystemId("kks");
    logEntryType.setCustomerPic("123456-123A");
    logEntryType.setDataItemId("it-id");
    logEntryType.setDataItemType("it-type");
    logEntryType.setMessage("Integration test log 2");
    logEntryType.setOperation("Test");
    logEntryType.setTimestamp(CalendarUtil.getXmlDateTime(new Date())); 
    logEntryType.setUserPic("123456-123A");
    logEntries.getLogEntry().add(logEntryType);
    
    port.opLog(logEntries, audit);
    
    // Verify operation result from DB
    assertThat(jdbcTemplate.queryForInt("SELECT COUNT(*) FROM log WHERE data_item_id = 'it-id'"), is(2));
  
    // check the first result
    SqlRowSet res = jdbcTemplate.queryForRowSet("SELECT * FROM log WHERE data_item_id = 'it-id'");
    res.next();
    assertThat("clientSystemId", res.getString("CLIENT_SYSTEM_ID"), is("kks"));
    assertThat("customerPic", res.getString("CUSTOMER_PIC"), is("123456-123A"));
    assertThat("dataItemType", res.getString("DATA_ITEM_TYPE"), is("it-type"));
    assertThat("message", res.getString("MESSAGE"), is("Integration test log"));
    assertThat("operation", res.getString("OPERATION"), is("Test"));
   
    String dateStr = df.format(CalendarUtil.getXmlDateTime(date).toGregorianCalendar().getTime()) +".0";
    assertThat("timestamp", res.getString("TIMESTAMP"), is(dateStr));
    assertThat("userPic", res.getString("USER_PIC"), is("123456-123A"));
     
  }

  /**
   * Tests that all three rows are returned from the log
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testQueryLog() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
   

    // Insert three rows in log table
    TestDbUtils.writeToLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
    criteriatype.setCustomerPic("030303A022T");
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2009, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2011, 11, 11, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setDataItemType("kks.vasu");
    criteriatype.setLogType("loki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, getAudit("log"));
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 3);
    
    // test that the query resulted in writing a row in admin log
/*    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int day = Calendar.getInstance().get(Calendar.DATE);
    
    start = DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month, day, 0, 0, 0, 0, 0);
    end = DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month, day, 0, 0, 0, 0, 0);
    LogQueryCriteriaType admincriteriatype = new LogQueryCriteriaType();
    
    admincriteriatype.setStartTime(start);
    admincriteriatype.setEndTime(end);
    admincriteriatype.setLogType("seurantaloki");
    
    LogEntriesType adminentriestype = port.opQueryLog(criteriatype, getAudit("adminlog"));
    
    assertEquals(adminentriestype.getLogEntry().get(0).getUserPic(), "101010-1010");
    assertEquals(adminentriestype.getLogEntry().get(0).getOperation(), "view log");
    assertEquals(adminentriestype.getLogEntry().get(0).getCustomerPic(), "030303A022T");
    assertEquals(adminentriestype.getLogEntry().get(0).getMessage(), "030303A022T kks.vasu 2009-01-01 - 2011-11-11");
    assertEquals(adminentriestype.getLogEntry().get(0).getDataItemType(), "kks.vasu");
 */
  }
  
  /**
   * Tests that the query only returns one row
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testQueryLogOneRow() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("log"); 

    // Insert three rows in log table
    TestDbUtils.writeToLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
    criteriatype.setCustomerPic("030303A022T");
  
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2009, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 01, 11, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setDataItemType("kks.vasu");
    criteriatype.setLogType("loki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 1);
  }

  /**
   * Tests that the entry returned from the log is correct.
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testQueryLogMatchEntry() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("log"); 

    // Insert three rows in log table
    TestDbUtils.writeToLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
    criteriatype.setCustomerPic("030303A022T");
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2011, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2011, 11, 11, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setDataItemType("kks.vasu");
    criteriatype.setLogType("loki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 1);
    
    assertEquals(entriestype.getLogEntry().get(0).getClientSystemId(), "kks"); 
    assertEquals(entriestype.getLogEntry().get(0).getCustomerPic(), "030303A022T");
    assertEquals(entriestype.getLogEntry().get(0).getDataItemId(), "256");
    assertEquals(entriestype.getLogEntry().get(0).getDataItemType(), "kks.vasu");
    assertEquals(entriestype.getLogEntry().get(0).getMessage(), "vasu");
    assertEquals(entriestype.getLogEntry().get(0).getOperation(), "view");
    XMLGregorianCalendar time = DatatypeFactory.newInstance().newXMLGregorianCalendar(2011, 1, 5, 15, 0, 34, 0, 120);
    assertEquals(entriestype.getLogEntry().get(0).getTimestamp(), time);
    assertEquals(entriestype.getLogEntry().get(0).getUserPic(), "292929-2929");
  }

  /**
   * Tests a query that does not return anything
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testQueryLogNoResults() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("log"); 

    // Insert three rows in log table
    TestDbUtils.writeToLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
    criteriatype.setCustomerPic("030303A022T");

    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2009, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 01, 11, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setDataItemType("pyh");
    criteriatype.setLogType("loki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 0);
  }
  
  /**
   * Tests a query with a wrong kind of criteria, should throw an exception
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testQueryLogFail() throws DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("log"); 

    // Insert three rows in log table
    TestDbUtils.writeToLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
    criteriatype.setCustomerPic(null);
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2009, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 01, 11, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setDataItemType("pyh");
    criteriatype.setLogType("loki");

    try{
      LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
   
    // Verify operation result from DB
      assertEquals(entriestype.getLogEntry().size(), 0);
      fail();
    }catch(ServiceFault sf){
      assertEquals(sf.getFaultInfo().getMessage(), "Invalid query criteria");
    }
  }
  
  /**
   * Tests that all three rows are returned from the admin log 
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testAdminQueryLog() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("adminlog"); 

    // Insert three rows in log table
    TestDbUtils.writeToAdminLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2009, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2011, 11, 11, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setLogType("seurantaloki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 3);
  }
  
  /**
   * Tests that the query returns one result from admin log 
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testAdminQueryLogOneResult() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("adminlog"); 

    // Insert three rows in log table
    TestDbUtils.writeToAdminLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 12, 23, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setLogType("seurantaloki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 1);
  }
  
  /**
   * Tests that the entry returned from the admin log is correct.
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testAdminQueryLogMatchEntry() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("adminlog"); 

    // Insert three rows in log table
    TestDbUtils.writeToAdminLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 01, 01, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 12, 23, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setLogType("seurantaloki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().get(0).getCustomerPic(), "030303A022T");
    assertEquals(entriestype.getLogEntry().get(0).getUserPic(), "101010-1010");
    assertEquals(entriestype.getLogEntry().get(0).getOperation(), "view log");
    assertEquals(entriestype.getLogEntry().get(0).getMessage(), "030303A022T kks.vasu 2010-01-06 - 2010-09-30");
  }
  
  /**
   * Tests that the query does not return anything 
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testAdminQueryLogNoResults() throws ServiceFault, DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("adminlog"); 

    // Insert three rows in log table
    TestDbUtils.writeToAdminLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 1, 1, 0, 0, 0, 0, 0);
    XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 1, 2, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    criteriatype.setEndTime(end);
    criteriatype.setLogType("seurantaloki");

    LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);
    
    // Verify operation result from DB
    assertEquals(entriestype.getLogEntry().size(), 0);
  }
  
  /**
   * Tests a query with a wrong kind of criteria, should fail 
   * @throws ServiceFault
   * @throws DatatypeConfigurationException 
   */
  @Test
  public void testAdminQueryLogFail() throws DatatypeConfigurationException {
    LogServicePortType port = getLogService();
    AuditInfoType audit = getAudit("adminlog"); 

    // Insert three rows in log table
    TestDbUtils.writeToAdminLogTable(jdbcTemplate);
    // set the query criteria
    LogQueryCriteriaType criteriatype = new LogQueryCriteriaType();
   
    // set for query start date 2009-01-01 and end date 2011-11-11
    XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(2010, 1, 1, 0, 0, 0, 0, 0);
    criteriatype.setStartTime(start);
    // do not set end time for criteria
    criteriatype.setLogType("seurantaloki");

    try{
      LogEntriesType entriestype = port.opQueryLog(criteriatype, audit);

      // Verify operation result from DB
      assertEquals(entriestype.getLogEntry().size(), 0);
      fail();
    } catch (ServiceFault sf){
      assertEquals(sf.getFaultInfo().getMessage(), "Invalid query criteria");
    }
  }
 
  /**
  * Tests the archive operation
  * @throws ServiceFault
  * @throws DatatypeConfigurationException 
  */
 @Test
 public void testArchiveLog() throws DatatypeConfigurationException {
   LogServicePortType port = getLogService();
   AuditInfoType audit = getAudit("log"); 

   // Insert three rows in log table
   TestDbUtils.writeToLogTable(jdbcTemplate);
  
   // set archive date 2011-01-01
   XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(2011, 1, 1, 0, 0, 0, 0, 0);
   LogArchivalParametersType archiveParametersType = new LogArchivalParametersType();
   archiveParametersType.setEndDate(date);
   ArchivalResultsType archiveCount = null;
   
   try{
     // call to log archive service
     archiveCount = port.opArchiveLog(archiveParametersType, audit);
   }catch(ServiceFault sf){
     fail();
   }
   if(archiveCount != null){
     assertEquals(archiveCount.getLogEntryCount(), 2); 
   } else{
     fail();
   }
  }
  
 /**
  * Tests the archive operation when there is nothing to archive on a given date
  * @throws ServiceFault
  * @throws DatatypeConfigurationException 
  */
 @Test
 public void testArchiveLogNothingToArchive() throws DatatypeConfigurationException {
   LogServicePortType port = getLogService();
   AuditInfoType audit = getAudit("log"); 

   // Insert three rows in log table
   TestDbUtils.writeToLogTable(jdbcTemplate);
  
   // set archive date 2009-01-01
   XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(2009, 1, 1, 0, 0, 0, 0, 0);
   LogArchivalParametersType archiveParametersType = new LogArchivalParametersType();
   archiveParametersType.setEndDate(date);
   ArchivalResultsType archiveCount = null;
   
   try{
     // call to log archive service
     archiveCount = port.opArchiveLog(archiveParametersType, audit);
   }catch(ServiceFault sf){
     fail();
   }
   if(archiveCount != null){
     assertEquals(archiveCount.getLogEntryCount(), 0); 
   } else{
     fail();
   }
  }
 
 /**
  * Tests the archive operation when the archive date is wrong
  * @throws ServiceFault
  * @throws DatatypeConfigurationException 
  */
 @Test
 public void testArchiveLogWrongArchiveDate() throws DatatypeConfigurationException {
   LogServicePortType port = getLogService();
   AuditInfoType audit = getAudit("log"); 

   // Insert three rows in log table
   TestDbUtils.writeToLogTable(jdbcTemplate);
  
   // set archive date 2014-01-01
   XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(2014, 1, 1, 0, 0, 0, 0, 0);
   LogArchivalParametersType archiveParametersType = new LogArchivalParametersType();
   archiveParametersType.setEndDate(date);
   
   try{
     // call to log archive service
     port.opArchiveLog(archiveParametersType, audit);
     fail();
   }catch(ServiceFault sf){
     assertEquals(sf.getFaultInfo().getMessage(), "Invalid archive date");
   }
  }
 
 /* lisätestit:
 /* -query admin logiin kirjataan logiin
  * -query logiin kirjataan admin logiin
  * -archive kirjataan admin logiin
  */
 
  private LogServicePortType getLogService() {
    LogServiceFactory logServiceFactory = new LogServiceFactory(
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_USERNAME),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_PWD),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_LOK_SRV_ENDPOINT_ADDRESS));
    return logServiceFactory.getLogService();
  }
  
  private AuditInfoType getAudit(String logtype) {
    AuditInfoType audit = new AuditInfoType();
    audit.setComponent("lok"); 
    if("log".equals(logtype)){
      audit.setUserId("101010-1010"); // pic from the session    
    } else if("adminlog".equalsIgnoreCase(logtype)){
      audit.setUserId("121212-1212");
    }
   
    return audit;
  }  
  
  
}
