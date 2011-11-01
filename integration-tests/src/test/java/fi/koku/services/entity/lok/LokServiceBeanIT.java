package fi.koku.services.entity.lok;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.log.v1.ServiceFault;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.test.util.TestDbUtils;
import fi.koku.services.test.util.TestPropertiesUtil;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogServiceFactory;
import fi.koku.services.utility.log.v1.LogServicePortType;

/**
 * LokServiceBeanIT.
 * 
 * @author makinsu
 *
 */
public class LokServiceBeanIT {

  JdbcTemplate jdbcTemplate = TestDbUtils.getJdbcTemplateInstance();
  
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
    AuditInfoType audit = getAudit();

    LogEntriesType logEntries = new LogEntriesType();
    
    LogEntryType logEntryType = new LogEntryType();
    logEntryType.setClientSystemId("kks");
    logEntryType.setCustomerPic("123456-123A");
    logEntryType.setDataItemId("it-id");
    logEntryType.setDataItemType("it-type");
    logEntryType.setMessage("Integration test log");
    logEntryType.setOperation("Test");
    logEntryType.setTimestamp(CalendarUtil.getXmlDateTime(new Date()));
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
  }
  
  private LogServicePortType getLogService() {
    LogServiceFactory logServiceFactory = new LogServiceFactory(
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_USERNAME),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_PWD),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_LOK_SRV_ENDPOINT_ADDRESS));
    return logServiceFactory.getLogService();
  }
  
  private AuditInfoType getAudit() {
    AuditInfoType audit = new AuditInfoType();
    audit.setComponent("kks");
    audit.setUserId("integration-test");
    return audit;
  }  
}
