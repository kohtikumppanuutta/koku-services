/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.samples;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.customer.impl.Constants;
import fi.koku.services.entity.customer.v1.AuditInfoType;

/**
 * Parse and process SOAP header and pass a message to the service endpoint implementation.
 *
 * TODO: SOAP mustUnderstand processing
 * 
 * @author aspluma
 */
public class AuditInfoHandler implements SOAPHandler<SOAPMessageContext> {
  private Logger logger = LoggerFactory.getLogger(AuditInfoHandler.class);
  private JAXBContext jaxbContext;
  private static final Set<QName> headers = createHeaders();

  @PostConstruct
  public void init() {
    try {
      jaxbContext = JAXBContext.newInstance(AuditInfoType.class);
    } catch (JAXBException e) {
      logger.error("error: "+e.getMessage(), e);
      throw new RuntimeException("failed to create JAXBContext", e);
    }
  }
  
  private static Set<QName> createHeaders() {
    Set<QName> hdrs = new HashSet<QName>();
    hdrs.add(Constants.NAME_AUDIT_INFO);
    return hdrs;
  }
  

  @PreDestroy
  public void destroy() {
    logger.info("PreDestroy");
  }
  
  @Override
  public void close(MessageContext msgCtx) {
    logger.info("close");
  }

  @Override
  public boolean handleFault(SOAPMessageContext msgCtx) {
    logger.info("handleFault");
    return true;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext msgCtx) {
    logger.info("handleMessage");
    Boolean isOutgoing = (Boolean) msgCtx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if(isOutgoing)
      return true;
    
    SOAPMessage msg = msgCtx.getMessage();
    try {
      SOAPHeader hdr = msg.getSOAPHeader();
      if(hdr == null)
        return true;
      
      for(Iterator<?> i = hdr.extractAllHeaderElements(); i.hasNext(); ) {
        SOAPHeaderElement h = (SOAPHeaderElement)i.next();
        logger.info("h: "+h.getNodeName());
        if(!Constants.NAME_AUDIT_INFO.equals(h.getElementQName()))
          continue;
          
        JAXBElement<AuditInfoType> a = jaxbContext.createUnmarshaller().unmarshal(h, AuditInfoType.class);
        logger.info("audit: "+a+", "+a.getValue().getComponent());
        
        msgCtx.put("myownmsg", "hello, world");
        msgCtx.setScope("myownmsg", MessageContext.Scope.APPLICATION);
      }
      logger.info("headers processed");
    } catch (SOAPException e) {
      logger.error("soapexception: "+e.getMessage(), e);
    } catch (JAXBException e) {
      logger.error("jaxb error: "+e.getMessage(), e);
      e.printStackTrace();
    }
    
    return true;
  }

  @Override
  public Set<QName> getHeaders() { 
    logger.info("getHeaders");
    return headers;
  }
  
}
