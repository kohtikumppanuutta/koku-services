package fi.koku.services.test.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

/**
 * 
 * Some DB related operations.
 * 
 * @author laukksa
 * @author makinsu
 *
 */
public class TestDbUtils {

  private TestDbUtils() {
  }
  
  public static JdbcTemplate getJdbcTemplateInstance() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl(TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_DS_URL));
    dataSource.setUsername(TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_DS_USERNAME));
    dataSource.setPassword(TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_DS_PWD));
    
    return new JdbcTemplate(dataSource);    
  }
  
  public static void executeSqlScriptFromClasspath(String resourcePath, JdbcTemplate template){
    Resource classpathResource = new ClassPathResource(resourcePath);
    SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(template.getDataSource());
    SimpleJdbcTestUtils.executeSqlScript(simpleJdbcTemplate, classpathResource, false);
  }
  
  public static void deleteFromAllTables(JdbcTemplate template) {
    executeSqlScriptFromClasspath("/scripts/delete-from-all-tables.sql", template);
  }
  
  public static void writeToLogTable(JdbcTemplate template){
    executeSqlScriptFromClasspath("/scripts/insert-to-log.sql", template);
  }
  
  public static void writeToAdminLogTable(JdbcTemplate template){
    executeSqlScriptFromClasspath("/scripts/insert-to-admin-log.sql", template);
  }
  
}
