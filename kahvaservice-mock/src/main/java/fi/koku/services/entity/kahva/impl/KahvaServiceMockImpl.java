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
    
  public KahvaServiceMockImpl(){
    //Initialize props
  }
  
  
  public User getUserById(String id){
    Properties props = load("getUsersByIdMock");
    
    LOG.info("Currently supported (and required) user information: userId,ssn,firstName,lastName,email. " +
    		"Example row: toivo.toivola=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi");

    User u = new User();
    if(props!=null){
    String property = props.getProperty(id).trim(); //id is key in properties
    String[] p = property.split(",");
    
    u.setUserId(p[0]);
    u.setSsn(p[1]);
    
    u.setFirstName(p[2]);
    u.setLastName(p[3]);
    u.setEmail(p[4]);
    }
    return u;
  }
  
  private Properties load(String propsName)  {
    Properties props = new Properties();
    try {
      URL url = ClassLoader.getSystemResource(propsName);
      props.load(url.openStream());
    } catch (IOException e) {
        LOG.error("Failed to load properties file with propsName="+propsName);
    }
    return props;
}
  
}
