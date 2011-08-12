package fi.koku.services.entity.customer;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Example JUnit test with Hamcrest matchers.
 * 
 * @author Ixonos / laukksa
 *
 */
public class CustomerServiceBeanTest {

  @Test
  public void testExample() {
    boolean flag = true;
    assertThat("Flag must be true", flag, is(true));
    assertThat("Flag must not be false", flag, not(false));
    
    final String name = null;
    assertThat("Name must be null", name, nullValue());
  }
}
