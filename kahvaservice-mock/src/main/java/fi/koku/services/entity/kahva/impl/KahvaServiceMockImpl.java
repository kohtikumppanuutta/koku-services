/**
 * Simple mock for Kahva service
 */
package fi.koku.services.entity.kahva.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.tampere.hrsoa.entity.User;

/**
 * @author mikkope
 * 
 */
public class KahvaServiceMockImpl {

  private final Logger LOG = LoggerFactory.getLogger(KahvaServiceMockImpl.class);

  public KahvaServiceMockImpl() {
    // Initialize props
  }

  /**
   * Mock user data
   * #TODO# Probably "not found" should throw exception at some point
   * 
   * @param id userId (from portal in this case) that is a key in properties file
   * @return Returns mock user data read from properties file. If match not found return empty User object
   */
  
  public User getUserById(String id) {
    Properties props = load("/getUsersByIdMock.properties");

    LOG.info("Currently supported (and required) user information: userId,ssn,firstName,lastName,email. "
        + "Example row: toivo.toivola=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi");

    User u = new User();
    if (props != null) {
      LOG.info("props=" + props.toString());
      String property = props.getProperty(id).trim(); // id is key in properties
      LOG.info("used property=" + property);
      if (property != null && !"".equals(property)) {

        //Put values from props-file to User object
        String[] p = property.split(",");
        u.setUserId(p[0]);
        u.setSsn(p[1]);

        u.setFirstName(p[2]);
        u.setLastName(p[3]);
        u.setEmail(p[4]);
      }
    }
    return u;
  }
  
  
    public User getUserByPic(String pic) {
		Properties props = load("/getUsersByPicMock.properties");

		LOG.info("Currently supported (and required) user information: userId,ssn,firstName,lastName,email. "
			+ "Example row: 111111-1111=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi");

		User u = new User();
		if (props != null) {
		  LOG.info("props=" + props.toString());
		  String property = props.getProperty(pic).trim(); // id is key in properties
		  LOG.info("used property=" + property);
		  if (property != null && !"".equals(property)) {

			//Put values from props-file to User object
			String[] p = property.split(",");
			u.setUserId(p[0]);
			u.setSsn(p[1]);

			u.setFirstName(p[2]);
			u.setLastName(p[3]);
			u.setEmail(p[4]);
		  }
		}
		return u;
  }

  private Properties load(String propsName) {
    LOG.info("Trying to load properties propsName=" + propsName);
    Properties props = new Properties();
    try {
      URL in = this.getClass().getClassLoader().getResource(propsName);
      props.load(in.openStream());

    } catch (IOException e) {
      LOG.error("Failed to load properties file with propsName="+propsName);
    }
    return props;
  }

}
