package fi.koku.services.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * TestPropertiesUtil for integration tests.
 * 
 * @author Ixonos / laukksa
 *
 */
public class TestPropertiesUtil {

  private static Properties properties = new Properties();
  
  private static Properties defaultProps = new Properties();
  
  public static final String KOKU_SRV_LAYER_ENDPOINT_ADDRESS = "koku.srv.layer.endpoint.address";
  
  // Default values are defined here (CI environment uses these)
  static {
    defaultProps.setProperty(KOKU_SRV_LAYER_ENDPOINT_ADDRESS, "http://62.61.65.15:8180");
  }
  
  static {
    InputStream is = null;
    try {
      is = TestPropertiesUtil.class.getClassLoader().getResourceAsStream("koku-test.properties");
      if (is != null) {
        properties.load(is);
      } else {
        // Cannot read properties file, use default values
        properties = defaultProps;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          // Ignore this
        }
      }
    }
  }
  
  /**
   * Get property.
   * 
   * @param key
   * @return value of property
   */
  public static String getProperty(String key) {
    return properties.getProperty(key);
  }
}
