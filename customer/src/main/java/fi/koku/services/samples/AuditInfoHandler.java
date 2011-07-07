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

import fi.koku.services.common.v1.AuditInfoType;
import fi.koku.services.entity.customer.impl.Constants;

/**
 * Parse and process SOAP header and pass a message to the service endpoint implementation.
 *
 * TODO: SOAP mustUnderstand processing
 * 
 * @author aspluma
 */
public class AuditInfoHandler implements SOAPHandler<SOAPMessageContext> {

  private JAXBContext jaxbContext;
  private static final Set<QName> headers = createHeaders();

  @PostConstruct
  public void init() {
    try {
      jaxbContext = JAXBContext.newInstance(fi.koku.services.common.v1.AuditInfoType.class);
    } catch (JAXBException e) {
      System.out.println("error: "+e.getMessage());
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
    System.out.println("PreDestroy");
  }
  
  @Override
  public void close(MessageContext msgCtx) {
    System.out.println("close");
  }

  @Override
  public boolean handleFault(SOAPMessageContext msgCtx) {
    System.out.println("handleFault");
    return true;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext msgCtx) {
    System.out.println("handleMessage");
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
        System.out.println("h: "+h.getNodeName());
        if(!Constants.NAME_AUDIT_INFO.equals(h.getElementQName()))
          continue;
          
        JAXBElement<AuditInfoType> a = jaxbContext.createUnmarshaller().unmarshal(h, AuditInfoType.class);
        System.out.println("audit: "+a+", "+a.getValue().getComponent());
        
        msgCtx.put("myownmsg", "hello, world");
        msgCtx.setScope("myownmsg", MessageContext.Scope.APPLICATION);
      }
      System.out.println("headers processed");
    } catch (SOAPException e) {
      System.out.println("soapexception: "+e.getMessage());
      e.printStackTrace();
    } catch (JAXBException e) {
      System.out.println("jaxb error: "+e.getMessage());
      e.printStackTrace();
    }
    
    return true;
  }

  @Override
  public Set<QName> getHeaders() { 
    System.out.println("getHeaders");
    return headers;
  }
  
}
